/*
Navicat MySQL Data Transfer

Source Server         : 项目测试库
Source Server Version : 50543
Source Host           : 172.30.10.78:3306
Source Database       : tradingdesk

Target Server Type    : MYSQL
Target Server Version : 50543
File Encoding         : 65001

Date: 2019-08-04 13:40:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `login_name` varchar(45) NOT NULL COMMENT '登录名',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `name` varchar(45) DEFAULT NULL COMMENT '姓名',
  `lang` enum('en','zh','ja') DEFAULT 'en',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `profile_picture` varchar(200) DEFAULT NULL COMMENT '头像',
  `phone` varchar(45) DEFAULT NULL,
  `user_id` varchar(128) NOT NULL DEFAULT 'abc',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `login_name_UNIQUE` (`login_name`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
