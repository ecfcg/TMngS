package tmngs.util.date;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * 月次サイクル
 */
public enum MonthlyCycle {
  /** 基準日 */
  BASE_DAY(Function.identity()),
  /** 月初日 */
  FIRST_DAY_OF_THE_MONTH(d -> LocalDate.of(d.getYear(), d.getMonth(), 1)),
  /** 月末 */
  END_OF_MONTH(d -> LocalDate.of(d.getYear(), d.getMonth(), 31));

  /** 日を調整する関数 */
  private final Function<LocalDate, LocalDate> adjuster;

  /**
   * @param adjuster
   */
  private MonthlyCycle(Function<LocalDate, LocalDate> adjuster) {
    this.adjuster = adjuster;
  }

  /**
   * 基準日の翌サイクルの日付を取得する.
   * 
   * @param baseDate 基準日
   * @param cycle サイクル月数
   * @return 基準日のサイクル月後の日付
   */
  public LocalDate calcNext(LocalDate baseDate, int cycle) {
    return adjuster.apply(baseDate.plusMonths(cycle));
  }
}
