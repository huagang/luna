ALTER TABLE ms_merchant_manage ADD trade_status int(11) NOT NULL DEFAULT '0' COMMENT '商户交易直通车开通状态';
ALTER TABLE ms_merchant_manage ADD unique_id char(32) default NULL comment "关联用户的id";
ALTER TABLE ms_merchant_manage ADD unique (`unique_id`);

ALTER TABLE ms_show_app drop index `app_name`;
ALTER TABLE ms_show_app drop INDEX `business_id`;
ALTER TABLE ms_show_app add UNIQUE(business_id, app_name);

update ms_farm_field set limits ="{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name = "start_page_foreground_pic";
update ms_farm_field set limits ="{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name = "start_page_background_pic";
update ms_farm_field set limits ="{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}" where name = "manager_pic";
update ms_farm_field set limits ="{\"TEXT\":[{\"empty\":true,\"max\":255}]}" where name = "all_chosen_room_panorama_type";
update ms_farm_field set limits ="{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}],\"TEXT\":[{\"empty\":true,\"max\":255}],\"num\":{\"min\":3}}" where name = "delicacy";
update ms_farm_field set limits ="{\"TEXT\":[{\"empty\":true,\"max\":255}]}" where name = "country_enjoyment";
update ms_farm_field set limits="{\"CHECKBOX\":[{\"empty\":true}]}" where name = "facility";

alter table ms_route drop index `name`;
alter table ms_route add unique key `name` (`business_id`, `name`);