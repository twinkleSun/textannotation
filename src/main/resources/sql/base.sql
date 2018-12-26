use textannotation;
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `did` INT(11) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `filename` varchar(255) DEFAULT NULL COMMENT '文件名',
  `filetype` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `filesize` INT(11) DEFAULT NULL COMMENT '文件大小',
  `relativepath` varchar(255) DEFAULT NULL COMMENT '相对路径',
  `absolutepath` varchar(255) DEFAULT NULL COMMENT '绝对路径',
  `docuploadtime` varchar(255) DEFAULT NULL COMMENT '上传时间',
  `doccomptime` varchar(255) DEFAULT NULL  COMMENT '完成时间',
  `docstatus` varchar(255) DEFAULT NULL  COMMENT '全文完成状态',
  `userid` int(11) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`did`),
  FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `document` VALUES ('1', 'test', '.doc', '20', 'path', 'path', '2017-12-07 11:17:19', '2017-12-07 11:17:19','未完成','1');

use textannotation;
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '段落内容ID',
  `paracontent` varchar(20000) DEFAULT NULL COMMENT '段落内容',
  `paraindex` INT(11) DEFAULT NULL COMMENT '段落索引',
  `parastatus` varchar(255) DEFAULT NULL COMMENT '完成情况',
  `paracomptime` varchar(255) DEFAULT NULL COMMENT '段落完成时间',
  `documentid` INT(11) NOT NULL COMMENT '文件ID',
  PRIMARY KEY (`cid`),
  FOREIGN KEY (`documentid`) REFERENCES `document` (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `content` VALUES ('1', '第一段的内容','1','未完成', '2017-12-07 11:17:19','1');
INSERT INTO `content` VALUES ('2', '第二段的内容','2','未完成', '2017-12-07 11:17:19', '1');
INSERT INTO `content` VALUES ('3', '第三段的内容','3','未完成', '2017-12-07 11:17:19', '1');


use textannotation;
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `tid` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `title` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `description` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `type` varchar(255) DEFAULT NULL COMMENT '任务类型',
  `createtime` varchar(255) DEFAULT NULL COMMENT '创建日期',
  `deadline` varchar(255) DEFAULT NULL COMMENT '截止日期',
  `taskcomptime` varchar(255) DEFAULT NULL COMMENT '完成日期',
  `taskcompstatus` varchar(255) DEFAULT NULL COMMENT '任务进行状态',
  `otherinfo` varchar(255) DEFAULT NULL COMMENT '其他备注',
  `userid` INT(11) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`tid`),
  FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `task` VALUES ('1', '测试', '任务描述','信息抽取', '2017-03-28 09:40:31', '2017-03-28 09:40:31', '2017-03-28 09:40:31', '进行中','备注','1');

use textannotation;
DROP TABLE IF EXISTS `task_document`;
CREATE TABLE `task_document` (
  `taskid` int(11) NOT NULL COMMENT '任务ID',
  `documentid` INT(11) NOT NULL COMMENT '文件ID',
  FOREIGN KEY (`taskid`) REFERENCES `task` (`tid`),
  FOREIGN KEY (`documentid`) REFERENCES `document` (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `task_document` VALUES ('1', '1');

use textannotation;
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label` (
  `lid` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `labelname` varchar(255) DEFAULT NULL COMMENT '标签名称',
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `label` VALUES ('1', '测试label');


use textannotation;
DROP TABLE IF EXISTS `task_label`;
CREATE TABLE `task_label` (
  `taskid` int(11) NOT NULL COMMENT '文件ID',
  `labelid` INT(11) NOT NULL COMMENT '标签ID',
  FOREIGN KEY (`taskid`) REFERENCES `task` (`tid`),
  FOREIGN KEY (`labelid`) REFERENCES `label` (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `task_label` VALUES ('1', '1');


use textannotation;
DROP TABLE IF EXISTS `dotask`;
CREATE TABLE `dotask` (
  `dtid` int(11) NOT NULL AUTO_INCREMENT COMMENT '做任务ID',
  `userid` int(11) DEFAULT NULL COMMENT '做任务用户ID',
  `taskid` int(11) DEFAULT NULL COMMENT '做任务任务ID',
  `contentid` int(11) DEFAULT NULL COMMENT '做任务段落ID',
  PRIMARY KEY (`dtid`),
  FOREIGN KEY (`userid`) REFERENCES `user` (`id`),
  FOREIGN KEY (`taskid`) REFERENCES `task` (`tid`),
  FOREIGN KEY (`contentid`) REFERENCES `content` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `dotask` VALUES ('1', '1','1','1');
INSERT INTO `dotask` VALUES ('2', '1','1','2');
INSERT INTO `dotask` VALUES ('3', '1','1','3');

use textannotation;
DROP TABLE IF EXISTS `dotaskdetail`;
CREATE TABLE `dotaskdetail` (
  `dtdid` int(11) NOT NULL AUTO_INCREMENT COMMENT '做任务详细描述ID',
  `dotaskid` int(11) DEFAULT NULL COMMENT '做任务详细描述做任务ID',
  `labelid` int(11) DEFAULT NULL COMMENT '做任务详细描述标签ID',
  `contentbegin` int(11) DEFAULT NULL COMMENT '做任务详细描述段落开始ID',
  `contentend` int(11) DEFAULT NULL COMMENT '做任务详细描述段落结束ID',
  PRIMARY KEY (`dtdid`),
  FOREIGN KEY (`dotaskid`) REFERENCES `dotask` (`dtid`),
  FOREIGN KEY (`labelid`) REFERENCES `label` (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `dotaskdetail` VALUES ('1', '1','1','1','2');
INSERT INTO `dotaskdetail` VALUES ('2', '1','1','2','3');
INSERT INTO `dotaskdetail` VALUES ('3', '1','1','3','4');

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
