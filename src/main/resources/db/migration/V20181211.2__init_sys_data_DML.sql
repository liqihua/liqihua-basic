-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('23', null, '系统设置', 'sys', '1', '\0', '1', '2018-12-12 15:50:55', '2018-12-12 15:50:55');
INSERT INTO `sys_menu` VALUES ('24', '23', '系统用户', 'sysUser/list', '2', '\0', '4', '2018-12-12 15:51:21', '2018-12-12 16:49:37');
INSERT INTO `sys_menu` VALUES ('25', '23', '系统菜单', 'sysMenu/list', '2', '\0', '1', '2018-12-12 16:21:20', '2018-12-12 16:49:45');
INSERT INTO `sys_menu` VALUES ('26', '23', '系统权限', 'sysPerm/list', '2', '\0', '2', '2018-12-12 16:21:52', '2018-12-12 16:49:53');
INSERT INTO `sys_menu` VALUES ('27', '23', '系统角色', 'sysRole/list', '2', '\0', '3', '2018-12-12 16:22:12', '2018-12-12 16:50:00');

-- ----------------------------
-- Records of sys_perm
-- ----------------------------
INSERT INTO `sys_perm` VALUES ('13', '用户列表', 'sysUser-list', '', '2018-12-12 16:28:35', '2018-12-12 16:30:40');
INSERT INTO `sys_perm` VALUES ('14', '保存用户', 'sysUser-save', '', '2018-12-12 16:31:15', '2018-12-12 16:31:15');
INSERT INTO `sys_perm` VALUES ('15', '删除用户', 'sysUser-delete', '', '2018-12-12 16:31:50', '2018-12-12 16:31:50');
INSERT INTO `sys_perm` VALUES ('16', '设置角色', 'sysUser-setRole', '', '2018-12-12 16:33:22', '2018-12-12 16:33:22');
INSERT INTO `sys_perm` VALUES ('17', '菜单列表', 'sysMenu-list', '', '2018-12-12 16:34:21', '2018-12-12 16:34:21');
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
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级账号', '2018-12-12 16:41:17', '2018-12-12 16:41:17');

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('4', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'admin', 'admin', '', '/upload/25-1.jpeg', '13588888888', 'admin', '\0', '2018-11-13 09:49:52', '2018-12-11 15:39:47');

-- ----------------------------
-- Records of sys_perm_menu
-- ----------------------------
INSERT INTO `sys_perm_menu` VALUES ('11', '24', '13', '2018-12-12 16:30:40', '2018-12-12 16:30:40');
INSERT INTO `sys_perm_menu` VALUES ('12', '24', '14', '2018-12-12 16:31:15', '2018-12-12 16:31:15');
INSERT INTO `sys_perm_menu` VALUES ('13', '24', '15', '2018-12-12 16:31:50', '2018-12-12 16:31:50');
INSERT INTO `sys_perm_menu` VALUES ('14', '24', '16', '2018-12-12 16:33:22', '2018-12-12 16:33:22');
INSERT INTO `sys_perm_menu` VALUES ('15', '25', '17', '2018-12-12 16:34:21', '2018-12-12 16:34:21');
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
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1', '23', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_menu` VALUES ('2', '1', '24', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_menu` VALUES ('3', '1', '25', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_menu` VALUES ('4', '1', '26', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_menu` VALUES ('5', '1', '27', '2018-12-12 16:42:27', '2018-12-12 16:42:27');

-- ----------------------------
-- Records of sys_role_perm
-- ----------------------------
INSERT INTO `sys_role_perm` VALUES ('1', '1', '13', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_perm` VALUES ('2', '1', '14', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_perm` VALUES ('3', '1', '15', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_perm` VALUES ('4', '1', '16', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_perm` VALUES ('5', '1', '17', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_perm` VALUES ('6', '1', '18', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_perm` VALUES ('7', '1', '19', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_perm` VALUES ('8', '1', '22', '2018-12-12 16:42:27', '2018-12-12 16:42:27');
INSERT INTO `sys_role_perm` VALUES ('9', '1', '21', '2018-12-12 16:42:28', '2018-12-12 16:42:28');
INSERT INTO `sys_role_perm` VALUES ('10', '1', '20', '2018-12-12 16:42:28', '2018-12-12 16:42:28');
INSERT INTO `sys_role_perm` VALUES ('11', '1', '23', '2018-12-12 16:42:28', '2018-12-12 16:42:28');
INSERT INTO `sys_role_perm` VALUES ('12', '1', '24', '2018-12-12 16:42:28', '2018-12-12 16:42:28');
INSERT INTO `sys_role_perm` VALUES ('13', '1', '25', '2018-12-12 16:42:28', '2018-12-12 16:42:28');
INSERT INTO `sys_role_perm` VALUES ('14', '1', '26', '2018-12-12 16:42:28', '2018-12-12 16:42:28');

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1', '4', '2018-12-12 16:42:41', '2018-12-12 16:42:41');