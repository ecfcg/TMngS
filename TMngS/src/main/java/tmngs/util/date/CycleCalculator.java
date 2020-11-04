package tmngs.util.date;

import java.time.LocalDate;
import java.time.Month;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import tmngs.dto.AdjustedDate;
import tmngs.type.HolidayAdjustType;
import tmngs.type.MonthlyAdjustType;

/**
 * 周期計算を行う
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CycleCalculator {

  /** 休日判定 */
  private final HolidayJudger holidayJudger = HolidayJudger.create();

  /**
   * 新しいインスタンスを作成する.
   * 
   * @return 新しいインスタンス
   */
  public static CycleCalculator create() {
    return new CycleCalculator();
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
      MonthlyAdjustType montylyAdjustType, HolidayAdjustType holidayAdjustType) {
    LocalDate nextBaseDate = baseDate.plusMonths(cycle);

    switch (montylyAdjustType) {
      case BASE_DAY:
        // 何もしない
        break;
      case FIRST_DAY_OF_THE_MONTH:
        nextBaseDate = LocalDate.of(nextBaseDate.getYear(), nextBaseDate.getMonthValue(), 1);
        break;
      case END_OF_MONTH:
        nextBaseDate = LocalDate.of(nextBaseDate.getYear(), nextBaseDate.getMonthValue(),
            nextBaseDate.lengthOfMonth());
        break;
    }

    return AdjustedDate.of(nextBaseDate,
        adjustByHoliday(nextBaseDate, nextBaseDate.getMonth(), holidayAdjustType));
  }

  /**
   * 基準日に対して休日調整を行った日付を返却する.
   * 
   * @param baseDate 基準日
   * @param nowMonth 現在の月
   * @param holidayAdjustType 休日調整区分
   * @return 休日調整後の日付
   */
  private LocalDate adjustByHoliday(LocalDate baseDate, Month nowMonth,
      HolidayAdjustType holidayAdjustType) {
    if (HolidayAdjustType.NONE.equals(holidayAdjustType) || !holidayJudger.isHoliday(baseDate)) {
      // 調整不要, もしくは休日ではない
      return baseDate;
    }

    return null;
  }
}
