package tmngs.type;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MonthlyCycleTest {

  @ParameterizedTest
  @CsvSource(value = {"2020-05-15, BASE_DAY, 2019-03-15, 14",
      "2020-05-01, FIRST_DAY_OF_THE_MONTH, 2020-05-15, 0",
      "2020-05-31, END_OF_MONTH, 2020-06-15, -1"})
  void calcNextTest(LocalDate expected, MonthlyCycle type, LocalDate baseDate, int cycle) {
    assertEquals(expected, type.calcNext(baseDate, cycle));
  }

}
