package tmngs.util.date;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * 日付調整計算関連ユーティリティ.
 */
public interface DateAdjuster {
  /** 調整しない. */
  static final Function<LocalDate, LocalDate> NOT_ADJUST = Function.identity();

  /** 前日を取得する. */
  static final Function<LocalDate, LocalDate> PREVIOUS_DAY = d -> d.plusDays(-1L);

  /** 翌日を取得する. */
  static final Function<LocalDate, LocalDate> FOLLOWING_DAY = d -> d.plusDays(1L);
}
