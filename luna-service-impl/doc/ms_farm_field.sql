# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 115.159.67.120 (MySQL 5.1.73)
# Database: hydb2
# Generation Time: 2016-08-14 03:44:22 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table ms_farm_field
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ms_farm_field`;

CREATE TABLE `ms_farm_field` (
  `name` varchar(64) DEFAULT NULL,
  `show_name` varchar(64) DEFAULT NULL,
  `display_order` int(11) NOT NULL COMMENT '字段顺序',
  `type` varchar(64) DEFAULT NULL,
  `limits` varchar(128) DEFAULT NULL,
  `placeholder` varchar(64) DEFAULT '' COMMENT '占位符显示',
  `options` text,
  `default_value` varchar(32) DEFAULT NULL COMMENT '默认值',
  `regist_hhmmss` timestamp NULL DEFAULT NULL,
  `up_hhmmss` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  KEY `field_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `ms_farm_field` WRITE;
/*!40000 ALTER TABLE `ms_farm_field` DISABLE KEYS */;

INSERT INTO `ms_farm_field` (`name`, `show_name`, `display_order`, `type`, `limits`, `placeholder`, `options`, `default_value`, `regist_hhmmss`, `up_hhmmss`)
VALUES
	('poi_info','POI 信息',1,'POI',NULL,'{\"TEXT\":[\"请输入POI id或名称\"]}',NULL,NULL,'2016-07-25 00:00:00','2016-08-06 14:59:49'),
	('start_page_foreground_pic','启动页前景图',2,'PIC','{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}',NULL,NULL,NULL,'2016-07-25 00:00:00','2016-08-10 10:45:49'),
	('start_page_background_pic','启动页背景图',3,'PIC','{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}',NULL,NULL,NULL,'2016-07-25 00:00:00','2016-08-12 14:30:56'),
	('manager_pic','经营者图片',4,'PIC','{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}]}',NULL,NULL,NULL,'2016-07-25 00:00:00','2016-08-10 10:45:52'),
	('manager_self_introduction','经营者自述',5,'TEXTAREA','{\"TEXTAREA\":[{\"empty\":true,\"max\":1024}]}',NULL,NULL,NULL,'2016-07-25 00:00:00','2016-08-10 10:46:25'),
	('well_chosen_room_panorama_type','精选房间全景标识',6,'RADIO_TEXT','{\"TEXT\":[{\"empty\":true,\"max\":255}]}','{\"TEXT\":[\"请输入相冊id或单场景点id\"]}','[{\"value\":2,\"label\":\"相册\"}]','{\"value\":2,\"label\":\"相册\"}','2016-07-25 00:00:00','2016-08-12 12:43:23'),
	('all_chosen_room_panorama_type','全部房间全景标识 ',7,'RADIO_TEXT','{\"TEXT\":[{\"empty\":true,\"max\":255}]}','{\"TEXT\":[\"请输入相冊id或单场景点id\"]}','[{\"value\":2,\"label\":\"相册\"},{\"value\":3,\"label\":\"自定义接口\"}]','{}','2016-07-25 00:00:00','2016-08-12 12:43:32'),
	('delicacy','美食',8,'TEXT_PIC_GROUP','{\"PIC\":[{\"empty\":true,\"max\":20,\"ext\":[\"png\",\"jpg\"]}],\"TEXT\":[{\"empty\":true,\"max\":255}],\"num\":{\"min\":3}}','{\"TEXT\":[\"请输入名称\"],\"PIC\":[\"请上传美食图片，尺寸要求，文件大小要求\"]}',NULL,NULL,'2016-07-25 00:00:00','2016-08-10 22:52:36'),
	('country_enjoyment','乡村野趣',9,'COUNTRY_ENJOYMENT','{\"TEXT\":[{\"empty\":true,\"max\":255}]}','{\"TEXT\":[\"请输入项目名称\"]}','[{\"value\":1,\"name\":\"采摘\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/192v112A0o0j1Z0V2f0m2F3y0A3v2r3Q.jpg\"},{\"value\":2,\"name\":\"垂钓\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/0L3h383v3g1h0J102V1C3g0V3j3f102v.jpg\"},{\"value\":3,\"name\":\"烧烤\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/2U2m3J2T1m0q1_2n0i1w3y2T2S1F281s.jpg\"},{\"value\":4,\"name\":\"篝火\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/3M3p2G2b11192D3E0Y2U1B0a342G0u3Y.jpg\"},{\"value\":5,\"name\":\"登山\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/0i1m0G19090D1V071v390q1i1f3n0J0f.jpg\"},{\"value\":6,\"name\":\"种植\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/2K2s3F2q1x1X2U2J2-1L2P1O23162u0n.jpg\"},{\"value\":7,\"name\":\"品酒\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/3G25083K2g0R1k31040m213y2X0M1033.jpg\"},{\"value\":8,\"name\":\"骑行\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/1Z010l2k0N2Y2b1S1e0X36121u1j352d.jpg\"},{\"value\":9,\"name\":\"自驾\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/2O120x3T1W2J0t1n3h2_3b3j123i2f2r.jpg\"},{\"value\":10,\"name\":\"徒步\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/1W160s3B1v050V2A0j0z3Y2A10381P2O.jpg\"},{\"value\":11,\"name\":\"滑草\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/20061J3x0H3X0p022S0T0r0W3O2q3w3M.jpg\"},{\"value\":12,\"name\":\"滑雪\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/1N3W1J23032B0-2k1i1q15232f2L0g2N.jpg\"},{\"value\":13,\"name\":\"赏花\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/2a3O2O0V2R0R0U2v1s0Z3b0z1D3I1c13.png\"},{\"value\":14,\"name\":\"游船\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/2r3A063-0k1P3P3A24023p2W1b1W3C11.jpg\"},{\"value\":15,\"name\":\"观鸟\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/2g3C3Y0b1q1e0A1T332B0k1K3d0F1F2w.jpg\"},{\"value\":16,\"name\":\"摄影\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/3k2N0w03030I0C2t1Q2w2n293m1A1L1n.jpg\"},{\"value\":17,\"name\":\"露营\",\"pic\":\"http://view.luna.visualbusiness.cn/release/poi/pic/20160814/0K3P0w1h1p2k3-2D2V3G2t3Z0p2B0C0o.jpg\"}]','[]','2016-07-25 00:00:00','2016-08-14 10:37:53'),
	('facility','场地设施',10,'CHECKBOX',NULL,NULL,'[{\"value\":1,\"label\":\"餐厅\"},{\"value\":2,\"label\":\"观景露台\"},{\"value\":3,\"label\":\"停车场\"},{\"value\":4,\"label\":\"素质拓展基地\"},{\"value\":5,\"label\":\"会议室\"},{\"value\":6,\"label\":\"儿童乐园\"},{\"value\":7,\"label\":\"垂钓园\"},{\"value\":8,\"label\":\"采摘园\"},{\"value\":9,\"label\":\"绿植园\"},{\"value\":10,\"label\":\"篝火场\"},{\"value\":11,\"label\":\"自助烧烤场\"},{\"value\":12,\"label\":\"KTV\"},{\"value\":13,\"label\":\"棋牌室\"},{\"value\":14,\"label\":\"健身房\"},{\"value\":15,\"label\":\"乒乓球场\"},{\"value\":16,\"label\":\"足球场\"},{\"value\":17,\"label\":\"篮球场\"},{\"value\":18,\"label\":\"羽毛球场\"}]','[]','2016-07-25 00:00:00','2016-08-13 15:42:09');

/*!40000 ALTER TABLE `ms_farm_field` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
