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

