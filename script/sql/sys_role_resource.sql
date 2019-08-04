/*
Navicat MySQL Data Transfer

Source Server         : 项目测试库
Source Server Version : 50543
Source Host           : 172.30.10.78:3306
Source Database       : tradingdesk

Target Server Type    : MYSQL
Target Server Version : 50543
File Encoding         : 65001

Date: 2019-08-04 13:40:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1123 DEFAULT CHARSET=utf8mb4 COMMENT='角色资源表';
