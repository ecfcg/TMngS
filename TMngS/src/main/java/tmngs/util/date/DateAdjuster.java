package tmngs.util.date;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * 日付調整計算関連ユーティリティ.
 */
public enum DateAdjuster {
  /** 調整しない. */
  NOT_ADJUST(Function.identity()),

  /** 前日を取得する. */
  PREVIOUS_DAY(d -> d.plusDays(-1L)),

  /** 翌日を取得する. */
  FOLLOWING_DAY(d -> d.plusDays(1L));

  /** 調整関数 */
  private final Function<LocalDate, LocalDate> adjuster;

  /**
   * @param adjuster
   */
  private DateAdjuster(Function<LocalDate, LocalDate> adjuster) {
    this.adjuster = adjuster;
  }

  /** 調整を実行する */
  public LocalDate apply(LocalDate target) {
    return adjuster.apply(target);
  }

  /**
   * 逆向きを取得する
   * 
   * @return 逆向きのDateAdjuster
   */
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
