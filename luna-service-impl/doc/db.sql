create table ms_business(
	`business_id` int(11) auto_increment comment '业务唯一标识符',
	`business_name` varchar(64) not null comment '业务名称',
	`business_code` varchar(32) not null comment '业务名称编码',
	`merchant_id` varchar(32) not null comment 'crm商户id',
	`app_id` int(11) not null default 0 comment '在线微景展id',
	`stat_id` int(11) not null default 0 comment '腾讯统计平台id',
	`secret_key` varchar(32) not null default '' comment '腾讯统计平台认证key',
	`create_user` varchar(32) DEFAULT NULL comment '业务创建人',
	`up_hhmmss` timestamp NULL DEFAULT NULL,
	`regist_hhmmss` timestamp NULL DEFAULT NULL,
	`updated_by_wjnm` varchar(64) DEFAULT 'system',
	primary key(business_id),
	unique(business_name),
	unique(business_code),
	key(merchant_id),
	key(app_id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '微景业务表';

create table ms_show_app (
  `app_id` int(11) NOT NULL AUTO_INCREMENT comment '微景展id',
  `app_name` varchar(64) NOT NULL comment '微景展名称',
  `app_code` varchar(32) not null comment '微景码编码',
  `business_id` int NOT NULL comment '微景业务id',
  `share_info_title` varchar(64) DEFAULT NULL comment '分享朋友圈标题',
  `share_info_des` varchar(256) DEFAULT NULL comment '分享朋友圈描述',
  `share_info_pic` varchar(256) DEFAULT NULL comment '分享朋友圈缩略图',
  `app_status` tinyint(4) not null DEFAULT 0 comment '微景展状态，-1：删除, 0:未上线，1：已上线',
  `app_addr` varchar(256) DEFAULT NULL comment '微景展对外发布地址',
  `owner` varchar(32) DEFAULT NULL,
  `pic_thumb` varchar(256) DEFAULT NULL,
  `note` varchar(512) DEFAULT NULL,
  `publish_time` timestamp NULL DEFAULT NULL comment '微景展发布时间',
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `del_flg` varchar(1) not null default '0',
  `UPDATED_BY_WJNM` varchar(64) DEFAULT 'system',
  primary key(app_id),
  unique(app_name)，
  key(business_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '微景展表';

create table ms_show_page(
  `page_id` bigint NOT NULL AUTO_INCREMENT,
  `page_code` varchar(32) NOT NULL,
  `page_name` varchar(64) NOT NULL,
  `page_addr` varchar(256) DEFAULT NULL,
  `page_status` tinyint(4) DEFAULT NULL,
  `app_id` int(11) NOT NULL,
  `template_id` int(11) NOT NULL,
  `main_flag` tinyint(1) DEFAULT NULL,
  `owner` varchar(32) DEFAULT NULL,
  `share_info_title` varchar(64) DEFAULT NULL,
  `share_info_des` varchar(256) DEFAULT NULL,
  `share_info_pic` varchar(256) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `online_time` timestamp NULL DEFAULT NULL,
  `note` varchar(512) DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `del_flg` varchar(1) NOT NULL DEFAULT '0',
  `UPDATED_BY_WJNM` varchar(64) DEFAULT 'system',
  PRIMARY KEY (`page_id`),
  unique(app_id, page_name),
  unique(app_id, page_code)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '微景展页面表';