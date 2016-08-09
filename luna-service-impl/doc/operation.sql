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


# 修改ms_poi_field,添加标签英文版本
update ms_poi_field set extension_attrs='{\"zh\":[{1:\"人文\"},{2:\"自然\"},{3:\"美食\"},{4:\"购物\"},{5:\"娱乐\"},{6:\"特色\"},{7:\"其他\"}],\"en\":[{1:\"Humanities\"},{2:\"Natural\"},{3:\"Food\"},{4:\"Shopping\"},{5:\"Entertainment\"},{6:\"Feature\"},{7:\"Others\"}]}' where field_name='scene_type';
update ms_poi_field set extension_attrs='{\"zh\":[{1:\"度假\"},{2:\"商务\"},{3:\"亲子\"},{4:\"特色\"},{5:\"其他\"}],\"en\":[{1:\"Holiday\"},{2:\"Business\"},{3:\"Family\"},{4:\"Feature\"},{5:\"Others\"}]}' where field_name='type';

# 添加一级类别,二级类别英文版本
alter table ms_poi_tag add column tag_name_en varchar(32) comment '英文名';

update ms_poi_tag set tag_name_en='Common TAG' where tag_id=1;
update ms_poi_tag set tag_name_en='Tourist attractions scenic spots' where tag_id=2;
update ms_poi_tag set tag_name_en='Accommodation services' where tag_id=3;
update ms_poi_tag set tag_name_en='Food and beverage service' where tag_id=4;
update ms_poi_tag set tag_name_en='Leisure and entertainment' where tag_id=5;
update ms_poi_tag set tag_name_en='Shopping' where tag_id=6;
update ms_poi_tag set tag_name_en='Infrastructure base installation' where tag_id=7;
update ms_poi_tag set tag_name_en='Others' where tag_id=8;
update ms_poi_tag set tag_name_en='Resorts/Scenic spots' where tag_id=9;
update ms_poi_tag set tag_name_en='Park' where tag_id=10;
update ms_poi_tag set tag_name_en='Botanical garden' where tag_id=11;
update ms_poi_tag set tag_name_en='Zoo' where tag_id=12;
update ms_poi_tag set tag_name_en='Aquarium' where tag_id=13;
update ms_poi_tag set tag_name_en='City square' where tag_id=14;
update ms_poi_tag set tag_name_en='Art gallery/memorial hall ' where tag_id=15;
update ms_poi_tag set tag_name_en='Taoist temples' where tag_id=16;
update ms_poi_tag set tag_name_en='Church' where tag_id=17;
update ms_poi_tag set tag_name_en='Others' where tag_id=18;
update ms_poi_tag set tag_name_en='Hotel' where tag_id=19;
update ms_poi_tag set tag_name_en='Featured bed and breakfast' where tag_id=20;
update ms_poi_tag set tag_name_en='Farm house' where tag_id=21;
update ms_poi_tag set tag_name_en='Others' where tag_id=22;
update ms_poi_tag set tag_name_en='Chinese restaurant' where tag_id=23;
update ms_poi_tag set tag_name_en='Western restaurant' where tag_id=24;
update ms_poi_tag set tag_name_en='Fast food' where tag_id=25;
update ms_poi_tag set tag_name_en='Buffet' where tag_id=26;
update ms_poi_tag set tag_name_en='Japanese food' where tag_id=27;
update ms_poi_tag set tag_name_en='Southeast Asian meal' where tag_id=28;
update ms_poi_tag set tag_name_en='Cold store' where tag_id=29;
update ms_poi_tag set tag_name_en='Bread dessert' where tag_id=30;
update ms_poi_tag set tag_name_en='Others' where tag_id=31;
update ms_poi_tag set tag_name_en='Nightclub' where tag_id=32;
update ms_poi_tag set tag_name_en='KTV' where tag_id=33;
update ms_poi_tag set tag_name_en='Disco' where tag_id=34;
update ms_poi_tag set tag_name_en='Bar' where tag_id=35;
update ms_poi_tag set tag_name_en='Teahouse' where tag_id=36;
update ms_poi_tag set tag_name_en='Cafe' where tag_id=37;
update ms_poi_tag set tag_name_en='Playground' where tag_id=38;
update ms_poi_tag set tag_name_en='Fishing park' where tag_id=39;
update ms_poi_tag set tag_name_en='Picking park' where tag_id=40;
update ms_poi_tag set tag_name_en='Water sports centre' where tag_id=41;
update ms_poi_tag set tag_name_en='Others' where tag_id=42;
update ms_poi_tag set tag_name_en='Integrated shopping mall' where tag_id=43;
update ms_poi_tag set tag_name_en='Convenience store' where tag_id=44;
update ms_poi_tag set tag_name_en='Commercial street' where tag_id=45;
update ms_poi_tag set tag_name_en='Supermarket' where tag_id=46;
update ms_poi_tag set tag_name_en='Pet market' where tag_id=47;
update ms_poi_tag set tag_name_en='Vegetable&Fish market' where tag_id=48;
update ms_poi_tag set tag_name_en='Shop' where tag_id=49;
update ms_poi_tag set tag_name_en='Boutiques' where tag_id=50;
update ms_poi_tag set tag_name_en='Others' where tag_id=51;
update ms_poi_tag set tag_name_en='Airport' where tag_id=52;
update ms_poi_tag set tag_name_en='Ticket office' where tag_id=53;
update ms_poi_tag set tag_name_en='Train station' where tag_id=54;
update ms_poi_tag set tag_name_en='Bus stop' where tag_id=55;
update ms_poi_tag set tag_name_en='Parking lot' where tag_id=56;
update ms_poi_tag set tag_name_en='Toilets' where tag_id=57;
update ms_poi_tag set tag_name_en='Public bicycles' where tag_id=58;
update ms_poi_tag set tag_name_en='Bank/ATM' where tag_id=59;
update ms_poi_tag set tag_name_en='Medical' where tag_id=60;
update ms_poi_tag set tag_name_en='Others' where tag_id=61;
update ms_poi_tag set tag_name_en='Others' where tag_id=62;