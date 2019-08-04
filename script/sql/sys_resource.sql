/*
Navicat MySQL Data Transfer

Source Server         : 项目测试库
Source Server Version : 50543
Source Host           : 172.30.10.78:3306
Source Database       : tradingdesk

Target Server Type    : MYSQL
Target Server Version : 50543
File Encoding         : 65001

Date: 2019-08-04 13:40:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '资源名称',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '资源排序',
  `href` varchar(45) DEFAULT NULL COMMENT '资源地址',
  `parent_id` bigint(20) NOT NULL,
  `type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '资源类型 1:菜单 2:按钮',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`,`sort`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COMMENT='系统资源表（菜单、按钮）';
