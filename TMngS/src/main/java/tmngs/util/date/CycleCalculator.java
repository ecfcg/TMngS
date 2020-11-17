package tmngs.util.date;

import java.time.LocalDate;
import tmngs.dto.AdjustedDate;

/**
 * 周期計算を行う
 */
public class CycleCalculator {

  /** 休日判定 */
  private final HolidayJudger holidayJudger;

  /**
   * @param holidayJudger
   */
  private CycleCalculator(HolidayJudger holidayJudger) {
    this.holidayJudger = holidayJudger;
  }

  /**
   * 新しいインスタンスを作成する.
   * 
   * @return 新しいインスタンス
   */
  public static CycleCalculator create(HolidayJudger holidayJudger) {
    return new CycleCalculator(holidayJudger);
  }

  /**
   * 基準日の翌サイクルの日付を計算する.<br>
   * 区分に応じた日付を取得する.
   * 
   * @param baseDate 基準日
   * @param cycle サイクル月数
   * @param montylyAdjustType 月次調整区分
   * @param holidayAdjustType 休日調整区分
   * @return 調整前後の日付
   */
  public AdjustedDate calcNextCycleByMonth(LocalDate baseDate, int cycle,
      MonthlyCycle montylyAdjustType, HolidayAdjustment holidayAdjustType) {

    LocalDate nextBaseDate = montylyAdjustType.calcNext(baseDate, cycle);
    return new AdjustedDate(nextBaseDate, holidayAdjustType.adjust(nextBaseDate, holidayJudger));
  }
}
