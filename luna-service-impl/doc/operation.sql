alter table ms_column add column business_id int(11) not null default 0 comment '业务id' after code;
alter table ms_column add UNIQUE (business_id, name);
alter table ms_column add UNIQUE (business_id, code);
alter table ms_column drop INDEX name;
alter table ms_column drop index code;
alter table ms_column add index(name);