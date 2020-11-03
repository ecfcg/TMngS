package tmngs.test;

import java.nio.file.Path;

/**
 * テストデータへのパスを管理する
 */
public interface TestDataManager {
  /**
   * クラス名からtest/dataへのパスを返却する.
   * 
   * @param className 完全修飾クラス名
   * @return test/resourceパス
   */
  static Path getTestDataDir(String className) {
    return Path.of("src/test/data", className.replace('.', '/'));
  }

  /**
   * テストクラスのtest/dataパスを返却する
   * 
   * @return test/resourceパス
   */
  static Path getTestDataDir() {
    var stackTrace = Thread.currentThread().getStackTrace();
    int callerIndex = -1;
    for (int i = 0; i < stackTrace.length; i++) {
      var className = stackTrace[i].getClassName();
      if (className.equals(TestDataManager.class.getName())) {
        callerIndex = i + 1;
        break;
      }
    }
    return getTestDataDir(stackTrace[callerIndex].getClassName());
  }

  /**
   * テストクラスの期待値格納ディレクトリへのパスを返却する
   * 
   * @return 期待値格納ディレクトリへのパス
   */
  static Path getExpectedDataDir() {
    return getTestDataDir().resolve("expected");
  }
}
