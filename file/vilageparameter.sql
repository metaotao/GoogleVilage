/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : mapparameter
Target Host     : localhost:3306
Target Database : mapparameter
Date: 2016-10-21 19:14:05
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for vilageparameter
-- ----------------------------
DROP TABLE IF EXISTS `vilageparameter`;
CREATE TABLE `vilageparameter` (
  `vilageName` varchar(100) DEFAULT NULL,
  `vilageArea` int(11) DEFAULT '0',
  `vilageDensity` int(11) DEFAULT '0',
  `vilageNeighbour` int(11) DEFAULT '0',
  `vilageDegree` int(11) DEFAULT '0',
  `cultureRating` int(11) DEFAULT '0',
  `vilageRadius` int(11) DEFAULT '0',
  `vilageSum` int(11) DEFAULT '0',
  `vilageStartTime` varchar(10) DEFAULT NULL,
  `vilageEndTime` varchar(10) DEFAULT NULL,
  `vilageSize` varchar(10) DEFAULT NULL,
  `vilageShape` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vilageparameter
-- ----------------------------
INSERT INTO `vilageparameter` VALUES ('南溪南村', '1054', '67', '45', '56', '87', '54', '548', '1458', '2016', '中', '方形');
INSERT INTO `vilageparameter` VALUES ('留村', '1248', '48', '56', '61', '74', '61', '531', '1847', '2016', '中', '片状');
INSERT INTO `vilageparameter` VALUES ('浯村', '987', '71', '51', '58', '60', '43', '611', '1548', '2016', '中', '带状');
INSERT INTO `vilageparameter` VALUES ('云村', '1452', '0', '0', '0', '0', '0', '0', '1689', '2016', '小', '方形');
