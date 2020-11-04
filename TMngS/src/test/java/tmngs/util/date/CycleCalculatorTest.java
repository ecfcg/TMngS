package tmngs.util.date;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tmngs.dto.AdjustedDate;
import tmngs.test.DataBaseTestBase;
import tmngs.test.reflect.FieldAccessor;
import tmngs.type.HolidayAdjustType;
import tmngs.type.MonthlyAdjustType;

public class CycleCalculatorTest extends DataBaseTestBase {

  /** テスト対象のインスタンス */
  private CycleCalculator cycleCalculator;

  @Override
  protected void setUpEach() {
    cycleCalculator = CycleCalculator.create();
  }

  @Test
  public void createTest() {
    assertNotNull(cycleCalculator);
    assertNotNull(FieldAccessor.get(cycleCalculator, "holidayJudger", CycleCalculator.class,
        HolidayJudger.class));
  }


  @ParameterizedTest
  @CsvSource({"2020-05-01, 2020-05-01, 2019-03-01, 14, BASE_DAY",
      "2020-05-01, 2020-05-01, 2020-05-15, 0, FIRST_DAY_OF_THE_MONTH",
      "2020-05-31, 2020-05-31, 2020-06-15, -1, END_OF_MONTH"})
  public void calcNextCycleByMonthTest(LocalDate expectedBase, LocalDate expectedAdjusted,
      LocalDate paramBase, int cycle, MonthlyAdjustType monthlyAdjustType) {

    assertEquals(AdjustedDate.of(expectedBase, expectedAdjusted), cycleCalculator
        .calcNextCycleByMonth(paramBase, cycle, monthlyAdjustType, HolidayAdjustType.NONE));
  }
}
