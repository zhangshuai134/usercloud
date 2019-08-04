/*
Navicat MySQL Data Transfer

Source Server         : 项目测试库
Source Server Version : 50543
Source Host           : 172.30.10.78:3306
Source Database       : tradingdesk

Target Server Type    : MYSQL
Target Server Version : 50543
File Encoding         : 65001

Date: 2019-08-04 13:40:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '角色名称',
  `enname` varchar(45) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';
