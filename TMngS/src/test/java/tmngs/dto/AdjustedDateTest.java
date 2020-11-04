package tmngs.dto;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AdjustedDateTest {

  @Test
  void ofTest() {
    var base = LocalDate.of(2020, 1, 1);
    var adjusted = LocalDate.of(2020, 1, 2);
    var actual = AdjustedDate.of(base, adjusted);
    assertEquals(base, actual.getBase());
    assertEquals(adjusted, actual.getAdjusted());
  }
}
