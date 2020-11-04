package tmngs.type;

/**
 * 休日調整区分
 */
public enum HolidayAdjustType {
  /** 調整なし */
  NONE,
  /** 前営業日 */
  BEFORE_BUSINESS_DAY,
  /** 翌営業日 */
  AFTER_BUSINESS_DAY,
  /** 月初営業日 */
  FIRST_BUSINESS_DAY,
  /** 最終営業日 */
  LAST_BUSINESS_DAY;
}
