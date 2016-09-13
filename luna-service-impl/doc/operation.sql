
CREATE TABLE `luna_order` (
  `id` int(11) AUTO_INCREMENT COMMENT '订单编号',
  `transaction_id` varchar(32) DEFAULT NULL COMMENT '皓月交易号',
  `certificate_num` varchar(8) DEFAULT NULL COMMENT '凭证号',
  `total_money` decimal(10,2) NOT NULL COMMENT '总金额',
  `pay_money` decimal(10,2) DEFAULT NULL COMMENT '实付款',
  `refund` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `status` int(4) NOT NULL COMMENT '订单状态',
  `pay_method` tinyint(1) NOT NULL COMMENT '支付方式。0：微信，1：支付宝',
  `merchant_id` varchar(32) NOT NULL COMMENT '商户id',
  `customer_name` varchar(64) NOT NULL COMMENT '客户姓名',
  `customer_phone` varchar(16) NOT NULL COMMENT '客户电话',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '付款时间',
  `deal_time` timestamp NULL DEFAULT NULL COMMENT '成交时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `transaction_id` (`transaction_id`),
  UNIQUE KEY `certificate_num` (`merchant_id`,`certificate_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

