/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50521
Source Host           : localhost:3306
Source Database       : liqihua_basic

Target Server Type    : MYSQL
Target Server Version : 50521
File Encoding         : 65001

Date: 2018-10-18 10:15:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for test_person
-- ----------------------------
DROP TABLE IF EXISTS `test_person`;
CREATE TABLE `test_person` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '人员',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `gender` bit(1) DEFAULT NULL COMMENT '性别：0女 1男',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `birthday` date DEFAULT NULL COMMENT '出生时间',
  `work_time` datetime DEFAULT NULL COMMENT '上班时间',
  `intro` text COMMENT '个人简介',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
