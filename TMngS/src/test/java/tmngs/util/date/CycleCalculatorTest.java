package tmngs.util.date;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import tmngs.dto.AdjustedDate;
import tmngs.test.DataBaseTestBase;
import tmngs.test.reflect.FieldAccessor;
import tmngs.type.MonthlyCycle;

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


  @Test
  public void calcNextCycleByMonthTest() {
    var expectedBase = LocalDate.of(2020, 5, 3);
    var expectedAdjusted = LocalDate.of(2020, 5, 1);
    var baseDate = LocalDate.of(2020, 5, 3);

    assertEquals(AdjustedDate.of(expectedBase, expectedAdjusted),
        cycleCalculator.calcNextCycleByMonth(baseDate, 0, MonthlyCycle.BASE_DAY,
            HolidayAdjustment.BEFORE_BUSINESS_DAY));
  }
}
