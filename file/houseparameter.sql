/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : mapparameter
Target Host     : localhost:3306
Target Database : mapparameter
Date: 2016-10-21 19:06:34
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for houseparameter
-- ----------------------------
DROP TABLE IF EXISTS `houseparameter`;
CREATE TABLE `houseparameter` (
  `vilageName` varchar(100) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `damageDegree` int(11) DEFAULT '0',
  `number` int(11) DEFAULT '0',
  `styleStartTime` varchar(10) DEFAULT NULL,
  `styleEndTime` varchar(10) DEFAULT NULL,
  `radius` int(11) DEFAULT '0',
  `relation` varchar(10) DEFAULT NULL,
  `humanDegree` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of houseparameter
-- ----------------------------
INSERT INTO `houseparameter` VALUES ('南溪南村', '天井式', '38', '548', '1458', '2016', '48', '4', '12');
INSERT INTO `houseparameter` VALUES ('留村', '天井式', '13', '421', '1847', '2016', '54', '5', '11');
INSERT INTO `houseparameter` VALUES ('浯村', '天井式', '24', '561', '1548', '2016', '48', '4', '7');
INSERT INTO `houseparameter` VALUES ('云村', '天井式', '21', '487', '1689', '2016', '35', '4', '14');
INSERT INTO `houseparameter` VALUES ('尤溪村', '天井式', '26', '621', '1458', '2016', '46', '5', '9');
