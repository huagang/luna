alter table ms_column add column business_id int(11) not null default 0 comment '业务id' after code;
alter table ms_column add UNIQUE (business_id, name);
alter table ms_column add UNIQUE (business_id, code);
alter table ms_column drop INDEX name;
alter table ms_column drop index code;
alter table ms_column add index(name);
alter table ms_column change category_id category_id varchar(32) default null;


update luna_menu set auth='pano-viewer:login:*,pano-viewer:album:*,pano-viewer:pano:*', url='http://pano.visualbusiness.cn/backstage/htmls/albumEdit.html' where id=7;
update ms_column, ms_article set ms_column.business_id = ms_article.business_id where ms_column.id=ms_article.column_id;

ALTER TABLE ms_merchant_manage ADD trade_status int(11) NOT NULL DEFAULT '0' COMMENT '商户交易直通车开通状态';
ALTER TABLE ms_merchant_manage ADD unique_id char(32) default NULL comment "关联用户的id";
ALTER TABLE ms_merchant_manage ADD unique (`unique_id`);
