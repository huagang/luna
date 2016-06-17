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
	key(app_id),
  key(regist_hhmmss),
  key(up_hhmmss)

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
  unique(app_name),
  key(business_id),
  key(up_hhmmss),
  key(regist_hhmmss)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '微景展表';



CREATE TABLE ms_column(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '栏目id',
  `name` VARCHAR(20) NOT NULL COMMENT '栏目名称',
  `code` VARCHAR(30) NOT NULL COMMENT '栏目简称',
  `category_id` VARCHAR(32) NOT NULL COMMENT '类别Id',
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`name`),
  UNIQUE (`code`),
  key(up_hhmmss),
  key(regist_hhmmss)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '内容栏目表';

CREATE TABLE ms_article(
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` VARCHAR(50) NOT NULL COMMENT '文章标题',
  `content` TEXT NOT NULL COMMENT '文章正文',
  `abstract_content` TEXT NOT NULL COMMENT '摘要',
  `abstract_pic` VARCHAR(255) COMMENT '文章头图',
  `audio` VARCHAR(255) COMMENT '音频文件地址',
  `video` VARCHAR(255) COMMENT '视频文件地址',
  `business_id` INT(11) COMMENT '业务id',
  `column_id` INT(11) NOT NULL DEFAULT 0 COMMENT '所属栏目',
  `author` VARCHAR(20) NOT NULL COMMENT '作者',
  `type` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '类型,0:中文,1:英文',
  `ref_id` INT(11) NOT NULL DEFAULT 0 COMMENT '对应的中/英文文章id',
  `up_hhmmss` timestamp NULL DEFAULT NULL,
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY (business_id),
  KEY (column_id),
  key(up_hhmmss),
  key(regist_hhmmss)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '文章内容表';