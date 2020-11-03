package tmngs.db.entity;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PostDeleteContext;
import org.seasar.doma.jdbc.entity.PostInsertContext;
import org.seasar.doma.jdbc.entity.PostUpdateContext;
import org.seasar.doma.jdbc.entity.PreDeleteContext;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;

/**
 * 
 */
public class MHolidayListener implements EntityListener<MHoliday> {

  @Override
  public void preInsert(MHoliday entity, PreInsertContext<MHoliday> context) {}

  @Override
  public void preUpdate(MHoliday entity, PreUpdateContext<MHoliday> context) {}

  @Override
  public void preDelete(MHoliday entity, PreDeleteContext<MHoliday> context) {}

  @Override
  public void postInsert(MHoliday entity, PostInsertContext<MHoliday> context) {}

  @Override
  public void postUpdate(MHoliday entity, PostUpdateContext<MHoliday> context) {}

  @Override
  public void postDelete(MHoliday entity, PostDeleteContext<MHoliday> context) {}
}
