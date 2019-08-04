/*
Navicat MySQL Data Transfer

Source Server         : 项目测试库
Source Server Version : 50543
Source Host           : 172.30.10.78:3306
Source Database       : tradingdesk

Target Server Type    : MYSQL
Target Server Version : 50543
File Encoding         : 65001

Date: 2019-08-04 13:40:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id，无实意',
  `user_id` varchar(128) NOT NULL COMMENT '用户id',
  `role_id` varchar(128) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';
