CREATE TABLE `ms_show_page_share`(
  `id` int(11) AUTO_INCREMENT,
  `business_id` INT(11) NOT NULL,
  `title` varchar(32) NOT NULL ,
  `description` VARCHAR(128) NOT NULL,
  `pic` VARCHAR(128) NOT NULL,
  PRIMARY KEY (id),
  KEY (business_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '微景展分享信息';