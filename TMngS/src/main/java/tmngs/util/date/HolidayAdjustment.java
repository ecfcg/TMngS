package tmngs.util.date;

import java.time.LocalDate;

/**
 * 休日調整区分
 */
public enum HolidayAdjustment {
  /** 調整なし */
  NONE(DateAdjuster.NOT_ADJUST),
  /** 前営業日 */
  BEFORE_BUSINESS_DAY(DateAdjuster.PREVIOUS_DAY),
  /** 翌営業日 */
  AFTER_BUSINESS_DAY(DateAdjuster.FOLLOWING_DAY),
  /** 月初営業日 */
  FIRST_BUSINESS_DAY(DateAdjuster.PREVIOUS_DAY),
  /** 最終営業日 */
  LAST_BUSINESS_DAY(DateAdjuster.FOLLOWING_DAY);

  /** 休日調整時に利用する関数 */
  private final DateAdjuster standardAdjuster;

  /**
   * @param standardAdjuster
   */
  private HolidayAdjustment(DateAdjuster standardAdjuster) {
    this.standardAdjuster = standardAdjuster;
  }

  /**
   * @return standardAdjuster
   */
  public final DateAdjuster getStandardAdjuster() {
    return standardAdjuster;
  }

  /**
   * 休日調整後の日付を取得する.
   * 
   * @param target 調整対象の日付
   * @param holidayJudger 休日判定
   * @return 休日調整後の日付
   */
  public LocalDate adjust(LocalDate target, HolidayJudger holidayJudger) {
    if (HolidayAdjustment.NONE.equals(this) || !holidayJudger.isHoliday(target)) {
      // 調整不要, もしくは休日ではない
      return target;
    }

    var adjusted = target;
    var fixedMonth = target.getMonth();
    var adjuster = standardAdjuster;
    int i = 0;
    while (true) {
      i++;
      adjusted = adjuster.apply(adjusted);
      if ((HolidayAdjustment.FIRST_BUSINESS_DAY.equals(this)
          || HolidayAdjustment.LAST_BUSINESS_DAY.equals(this))
          && (!fixedMonth.equals(adjusted.getMonth()))) {
        // 月を跨ぎ、かつ月跨ぎが許されない場合は逆向きに再調整する
        adjuster = adjuster.getReverser();
        continue;
      }
      if (!holidayJudger.isHoliday(adjusted)) {
        // 休日でなければ終了.
        break;
      }
      if (100 < i) {
        // 100回以上調整しても平日が現れない場合は強制終了する.
        throw new RuntimeException("休日調整の回数が規定回数を超過しました。祝日の設定を見直してください。");
      }
      // 休日であれば調整を継続する.
    }
    return adjusted;
  }
}
