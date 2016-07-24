CREATE TABLE `ms_show_page_share`(
  `id` int(11) AUTO_INCREMENT,
  `app_id` INT(11) NOT NULL,
  `title` varchar(32) NOT NULL ,
  `description` VARCHAR(128) NOT NULL,
  `pic` VARCHAR(128) NOT NULL,
  `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY (app_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '微景展分享信息';


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

create table luna_module(
  id int(11) auto_increment,
  name varchar(10) not null comment '模块名称',
  code varchar(16) not null comment '模块编码，对应restful中模块路径',
  extra text comment '模块下拉选项',
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
  module_id int(11) not null comment '模块名称',
  display_order int(11) not null comment '展示顺序',
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
