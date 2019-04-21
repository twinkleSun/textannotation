use textannotation;
DROP TABLE IF EXISTS `dtu_comment`;
CREATE TABLE `dtu_comment` (
  `dtu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '��������ϸ����ID',
  `dtd_id` int(11) NOT NULL COMMENT '������ID',
  `u_id` int(11) DEFAULT NULL COMMENT '��������ϸ������ǩID',
  `cnum` int(11) DEFAULT NULL COMMENT 'label��Ӧ��content��ʼλ��',
  PRIMARY KEY (`dtu_id`),
  FOREIGN KEY (`dtd_id`) REFERENCES `dt_classify` (`dtd_id`),
  FOREIGN KEY (`u_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

use textannotation;
DROP TABLE IF EXISTS `dtasktype`;
CREATE TABLE `dtasktype` (
  `dty_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '�������Ƿ�����������ID',
  `tasktype` int(11) NOT NULL COMMENT '�������',
  `typevalue` int(11) NOT NULL COMMENT '�������Ƿ�����������ֵ',
  `u_id` int(11) DEFAULT NULL COMMENT '�û�ID',
  PRIMARY KEY (`dty_id`),
  FOREIGN KEY (`u_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `dtasktype` VALUES ('1', '1','1','1');
INSERT INTO `dtasktype` VALUES ('2', '2','1','1');
INSERT INTO `dtasktype` VALUES ('3', '3','1','1');
INSERT INTO `dtasktype` VALUES ('4', '4','1','1');
INSERT INTO `dtasktype` VALUES ('5', '5','1','1');
INSERT INTO `dtasktype` VALUES ('6', '6','1','1');

use textannotation;
DROP TABLE IF EXISTS `point`;
CREATE TABLE `point` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����ID',
  `deficitvalue` int(11) NOT NULL COMMENT '�����ļ���Ƿ�Ļ���ֵ',
  `obtainvalue` int(11) NOT NULL COMMENT '�������ȡ�Ļ���ֵ',
  `u_id` int(11) DEFAULT NULL COMMENT '�û�ID',
  PRIMARY KEY (`p_id`),
  FOREIGN KEY (`u_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `point` VALUES ('1', '100','100','1');

use textannotation;
DROP TABLE IF EXISTS `pointunit`;
CREATE TABLE `pointunit` (
  `pu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '���ֵ�λID',
  `pointunit` int(11) NOT NULL COMMENT '���ֵ�λ',
  `task_id` int(11) DEFAULT NULL COMMENT '����ID',
  PRIMARY KEY (`pu_id`),
  FOREIGN KEY (`task_id`) REFERENCES `task` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `pointunit` VALUES ('1', '10','1');

