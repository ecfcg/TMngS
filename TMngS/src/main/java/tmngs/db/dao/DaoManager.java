package tmngs.db.dao;

import tmngs.db.config.DbConfig;

/**
 * DAO生成ユーティリティ
 */
public interface DaoManager {
  /** Singleton config. */
  static final DbConfig CONFIG = DbConfig.singleton();

  static MHolidayDao createMHolidayDao() {
    return new MHolidayDaoImpl(CONFIG);
  }

  static MUserDao createMUserDao() {
    return new MUserDaoImpl(CONFIG);
  }
}
