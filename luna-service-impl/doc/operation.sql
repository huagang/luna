CREATE TABLE `ms_show_page_share`(
  `id` int(11) AUTO_INCREMENT,
  `business_id` INT(11) NOT NULL,
  `title` varchar(32) NOT NULL ,
  `description` VARCHAR(128) NOT NULL,
  `pic` VARCHAR(128) NOT NULL,
  PRIMARY KEY (id),
  KEY (business_id)
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

INSERT INTO ms_poi_field (field_name, field_show_name, field_alias, display_order, field_type, field_size, field_tips_for_templete) VALUES ('thermodynamic_diagram', '热力图ID', 'thermodynamic_diagram', 25, 1, 255, '');

INSERT INTO ms_r_tag_field (tag_id, field_name) VALUES (2, 'thermodynamic_diagram');
ALTER TABLE ms_article ADD COLUMN short_title VARCHAR(64) DEFAULT '' AFTER title;

# 将上传视频大小限制改为20M
update ms_poi_field set field_size=20 where field_name='video';

INSERT INTO ms_poi_field (field_name, field_show_name, field_alias, display_order, field_type, field_size, placeholder, field_tips_for_templete, extension_attrs, field_introduction, regist_hhmmss, up_hhmmss) VALUES('activity_id','活动ID’,'activity_id', 26, 1, 255, '请输入活动ID', '可为空', null, null, '2016-07-26 17:00:00', '2016-07-26 17:00:00');

INSERT INTO ms_r_tag_field(tag_id,field_name,regist_hhmmss,up_hhmmss) VALUES(2,'activity_id','2016-07-26 17:00:00','2016-07-26 17:00:00');
INSERT INTO ms_r_tag_field(tag_id,field_name,regist_hhmmss,up_hhmmss) VALUES(3,'activity_id','2016-07-26 17:00:00','2016-07-26 17:00:00');
INSERT INTO ms_r_tag_field(tag_id,field_name,regist_hhmmss,up_hhmmss) VALUES(4,'activity_id','2016-07-26 17:00:00','2016-07-26 17:00:00');
INSERT INTO ms_r_tag_field(tag_id,field_name,regist_hhmmss,up_hhmmss) VALUES(5,'activity_id','2016-07-26 17:00:00','2016-07-26 17:00:00');
INSERT INTO ms_r_tag_field(tag_id,field_name,regist_hhmmss,up_hhmmss) VALUES(6,'activity_id','2016-07-26 17:00:00','2016-07-26 17:00:00');
