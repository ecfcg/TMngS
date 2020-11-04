package tmngs.dto;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * 休日調整後の日付.<br>
 * 休日調整前と調整後を共に保持する.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class AdjustedDate {
  /** 休日調整前の基準日. */
  private final LocalDate base;
  /** 休日調整後の日付. */
  private final LocalDate adjusted;

  /**
   * 新しいインスタンスを生成する.
   * 
   * @param base 休日調整前の基準日.
   * @param adjusted 休日調整後の日付.
   * @return 新しいインスタンス
   */
  public static AdjustedDate of(LocalDate base, LocalDate adjusted) {
    return new AdjustedDate(base, adjusted);
  }
}
