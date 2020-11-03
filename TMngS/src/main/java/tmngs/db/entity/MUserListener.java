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
public class MUserListener implements EntityListener<MUser> {

  @Override
  public void preInsert(MUser entity, PreInsertContext<MUser> context) {}

  @Override
  public void preUpdate(MUser entity, PreUpdateContext<MUser> context) {}

  @Override
  public void preDelete(MUser entity, PreDeleteContext<MUser> context) {}

  @Override
  public void postInsert(MUser entity, PostInsertContext<MUser> context) {}

  @Override
  public void postUpdate(MUser entity, PostUpdateContext<MUser> context) {}

  @Override
  public void postDelete(MUser entity, PostDeleteContext<MUser> context) {}
}
