/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50538
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50538
File Encoding         : 65001

Date: 2016-07-22 06:00:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for catcher
-- ----------------------------
DROP TABLE IF EXISTS `catcher`;
CREATE TABLE `catcher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `baseInfo` longtext,
  `content` longtext,
  `creatTime` datetime DEFAULT NULL,
  `state` int(11) NOT NULL,
  `time` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `urlId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1875 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of catcher
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `method` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'user:lock', '锁定用户', '-1');
INSERT INTO `permission` VALUES ('2', 'play:qrcode', '二维码菜单', '-10');
INSERT INTO `permission` VALUES ('3', 'permission:list', '权限列表', '-1');
INSERT INTO `permission` VALUES ('4', 'role:modify', '添加角色', '-1');
INSERT INTO `permission` VALUES ('5', 'user:modify', '添加用户', '-1');
INSERT INTO `permission` VALUES ('6', 'permission:add', '增加权限', '-1');
INSERT INTO `permission` VALUES ('7', 'permission:del', '删除权限', '-1');
INSERT INTO `permission` VALUES ('8', 'play:qrcodeRun', '二维码执行', '-10');
INSERT INTO `permission` VALUES ('9', 'user:add', '添加用户', '-1');
INSERT INTO `permission` VALUES ('10', 'role:del', '删除角色', '-1');
INSERT INTO `permission` VALUES ('11', 'user:list', '用户列表', '-1');
INSERT INTO `permission` VALUES ('12', 'role:add', '添加角色', '-1');
INSERT INTO `permission` VALUES ('13', 'role:lock', '锁定角色', '-1');
INSERT INTO `permission` VALUES ('14', 'permission:lock', '锁定权限', '-1');
INSERT INTO `permission` VALUES ('15', 'permission:modify', '修改权限', '-1');
INSERT INTO `permission` VALUES ('16', 'login:exit', '退出', '-20');
INSERT INTO `permission` VALUES ('17', 'role:list', '角色列表', '-1');
INSERT INTO `permission` VALUES ('18', 'user:del', '删除用户', '-1');
INSERT INTO `permission` VALUES ('19', 'user:info', '当前用户信息', '-1');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `recordTime` datetime DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES ('1', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-19 21:56:11', 'admin');
INSERT INTO `record` VALUES ('2', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-19 22:16:08', 'admin');
INSERT INTO `record` VALUES ('3', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-19 22:16:33', 'admin');
INSERT INTO `record` VALUES ('4', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-19 22:17:25', 'admin');
INSERT INTO `record` VALUES ('5', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-19 22:18:10', 'admin');
INSERT INTO `record` VALUES ('6', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-19 22:18:12', 'admin');
INSERT INTO `record` VALUES ('7', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-19 22:20:45', 'admin');
INSERT INTO `record` VALUES ('8', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-19 22:56:44', 'admin');
INSERT INTO `record` VALUES ('9', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-19 23:48:31', 'admin');
INSERT INTO `record` VALUES ('10', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-20 00:06:47', 'admin');
INSERT INTO `record` VALUES ('11', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-20 00:40:43', 'admin');
INSERT INTO `record` VALUES ('12', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-20 21:16:41', 'admin');
INSERT INTO `record` VALUES ('13', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-20 22:16:41', 'admin');
INSERT INTO `record` VALUES ('14', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-20 22:30:08', 'admin');
INSERT INTO `record` VALUES ('15', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-20 22:30:50', 'admin');
INSERT INTO `record` VALUES ('16', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-22 04:10:17', 'admin');
INSERT INTO `record` VALUES ('17', '{}', '0:0:0:0:0:0:0:1', '当前用户信息', '2016-07-22 04:35:32', 'admin');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '-1');
INSERT INTO `role` VALUES ('2', '普通用户', '-10');
INSERT INTO `role` VALUES ('3', '黑名单', '-20');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FK_fn4pldu982p9u158rpk6nho5k` (`permission_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1');
INSERT INTO `role_permission` VALUES ('1', '2');
INSERT INTO `role_permission` VALUES ('1', '3');
INSERT INTO `role_permission` VALUES ('1', '4');
INSERT INTO `role_permission` VALUES ('1', '5');
INSERT INTO `role_permission` VALUES ('1', '6');
INSERT INTO `role_permission` VALUES ('1', '7');
INSERT INTO `role_permission` VALUES ('1', '8');
INSERT INTO `role_permission` VALUES ('1', '9');
INSERT INTO `role_permission` VALUES ('1', '10');
INSERT INTO `role_permission` VALUES ('1', '11');
INSERT INTO `role_permission` VALUES ('1', '12');
INSERT INTO `role_permission` VALUES ('1', '13');
INSERT INTO `role_permission` VALUES ('1', '14');
INSERT INTO `role_permission` VALUES ('1', '15');
INSERT INTO `role_permission` VALUES ('1', '16');
INSERT INTO `role_permission` VALUES ('1', '17');
INSERT INTO `role_permission` VALUES ('1', '18');
INSERT INTO `role_permission` VALUES ('1', '19');
INSERT INTO `role_permission` VALUES ('2', '2');
INSERT INTO `role_permission` VALUES ('2', '8');
INSERT INTO `role_permission` VALUES ('2', '16');
INSERT INTO `role_permission` VALUES ('3', '16');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `a` char(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------

-- ----------------------------
-- Table structure for urlsinfo
-- ----------------------------
DROP TABLE IF EXISTS `urlsinfo`;
CREATE TABLE `urlsinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `careatTime` datetime DEFAULT NULL,
  `contentPattern` varchar(255) DEFAULT NULL,
  `keyWord` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `otherPattern` varchar(255) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `timePattern` varchar(255) DEFAULT NULL,
  `titlePattern` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `urlPattern` varchar(255) DEFAULT NULL,
  `urls` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of urlsinfo
-- ----------------------------
INSERT INTO `urlsinfo` VALUES ('1', '2016-07-20 00:46:25', '#ozoom', '王安顺', '重庆日报', null, '1', null, 'td&gt;strong', '0', null, 'http://www.cqrb.cn/html/cqrb/%yyyy-MM/dd%/001/node.htm');
INSERT INTO `urlsinfo` VALUES ('2', '2016-07-20 00:50:40', '.content', '王安顺', '解放日报（上海）', null, '1', null, '.title :not(h5)', '0', null, 'http://newspaper.jfdaily.com/jfrb/html/%yyyy-MM/dd%/node_2.htm');
INSERT INTO `urlsinfo` VALUES ('3', '2016-07-20 21:56:08', '', '王安顺', '广西日报', null, '1', null, '', '1', null, 'http://gxrb.gxnews.com.cn/html/%yyyy-MM/dd%/node_5.htm');
INSERT INTO `urlsinfo` VALUES ('4', '2016-07-19 21:56:34', '', '王安顺', '海南日报', '', '1', '', '', '0', '', 'http://hnrb.hinews.cn/html/%yyyy-MM/dd%/node_1.htm');
INSERT INTO `urlsinfo` VALUES ('5', '2016-07-22 04:25:50', '', '王安顺', '北京日报', null, '0', null, '', '0', null, 'http://bjrb.bjd.com.cn/html/%yyyy-MM/dd%/node_1.htm');
INSERT INTO `urlsinfo` VALUES ('6', '2016-07-19 21:56:34', '', '王安顺', '河北日报', '', '1', '', '', '0', '', 'http://hbrb.hebnews.cn/html/%yyyy-MM/dd%/node_2.htm');
INSERT INTO `urlsinfo` VALUES ('7', '2016-07-19 21:56:34', '', '王安顺', '福建日报', '', '1', '', '', '0', '', 'http://fjrb.fjsen.com/fjrb/html/%yyyy-MM/dd%/node_22.htm');
INSERT INTO `urlsinfo` VALUES ('8', '2016-07-19 21:56:34', '', '王安顺', '天津日报', '', '1', '', '', '0', '', 'http://epaper.tianjinwe.com/tjrb/tjrb/%yyyy-MM/dd%/node_2.htm');
INSERT INTO `urlsinfo` VALUES ('9', '2016-07-19 21:56:34', '', '王安顺', '湖北日报', '', '1', '', '', '0', '', 'http://hbrb.cnhubei.com/HTML/hbrb/%yyyyMMdd%/');
INSERT INTO `urlsinfo` VALUES ('10', '2016-07-19 21:56:34', '', '王安顺', '陕西日报', '', '1', '', '', '0', '', 'http://esb.sxdaily.com.cn/sxrb/%yyyyMMdd%/html/index.htm');
INSERT INTO `urlsinfo` VALUES ('11', '2016-07-19 21:56:34', '', '王安顺', '江西日报', '', '1', '', '', '0', '', 'http://epaper.jxnews.com.cn/jxrb/html/%yyyy-MM/dd%/node_187.htm');
INSERT INTO `urlsinfo` VALUES ('12', '2016-07-19 21:56:34', '', '王安顺', '宁夏日报', '', '1', '', '', '0', '', 'http://sz.nxnews.net/nxrb/html/%yyyy-MM/dd%/node_2.htm');
INSERT INTO `urlsinfo` VALUES ('13', '2016-07-19 21:56:34', '', '王安顺', '湖南日报', '', '1', '', '', '0', '', 'http://hnrb.voc.com.cn/hnrb_epaper/html/%yyyy-MM/dd%/node_201.htm');
INSERT INTO `urlsinfo` VALUES ('14', '2016-07-19 21:56:34', '', '王安顺', '甘肃日报', '', '1', '', '', '0', '', 'http://epaper.gansudaily.com.cn/gsrb/html/%yyyy-MM/dd%/node_2.htm');
INSERT INTO `urlsinfo` VALUES ('15', '2016-07-19 21:56:34', '', '王安顺', '新华日报（江苏）', '', '1', '', '', '0', '', 'http://xhrb.jschina.com.cn/mp2/html/%yyyy-MM/dd%/node_2.htm');
INSERT INTO `urlsinfo` VALUES ('16', '2016-07-19 21:56:34', '', '王安顺', '浙江日报', '', '1', '', '', '0', '', 'http://zjrb.zjol.com.cn/html/%yyyy-MM/dd%/node_18.htm');
INSERT INTO `urlsinfo` VALUES ('17', '2016-07-19 21:56:34', '', '王安顺', '安徽日报', '', '1', '', '', '0', '', 'http://epaper.anhuinews.com/html/ahrb/%yyyyMMdd%/');
INSERT INTO `urlsinfo` VALUES ('18', '2016-07-19 21:56:34', '', '王安顺', '贵州日报', '', '1', '', '', '0', '', 'http://szb.gzrbs.com.cn/gzrb/gzrb/rb/%yyyyMMdd%/Page01OW.htm');
INSERT INTO `urlsinfo` VALUES ('19', '2016-07-19 21:56:34', '', '王安顺', '云南日报', '', '1', '', '', '0', '', 'http://yndaily.yunnan.cn/html/%yyyy-MM/dd%/node_2.htm');
INSERT INTO `urlsinfo` VALUES ('20', '2016-07-19 21:56:34', '', '王安顺', '山西日报', '', '1', '', '', '0', '', 'http://epaper.sxrb.com/shtml/sxrb/%yyyyMMdd%/index.shtml');
INSERT INTO `urlsinfo` VALUES ('21', '2016-07-19 21:56:34', '', '王安顺', '河南日报', '', '1', '', '', '0', '', 'http://newpaper.dahe.cn/hnrb/html/%yyyy-MM/dd%/node_2.htm');
INSERT INTO `urlsinfo` VALUES ('22', '2016-07-19 21:56:34', '', '王安顺', '大众日报（山东）', '', '1', '', '', '0', '', 'http://paper.dzwww.com/dzrb/content/%yyyyMMdd%/Page01NU.htm');
INSERT INTO `urlsinfo` VALUES ('23', '2016-07-19 21:56:34', '', '王安顺', '新疆日报', '', '1', '', '', '0', '', 'http://epaper.xjdaily.com/index.aspx?date=%yyyy-MM-dd%&paperType=xjrb');
INSERT INTO `urlsinfo` VALUES ('24', '2016-07-19 21:56:34', '', '王安顺', '辽宁日报', '', '1', '', '', '0', '', 'http://epaper.lnd.com.cn/paper/lnrb/html/%yyyy-MM/dd%/node_2.htm');
INSERT INTO `urlsinfo` VALUES ('25', '2016-07-19 21:56:34', '', '王安顺', '青海日报', '', '1', '', '', '0', '', 'http://epaper.tibet3.com/qhrb/html/%yyyy-MM/dd%/node_2.htm');
INSERT INTO `urlsinfo` VALUES ('26', '2016-07-19 21:56:34', '', '王安顺', '西藏日报', '', '1', '', '', '0', '', 'http://epaper.chinatibetnews.com/xzrb/html/%yyyy-MM/dd%/node_4.htm');
INSERT INTO `urlsinfo` VALUES ('27', '2016-07-19 21:56:34', '', '王安顺', '黑龙江日报', '', '1', '', '', '0', '', 'http://epaper.hljnews.cn/hljrb/%yyyyMMdd%/1.html');
INSERT INTO `urlsinfo` VALUES ('28', '2016-07-19 21:56:34', '', '王安顺', '南方日报（广东）', '', '1', '', '', '0', '', 'http://epaper.southcn.com/nfdaily/html/%yyyy-MM/dd%/node_581.htm');
INSERT INTO `urlsinfo` VALUES ('29', '2016-07-19 21:56:34', '', '王安顺', '四川日报', '', '1', '', '', '0', '', 'http://epaper.scdaily.cn/shtml/scrb/%yyyyMMdd%/index.shtml');
INSERT INTO `urlsinfo` VALUES ('30', '2016-07-19 21:56:34', '', '王安顺', '内蒙古日报', '', '1', '', '', '0', '', 'http://szb.northnews.cn/nepaper/nmgrb/html/%yyyy-MM/dd%/node_3.htm');
INSERT INTO `urlsinfo` VALUES ('31', '2016-07-19 21:56:34', '', '王安顺', '吉林日报', '', '1', '', '', '0', '', 'http://jlrbszb.chinajilin.com.cn/html/%yyyy-MM/dd%/node_267.htm');
INSERT INTO `urlsinfo` VALUES ('60', '2016-07-20 22:49:36', '', '王安顺', '重庆日报', null, '1', null, '', '0', null, 'http://epaper.cqrb.cn/html/cqrb/%yyyy-MM/dd%/001/node.htm');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `headImg` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gj2fy3dcix7ph7k8684gka40c` (`name`),
  UNIQUE KEY `UK_b3kndshgdve0qsvxejarm579s` (`nickName`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin@yuanayuan.com', null, 'admin', '超级管理员', '105ED4564E73AE7F9DBACFBF93848894', '男', '-1');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_it77eq964jhfqtu54081ebtio` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
