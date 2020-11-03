package tmngs.util.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import tmngs.db.dao.DaoManager;
import tmngs.db.entity.MHoliday;

/**
 * 対象の日付が休日か判定する.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HolidayJudger {
  /** 休日一覧 */
  private final Set<LocalDate> holidays;

  /**
   * インスタンスを生成する.
   * 
   * @return 新しいHolidayJudgerインスタンス
   */
  public static HolidayJudger create() {
    Set<LocalDate> holidays = DaoManager.createMHolidayDao().selectAll().stream()
        .map(MHoliday::getHoliday).collect(Collectors.toSet());
    return new HolidayJudger(holidays);
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
