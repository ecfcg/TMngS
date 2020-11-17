package tmngs.dto;

import java.time.LocalDate;

/**
 * 休日調整後の日付.<br>
 * 休日調整前と調整後を共に保持する.
 */
public record AdjustedDate(LocalDate base, LocalDate adjusted) {
}
