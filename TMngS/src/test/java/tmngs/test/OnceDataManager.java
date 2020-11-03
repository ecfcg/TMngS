package tmngs.test;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import tmngs.db.config.DbConfig;
import tmngs.test.dbunit.OperationUtil;

/**
 * テスト実行前に一度だけ投入するデータを管理する.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OnceDataManager {
  /** テスト実行前に一度だけ投入するデータが格納されているディレクトリの一覧. */
  private final List<Path> csvDirs;

  /**
   * OnceDataManagerを作成する.
   * 
   * @param csvDirs テスト実行前に一度だけ投入するデータが格納されているディレクトリの一覧
   * @return OnceDataManager
   */
  public static OnceDataManager of(List<Path> csvDirs) {
    return new OnceDataManager(Collections.unmodifiableList(csvDirs));
  }

  /**
   * テストデータを投入してコミットする.
   */
  public void insert() {
    DbConfig.singleton().getTransactionManager()
        .required(() -> csvDirs.forEach(OperationUtil::cleanInsertCsv));
  }

  /**
   * テストデータを投入したテーブルをtruncateする.
   */
  public void truncate() {
    DbConfig.singleton().getTransactionManager()
        .required(() -> csvDirs.forEach(OperationUtil::truncateCsv));
  }
}
