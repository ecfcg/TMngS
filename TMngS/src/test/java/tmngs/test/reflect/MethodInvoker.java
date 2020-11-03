package tmngs.test.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 単体テスト時に不可視メソッドを実行するラッパー
 */
public interface MethodInvoker {

  /**
   * 不可視のインスタンスメソッドを実行する.
   * 
   * @param <R> 戻り値の型
   * @param obj 実行するインスタンス
   * @param methodName メソッド名
   * @param clazz 対象クラス
   * @param returnClass 戻り値の型
   * @param paramClasses 引数の型
   * @param params 引数
   * @return 実行したメソッドの戻り値
   */
  static <R> R invoke(Object obj, String methodName, Class<?> clazz, Class<R> returnClass,
      Class<?>[] paramClasses, Object[] params) {
    Method m;
    try {
      m = clazz.getDeclaredMethod(methodName, paramClasses);
      m.setAccessible(true);
      @SuppressWarnings("unchecked")
      R result = (R) m.invoke(obj, params);
      return result;
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      // 例外は無視する
      throw new RuntimeException(e);
    }
  }

  /**
   * 不可視の静的メソッドを実行する.
   * 
   * @param <R> 戻り値の型
   * @param methodName メソッド名
   * @param clazz 対象クラス
   * @param returnClass 戻り値の型
   * @param paramClasses 引数の型
   * @param params 引数
   * @return 実行したメソッドの戻り値
   */
  static <R> R invoke(String methodName, Class<?> clazz, Class<R> returnClass,
      Class<?>[] paramClasses, Object[] params) {
    return invoke(null, methodName, clazz, returnClass, paramClasses, params);
  }

  /**
   * 不可視のインスタンスメソッドを実行する.
   * 
   * @param obj 実行するインスタンス
   * @param methodName メソッド名
   * @param clazz 対象クラス
   * @param paramClasses 引数の型
   * @param params 引数
   */
  static void invoke(Object obj, String methodName, Class<?> clazz, Class<?>[] paramClasses,
      Object[] params) {
    Method m;
    try {
      m = clazz.getDeclaredMethod(methodName, paramClasses);
      m.setAccessible(true);
      m.invoke(obj, params);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      // 例外は無視する
      throw new RuntimeException(e);
    }
  }

  /**
   * 不可視の静的メソッドを実行する.
   * 
   * @param methodName メソッド名
   * @param clazz 対象クラス
   * @param paramClasses 引数の型
   * @param params 引数
   */
  static void invoke(String methodName, Class<?> clazz, Class<?>[] paramClasses, Object[] params) {
    invoke(null, methodName, clazz, paramClasses, params);
  }
}
