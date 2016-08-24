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


INSERT INTO `luna_dev`.`luna_menu` (`name`, `code`, `module_id`, `display_order`, `status`) VALUES ('商品类别管理', 'goodsCategory', '3', '2', '1');
UPDATE `luna_dev`.`luna_menu` SET `display_order`='3' WHERE `id`='10';
UPDATE `luna_dev`.`luna_menu` SET `display_order`='4' WHERE `id`='11';
UPDATE `luna_dev`.`luna_menu` SET `display_order`='5' WHERE `id`='12';

INSERT INTO `luna_dev`.`luna_role_menu` (`role_id`, `menu_id`) VALUES ('1', '22');


