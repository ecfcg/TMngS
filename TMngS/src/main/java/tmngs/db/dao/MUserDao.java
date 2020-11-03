package tmngs.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import tmngs.db.entity.MUser;
import java.util.List;

/**
 */
@Dao
public interface MUserDao {

  /**
   * @param userId
   * @return the MUser entity
   */
  @Select
  MUser selectById(String userId);

  /**
   * @return all MUser entities
   */
  @Select
  List<MUser> selectAll();

  /**
   * @param entity
   * @return affected rows
   */
  @Insert
  int insert(MUser entity);

  /**
   * @param entity
   * @return affected rows
   */
  @Update
  int update(MUser entity);

  /**
   * @param entity
   * @return affected rows
   */
  @Delete
  int delete(MUser entity);
}
