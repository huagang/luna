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

INSERT INTO ms_resource_uri(resource_id, resource_name, parent_id, resource_uri, level_type, `status`, regist_hhmmss, up_hhmmss)
VALUES (157, '获取文章栏目', 132, '/manage/article.do?method=read_column', 3, 1, '2016-07-24 22:57:17', '2016-07-24 22:57:17');