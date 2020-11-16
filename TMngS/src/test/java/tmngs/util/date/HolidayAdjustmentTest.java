package tmngs.util.date;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tmngs.test.DataBaseTestBase;
import tmngs.test.OnceDataManager;
import tmngs.test.TestDataManager;
import tmngs.util.date.HolidayAdjustment;
import tmngs.util.date.HolidayJudger;

public class HolidayAdjustmentTest extends DataBaseTestBase {
  private static Path testDataDir = TestDataManager.getTestDataDir();
  private static OnceDataManager odm =
      OnceDataManager.of(List.of(testDataDir.resolve("adjustTest_Exception")));

  /** テスト時に利用する休日判定 */
  private HolidayJudger holidayJudger;

  @BeforeAll
  public static void setUpBeforeClass() {
    // 他のテストに影響を与えないため事前に投入する.
    odm.insert();
  }

  @AfterAll
  public static void tearDownAfterClass() {
    odm.truncate();
  }

  @Override
  public void setUpEach() {
    holidayJudger = HolidayJudger.create();
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
