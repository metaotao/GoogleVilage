/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : mapparameter
Target Host     : localhost:3306
Target Database : mapparameter
Date: 2016-10-22 10:45:52
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for historyrecord
-- ----------------------------
DROP TABLE IF EXISTS `historyrecord`;
CREATE TABLE `historyrecord` (
  `vilageName` varchar(100) DEFAULT NULL,
  `tangSum` int(11) DEFAULT '0',
  `songSum` int(11) DEFAULT '0',
  `yuanSum` int(11) DEFAULT '0',
  `mingSum` int(11) DEFAULT '0',
  `qingSum` int(11) DEFAULT '0',
  `minguoSum` int(11) DEFAULT '0',
  `nowSum` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of historyrecord
-- ----------------------------
INSERT INTO `historyrecord` VALUES ('南溪南村', '803', '700', '650', '600', '578', '570', '548');
INSERT INTO `historyrecord` VALUES ('云村', '789', '811', '750', '700', '600', '500', '521');
INSERT INTO `historyrecord` VALUES ('留村', '801', '851', '740', '700', '620', '578', '571');
INSERT INTO `historyrecord` VALUES ('尤溪村', '851', '921', '810', '701', '681', '650', '600');
