package tmngs.util.date;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DateAdjusterTest {

  @Test
  void NOT_ADJUST_Test() {
    var param = LocalDate.of(2020, 1, 1);
    assertEquals(param, DateAdjuster.NOT_ADJUST.apply(param));
    assertEquals(DateAdjuster.NOT_ADJUST, DateAdjuster.NOT_ADJUST.getReverser());
  }

  @Test
  void PREVIOUS_DAY_Test() {
    var param = LocalDate.of(2020, 1, 1);
    var expected = LocalDate.of(2019, 12, 31);
    assertEquals(expected, DateAdjuster.PREVIOUS_DAY.apply(param));
    assertEquals(DateAdjuster.FOLLOWING_DAY, DateAdjuster.PREVIOUS_DAY.getReverser());
  }

  @Test
  void FOLLOWING_DAY_Test() {
    var param = LocalDate.of(2019, 12, 31);
    var expected = LocalDate.of(2020, 1, 1);
    assertEquals(expected, DateAdjuster.FOLLOWING_DAY.apply(param));
    assertEquals(DateAdjuster.PREVIOUS_DAY, DateAdjuster.FOLLOWING_DAY.getReverser());
  }
}
