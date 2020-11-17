package tmngs.util.date;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tmngs.test.CsvDataConverter;
import tmngs.test.TestDataManager;
import tmngs.test.reflect.FieldAccessor;

class HolidayJudgerTest {
  private static Path testDataDir = TestDataManager.getTestDataDir();

  /** テスト対象のインスタンス */
  private HolidayJudger holidayJudger;

  @BeforeEach
  public void setUp() {
    var holidays = CsvDataConverter.getLocalDate(testDataDir.resolve("default.csv"));
    holidayJudger = HolidayJudger.create(holidays);
  }

  @Test
  public void createTest() {
    @SuppressWarnings("unchecked")
    Set<LocalDate> holidays =
        FieldAccessor.get(holidayJudger, "holidays", HolidayJudger.class, Set.class);
    var expectedHolidays =
        Set.of(LocalDate.of(2020, 5, 3), LocalDate.of(2020, 5, 4), LocalDate.of(2020, 5, 5));
    assertEquals(expectedHolidays, holidays);
  }

  @ParameterizedTest
  @CsvSource({"false, 2020-05-01", "true, 2020-05-05", "true, 2020-05-09", "true, 2020-05-10"})
  public void isHolidayTest(boolean expected, LocalDate date) {
    assertEquals(expected, holidayJudger.isHoliday(date));
  }
}
