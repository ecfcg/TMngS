package tmngs.db.config;

import javax.sql.DataSource;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.seasar.doma.jdbc.tx.TransactionManager;

/**
 * enumによるSingleton実装
 * 
 * @see org.seasar.doma.jdbc.Config
 */
public enum DbConfig implements Config {
  INSTANCE;

  private final Dialect dialect = DbSetting.dialect();;
  private final LocalTransactionDataSource dataSource = DbSetting.dataSource();;
  private final TransactionManager transactionManager =
      new LocalTransactionManager(dataSource.getLocalTransaction(getJdbcLogger()));;

  public static DbConfig singleton() {
    return INSTANCE;
  }

  @Override
  public final Dialect getDialect() {
    return dialect;
  }

  @Override
  public final DataSource getDataSource() {
    return dataSource;
  }

  @Override
  public final TransactionManager getTransactionManager() {
    return transactionManager;
  }
}
