/*
Navicat MySQL Data Transfer

Source Server         : 问答
Source Server Version : 50733
Source Host           : 59.110.23.184:3306
Source Database       : ask-community

Target Server Type    : MYSQL
Target Server Version : 50733
File Encoding         : 65001

Date: 2021-07-03 23:05:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for talk
-- ----------------------------
DROP TABLE IF EXISTS `talk`;
CREATE TABLE `talk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标题',
  `description` varchar(1024) CHARACTER SET utf8mb4 NOT NULL COMMENT '描述',
  `tag` varchar(128) DEFAULT NULL COMMENT '标签',
  `images` varchar(2048) DEFAULT NULL COMMENT '图片地址',
  `video` varchar(128) DEFAULT NULL COMMENT '视频地址',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '类型',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态',
  `permission` int(11) NOT NULL DEFAULT '0' COMMENT '权限',
  `creator` bigint(20) NOT NULL COMMENT '发布者',
  `view_count` int(11) NOT NULL DEFAULT '0' COMMENT '浏览数',
  `comment_count` int(11) NOT NULL DEFAULT '0' COMMENT '评论数',
  `like_count` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `gmt_latest_comment` bigint(20) NOT NULL COMMENT '最后评论时间',
  `created` bigint(20) NOT NULL COMMENT '创建时间',
  `updated` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of talk
-- ----------------------------

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created` bigint(20) DEFAULT NULL,
  `like_count` int(10) DEFAULT NULL,
  `type` bigint(1) DEFAULT NULL,
  `comment_count` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
INSERT INTO `tb_comment` VALUES ('37', '1', '12', '求指教！！！！', '1621066659328', '1', '1', '0');
INSERT INTO `tb_comment` VALUES ('38', '1', '5', '为什么Win10不行？', '1621066719559', '0', '2', '3');
INSERT INTO `tb_comment` VALUES ('39', '1', '10', '虚拟机内存与操作系统有什么关联？', '1621066761538', '0', '2', '0');
INSERT INTO `tb_comment` VALUES ('40', '6', '38', '123', '1621248363995', '0', '9', '0');
INSERT INTO `tb_comment` VALUES ('41', '6', '38', '123456', '1621248373616', '0', '9', '0');
INSERT INTO `tb_comment` VALUES ('42', '6', '38', '回复 @Aries社区_编号107781 ：789', '1621248390869', '0', '9', '0');
INSERT INTO `tb_comment` VALUES ('43', '1', '15', '除了这个特点还有什么？', '1622889241816', '1', '2', '1');
INSERT INTO `tb_comment` VALUES ('44', '1', '43', '还可以内嵌tomcat', '1622889271539', '0', '9', '0');

-- ----------------------------
-- Table structure for tb_like
-- ----------------------------
DROP TABLE IF EXISTS `tb_like`;
CREATE TABLE `tb_like` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `target_id` bigint(20) DEFAULT NULL,
  `type` int(1) DEFAULT NULL COMMENT '目标类型:\r\n1评论点赞\r\n2帖子收藏',
  `liker` bigint(20) DEFAULT NULL,
  `created` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_like
-- ----------------------------
INSERT INTO `tb_like` VALUES ('4', '7', '2', '3', '1620206491377');
INSERT INTO `tb_like` VALUES ('5', '7', '2', '2', '1620206589524');
INSERT INTO `tb_like` VALUES ('6', '5', '2', '1', '1620207512692');
INSERT INTO `tb_like` VALUES ('7', '10', '1', '1', '1620207517757');
INSERT INTO `tb_like` VALUES ('8', '37', '1', '1', '1621066666217');
INSERT INTO `tb_like` VALUES ('9', '12', '2', '1', '1621066669186');
INSERT INTO `tb_like` VALUES ('10', '11', '2', '1', '1621076684908');
INSERT INTO `tb_like` VALUES ('11', '13', '2', '1', '1621076694965');
INSERT INTO `tb_like` VALUES ('12', '43', '1', '1', '1622889247601');
INSERT INTO `tb_like` VALUES ('13', '15', '2', '1', '1622889249903');

-- ----------------------------
-- Table structure for tb_post
-- ----------------------------
DROP TABLE IF EXISTS `tb_post`;
CREATE TABLE `tb_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` bigint(1) DEFAULT NULL COMMENT '所属专栏 ：\r\n1：提问\r\n2：分享\r\n3：讨论\r\n 4：建议\r\n5:公告\r\n6:动态',
  `created` bigint(20) DEFAULT NULL,
  `updated` bigint(20) DEFAULT NULL,
  `status` bigint(1) DEFAULT NULL COMMENT '状态：1:未结。2:已结。3:精华',
  `author_id` bigint(20) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `permission` int(1) DEFAULT NULL,
  `like_count` int(10) DEFAULT NULL,
  `view_count` bigint(10) DEFAULT NULL,
  `gmt_latest_comment` bigint(20) DEFAULT NULL,
  `comment_count` bigint(10) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_post
-- ----------------------------
INSERT INTO `tb_post` VALUES ('5', '2', '1620202470248', '1621066425745', '1', '1', '笔记,公告', '1', '1', '17', null, '2', 'Python环境搭建', '<p id=\"descriptionP\">&lt;p&gt;123&lt;/p&gt;</p><p><br></p>');
INSERT INTO `tb_post` VALUES ('6', '4', '1620202511700', '1621066464669', '1', '1', '笔记,闲聊', '1', '0', '5', null, '1', '“大厂”直通车', '<p id=\"descriptionP\">&lt;p&gt;456&lt;/p&gt;</p><p><br></p>');
INSERT INTO `tb_post` VALUES ('10', '2', '1621066390158', '1621066390158', '1', '1', '笔记,java', '1', '0', '4', null, '1', 'Java内存模型', '<p>12312312313</p>');
INSERT INTO `tb_post` VALUES ('11', '2', '1621066494819', '1621066494819', '1', '1', '分享,讨论,灌水', '1', '1', '2', null, '0', 'Java常见面试题', '<p>1231231</p>');
INSERT INTO `tb_post` VALUES ('12', '1', '1621066566958', '1621066566958', '1', '1', '闲聊,套路', '1', '1', '8', null, '1', '前端与后端，应届生该如何选择？', '<p>还有一年毕业，请问各位大佬，前端合适一点还是后端合适一点？</p>');
INSERT INTO `tb_post` VALUES ('13', '3', '1621076622152', '1621076622152', '1', '1', '笔记,讨论', '1', '1', '2', null, '0', '新人入职第一步', '<p>123123123123</p>');
INSERT INTO `tb_post` VALUES ('14', '1', '1621076655785', '1621076655785', '1', '1', '表白,讨论,教程', '1', '0', '1', null, '0', '怎么对暗恋对象表白？', '<p>12313213</p>');
INSERT INTO `tb_post` VALUES ('15', '2', '1622889069559', '1622889069559', '1', '1', '分享,java', '1', '1', '10', null, '1', 'Spring Boot简介', '<p>约定大于配置。。。</p>');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `activating` int(10) DEFAULT NULL COMMENT '是否激活1true2false',
  `email` varchar(50) DEFAULT NULL,
  `created` bigint(20) DEFAULT NULL,
  `updated` bigint(20) DEFAULT NULL,
  `city` text COMMENT '所在城市',
  `signature` text COMMENT '个人签名',
  `sex` bigint(10) DEFAULT '1' COMMENT '性别 1男2女',
  `avatar_url` varchar(255) DEFAULT '/images/default-avatar.png' COMMENT '头像网址',
  `last_login_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'admin123', 'e10adc3949ba59abbe56e057f20f883e', null, '2318699921@qq.com', '1620035800042', '1620037686734', '北京-北京-东城区', '嘀嘀嘀嘀', '2', '/static/images/76661abe-48fd-4bb3-921c-5baaf235fde5.jpg', '1623051521573');
INSERT INTO `tb_user` VALUES ('2', 'Aries社区_编号734639', 'a4674a41a1308c049a78b69280468786', null, '775728001@qq.com', '1620206218881', '1620206218881', null, null, '1', '/images/avatar/default.png', '1620206251289');
INSERT INTO `tb_user` VALUES ('3', 'cyh', '96e79218965eb72c92a549dd5a330112', null, 'cyh2554y@163.com', '1620206326240', '1620206377513', '北京-北京-东城区', '11111', '1', '/static/images/e36751d4-c93b-4b87-bdbd-d2ab2896b047.jpg', '1620206346927');
INSERT INTO `tb_user` VALUES ('6', 'yZC', 'e10adc3949ba59abbe56e057f20f883e', null, 'yc2251518051@163.com', '1621248255189', '1621248819335', '全部-全部-全部', '', '1', '/images/avatar/default.png', '1621248286883');
