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