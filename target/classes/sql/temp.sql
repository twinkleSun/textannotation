use textannotation;
DROP TABLE IF EXISTS `dtu_comment`;
CREATE TABLE `dtu_comment` (
  `dtu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '做任务详细描述ID',
  `dtd_id` int(11) NOT NULL COMMENT '做任务ID',
  `u_id` int(11) DEFAULT NULL COMMENT '做任务详细描述标签ID',
  `cnum` int(11) DEFAULT NULL COMMENT 'label对应的content开始位置',
  PRIMARY KEY (`dtu_id`),
  FOREIGN KEY (`dtd_id`) REFERENCES `dt_classify` (`dtdid`),
  FOREIGN KEY (`u_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;