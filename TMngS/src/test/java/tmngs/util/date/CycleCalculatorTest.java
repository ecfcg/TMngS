package tmngs.util.date;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import tmngs.dto.AdjustedDate;
import tmngs.test.reflect.FieldAccessor;

public class CycleCalculatorTest {

  /** テスト対象のインスタンス */
  private CycleCalculator cycleCalculator =
      CycleCalculator.create(HolidayJudger.create(Collections.emptySet()));

  @Test
  public void createTest() {
    assertNotNull(cycleCalculator);
    assertNotNull(FieldAccessor.get(cycleCalculator, "holidayJudger", CycleCalculator.class,
        HolidayJudger.class));
  }

  @Test
  public void calcNextCycleByMonthTest() {
    var expectedBase = LocalDate.of(2020, 5, 3);
    var expectedAdjusted = LocalDate.of(2020, 5, 1);
    var baseDate = LocalDate.of(2020, 5, 3);

    assertEquals(new AdjustedDate(expectedBase, expectedAdjusted),
        cycleCalculator.calcNextCycleByMonth(baseDate, 0, MonthlyCycle.BASE_DAY,
            HolidayAdjustment.BEFORE_BUSINESS_DAY));
  }
}
