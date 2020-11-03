package tmngs.db.dao;

import java.time.LocalDate;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import tmngs.db.entity.MHoliday;
import java.util.List;

/**
 */
@Dao
public interface MHolidayDao {

  /**
   * @param holiday
   * @return the MHoliday entity
   */
  @Select
  MHoliday selectById(LocalDate holiday);

  /**
   * @return all MHoliday entities
   */
  @Select
  List<MHoliday> selectAll();

  /**
   * @param entity
   * @return affected rows
   */
  @Insert
  int insert(MHoliday entity);

  /**
   * @param entity
   * @return affected rows
   */
  @Update
  int update(MHoliday entity);

  /**
   * @param entity
   * @return affected rows
   */
  @Delete
  int delete(MHoliday entity);
}
