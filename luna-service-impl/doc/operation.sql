
CREATE TABLE `ms_operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(32) NOT NULL COMMENT '资源id',
  `type` varchar(8) NOT NULL COMMENT '资源类型',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `unique_id` varchar(32) NOT NULL COMMENT '用户id',
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `ms_route` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '线路id',
  `name` varchar(64) NOT NULL COMMENT '线路名称',
  `business_id` int(11) NOT NULL COMMENT '所属业务id',
  `description` varchar(1024) DEFAULT '' COMMENT '线路介绍',
  `cost_id` int(1) NOT NULL COMMENT '体力消耗值',
  `cover` varchar(255) DEFAULT '' COMMENT '封面图',
  `unique_id` char(32) NOT NULL COMMENT '创建人id',
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `business_id` (`business_id`)
)ENGINE=InnoDB default charset=utf8 comment='线路表';

# 增加 线路管理 菜单
insert into luna_menu (`id`,`name`,`code`,`module_id`,`display_order`) values(20,"线路管理","route",4,7);
insert into luna_role_menu (`role_id`,`menu_id`) values(1,20);
insert into luna_role_menu (`role_id`,`menu_id`) values(2,20);
insert into luna_role_menu (`role_id`,`menu_id`) values(3,20);
insert into luna_role_menu (`role_id`,`menu_id`) values(8,20);
insert into luna_role_menu (`role_id`,`menu_id`) values(9,20);



CREATE TABLE `luna_goods_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `name` varchar(32) NOT NULL COMMENT '类别名称',
  `root` int(11) DEFAULT NULL COMMENT '类别上级id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `depth` int(11) NOT NULL COMMENT '类别深度',
  `abbreviation` varchar(16) NOT NULL COMMENT '类别简称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `abbreviation_UNIQUE` (`abbreviation`),
  KEY `root_idx` (`root`),
  CONSTRAINT `root` FOREIGN KEY (`root`) REFERENCES `luna_goods_category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类别表';


INSERT INTO luna_menu` (`name`, `code`, `module_id`, `display_order`, `status`) VALUES ('商品类别管理', 'goodsCategory', '3', '2', '1');
UPDATE luna_menu` SET `display_order`='3' WHERE `id`='10';
UPDATE `luna_menu` SET `display_order`='4' WHERE `id`='11';
UPDATE `luna_menu` SET `display_order`='5' WHERE `id`='12';

INSERT INTO luna_role_menu` (`role_id`, `menu_id`) VALUES ('1', (SELECT `luna_menu`.id FROM `luna_menu` WHERE `luna_menu`.name = "商品类别管理"));


CREATE TABLE `luna_trade_application` (
  `application_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '申请id',
  `contact_name` varchar(20) NOT NULL COMMENT '申请商户名称',
  `contact_phone` varchar(16) NOT NULL COMMENT '申请人联系方式',
  `email` varchar(36) NOT NULL COMMENT '申请人邮箱',
  `idcard_pic_url` varchar(200) NOT NULL COMMENT '申请人身份证正反面照片地址',
  `idcard_period` varchar(26) NOT NULL COMMENT '申请人身份证有效期',
  `merchant_name` varchar(36) NOT NULL COMMENT '商户主体名称',
  `merchant_phone` varchar(16) NOT NULL COMMENT '商户客服电话',
  `merchant_no` varchar(30) NOT NULL COMMENT '商户营业执照号',
  `licence_pic_url` varchar(200) NOT NULL COMMENT '商户营业执照照片地址',
  `licence_period` varchar(26) NOT NULL COMMENT '商户营业执照有效期',
  `account_type` int(11) NOT NULL COMMENT '商户账户类型',
  `account_name` varchar(36) NOT NULL COMMENT '商户账户名称',
  `account_bank` varchar(36) NOT NULL COMMENT '商户账户开户银行',
  `account_city` varchar(36) NOT NULL COMMENT '商户账户开户城市',
  `account_address` varchar(36) NOT NULL COMMENT '商户账户开户行',
  `account_no` varchar(20) NOT NULL COMMENT '商户开户账号',
  `update_time` datetime NOT NULL COMMENT '申请更新时间',
  `app_status` int(11) NOT NULL COMMENT '申请状态',
  `merchant_id` varchar(32) NOT NULL COMMENT '申请商户ID',
  `account_province` varchar(36) NOT NULL COMMENT '商户账户开户省市',
  PRIMARY KEY (`application_id`),
  KEY `user_id_idx` (`merchant_id`),
  KEY `sort_idx` (`app_status`,`update_time`),
  CONSTRAINT `merchant_id` FOREIGN KEY (`merchant_id`) REFERENCES `ms_merchant_manage` (`merchant_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户交易申请表';


ALTER TABLE `ms_merchant_manage`
ADD COLUMN `trade_status` INT NOT NULL DEFAULT 0 COMMENT '商户交易直通车开通状态' AFTER `updated_by_unique_id`;

INSERT INTO `luna_menu` (`name`, `code`, `module_id`, `status`) VALUES ('交易直通车', 'tradeApplication', '3', '1');
UPDATE `luna_menu` SET `display_order`='2' WHERE `id`='9';
UPDATE `luna_menu` SET `display_order`='4' WHERE `id`='10';
UPDATE `luna_menu` SET `display_order`='5' WHERE `id`='11';
UPDATE `luna_menu` SET `display_order`='6' WHERE `id`='12';
UPDATE `luna_menu` SET `display_order`='3' WHERE `id`='22';

INSERT INTO `luna_role_menu` (`role_id`, `menu_id`) VALUES ('6', (SELECT `luna_menu`.id FROM `luna_menu` WHERE `luna_menu`.name = "交易直通车"));
INSERT INTO `luna_role_menu` (`role_id`, `menu_id`) VALUES ('7', (SELECT `luna_menu`.id FROM `luna_menu` WHERE `luna_menu`.name = "交易直通车"));

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

ALTER TABLE ms_show_app drop index `app_name`;
ALTER TABLE ms_show_app drop INDEX `business_id`;
ALTER TABLE ms_show_app add UNIQUE(business_id, app_name);
UPDATE luna_menu SET status=0 WHERE code='business';


update luna_menu set auth='pano-viewer:login:*,pano-viewer:album:*,pano-viewer:pano:*', url='http://pano.visualbusiness.cn/backstage/htmls/albumEdit.html' where id=7;

INSERT INTO `luna_menu` (`name`, `code`, `module_id`, `display_order`) VALUES ('消息管理', 'message', '1', '5');

INSERT INTO `luna_role_menu` (`role_id`,`menu_id`) VALUES ('1',(SELECT `luna_menu`.id FROM `luna_menu` WHERE `luna_menu`.name = "消息管理"));

INSERT INTO `luna_role_menu` (`role_id`,`menu_id`) VALUES ('2',(SELECT `luna_menu`.id FROM `luna_menu` WHERE `luna_menu`.name = "消息管理"));

ALTER TABLE `luna_city`
ADD INDEX `for_search` (`city_name` ASC, `city_root` ASC);

ALTER TABLE `luna_bank_branch`
ADD INDEX `for_search` (`lname` ASC, `bnkcode` ASC);

