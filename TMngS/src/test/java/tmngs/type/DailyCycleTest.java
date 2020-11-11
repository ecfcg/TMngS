package tmngs.type;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tmngs.test.DataBaseTestBase;
import tmngs.util.date.HolidayJudger;

public class DailyCycleTest extends DataBaseTestBase {
  /** テスト時に利用する休日判定 */
  private HolidayJudger holidayJudger;

  @Override
  public void setUpEach() {
    holidayJudger = HolidayJudger.create();
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-04-30, 2020-05-04, 4"})
  public void calcNextTest_BEFORE_DAYS(LocalDate expected, LocalDate target, int cycle) {
    assertEquals(expected, DailyCycle.BEFORE_DAYS.calcNext(target, cycle, holidayJudger));
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-09-01, 2020-08-28, 4"})
  public void calcNextTest_AFTER_DAYS(LocalDate expected, LocalDate target, int cycle) {
    assertEquals(expected, DailyCycle.AFTER_DAYS.calcNext(target, cycle, holidayJudger));
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-04-28, 2020-05-04, 4"})
  public void calcNextTest_BEFORE_BUSINESS_DAYS(LocalDate expected, LocalDate target, int cycle) {
    assertEquals(expected, DailyCycle.BEFORE_BUSINESS_DAYS.calcNext(target, cycle, holidayJudger));
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-09-03, 2020-08-28, 4"})
  public void calcNextTest_AFTER_BUSINESS_DAYS(LocalDate expected, LocalDate target, int cycle) {
    assertEquals(expected, DailyCycle.AFTER_BUSINESS_DAYS.calcNext(target, cycle, holidayJudger));
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-05-01, 2020-05-04, 4", "2020-08-03, 2020-08-02, 4",
      "2020-08-03, 2020-08-01, 4", "2020-08-25, 2020-08-31, 4"})
  public void calcNextTest_FIRST_BUSINESS_DAY(LocalDate expected, LocalDate target, int cycle) {
    assertEquals(expected, DailyCycle.FIRST_BUSINESS_DAY.calcNext(target, cycle, holidayJudger));
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-08-31, 2020-08-28, 4", "2020-05-29, 2020-05-30, 4",
      "2020-05-29, 2020-05-31, 4","2020-05-07, 2020-05-01, 4"})
  public void calcNextTest_LAST_BUSINESS_DAY(LocalDate expected, LocalDate target, int cycle) {
    assertEquals(expected, DailyCycle.LAST_BUSINESS_DAY.calcNext(target, cycle, holidayJudger));
  }
}
