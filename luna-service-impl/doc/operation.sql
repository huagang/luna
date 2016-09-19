create table room_basic_info (
  id int(11) AUTO_INCREMENT comment '房间id',
  name VARCHAR(64) NOT NULL comment '房间名称',
  merchant_id VARCHAR(32) NOT NULL COMMENT '商户id',
  cate_id INT NOT NULL COMMENT '商品类别',
  start_time DATETIME NOT NULL COMMENT '开始时间',
  end_time DATETIME NOT NULL COMMENT '结束时间',
  workday_price DOUBLE(10,2) NOT NULL COMMENT '平日价格',
  weekend_price DOUBLE(10,2) NOT NULL COMMENT '周末价格',
  count int NOT NULL DEFAULT 0 COMMENT '库存量',
  area double(10,2) NOT NULL COMMENT '总面积',
  floor int NOT NULL COMMENT '楼层',
  persons int NOT NULL COMMENT '人数',
  decoration int NOT NULL COMMENT '装修特点',
  landscape int NOT NULL COMMENT '景观特点',
  breakfast TINYINT NOT NULL DEFAULT 0 COMMENT '是否含早餐, 0:不含,1:含',
  room_num int NOT NULL DEFAULT 1 COMMENT '卧室数目',
  bed_info TEXT NOT NULL COMMENT '床型',
  basic_equip VARCHAR(32) NOT NULL COMMENT '基础设施',
  bedroom_equip VARCHAR(32) NOT NULL COMMENT '卧室',
  bath_equip VARCHAR(32) NOT NULL COMMENT '卫浴',
  cook_equip VARCHAR(32) NOT NULL COMMENT '餐厨',
  entertainment_equip VARCHAR(32) NOT NULL COMMENT '娱乐',
  pic_type int NOT NULL COMMENT '素材类型: 0:全景,1:平面图,2:无',
  pic_info TEXT COMMENT '图片内容',
  is_online TINYINT DEFAULT 0 COMMENT '是否上架',
  is_del TINYINT DEFAULT 0 COMMENT '是否删除',
  unique_id varchar(32) not null COMMENT '操作用户id',
  publish_time TIMESTAMP NULL DEFAULT NULL COMMENT '上架时间',
  create_time TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  update_time TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE (merchant_id, name),
  key(update_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间基础信息表';

create table room_dynamic_info(
  room_id int(11) NOT NULL COMMENT '房间id',
  day DATETIME NOT NULL COMMENT '日期',
  is_special TINYINT NOT NULL DEFAULT 0 COMMENT '是否单独设置,避免被批量更新',
  count int NOT NULL COMMENT '库存数量',
  sold INT NOT NULL DEFAULT 0 COMMENT '已卖数量',
  price DOUBLE(10,2) NOT NULL COMMENT '价格',
  create_time TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  update_time TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (room_id, day),
  key(day),
  KEY(update_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间动态信息表';

create table form_field (
  id int auto_increment,
  table_name varchar(32) not null COMMENT '表名称',
  lable_name varchar(32) NOT NULL COMMENT '元素标签名称',
  field_name varchar(32) not null COMMENT '字段名称',
  display_order int NOT NULL COMMENT '元素页面显示顺序',
  type varchar(16) not null COMMENT '页面元素类型',
  description varchar(128) COMMENT '描述字段信息',
  validator text COMMENT '页面元素约束描述',
  field_validator text COMMENT '字段约束描述',
  data text COMMENT '页面元素绑定的数据',
  default_value VARCHAR(16) COMMENT '页面元素默认值',
  placeholder varchar(32) COMMENT '元素内提示内容',
  editable tinyint not null default 1 COMMENT '元素是否可编辑',
  create_time TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
  update_time TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  primary key(id),
  unique(table_name, field_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通用表单字段设置';

create table goods_category_table (
  category_id int not null COMMENT '商品类别id',
  table_name varchar(32) not NULL COMMENT '表名称',
  PRIMARY KEY (category_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='不同商品类别对应的数据表';

insert into form_field(table_name, lable_name, field_name, display_order, type, description, validator, field_validator, placeholder, create_time)
    VALUES ('room_basic_info', '商品名称', 'name', 1, 'text', '商品名称', '"text":[{"type":"string", "required":true, min:2, max:64}]', '{"type":"string", "required":true, min:2, max:64}', "输入商品名称,不超过64个字符", '2016-09-18 10:20:00');

CREATE TABLE `luna_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `transaction_id` varchar(32) DEFAULT NULL COMMENT '皓月交易号',
  `certificate_num` varchar(8) DEFAULT NULL COMMENT '凭证号',
  `total_money` double NOT NULL DEFAULT '0' COMMENT '总金额',
  `pay_money` double NOT NULL DEFAULT '0' COMMENT '需要付款金额',
  `refund` double NOT NULL DEFAULT '0' COMMENT '退款金额',
  `status` int(4) NOT NULL COMMENT '订单状态',
  `pay_method` tinyint(1) NOT NULL COMMENT '支付方式。0：微信，1：支付宝',
  `merchant_id` varchar(32) NOT NULL COMMENT '商户id',
  `customer_name` varchar(64) NOT NULL COMMENT '客户姓名',
  `customer_phone` varchar(16) NOT NULL COMMENT '客户电话',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '付款时间',
  `deal_time` timestamp NULL DEFAULT NULL COMMENT '成交时间',
  `trade_no` char(16) DEFAULT NULL COMMENT '交易订单号',
  `paid_money` double DEFAULT NULL COMMENT '实付金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `transaction_id` (`transaction_id`),
  UNIQUE KEY `certificate_num` (`merchant_id`,`certificate_num`),
  UNIQUE KEY `trade_no_UNIQUE` (`trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';


CREATE TABLE `luna_order_goods` (
  `id` int(11) NOT NULL COMMENT '订单编号',
  `goods_id` varchar(32) NOT NULL COMMENT '商品id',
  `category_id` int(11) NOT NULL COMMENT '类别id',
  `goods_price` double NOT NULL DEFAULT '0' COMMENT '商品单价',
  `goods_count` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `extra` varchar(255) DEFAULT NULL COMMENT '拓展字段',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  KEY `id` (`id`),
  KEY `goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品对应表';