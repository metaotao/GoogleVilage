/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : mapparameter
Target Host     : localhost:3306
Target Database : mapparameter
Date: 2016-10-22 09:06:24
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for results
-- ----------------------------
DROP TABLE IF EXISTS `results`;
CREATE TABLE `results` (
  `vilageName` varchar(100) DEFAULT NULL,
  `accuracyRating` varchar(5) DEFAULT NULL,
  `greenPercentage` varchar(5) DEFAULT NULL,
  `fieldPercentage` varchar(5) DEFAULT NULL,
  `roadPercentage` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of results
-- ----------------------------
INSERT INTO `results` VALUES ('南溪南村', '95', '65', '24', '11');
INSERT INTO `results` VALUES ('云村', '91', '53', '27', '20');
INSERT INTO `results` VALUES ('留村', '89', '57', '30', '13');
INSERT INTO `results` VALUES ('宏村', '93', '59', '30', '11');
