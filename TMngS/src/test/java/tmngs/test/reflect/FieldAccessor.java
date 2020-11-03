package tmngs.test.reflect;

import java.lang.reflect.Field;

/**
 * 単体テスト時に不可視のFieldにアクセスするラッパー
 */
public interface FieldAccessor {

  /**
   * 不可視のフィールドを取得する.
   * 
   * @param <T> フィールドの型
   * @param obj 取得元のインスタンス
   * @param fieldName フィールド名
   * @param clazz 対象クラス
   * @param fieldClass フィールドの型
   * @return 不可視のフィールド
   */
  static <T> T get(Object obj, String fieldName, Class<?> clazz, Class<T> fieldClass) {
    try {
      Field f = clazz.getDeclaredField(fieldName);
      f.setAccessible(true);
      @SuppressWarnings("unchecked")
      T result = (T) f.get(obj);
      return result;
    } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
      // 例外は無視する
      throw new RuntimeException(e);
    }
  }

  /**
   * 不可視のフィールドを取得する.
   * 
   * @param <T> フィールドの型
   * @param fieldName フィールド名
   * @param clazz 対象クラス
   * @param fieldClass フィールドの型
   * @return 不可視のフィールド
   */
  static <T> T get(String fieldName, Class<?> clazz, Class<T> fieldClass) {
    return get(null, fieldName, clazz, fieldClass);
  }

  /**
   * 不可視のフィールドに値を代入する.
   * 
   * @param obj 代入するインスタンス
   * @param fieldName フィールド名
   * @param clazz 対象クラス
   * @param item 代入する値
   */
  static void set(Object obj, String fieldName, Class<?> clazz, Object item) {
    try {
      Field f = clazz.getDeclaredField(fieldName);
      f.setAccessible(true);
      f.set(obj, item);
    } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
      // 例外は無視する
      throw new RuntimeException(e);
    }
  }

  /**
   * 不可視のフィールドに値を代入する.
   * 
   * @param fieldName フィールド名
   * @param clazz 対象クラス
   * @param item 代入する値
   */
  static void set(String fieldName, Class<?> clazz, Object item) {
    set(null, fieldName, clazz, item);
  }
}
