package tmngs.db.config;

import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.PostgresDialect;

public interface DbSetting {
  /**
   * DataSourceを取得する.
   * 
   * @return DataSource
   */
  static LocalTransactionDataSource dataSource() {
    // TODO JNDIより取得に変更する
    PGSimpleDataSource ds = new PGSimpleDataSource();
    ds.setServerName("localhost");
    ds.setPortNumber(5432);
    ds.setUser("tmngs");
    ds.setPassword("tmngs");
    ds.setCurrentSchema("tmngs");
    return new LocalTransactionDataSource(ds);
  }

  /**
   * Dialectを取得する.
   * 
   * @return Dialect
   */
  static Dialect dialect() {
    return new PostgresDialect();
  }
}
