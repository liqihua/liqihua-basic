SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) unsigned DEFAULT NULL COMMENT '父级id',
  `title` varchar(255) NOT NULL COMMENT '菜单标题',
  `router_name` varchar(255) NOT NULL COMMENT 'vue的路由名称',
  `level` tinyint(3) unsigned NOT NULL COMMENT '1：一级菜单，2：二级菜单，3：三级菜单，...',
  `hide` bit(1) NOT NULL COMMENT '1：隐藏，0：显示',
  `rank_num` int(3) unsigned DEFAULT NULL COMMENT '排序',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COMMENT='菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('23', null, '系统设置', 'sys', '1', '\0', '999', '2018-12-12 15:50:55', '2018-12-27 11:19:58');
INSERT INTO `sys_menu` VALUES ('24', '23', '系统用户', 'sysUser/list', '2', '\0', '4', '2018-12-12 15:51:21', '2018-12-12 16:49:37');
INSERT INTO `sys_menu` VALUES ('25', '23', '系统菜单', 'sysMenu/list', '2', '\0', '1', '2018-12-12 16:21:20', '2018-12-12 16:49:45');
INSERT INTO `sys_menu` VALUES ('26', '23', '系统权限', 'sysPerm/list', '2', '\0', '2', '2018-12-12 16:21:52', '2018-12-12 16:49:53');
INSERT INTO `sys_menu` VALUES ('27', '23', '系统角色', 'sysRole/list', '2', '\0', '3', '2018-12-12 16:22:12', '2018-12-12 16:50:00');
INSERT INTO `sys_menu` VALUES ('41', null, '数据管理', 'pro', '1', '\0', '1', '2018-12-27 18:03:35', '2018-12-27 18:03:35');

-- ----------------------------
-- Table structure for sys_perm
-- ----------------------------
DROP TABLE IF EXISTS `sys_perm`;
CREATE TABLE `sys_perm` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '权限名称',
  `symbol` varchar(255) NOT NULL COMMENT 'shiro符号',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COMMENT='权限';

-- ----------------------------
-- Records of sys_perm
-- ----------------------------
INSERT INTO `sys_perm` VALUES ('13', '用户列表', 'sysUser-list', '', '2018-12-12 16:28:35', '2018-12-12 16:30:40');
INSERT INTO `sys_perm` VALUES ('14', '保存用户', 'sysUser-save', '', '2018-12-12 16:31:15', '2018-12-12 16:31:15');
INSERT INTO `sys_perm` VALUES ('15', '删除用户', 'sysUser-delete', '', '2018-12-12 16:31:50', '2018-12-12 16:31:50');
INSERT INTO `sys_perm` VALUES ('16', '设置角色', 'sysUser-setRole', '', '2018-12-12 16:33:22', '2018-12-12 16:33:22');
INSERT INTO `sys_perm` VALUES ('18', '保存菜单', 'sysMenu-save', '', '2018-12-12 16:35:34', '2018-12-12 16:35:34');
INSERT INTO `sys_perm` VALUES ('19', '删除菜单', 'sysMenu-delete', '', '2018-12-12 16:35:57', '2018-12-12 16:35:57');
INSERT INTO `sys_perm` VALUES ('20', '权限列表', 'sysPerm-list', '', '2018-12-12 16:37:25', '2018-12-12 16:37:25');
INSERT INTO `sys_perm` VALUES ('21', '保存权限', 'sysPerm-save', '', '2018-12-12 16:37:45', '2018-12-12 16:37:45');
INSERT INTO `sys_perm` VALUES ('22', '删除权限', 'sysPerm-delete', '', '2018-12-12 16:38:30', '2018-12-12 16:38:30');
INSERT INTO `sys_perm` VALUES ('23', '角色列表', 'sysRole-list', '', '2018-12-12 16:39:16', '2018-12-12 16:39:16');
INSERT INTO `sys_perm` VALUES ('24', '保存角色', 'sysRole-save', '', '2018-12-12 16:39:44', '2018-12-12 16:39:44');
INSERT INTO `sys_perm` VALUES ('25', '删除角色', 'sysRole-delete', '', '2018-12-12 16:40:09', '2018-12-12 16:40:09');
INSERT INTO `sys_perm` VALUES ('26', '分配权限', 'sysRole-setPerm', '', '2018-12-12 16:41:48', '2018-12-12 16:41:48');

-- ----------------------------
-- Table structure for sys_perm_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_perm_menu`;
CREATE TABLE `sys_perm_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单id',
  `perm_id` bigint(20) unsigned NOT NULL COMMENT '权限id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限树形关系';

-- ----------------------------
-- Records of sys_perm_menu
-- ----------------------------
INSERT INTO `sys_perm_menu` VALUES ('11', '24', '13', '2018-12-12 16:30:40', '2018-12-12 16:30:40');
INSERT INTO `sys_perm_menu` VALUES ('12', '24', '14', '2018-12-12 16:31:15', '2018-12-12 16:31:15');
INSERT INTO `sys_perm_menu` VALUES ('13', '24', '15', '2018-12-12 16:31:50', '2018-12-12 16:31:50');
INSERT INTO `sys_perm_menu` VALUES ('14', '24', '16', '2018-12-12 16:33:22', '2018-12-12 16:33:22');
INSERT INTO `sys_perm_menu` VALUES ('16', '25', '18', '2018-12-12 16:35:34', '2018-12-12 16:35:34');
INSERT INTO `sys_perm_menu` VALUES ('17', '25', '19', '2018-12-12 16:35:57', '2018-12-12 16:35:57');
INSERT INTO `sys_perm_menu` VALUES ('18', '26', '20', '2018-12-12 16:37:25', '2018-12-12 16:37:25');
INSERT INTO `sys_perm_menu` VALUES ('19', '26', '21', '2018-12-12 16:37:45', '2018-12-12 16:37:45');
INSERT INTO `sys_perm_menu` VALUES ('20', '26', '22', '2018-12-12 16:38:30', '2018-12-12 16:38:30');
INSERT INTO `sys_perm_menu` VALUES ('21', '27', '23', '2018-12-12 16:39:16', '2018-12-12 16:39:16');
INSERT INTO `sys_perm_menu` VALUES ('22', '27', '24', '2018-12-12 16:39:44', '2018-12-12 16:39:44');
INSERT INTO `sys_perm_menu` VALUES ('23', '27', '25', '2018-12-12 16:40:09', '2018-12-12 16:40:09');
INSERT INTO `sys_perm_menu` VALUES ('24', '27', '26', '2018-12-12 16:41:48', '2018-12-12 16:41:48');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级账号', '2018-12-12 16:41:17', '2018-12-12 16:41:17');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=240 DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单树形关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('235', '1', '23', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_menu` VALUES ('236', '1', '25', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_menu` VALUES ('237', '1', '26', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_menu` VALUES ('238', '1', '27', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_menu` VALUES ('239', '1', '24', '2018-12-27 18:01:52', '2018-12-27 18:01:52');

-- ----------------------------
-- Table structure for sys_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_perm`;
CREATE TABLE `sys_role_perm` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色id',
  `perm_id` bigint(20) unsigned NOT NULL COMMENT '权限id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=323 DEFAULT CHARSET=utf8mb4 COMMENT='角色权限树形关系';

-- ----------------------------
-- Records of sys_role_perm
-- ----------------------------
INSERT INTO `sys_role_perm` VALUES ('310', '1', '13', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('311', '1', '23', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('312', '1', '14', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('313', '1', '24', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('314', '1', '25', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('315', '1', '15', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('316', '1', '16', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('317', '1', '26', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('318', '1', '18', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('319', '1', '19', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('320', '1', '22', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('321', '1', '21', '2018-12-27 18:01:52', '2018-12-27 18:01:52');
INSERT INTO `sys_role_perm` VALUES ('322', '1', '20', '2018-12-27 18:01:52', '2018-12-27 18:01:52');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色id',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1', '4', '2018-12-12 16:42:41', '2018-12-12 16:42:41');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '账号',
  `password` varchar(255) NOT NULL COMMENT '密码-sha1',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `gender` bit(1) DEFAULT NULL COMMENT '1：男，0：女',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `locked` bit(1) NOT NULL COMMENT '1：冻结，0：正常',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT=' 用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('4', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'admin', 'admin', '', '/upload/25-1.jpeg', '13588888888', 'admin', '\0', '2018-11-13 09:49:52', '2018-12-11 15:39:47');
