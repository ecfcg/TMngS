package tmngs.util.date;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * 週次サイクル
 */
public interface WeeklyCycle {
  /**
   * 基準日の翌サイクルの日付を取得する.
   * 
   * @param baseDate 基準日
   * @param cycle サイクル日数
   * @return 翌サイクルの日付
   */
  public static LocalDate nextCycle(LocalDate baseDate, int cycle) {
    return baseDate.plusWeeks(cycle);
  }

  /**
   * 基準日の直近の対象曜日の日付を取得する
   * 
   * @param baseDate 基準日
   * @param dayOfWeek 対象曜日
   * @param containsBaseDate 基準日が対象曜日の場合基準日とするならtrue
   * @return 基準日の直近の対象曜日の日付
   */
  public static LocalDate nextWeekDay(LocalDate baseDate, DayOfWeek dayOfWeek,
      boolean containsBaseDate) {
    var base = containsBaseDate ? baseDate : baseDate.plusDays(1);
    while (true) {
      if (dayOfWeek.equals(base.getDayOfWeek())) {
        return base;
      }
      base = base.plusDays(1);
    }
  }
}
