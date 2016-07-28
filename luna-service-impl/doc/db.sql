CREATE TABLE `ms_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(50) NOT NULL COMMENT '文章标题',
  `content` text NOT NULL COMMENT '文章正文',
  `abstract_content` text NOT NULL COMMENT '摘要',
  `abstract_pic` varchar(255) DEFAULT NULL COMMENT '文章头图',
  `audio` varchar(255) DEFAULT NULL COMMENT '音频文件地址',
  `video` varchar(255) DEFAULT NULL COMMENT '视频文件地址',
  `business_id` int(11) DEFAULT NULL COMMENT '业务id',
  `column_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属栏目',
  `author` varchar(20) NOT NULL COMMENT '作者',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型,0:中文,1:英文',
  `ref_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应的中/英文文章id',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态,0:未发布,1:已发布',
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`title`),
  KEY `business_id` (`business_id`),
  KEY `column_id` (`column_id`),
  KEY `up_hhmmss` (`up_hhmmss`),
  KEY `regist_hhmmss` (`regist_hhmmss`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章内容表';

CREATE TABLE `ms_biz_module` (
  `biz_module_code` varchar(32) NOT NULL,
  `biz_module_name` varchar(64) NOT NULL,
  `status` char(1) NOT NULL DEFAULT '0',
  `ds_order` varchar(255) DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_unique_id` char(32) DEFAULT 'system',
  PRIMARY KEY (`biz_module_code`),
  UNIQUE KEY `biz_module_name` (`biz_module_name`),
  UNIQUE KEY `biz_module_code` (`biz_module_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_business` (
  `business_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '业务唯一标识符',
  `business_name` varchar(64) NOT NULL COMMENT '业务名称',
  `business_code` varchar(32) NOT NULL COMMENT '业务名称编码',
  `merchant_id` varchar(32) NOT NULL COMMENT 'crm商户id',
  `app_id` int(11) NOT NULL DEFAULT '0' COMMENT '在线微景展id',
  `stat_id` int(11) NOT NULL DEFAULT '0' COMMENT '腾讯统计平台id',
  `secret_key` varchar(32) NOT NULL DEFAULT '' COMMENT '腾讯统计平台认证key',
  `create_user` varchar(32) DEFAULT NULL COMMENT '业务创建人',
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_wjnm` varchar(64) DEFAULT 'system',
  PRIMARY KEY (`business_id`),
  UNIQUE KEY `business_name` (`business_name`),
  UNIQUE KEY `business_code` (`business_code`),
  KEY `merchant_id` (`merchant_id`),
  KEY `app_id` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微景业务表';

CREATE TABLE `ms_category` (
  `category_id` varchar(32) NOT NULL,
  `nm_zh` varchar(64) NOT NULL,
  `nm_en` varchar(64) NOT NULL,
  `del_flg` varchar(1) DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_wjnm` varchar(32) DEFAULT 'system' COMMENT '''更新者''',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '栏目id',
  `name` varchar(20) NOT NULL COMMENT '栏目名称',
  `code` varchar(30) NOT NULL COMMENT '栏目简称',
  `category_id` varchar(32) NOT NULL COMMENT '类别Id',
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `code` (`code`),
  KEY `up_hhmmss` (`up_hhmmss`),
  KEY `regist_hhmmss` (`regist_hhmmss`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='内容栏目表';

CREATE TABLE `ms_function` (
  `ms_function_code` varchar(32) NOT NULL,
  `ms_function_name` varchar(64) NOT NULL,
  `ms_function_status` char(1) NOT NULL DEFAULT '0',
  `biz_module_code` varchar(32) NOT NULL,
  `ds_order` int(11) NOT NULL,
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '''功能使用可否的状态''',
  `note` varchar(256) DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_unique_id` char(32) DEFAULT 'system',
  PRIMARY KEY (`ms_function_code`),
  UNIQUE KEY `ms_function_name` (`ms_function_name`),
  UNIQUE KEY `ms_function_code` (`ms_function_code`),
  KEY `biz_module_code` (`biz_module_code`),
  CONSTRAINT `ms_function_ibfk_1` FOREIGN KEY (`biz_module_code`) REFERENCES `ms_biz_module` (`biz_module_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_logon_log` (
  `unique_id` varchar(64) NOT NULL,
  `mode` int(1) NOT NULL,
  `ip_address` varchar(16) NOT NULL DEFAULT '',
  `regist_hhmmss` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_merchant_manage` (
  `merchant_id` varchar(32) NOT NULL COMMENT '商户id',
  `merchant_nm` varchar(128) NOT NULL COMMENT '商户名称',
  `merchant_phonenum` varchar(16) NOT NULL COMMENT '商户电话',
  `category_id` varchar(32) NOT NULL,
  `province_id` varchar(32) NOT NULL,
  `city_id` varchar(32) NOT NULL,
  `county_id` varchar(32) DEFAULT NULL,
  `merchant_addr` varchar(128) NOT NULL COMMENT '商户地址',
  `resource_content` varchar(512) DEFAULT NULL,
  `lat` decimal(9,6) DEFAULT NULL COMMENT '纬度',
  `lng` decimal(9,6) DEFAULT NULL COMMENT '经度',
  `merchant_info` varchar(512) DEFAULT NULL COMMENT '商户简介',
  `contact_nm` varchar(64) NOT NULL COMMENT '联系人名字',
  `contact_phonenum` varchar(16) NOT NULL COMMENT '联系人电话',
  `contact_mail` varchar(32) NOT NULL COMMENT '联系人邮箱',
  `salesman_id` varchar(32) NOT NULL DEFAULT 'system' COMMENT '业务员id',
  `salesman_nm` varchar(64) NOT NULL DEFAULT 'system' COMMENT '业务员名字',
  `status_id` varchar(2) NOT NULL DEFAULT '' COMMENT '处理状态',
  `del_flg` varchar(1) DEFAULT '0',
  `regist_hhmmss` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `up_hhmmss` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_unique_id` varchar(64) DEFAULT 'system',
  PRIMARY KEY (`merchant_id`),
  UNIQUE KEY `MERCHANT_NM` (`merchant_nm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CRM客户管理';

CREATE TABLE `ms_poi_field` (
  `field_name` varchar(64) NOT NULL,
  `field_show_name` varchar(64) DEFAULT NULL,
  `display_order` int(11) NOT NULL,
  `field_type` int(10) unsigned NOT NULL,
  `field_size` int(11) DEFAULT NULL,
  `placeholder` varchar(64) DEFAULT '',
  `field_tips_for_templete` varchar(255) DEFAULT NULL,
  `extension_attrs` varchar(1023) DEFAULT NULL,
  `field_introduction` varchar(255) DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_unique_id` varchar(32) DEFAULT 'system',
  KEY `field_name` (`field_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_poi_tag` (
  `tag_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '属性id，即tagid',
  `tag_name` varchar(32) NOT NULL,
  `ds_order` int(10) unsigned DEFAULT NULL,
  `tag_level` int(11) DEFAULT NULL,
  `parent_tag_id` int(10) unsigned DEFAULT NULL,
  `editable_flag` char(1) DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_unique_id` varchar(32) DEFAULT 'system',
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `tag_name` (`tag_name`,`parent_tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='根据预定义的属性对POI进行标记，支持多选，其中景点、住宿、餐饮、娱乐、购物数据库提前定义好，不可删除，其他类别的.';

CREATE TABLE `ms_r_function_resource_uri` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ms_function_code` varchar(32)  NOT NULL,
  `resource_id` int(11) NOT NULL,
  `del_flg` char(1) NOT NULL DEFAULT '0',
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ms_function_code` (`ms_function_code`,`resource_id`) USING BTREE,
  KEY `resource_id` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_r_role_function` (
  `ms_role_code` varchar(32) NOT NULL,
  `ms_function_code` varchar(64) NOT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_unique_id` char(32) DEFAULT 'system',
  PRIMARY KEY (`ms_role_code`,`ms_function_code`),
  KEY `ms_function_code` (`ms_function_code`),
  CONSTRAINT `ms_r_role_function_ibfk_1` FOREIGN KEY (`ms_role_code`) REFERENCES `ms_role` (`ms_role_code`),
  CONSTRAINT `ms_r_role_function_ibfk_2` FOREIGN KEY (`ms_function_code`) REFERENCES `ms_function` (`ms_function_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_r_tag_field` (
  `tag_id` int(11) NOT NULL,
  `field_name` varchar(64) CHARACTER SET utf8 NOT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by_unique_id` varchar(255) DEFAULT 'system',
  PRIMARY KEY (`tag_id`,`field_name`),
  KEY `field_name` (`field_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_r_user_role` (
  `unique_id` char(32) NOT NULL,
  `ms_role_code` varchar(64) NOT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_unique_id` varchar(64) DEFAULT 'system',
  PRIMARY KEY (`unique_id`,`ms_role_code`),
  KEY `ms_role_code` (`ms_role_code`),
  CONSTRAINT `ms_r_user_role_ibfk_3` FOREIGN KEY (`unique_id`) REFERENCES `ms_user_luna` (`unique_id`),
  CONSTRAINT `ms_r_user_role_ibfk_2` FOREIGN KEY (`ms_role_code`) REFERENCES `ms_role` (`ms_role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_reg_email` (
  `token` varchar(64) NOT NULL COMMENT '邮件注册码',
  `ms_role_code` varchar(32) NOT NULL COMMENT '权限码',
  `biz_module_code` varchar(32) NOT NULL COMMENT '业务码',
  `email` varchar(32) NOT NULL COMMENT '邮箱地址',
  `status` varchar(1) DEFAULT '0' COMMENT '0：未过期，未注册 1：注册',
  `regist_hhmmss` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_unique_id` varchar(64) DEFAULT 'system',
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮箱注册';

CREATE TABLE `ms_resource_uri` (
  `resource_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `resource_name` varchar(128) NOT NULL,
  `parent_id` int(128) NOT NULL,
  `resource_uri` varchar(255) DEFAULT NULL,
  `level_type` int(11) NOT NULL DEFAULT '3',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可用(1可用，0不可用)',
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_role` (
  `ms_role_code` varchar(32) NOT NULL,
  `ms_role_name` varchar(64) NOT NULL,
  `short_role_name` varchar(32) DEFAULT NULL,
  `ms_role_type` varchar(32) NOT NULL,
  `ms_role_auth` varchar(4) NOT NULL,
  `biz_module_code` varchar(32) NOT NULL,
  `ds_order` int(11) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `del_flg` varchar(1) NOT NULL DEFAULT '0',
  `updated_by_unique_id` char(32) DEFAULT 'system',
  PRIMARY KEY (`ms_role_code`),
  UNIQUE KEY `ms_role_name` (`ms_role_name`),
  KEY `biz_module_code` (`biz_module_code`),
  CONSTRAINT `ms_role_ibfk_1` FOREIGN KEY (`biz_module_code`) REFERENCES `ms_biz_module` (`biz_module_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_show_app` (
  `app_id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL,
  `app_code` varchar(32) NOT NULL,
  `business_id` int(11) NOT NULL COMMENT '微景业务id',
  `share_info_title` varchar(64) DEFAULT NULL,
  `share_info_des` varchar(256) DEFAULT NULL,
  `share_info_pic` varchar(256) DEFAULT NULL,
  `app_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '微景展状态，-1：删除, 0:未上线，1：已上线',
  `app_addr` varchar(256) DEFAULT NULL,
  `owner` varchar(32) DEFAULT NULL,
  `publish_time` timestamp NULL DEFAULT NULL,
  `pic_thumb` varchar(256) DEFAULT NULL,
  `note` varchar(512) DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `del_flg` varchar(1) NOT NULL DEFAULT '0',
  `UPDATED_BY_WJNM` varchar(64) DEFAULT 'system',
  PRIMARY KEY (`app_id`),
  UNIQUE KEY `app_name` (`app_name`),
  KEY `business_id` (`business_id`),
  KEY `up_hhmmss` (`up_hhmmss`),
  KEY `regist_hhmmss` (`regist_hhmmss`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_user_luna` (
  `unique_id` char(32) NOT NULL DEFAULT '' COMMENT '''皓月系统里的唯一用户ID''',
  `org` varchar(64) DEFAULT NULL,
  `status` char(1) NOT NULL DEFAULT '0',
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_unique_id` char(32) DEFAULT 'system' COMMENT '''操作者的user_code''',
  PRIMARY KEY (`unique_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_user_pw` (
  `luna_name` varchar(64) NOT NULL,
  `pw_luna_md5` varchar(32) NOT NULL,
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '''头像地址''',
  `unique_id` char(32) NOT NULL,
  `email` varchar(32) DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_unique_id` char(32) DEFAULT 'system',
  PRIMARY KEY (`luna_name`),
  UNIQUE KEY `unique_id` (`unique_id`) USING BTREE,
  CONSTRAINT `ms_user_pw_ibfk_1` FOREIGN KEY (`unique_id`) REFERENCES `ms_user_luna` (`unique_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_user_third_login` (
  `third_unionid` varchar(32) NOT NULL COMMENT '''第三方的unionid''',
  `mode` int(1) NOT NULL COMMENT '''0:微信,1:qq''',
  `nickname` varchar(64) DEFAULT NULL,
  `head_img_url` varchar(255) DEFAULT NULL,
  `unique_id` varchar(32) NOT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_unique_id` char(32) DEFAULT 'system',
  PRIMARY KEY (`third_unionid`,`mode`),
  KEY `unique_id` (`unique_id`) USING BTREE,
  CONSTRAINT `ms_user_third_login_ibfk_1` FOREIGN KEY (`unique_id`) REFERENCES `ms_user_luna` (`unique_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `ms_video_upload` (
  `vod_file_id` varchar(64) NOT NULL COMMENT '上传文件id',
  `vod_original_file_url` varchar(512) DEFAULT NULL COMMENT '原始视频地址，definition=0',
  `vod_normal_mp4_url` varchar(512) DEFAULT NULL COMMENT '标清视频地址，definition=20',
  `vod_phone_hls_url` varchar(512) DEFAULT NULL COMMENT '手机hls视频地址，definition=210',
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '0：正在进行/失败，1：转码成功',
  `regist_hhmmss` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `updated_by_unique_id` varchar(64) DEFAULT 'system',
  PRIMARY KEY (`vod_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

CREATE TABLE `ms_zone` (
  `ID` varchar(8) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `PARENT_ID` varchar(8) NOT NULL,
  `SHORT_NM` varchar(64) NOT NULL,
  `LEVEL_TYPE` char(1) NOT NULL,
  `CITY_CODE` varchar(8) NOT NULL,
  `ZIP_CODE` varchar(8) NOT NULL,
  `MERGER_NAME` varchar(128) NOT NULL,
  `QQ__FORMAT_MERGER_NAME` varchar(128) DEFAULT NULL,
  `LAT` decimal(9,6) DEFAULT NULL,
  `LNG` decimal(9,6) DEFAULT NULL,
  `PINYIN` varchar(64) NOT NULL,
  `DEL_FLG` varchar(1) DEFAULT '0',
  `REGIST_HHMMSS` timestamp NOT NULL DEFAULT '2015-12-31 17:00:00',
  `UP_HHMMSS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATED_BY_WJNM` varchar(64) DEFAULT 'system',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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