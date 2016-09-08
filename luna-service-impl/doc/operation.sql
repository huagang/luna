
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
  `account_no` varchar(30) NOT NULL COMMENT '商户开户账号',
  `update_time` datetime NOT NULL COMMENT '申请更新时间',
  `app_status` int(11) NOT NULL COMMENT '申请状态:0 checking, 1 accept, 2 refuse',
  `merchant_id` varchar(32) NOT NULL COMMENT '申请商户ID',
  `account_province` varchar(36) NOT NULL COMMENT '商户账户开户省市',
  `idcard_no` varchar(20) NOT NULL COMMENT '申请人身份证号',
  PRIMARY KEY (`application_id`),
  KEY `user_id_idx` (`merchant_id`),
  KEY `sort_idx` (`app_status`,`update_time`),
  CONSTRAINT `merchant_id` FOREIGN KEY (`merchant_id`) REFERENCES `ms_merchant_manage` (`merchant_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户交易申请表';


CREATE TABLE `luna_user_merchant` (
  `unique_id` char(32) NOT NULL,
  `merchant_id` varchar(32) NOT NULL COMMENT '商户id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`unique_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和商户关联表';


-- 菜单
INSERT INTO `luna_menu` (`name`, `code`, `module_id`, `display_order`) VALUES ('消息管理', 'message', '1', '5');
INSERT INTO `luna_menu` (`name`, `code`, `module_id`, `display_order`, `status`) VALUES ('交易直通车', 'tradeApplication', '3', 1, 1);

UPDATE `luna_menu` SET `display_order`='2' WHERE `id`='9';
UPDATE `luna_menu` SET `display_order`='3' WHERE `id`='10';
UPDATE `luna_menu` SET `display_order`='4' WHERE `id`='11';
UPDATE `luna_menu` SET `display_order`='5' WHERE `id`='12';
UPDATE `luna_menu` SET `code`='goodsCategory', status=1 WHERE `id`='5';

-- 角色\ 菜单
INSERT INTO `luna_role_menu` (`role_id`, `menu_id`) VALUES ('6', (SELECT `luna_menu`.id FROM `luna_menu` WHERE `luna_menu`.name = "交易直通车"));
INSERT INTO `luna_role_menu` (`role_id`, `menu_id`) VALUES ('7', (SELECT `luna_menu`.id FROM `luna_menu` WHERE `luna_menu`.name = "交易直通车"));
INSERT INTO `luna_role_menu` (`role_id`, `menu_id`) VALUES ('1', (SELECT `luna_menu`.id FROM `luna_menu` WHERE `luna_menu`.name = "交易直通车"));
INSERT INTO `luna_role_menu` (`role_id`,`menu_id`) VALUES ('1',(SELECT `luna_menu`.id FROM `luna_menu` WHERE `luna_menu`.name = "消息管理"));
INSERT INTO `luna_role_menu` (`role_id`,`menu_id`) VALUES ('2',(SELECT `luna_menu`.id FROM `luna_menu` WHERE `luna_menu`.name = "消息管理"));

ALTER TABLE ms_show_app drop index `app_name`;
ALTER TABLE ms_show_app drop INDEX `business_id`;
ALTER TABLE ms_show_app add UNIQUE(business_id, app_name);

-- 城市
ALTER TABLE `luna_city` ADD INDEX `for_search` (`city_name` ASC, `city_root` ASC);

-- 银行
ALTER TABLE `luna_bank_branch` ADD INDEX `for_search` (`lname` ASC, `bnkcode` ASC);


-- 商户表
ALTER TABLE `ms_merchant_manage`
ADD COLUMN `trade_status` INT NOT NULL DEFAULT 0 COMMENT '商户交易直通车开通状态' AFTER `updated_by_unique_id`;

alter table ms_merchant_manage add  `application_id` int(11) default NULL COMMENT '申请id';
alter table ms_merchant_manage add  `idcard_pic_url` varchar(200) default NULL COMMENT '申请人身份证正反面照片地址';
alter table ms_merchant_manage add  `idcard_period` varchar(26) default NULL COMMENT '申请人身份证有效期';
alter table ms_merchant_manage add  `merchant_no` varchar(30) default NULL COMMENT '商户营业执照号';
alter table ms_merchant_manage add  `licence_period` varchar(26) default NULL COMMENT '商户营业执照有效期';
alter table ms_merchant_manage add  `account_type` int(11) default NULL COMMENT '商户账户类型';
alter table ms_merchant_manage add  `account_name` varchar(36) default NULL COMMENT '商户账户名称';
alter table ms_merchant_manage add  `account_bank` varchar(36) default NULL COMMENT '商户账户开户银行';
alter table ms_merchant_manage add  `account_city` varchar(36) default NULL COMMENT '商户账户开户城市';
alter table ms_merchant_manage add  `account_address` varchar(36) default NULL COMMENT '商户账户开户行';
alter table ms_merchant_manage add  `account_no` varchar(20) default NULL COMMENT '商户开户账号';
alter table ms_merchant_manage add  `app_status` int(11) default NULL COMMENT '申请状态';
alter table ms_merchant_manage add  `account_province` varchar(36) default NULL;
alter table ms_merchant_manage add  `idcard_no` varchar(20) default NULL comment '身份证号';

-- 农家表
update ms_farm_field set limits ="{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name = "start_page_foreground_pic";
update ms_farm_field set limits ="{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name = "start_page_background_pic";
update ms_farm_field set limits ="{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name = "manager_pic";
update ms_farm_field set limits ="{\"TEXT\":[{\"empty\":true,\"max\":255}]}" where name = "all_chosen_room_panorama_type";
update ms_farm_field set limits ="{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}],\"TEXT\":[{\"empty\":true,\"max\":255}],\"num\":{\"min\":3}}" where name = "delicacy";
update ms_farm_field set limits ="{\"TEXT\":[{\"empty\":true,\"max\":255}]}" where name = "country_enjoyment";
update ms_farm_field set limits="{\"CHECKBOX\":[{\"empty\":true}]}" where name = "facility";

-- 线路管理
alter table ms_route drop index `name`;
alter table ms_route add unique key `name` (`business_id`, `name`);
UPDATE luna_menu SET status=0 WHERE code='business';

-- 邮箱注册
alter table luna_reg_email add `merchant_id` varchar(32) DEFAULT NULL COMMENT '商户id';

-- 文章
alter table ms_article add column source varchar(511) default '' comment '文章来源' after author;

-- poi 增加分类
insert into ms_poi_tag (`tag_id`,`tag_name`,`ds_order`,`tag_level`,`parent_tag_id`,`editable_flag`,`tag_name_en`,`regist_hhmmss`,`up_hhmmss`)
values(63,'临时展位','9',1,0,0,'Temporary booth','2016-09-07 17:52:47','2016-09-07 17:52:47');
insert into ms_poi_tag (`tag_id`,`tag_name`,`ds_order`,`tag_level`,`parent_tag_id`,`editable_flag`,`tag_name_en`,`regist_hhmmss`,`up_hhmmss`)
values(64,'住宿','1',2,63,0,'Accommodation services','2016-09-07 17:52:47','2016-09-07 17:52:47');
insert into ms_poi_tag (`tag_id`,`tag_name`,`ds_order`,`tag_level`,`parent_tag_id`,`editable_flag`,`tag_name_en`,`regist_hhmmss`,`up_hhmmss`)
values(65,'餐饮','2',2,63,0,'Food and beverage service','2016-09-07 17:52:47','2016-09-07 17:52:47');
insert into ms_poi_tag (`tag_id`,`tag_name`,`ds_order`,`tag_level`,`parent_tag_id`,`editable_flag`,`tag_name_en`,`regist_hhmmss`,`up_hhmmss`)
values(66,'娱乐','3',2,63,0,'Leisure and entertainment','2016-09-07 17:52:47','2016-09-07 17:52:47');
insert into ms_poi_tag (`tag_id`,`tag_name`,`ds_order`,`tag_level`,`parent_tag_id`,`editable_flag`,`tag_name_en`,`regist_hhmmss`,`up_hhmmss`)
values(67,'购物','4',2,63,0,'Shopping','2016-09-07 17:52:47','2016-09-07 17:52:47');
insert into ms_poi_tag (`tag_id`,`tag_name`,`ds_order`,`tag_level`,`parent_tag_id`,`editable_flag`,`tag_name_en`,`regist_hhmmss`,`up_hhmmss`)
values(68,'基础设施','5',2,63,0,'Infrastructure base installation','2016-09-07 17:52:47','2016-09-07 17:52:47');
insert into ms_poi_tag (`tag_id`,`tag_name`,`ds_order`,`tag_level`,`parent_tag_id`,`editable_flag`,`tag_name_en`,`regist_hhmmss`,`up_hhmmss`)
values(69,'其他','6',2,63,0,'Others','2016-09-07 17:52:47','2016-09-07 17:52:47');
insert into ms_poi_tag (`tag_id`,`tag_name`,`ds_order`,`tag_level`,`parent_tag_id`,`editable_flag`,`tag_name_en`,`regist_hhmmss`,`up_hhmmss`)
values(70,'非遗','10',2,2,0,'Intangible cultural heritage','2016-09-08 17:52:47','2016-09-08 17:52:47');
insert into ms_poi_tag (`tag_id`,`tag_name`,`ds_order`,`tag_level`,`parent_tag_id`,`editable_flag`,`tag_name_en`,`regist_hhmmss`,`up_hhmmss`)
values(71,'乡村','11',2,2,0,'Village','2016-09-08 17:52:47','2016-09-08 17:52:47');
update ms_poi_tag set ds_order=99 where tag_id in (18,22,31,42,51,61,62);
