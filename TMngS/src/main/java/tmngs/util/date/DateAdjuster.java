package tmngs.util.date;

import java.time.LocalDate;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 日付調整計算関連ユーティリティ.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum DateAdjuster {
  /** 調整しない. */
  NOT_ADJUST(Function.identity()),

  /** 前日を取得する. */
  PREVIOUS_DAY(d -> d.plusDays(-1L)),

  /** 翌日を取得する. */
  FOLLOWING_DAY(d -> d.plusDays(1L));

  private final Function<LocalDate, LocalDate> adjuster;

  public LocalDate apply(LocalDate target) {
    return adjuster.apply(target);
  }

  public DateAdjuster getReverser() {
    switch (this) {
      case NOT_ADJUST:
        return this;
      case PREVIOUS_DAY:
        return FOLLOWING_DAY;
      case FOLLOWING_DAY:
        return PREVIOUS_DAY;
    }
    return null;
  }
}
