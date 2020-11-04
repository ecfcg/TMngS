package tmngs.test;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.seasar.doma.jdbc.tx.LocalTransaction;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import tmngs.db.config.DbConfig;
import tmngs.test.dbunit.OperationUtil;

/**
 * DB関連テスト用の基底クラス
 */
public abstract class AbstractDataBaseTestBase {
  private final DbConfig config = DbConfig.singleton();
  private final LocalTransactionManager tm =
      (LocalTransactionManager) config.getTransactionManager();
  private final LocalTransaction tx = tm.getTransaction();

  /**
   * 各テストメソッドの前処理. <br>
   * テストデータを投入後、前処理を実行する.
   */
  @BeforeEach
  public final void setUpDataBase() {
    tx.begin();
    cleanInsertList().forEach(OperationUtil::cleanInsertCsv);
    insertList().forEach(OperationUtil::insertCsv);
    setUpEach();
  }

  /**
   * 各テストの前にclean insertするテストデータが格納されているディレクトリを返却する.
   * 
   * @return 各テストの前にclean insertするテストデータが格納されているディレクトリ
   */
  protected List<Path> cleanInsertList() {
    return Collections.emptyList();
  }

  /**
   * 各テストの前にinsertするテストデータが格納されているディレクトリを返却する.
   * 
   * @return 各テストの前にinsertするテストデータが格納されているディレクトリ
   */
  protected List<Path> insertList() {
    return Collections.emptyList();
  }

  /**
   * DBにデータを投入後に行う処理を記述する.
   */
  protected void setUpEach() {}

  /**
   * テスト後にトランザクションをrollbackする.
   */
  @AfterEach
  public final void tearDownDataBase() {
    tx.rollback();
  }
}
