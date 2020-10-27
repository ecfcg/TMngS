drop table if exists tmngs.m_holiday cascade;
create table tmngs.m_holiday (
    holiday date primary key
);
comment on table tmngs.m_holiday is 'システム休日管理';
comment on column tmngs.m_holiday.holiday is '休日';
