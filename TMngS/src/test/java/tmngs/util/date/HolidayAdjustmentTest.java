package tmngs.util.date;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tmngs.test.CsvDataConverter;
import tmngs.test.TestDataManager;

public class HolidayAdjustmentTest {
  private static Path testDataDir = TestDataManager.getTestDataDir();

  /** テスト時に利用する休日判定 */
  private HolidayJudger holidayJudger;

  @BeforeEach
  public void setUp() {
    var holidays = CsvDataConverter.getLocalDate(testDataDir.resolve("adjustTest_Exception.csv"));
    holidayJudger = HolidayJudger.create(holidays);
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-05-01, 2020-05-01", "2020-05-02, 2020-05-02"})
  public void adjustTest_NONE(LocalDate expected, LocalDate target) {
    assertEquals(expected, HolidayAdjustment.NONE.adjust(target, holidayJudger));
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-05-01, 2020-05-01", "2020-05-01, 2020-05-02", "2020-05-01, 2020-05-03"})
  public void adjustTest_BEFORE_BUSINESS_DAY(LocalDate expected, LocalDate target) {
    assertEquals(expected, HolidayAdjustment.BEFORE_BUSINESS_DAY.adjust(target, holidayJudger));
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-05-01, 2020-05-01", "2020-05-04, 2020-05-03", "2020-05-04, 2020-05-02"})
  public void adjustTest_AFTER_BUSINESS_DAY(LocalDate expected, LocalDate target) {
    assertEquals(expected, HolidayAdjustment.AFTER_BUSINESS_DAY.adjust(target, holidayJudger));
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-05-01, 2020-05-01", "2020-05-01, 2020-05-02", "2020-05-01, 2020-05-03",
      "2020-08-03, 2020-08-02", "2020-08-03, 2020-08-01"})
  public void adjustTest_FIRST_BUSINESS_DAY(LocalDate expected, LocalDate target) {
    assertEquals(expected, HolidayAdjustment.FIRST_BUSINESS_DAY.adjust(target, holidayJudger));
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-05-01, 2020-05-01", "2020-05-04, 2020-05-03", "2020-05-04, 2020-05-02",
      "2020-05-29, 2020-05-30", "2020-05-29, 2020-05-31"})
  public void adjustTest_LAST_BUSINESS_DAY(LocalDate expected, LocalDate target) {
    assertEquals(expected, HolidayAdjustment.LAST_BUSINESS_DAY.adjust(target, holidayJudger));
  }

  @ParameterizedTest
  @CsvSource(value = {"BEFORE_BUSINESS_DAY, 2021-07-11", "AFTER_BUSINESS_DAY, 2021-04-01",
      "FIRST_BUSINESS_DAY, 2021-09-15", "LAST_BUSINESS_DAY, 2021-09-15"})
  public void adjustTest_Exception(HolidayAdjustment type, LocalDate target) {
    var e = assertThrows(RuntimeException.class, () -> type.adjust(target, holidayJudger));
    assertEquals("休日調整の回数が規定回数を超過しました。祝日の設定を見直してください。", e.getMessage());
  }
}
