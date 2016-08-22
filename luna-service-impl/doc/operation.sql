INSERT INTO ms_function(ms_function_code, ms_function_name, ms_function_status, biz_module_code, ds_order, `status`, note, regist_hhmmss, up_hhmmss, updated_by_unique_id)
VALUES ('manage_router', '线路管理', 0, 'poi', 9, 0, '信息数据', '2016-07-11 22:57:17', '2016-07-11 22:57:17', 'system');

INSERT INTO ms_r_role_function(ms_role_code, ms_function_code, up_hhmmss, regist_hhmmss, updated_by_unique_id)
VALUES ('luna_senior_admin', 'manage_router', '2016-07-11 22:57:17', '2016-07-11 22:57:17', 'system');

INSERT INTO ms_r_function_resource_uri(id, ms_function_code, resource_id, del_flg, regist_hhmmss, up_hhmmss)
VALUES (24, 'manage_router', 147, 0, '2016-07-11 22:57:17', '2016-07-11 22:57:17');

INSERT INTO ms_resource_uri(resource_id, resource_name, parent_id, resource_uri, level_type, `status`, regist_hhmmss, up_hhmmss)
VALUES (147, '线路管理-function', 5, NULL, 2, 1, '2016-07-11 22:57:17', '2016-07-11 22:57:17');

INSERT INTO ms_resource_uri(resource_id, resource_name, parent_id, resource_uri, level_type, `status`, regist_hhmmss, up_hhmmss)
VALUES (148, '线路列表初始化', 147, '/manage_router.do?method=init', 3, 1, '2016-07-11 22:57:17', '2016-07-11 22:57:17');

CREATE TABLE `luna_user` (
  `unique_id` char(32) NOT NULL,
  `luna_name` varchar(64) NOT NULL,
  `nick_name` varchar(64),
  `pw_luna_md5` varchar(32) NOT NULL,
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '''头像地址''',
  `email` varchar(32) DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`unique_id`),
  UNIQUE KEY `luna_name` (`luna_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table luna_module(
  id int(11) auto_increment,
  name varchar(10) not null comment '模块名称',
  code varchar(16) not null comment '模块编码，对应restful中模块路径',
  display_order int(11) not null comment '展示顺序',
  update_time timestamp default current_timestamp on update current_timestamp,
  primary key(id),
  unique(name),
  unique(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块表';

create table luna_menu(
  id int(11) auto_increment,
  name varchar(10) not null comment '菜单名称',
  code varchar(16) not null comment '菜单编码，对应restful中菜单路径',
  url varchar(256) default null comment '外部系统url，内部为空',
  auth varchar(256) default null comment '权限描述',
  module_id int(11) not null comment '模块名称',
  display_order int(11) not null comment '展示顺序',
  status tinyint(1) not null default 1 comment '是否有效',
  update_time timestamp default current_timestamp on update current_timestamp,
  primary key(id),
  key(module_id),
  unique(name),
  unique(code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

create table luna_role(
  id int(11) auto_increment,
  name varchar(16) not null comment '角色名称',
  code varchar(32) not null comment '角色编码',
  is_admin tinyint(1) not null default 0 comment '是否管理员',
  parent_id int not null comment '父角色',
  category_id int(11) not null comment '角色类别id',
  extra_value int(11) not null default -1 COMMENT '角色模块下拉默认值',
  update_time timestamp default current_timestamp on update current_timestamp,
  primary key(id),
  key(parent_id),
  unique(name),
  unique(code)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

create table luna_role_menu(
  role_id int(11) not null,
  menu_id int(11) not null,
  update_time timestamp default current_timestamp on update current_timestamp,
  primary key(role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单对应表';

CREATE TABLE `luna_reg_email` (
  `token` varchar(64) NOT NULL COMMENT '邮件注册码',
  `role_id`  int(11) NOT NULL COMMENT '权限Id',
  `email` varchar(32) NOT NULL COMMENT '邮箱地址',
  extra text comment '模块下拉选项',
  `status` tinyint(1) DEFAULT 0 COMMENT '0：未过期，未注册 1：注册',
  `regist_hhmmss` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `invite_unique_id` varchar(32) NOT NULL COMMENT '邀请人',
  PRIMARY KEY (`token`),
  UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮箱注册';

create table luna_role_category(
  id int(11) not null auto_increment comment '类别id',
  name varchar(16) not null comment '类别名称',
  extra text comment '类别下拉选项',
  update_time timestamp default current_timestamp on update current_timestamp,
  primary key(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色类别表';

alter table ms_show_app add column type INT NOT NULL DEFAULT 0 COMMENT '微景展类型,0:基本版,1:高级版,2:数据版';

# 修改农+ limits字段
update ms_farm_field set limits="{\"PIC\":[{\"empty\":false,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name="start_page_foreground_pic" ;
update ms_farm_field set limits="{\"PIC\":[{\"empty\":false,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name="start_page_background_pic" ;
update ms_farm_field set limits="{\"PIC\":[{\"empty\":false,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name="manager_pic" ;
update ms_farm_field set limits="{\"TEXTAREA\":[{\"empty\":false,\"max\":1024}]}" where name="manager_self_introduction" ;
update ms_farm_field set limits="{\"TEXT\":[{\"empty\":false,\"max\":255}]}" where name="well_chosen_room_panorama_type" ;
update ms_farm_field set limits="{\"TEXT\":[{\"empty\":false,\"max\":255}]}" where name="all_chosen_room_panorama_type" ;
update ms_farm_field set limits="{\"PIC\":[{\"empty\":false,\"max\":20,\"ext\":[\"png\",\"jpg\"]}],\"TEXT\":[{\"empty\":false,\"max\":255}],\"num\":{\"min\":3}}" where name="delicacy" ;
update ms_farm_field set limits="{\"TEXT\":[{\"empty\":false,\"max\":255}]}" where name="country_enjoyment" ;

# 修改菜单名字
update luna_menu set name="CRM" where id=13;
update luna_menu set name="POI数据管理" where id=6;
update luna_menu set name="POI数据关系配置" where id=15;

# 修改ms_farm_field 组件类型名称
update ms_farm_field set type="PANORAMA" where type="RADIO_TEXT";
update ms_farm_field set limits="{\"PIC\":[{\"empty\":false,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name="start_page_foreground_pic" ;
update ms_farm_field set limits="{\"PIC\":[{\"empty\":false,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name="start_page_background_pic" ;
update ms_farm_field set limits="{\"PIC\":[{\"empty\":false,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name="manager_pic" ;
update ms_farm_field set limits="{\"TEXTAREA\":[{\"empty\":false,\"max\":1024}]}" where name="manager_self_introduction" ;
update ms_farm_field set limits="{\"TEXT\":[{\"empty\":false,\"max\":255}]}" where name="well_chosen_room_panorama_type" ;
update ms_farm_field set limits="{\"TEXT\":[{\"empty\":false,\"max\":255}]}" where name="all_chosen_room_panorama_type" ;
update ms_farm_field set limits="{\"PIC\":[{\"empty\":false,\"max\":20,\"ext\":[\"png\",\"jpg\"]}],\"TEXT\":[{\"empty\":false,\"max\":255}],\"num\":{\"min\":3}}" where name="delicacy" ;
update ms_farm_field set limits="{\"TEXT\":[{\"empty\":false,\"max\":255}]}" where name="country_enjoyment" ;

CREATE TABLE `ms_operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(32) NOT NULL COMMENT '资源id',
  `type` varchar(8) NOT NULL COMMENT '资源类型',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `unique_id` varchar(32) NOT NULL COMMENT '用户id',
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into ms_resource_uri (resource_name,parent_id,resource_uri,level_type,status,regist_hhmmss,up_hhmmss) 
values('搜索线路列表',147,'/manage_router.do?method=async_search_routes',3,1,now(),now());

insert into ms_resource_uri (resource_name,parent_id,resource_uri,level_type,status,regist_hhmmss,up_hhmmss) 
values('创建线路',147,'/manage_router.do?method=create_route',3,1,now(),now());

insert into ms_resource_uri (resource_name,parent_id,resource_uri,level_type,status,regist_hhmmss,up_hhmmss) 
values('属性编辑',147,'/manage_router.do?method=edit_route',3,1,now(),now());

insert into ms_resource_uri (resource_name,parent_id,resource_uri,level_type,status,regist_hhmmss,up_hhmmss) 
values('线路删除',147,'/manage_router.do?method=del_route',3,1,now(),now());

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
