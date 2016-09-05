ALTER TABLE ms_merchant_manage ADD trade_status int(11) NOT NULL DEFAULT '0' COMMENT '商户交易直通车开通状态';
ALTER TABLE ms_merchant_manage ADD unique_id char(32) default NULL comment "关联用户的id";
ALTER TABLE ms_merchant_manage ADD unique (`unique_id`);

ALTER TABLE ms_show_app drop index `app_name`;
ALTER TABLE ms_show_app drop INDEX `business_id`;
ALTER TABLE ms_show_app add UNIQUE(business_id, app_name);