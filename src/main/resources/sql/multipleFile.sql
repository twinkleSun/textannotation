#instance表
use textannotation;
DROP TABLE IF EXISTS `instance`;
CREATE TABLE `instance` (
  `insid` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'instanceID',
  `insindex` varchar(255) DEFAULT NULL COMMENT 'file里的第几段，1、2、3等等',
  `insstatus` varchar(255) DEFAULT NULL COMMENT '该段instance完成状态',
  `inscomptime` varchar(11) DEFAULT NULL COMMENT '该段instance完成时间',
  `documentid` INT(11) NOT NULL COMMENT '文件ID',
  `labelid` INT(11) DEFAULT NULL COMMENT '标签ID,一个关系类别有一个label，配对关系为空',
  PRIMARY KEY (`insid`),
  FOREIGN KEY (`documentid`) REFERENCES `document` (`did`),
  FOREIGN KEY (`labelid`) REFERENCES `label` (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `instance` VALUES ('1', '1','未完成','','1','1');
INSERT INTO `instance` VALUES ('2', '1','未完成','','1','1');

#文本关系类别的item表
use textannotation;
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `itid` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'itemID',
  `itemcontent` varchar(255) DEFAULT NULL COMMENT 'item内容',
  `itemindex` varchar(255) NOT NULL COMMENT '取值1或2',
  `instanceid` INT(11) DEFAULT NULL COMMENT '对应instanceID',
  `labelid` INT(11) NOT NULL COMMENT '对应labelID，由发布者选是否添加label',
  PRIMARY KEY (`itid`),
  FOREIGN KEY (`instanceid`) REFERENCES `instance` (`insid`),
  FOREIGN KEY (`labelid`) REFERENCES `label` (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `item` VALUES ('1', 'item测试内容','1','1','1');
INSERT INTO `item` VALUES ('1', 'item测试内容','2','1','1');

# 文本配对的item表
use textannotation;
DROP TABLE IF EXISTS `listitem`;
CREATE TABLE `listitem` (
  `liid` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'listitemID',
  `litemcontent` varchar(255) DEFAULT NULL COMMENT 'listitem内容',
  `listindex` varchar(255) NOT NULL COMMENT '取值1或2,属于第一个list还是第二个list',
  `litemindex` varchar(255) DEFAULT NULL COMMENT '取值1、2、3等,属于第几个item',
  `litemstatus` varchar(255) DEFAULT NULL COMMENT '这个item配对关系是否已完成',
  `litemcomptime` varchar(255) DEFAULT NULL COMMENT '这个item配对关系完成时间',
  `instanceid` INT(11) DEFAULT NULL COMMENT '对应instanceID',
  PRIMARY KEY (`liid`),
  FOREIGN KEY (`instanceid`) REFERENCES `instance` (`insid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `listitem` VALUES ('1', 'listitem测试内容','1','1','未完成','','2');
INSERT INTO `listitem` VALUES ('2', 'listitem测试内容','2','1','未完成','','2');

# 文本配对的item关系表
use textannotation;
DROP TABLE IF EXISTS `itemrelation`;
CREATE TABLE `itemrelation` (
  `alitemid` INT(11) NOT NULL  COMMENT 'listitemID',
  `blitemid` INT(11) NOT NULL COMMENT 'listitemID',
  FOREIGN KEY (`alitemid`) REFERENCES `listitem` (`liid`),
  FOREIGN KEY (`blitemid`) REFERENCES `listitem` (`liid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `itemrelation` VALUES ('1', '2');