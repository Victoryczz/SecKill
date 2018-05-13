CREATE TABLE `seckill_test`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` VARCHAR(50) DEFAULT NULL COMMENT 'name',
  PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE `seckill_user`(
  `id` BIGINT(20) NOT NULL COMMENT '用户ID，手机号码',
  `nickname` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(32) DEFAULT NULL COMMENT 'MD5(MD5(pass+salt)+salt)',
  `salt` VARCHAR(10) DEFAULT NULL ,
  `head` VARCHAR(128) DEFAULT NULL COMMENT '头像,图片ID',
  `register_date` DATETIME DEFAULT NULL COMMENT '注册时间',
  `last_login_date` DATETIME DEFAULT NULL COMMENT '上次登录时间',
  `login_count` INT(11) DEFAULT '0' COMMENT '登录次数',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片ID',
  `goods_detail` longtext COMMENT '商品详情',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存,-1代表没有限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `seckill_goods` VALUES ('1', 'phonex', '双十一iphonex', '/img/iphonex.png', '性价比较高', '9999.00', '20');
INSERT INTO `seckill_goods` VALUES (NULL , 'meto10', '华为mate10', '/img/meta10.png', '限时降价', '1099.00', '20');

-- ----------------------------
-- Table structure for `miaosha_goods`
-- ----------------------------
DROP TABLE IF EXISTS `seckill_sk_goods`;
CREATE TABLE `seckill_sk_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `miaosha_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价格',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间' ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of miaosha_goods
-- ----------------------------
INSERT INTO `miaosha_goods` VALUES ('1', '1', '1.11', '6', '2018-01-30 15:16:37', '2018-02-02 17:16:09');
INSERT INTO `miaosha_goods` VALUES ('2', '2', '0.99', '6', '2018-01-30 14:18:34', '2018-02-02 17:16:40');

-- ----------------------------
-- Table structure for `order_info`
-- ----------------------------
DROP TABLE IF EXISTS `seckill_order`;
CREATE TABLE `seckill_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收货地址ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `order_channel` tinyint(4) DEFAULT '0' COMMENT '订单渠道，1pc,2android,3ios',
  `status` tinyint(4) DEFAULT '0' COMMENT '订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退货，5已完成',
  `create_date` datetime DEFAULT NULL COMMENT '订单创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
-- ----------------------------
-- Table structure for `miaosha_order`
-- ----------------------------
DROP TABLE IF EXISTS `seckill_sk_order`;
CREATE TABLE `seckill_sk_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
