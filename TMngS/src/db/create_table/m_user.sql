drop table if exists tmngs.m_user cascade;
create table tmngs.m_user (
    user_id char(32) primary key,
    password char(32) not null
);
comment on table tmngs.m_user is 'ユーザー管理';
comment on column tmngs.m_user.user_id is 'ユーザーID';
comment on column tmngs.m_user.password is 'パスワード';
