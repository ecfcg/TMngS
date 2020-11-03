package tmngs.test.dbunit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.seasar.doma.Column;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 * dtoのコレクションをIDataSetに変換する
 */
public interface DtoConverter {

  /**
   * dtoのコレクションをIDataSetに変換する
   * 
   * @param dtos 変換するコレクション
   * @return 変換後のIDataSet
   */
  static IDataSet dtosToDataSet(Collection<?> dtos) {
    if (dtos.isEmpty()) {
      throw new IllegalArgumentException("Augument is empty collection.");
    }

    var clazz = dtos.iterator().next().getClass();

    // テーブル情報を取得
    var tableAnnotation = clazz.getAnnotation(Table.class);
    if (tableAnnotation == null) {
      throw new IllegalArgumentException("Augument is not entity collection.");
    }

    // 各カラムのフィールドを取得
    Map<String, Field> columnFields =
        Stream.of(clazz.getDeclaredFields()).filter(f -> Objects.nonNull(getColumnAnnotation(f)))
            .collect(Collectors.toMap(f -> getColumnAnnotation(f).name(), Function.identity()));

    if (columnFields.isEmpty()) {
      throw new IllegalArgumentException("Augument is not entity collection.");
    }

    Map<String, Method> columnAccessors = getColumnAccessors(clazz, columnFields.keySet());
    try {
      return new DefaultDataSet(toITable(dtos, columnAccessors,
          createTableMetaData(tableAnnotation.name(), columnFields)));
    } catch (AmbiguousTableNameException e) {
      throw new IllegalArgumentException(
          "This dataset have the table of same name:" + tableAnnotation.name());
    }
  }

  /**
   * Columnアノテーションを取得する.<br>
   * 存在しなければnullを返却する
   * 
   * @param f 取得するFieldオブジェクト
   * @return Columnアノテーション
   */
  private static Column getColumnAnnotation(Field f) {
    return f.getAnnotation(Column.class);
  }

  /**
   * 対象テーブルの各カラム名に対応したアクセサの一覧を取得する.
   * 
   * @param clazz 対象テーブルのEntitiyクラス情報
   * @param columnNames 対象テーブルのカラム名の一覧
   * @return 各Columnに対応したアクセサの一覧
   */
  private static Map<String, Method> getColumnAccessors(Class<?> clazz,
      Collection<String> columnNames) {
    Map<String, Method> columnAccessors = new HashMap<>(columnNames.size());
    for (String columnName : columnNames) {
      var accessorName = String.join("", "get", snakeToPascal(columnName));
      try {
        Method m = clazz.getMethod(accessorName);
        columnAccessors.put(columnName, m);
      } catch (NoSuchMethodException | SecurityException e) {
        throw new IllegalArgumentException(
            "class:" + clazz.getName() + " column name:" + columnName);
      }
    }
    return columnAccessors;
  }

  /**
   * スネークケースの文字列をパスカルケースに変換する.
   * 
   * @param snakeStr スネークケースの文字列
   * @return パスカルケースの文字列
   */
  private static String snakeToPascal(String snakeStr) {
    if (snakeStr == null || snakeStr.isBlank()) {
      throw new IllegalArgumentException("Augument is null or blank.");
    }
    return Stream.of(snakeStr.split("_")).map(DtoConverter::toInitCaps)
        .collect(Collectors.joining());
  }

  /**
   * 先頭を大文字、それ以外を小文字に変換するに変換する。<br>
   * 空文字列が渡された場合の挙動は考慮しない。
   * 
   * @param src 変換前の文字列
   * @return 変換後の文字列
   */
  private static String toInitCaps(String src) {
    if (src.length() == 1) {
      return src.toUpperCase();
    }
    var first = src.substring(0, 1).toUpperCase();
    var other = src.substring(1).toLowerCase();
    return String.join("", first, other);
  }

  /**
   * 対象テーブルのITableMetaDataを作成する
   * 
   * @param tableName テーブル名
   * @param columnFields 対象テーブルEntityクラスのカラムフィールド情報
   * @return 対象テーブルのITableMetaData
   */
  private static ITableMetaData createTableMetaData(String tableName,
      Map<String, Field> columnFields) {

    List<org.dbunit.dataset.Column> columns = columnFields.entrySet().stream()
        .map(e -> new org.dbunit.dataset.Column(e.getKey(), resolveDataType(e.getValue())))
        .collect(Collectors.toList());

    List<String> ids = columnFields.entrySet().stream()
        .filter(e -> Objects.nonNull(e.getValue().getAnnotation(Id.class))).map(e -> e.getKey())
        .collect(Collectors.toList());

    org.dbunit.dataset.Column[] columnArray = new org.dbunit.dataset.Column[columns.size()];
    String[] idArray = new String[ids.size()];
    return new DefaultTableMetaData(tableName, columns.toArray(columnArray), ids.toArray(idArray));
  }

  /**
   * フィールドのクラスをDBUnitのDataTypeに解決する
   * 
   * @param f 対象のフィールド
   * @return 解決したDataType
   */
  private static DataType resolveDataType(Field f) {
    Class<?> fieldType = f.getType();
    if (fieldType.equals(Boolean.class)) {
      return DataType.BOOLEAN;
    }
    return DataType.UNKNOWN;
  }

  /**
   * DTOの内容をITableに格納する
   * 
   * @param dtos 格納するDTO
   * @param columnAccessors 各Columnに対応したアクセサの一覧
   * @param metaData ITableのITableMetaData
   * @return DTOの内容を格納したITable
   */
  private static ITable toITable(Collection<?> dtos, Map<String, Method> columnAccessors,
      ITableMetaData metaData) {
    var table = new DefaultTable(metaData);
    try {
      for (Object dto : dtos) {
        var i = table.getRowCount();
        table.addRow();
        for (Entry<String, Method> entry : columnAccessors.entrySet()) {
          table.setValue(i, entry.getKey(), entry.getValue().invoke(dto));
        }
      }
    } catch (DataSetException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    return table;
  }
}
