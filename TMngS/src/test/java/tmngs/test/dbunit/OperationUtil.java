package tmngs.test.dbunit;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.function.Function;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import tmngs.db.config.DbConfig;

/**
 * DBUnit関連ユーティリティ
 */
public interface OperationUtil {

  /**
   * csvファイルの内容をDBにInsertする
   * 
   * @param csvDirPath DBにInsertするcsvファイルが格納されているディレクトリ
   */
  static void insertCsv(Path csvDirPath) {
    excuteOperation(DatabaseOperation.INSERT, csvDirPath, FileConverter::dataSetFromCsv);
  }

  /**
   * csvファイルの内容をDBにclean Insertする
   * 
   * @param csvDirPath DBにInsertするcsvファイルが格納されているディレクトリ
   */
  static void cleanInsertCsv(Path csvDirPath) {
    excuteOperation(DatabaseOperation.CLEAN_INSERT, csvDirPath, FileConverter::dataSetFromCsv);
  }

  /**
   * csvファイルに記載されているテーブルをtruncateする
   * 
   * @param csvDirPath truncateするテーブルが記載されているcsvファイルが格納されているディレクトリ
   */
  static void truncateCsv(Path csvDirPath) {
    excuteOperation(DatabaseOperation.TRUNCATE_TABLE, csvDirPath, FileConverter::dataSetFromCsv);
  }

  /**
   * DBコネクションを取得する.
   * 
   * @return DBコネクション
   */
  private static IDatabaseConnection getConnection() {
    try {
      return new DatabaseDataSourceConnection(DbConfig.singleton().getDataSource(), "tmngs");
    } catch (SQLException e) {
      // テスト時の例外は無視する.
      throw new RuntimeException(e);
    }
  }

  /**
   * 指定したオペレーションを実行する
   * 
   * @param ope 実行するオペレーション
   * @param fisePath オペレーションを実行する対象へのパス
   * @param fileToIDataSet 指定したパスをIDataSetに変換する関数
   */
  private static void excuteOperation(DatabaseOperation ope, Path fisePath,
      Function<Path, IDataSet> fileToIDataSet) {
    try {
      ope.execute(getConnection(), fileToIDataSet.apply(fisePath));
    } catch (DatabaseUnitException | SQLException e) {
      // テスト時の例外は無視する.
      throw new RuntimeException(e);
    }
  }
}
