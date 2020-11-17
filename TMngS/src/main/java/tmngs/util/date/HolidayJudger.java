package tmngs.util.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 対象の日付が休日か判定する.
 */
public class HolidayJudger {
  /** 休日一覧 */
  private final Set<LocalDate> holidays;

  /**
   * @param holidays
   */
  private HolidayJudger(Set<LocalDate> holidays) {
    this.holidays = holidays;
  }

  /**
   * インスタンスを生成する.
   * 
   * @return 新しいHolidayJudgerインスタンス
   */
  public static HolidayJudger create(Collection<LocalDate> holidays) {
    var unmodifiable = holidays.stream().collect(Collectors.toUnmodifiableSet());
    return new HolidayJudger(unmodifiable);
  }

  /**
   * 指定した日付が休日であるか判定する.<br>
   * 土曜日、日曜日、祝日のいずれかであればtrueを返却する.
   * 
   * @param target 判定対象の日付
   * @return 土曜日、日曜日、祝日のいずれかであればtrue
   */
  public boolean isHoliday(LocalDate target) {
    var dow = target.getDayOfWeek();
    return DayOfWeek.SATURDAY.equals(dow) || DayOfWeek.SUNDAY.equals(dow)
        || holidays.contains(target);
  }
}
