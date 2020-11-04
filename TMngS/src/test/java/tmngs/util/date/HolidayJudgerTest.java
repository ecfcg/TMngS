package tmngs.util.date;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tmngs.test.DataBaseTestBase;
import tmngs.test.OnceDataManager;
import tmngs.test.TestDataManager;
import tmngs.test.reflect.FieldAccessor;

class HolidayJudgerTest extends DataBaseTestBase {
  private static Path testDataDir = TestDataManager.getTestDataDir();
  private static OnceDataManager odm = OnceDataManager.of(List.of(testDataDir.resolve("default")));

  /** テスト対象のインスタンス */
  private HolidayJudger holidayJudger;

  @BeforeAll
  public static void setUpBeforeClass() {
    odm.insert();
  }

  @AfterAll
  public static void tearDownAfterClass() {
    odm.truncate();
  }

  @Override
  protected void setUpEach() {
    holidayJudger = HolidayJudger.create();
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
