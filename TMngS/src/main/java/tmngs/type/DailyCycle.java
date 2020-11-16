package tmngs.type;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import tmngs.util.date.DateAdjuster;
import tmngs.util.date.HolidayAdjustment;
import tmngs.util.date.HolidayJudger;

/**
 * 日次サイクル
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum DailyCycle {
  /** 前日 */
  BEFORE_DAYS(HolidayAdjustment.NONE),
  /** 後日 */
  AFTER_DAYS(HolidayAdjustment.NONE),
  /** 前営業日 */
  BEFORE_BUSINESS_DAYS(HolidayAdjustment.BEFORE_BUSINESS_DAY),
  /** 翌営業日 */
  AFTER_BUSINESS_DAYS(HolidayAdjustment.AFTER_BUSINESS_DAY),
  /** 月初営業日 */
  FIRST_BUSINESS_DAY(HolidayAdjustment.FIRST_BUSINESS_DAY),
  /** 最終営業日 */
  LAST_BUSINESS_DAY(HolidayAdjustment.LAST_BUSINESS_DAY);

  /** 休日調整 */
  private final HolidayAdjustment holidayAdjustment;

  /**
   * 基準日の翌サイクルの日付を取得する.
   * 
   * @param baseDate 基準日
   * @param cycle サイクル日数
   * @param holidayJudger 休日判定
   * @return 次サイクルの日付
   */
  public LocalDate calcNext(LocalDate baseDate, int cycle, HolidayJudger holidayJudger) {
    DateAdjuster adjuster;
    switch (this) {
      case BEFORE_DAYS:
        adjuster = DateAdjuster.PREVIOUS_DAY;
        break;
      case AFTER_DAYS:
        adjuster = DateAdjuster.FOLLOWING_DAY;
        break;
      default:
        adjuster = holidayAdjustment.getStandardAdjuster();
    }

    var nextDate = baseDate;
    var fixedMonth = baseDate.getMonth();
    var cannotChangeMonth = FIRST_BUSINESS_DAY.equals(this) || LAST_BUSINESS_DAY.equals(this);
    for (var i = 0; i < cycle; i++) {
      var tmpDate = adjuster.apply(nextDate);
      if (cannotChangeMonth && !fixedMonth.equals(tmpDate.getMonth())) {
        return holidayAdjustment.adjust(nextDate, holidayJudger);
      }
      tmpDate = holidayAdjustment.adjust(tmpDate, holidayJudger);
      if (tmpDate.equals(nextDate)) {
        return nextDate;
      }
      nextDate = tmpDate;
    }
    return nextDate;
  }
}
