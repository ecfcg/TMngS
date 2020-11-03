package tmngs.db.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Metamodel;
import org.seasar.doma.Table;
import lombok.Data;

/**
 * ユーザー管理
 */
@Entity(listener = MUserListener.class, metamodel = @Metamodel)
@Table(name = "m_user")
@Data
public class MUser {

  /** ユーザーID */
  @Id
  @Column(name = "user_id")
  String userId;

  /** パスワード */
  @Column(name = "password")
  String password;
}
