package tmngs.db.entity;

import java.time.LocalDate;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;
import lombok.Data;

/**
 * システム休日管理
 */
@Entity(listener = MHolidayListener.class, metamodel = @Metamodel)
@Table(name = "m_holiday")
@Data
public class MHoliday {

  /** 休日 */
  @Id
  @Column(name = "holiday")
  LocalDate holiday;
}
