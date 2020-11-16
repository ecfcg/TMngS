package tmngs.util.date;

import static org.junit.jupiter.api.Assertions.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class WeeklyCycleTest {
  @ParameterizedTest
  @CsvSource(value = {"2020-11-01, 2020-11-01, 0", "2020-11-08, 2020-11-01, 1",
      "2020-11-22, 2020-11-01, 3", "2020-10-25, 2020-11-01, -1"})
  public void nextCycleTest(LocalDate expecteds, LocalDate baseDate, int cycle) {
    assertEquals(expecteds, WeeklyCycle.nextCycle(baseDate, cycle));
  }

  @ParameterizedTest
  @CsvSource(value = {"2020-11-08, 2020-11-01, SUNDAY, false",
      "2020-11-01, 2020-11-01, SUNDAY, true", "2020-11-08, 2020-11-02, SUNDAY, false"})
  public void nextWeekDayTest(LocalDate expecteds, LocalDate baseDate, DayOfWeek dayOfWeek,
      boolean containsBaseDate) {
    assertEquals(expecteds, WeeklyCycle.nextWeekDay(baseDate, dayOfWeek, containsBaseDate));
  }
}
