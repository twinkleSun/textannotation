use textannotation;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `email` varchar(255) NOT NULL COMMENT '用户邮箱',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `username` varchar(255) NOT NULL COMMENT '用户昵称',
  `regTime` datetime NOT NULL COMMENT '注册时间',
  `lastLogInTime` datetime NOT NULL COMMENT '上次登陆时间',
  `job` VARCHAR (255) NOT NULL COMMENT '用户职业',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'xxx@qq.com', '123456', 'test', '2017-03-28 09:40:31', '2017-03-28 09:40:31','student');
