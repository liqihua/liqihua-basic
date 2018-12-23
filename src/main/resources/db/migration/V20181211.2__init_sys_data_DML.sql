-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('23', null, '系统设置', 'sys', '1', '\0', '1', '2018-12-12 15:50:55', '2018-12-12 15:50:55');
INSERT INTO `sys_menu` VALUES ('24', '23', '系统用户', 'sysUser/list', '2', '\0', '4', '2018-12-12 15:51:21', '2018-12-12 16:49:37');
INSERT INTO `sys_menu` VALUES ('25', '23', '系统菜单', 'sysMenu/list', '2', '\0', '1', '2018-12-12 16:21:20', '2018-12-12 16:49:45');
INSERT INTO `sys_menu` VALUES ('26', '23', '系统权限', 'sysPerm/list', '2', '\0', '2', '2018-12-12 16:21:52', '2018-12-12 16:49:53');
INSERT INTO `sys_menu` VALUES ('27', '23', '系统角色', 'sysRole/list', '2', '\0', '3', '2018-12-12 16:22:12', '2018-12-12 16:50:00');
INSERT INTO `sys_menu` VALUES ('28', null, '测试', '/test', '1', '\0', '999', '2018-12-20 10:49:30', '2018-12-20 10:49:30');
INSERT INTO `sys_menu` VALUES ('29', '28', '测试a', 'testa', '2', '\0', '999', '2018-12-20 10:50:47', '2018-12-20 16:52:43');
INSERT INTO `sys_menu` VALUES ('31', '28', '测试b', 'testb', '2', '\0', '999', '2018-12-20 16:32:20', '2018-12-20 16:52:54');
INSERT INTO `sys_menu` VALUES ('37', '28', '测试c', 'testc', '2', '\0', '999', '2018-12-21 10:32:54', '2018-12-21 10:32:54');
INSERT INTO `sys_menu` VALUES ('38', '28', '测试d', 'testd', '2', '\0', '999', '2018-12-21 10:33:10', '2018-12-21 10:33:10');
INSERT INTO `sys_menu` VALUES ('39', '29', '测试a1', 'testa1', '3', '\0', '999', '2018-12-22 01:54:52', '2018-12-22 01:54:52');
INSERT INTO `sys_menu` VALUES ('40', '29', '测试a2', 'testa2', '3', '\0', '999', '2018-12-22 01:55:10', '2018-12-22 01:55:10');

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
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级账号', '2018-12-12 16:41:17', '2018-12-12 16:41:17');
INSERT INTO `sys_role` VALUES ('2', 'test1', '', '2018-12-14 15:49:43', '2018-12-14 15:49:43');

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('188', '1', '23', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_menu` VALUES ('189', '1', '25', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_menu` VALUES ('190', '1', '26', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_menu` VALUES ('191', '1', '27', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_menu` VALUES ('192', '1', '24', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_menu` VALUES ('193', '1', '28', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_menu` VALUES ('194', '1', '29', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_menu` VALUES ('195', '1', '31', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_menu` VALUES ('196', '1', '37', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_menu` VALUES ('197', '1', '38', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_menu` VALUES ('198', '2', '23', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_menu` VALUES ('199', '2', '25', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_menu` VALUES ('200', '2', '26', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_menu` VALUES ('201', '2', '27', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_menu` VALUES ('202', '2', '24', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_menu` VALUES ('203', '2', '28', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_menu` VALUES ('204', '2', '29', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_menu` VALUES ('205', '2', '39', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_menu` VALUES ('206', '2', '40', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_menu` VALUES ('207', '2', '38', '2018-12-22 01:55:46', '2018-12-22 01:55:46');


-- ----------------------------
-- Records of sys_role_perm
-- ----------------------------
INSERT INTO `sys_role_perm` VALUES ('251', '1', '13', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('252', '1', '14', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('253', '1', '15', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('254', '1', '16', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('255', '1', '18', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('256', '1', '19', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('257', '1', '22', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('258', '1', '21', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('259', '1', '20', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('260', '1', '23', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('261', '1', '24', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('262', '1', '25', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('263', '1', '26', '2018-12-21 15:20:21', '2018-12-21 15:20:21');
INSERT INTO `sys_role_perm` VALUES ('264', '2', '13', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('265', '2', '14', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('266', '2', '15', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('267', '2', '16', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('268', '2', '18', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('269', '2', '19', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('270', '2', '20', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('271', '2', '21', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('272', '2', '22', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('273', '2', '23', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('274', '2', '24', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('275', '2', '25', '2018-12-22 01:55:46', '2018-12-22 01:55:46');
INSERT INTO `sys_role_perm` VALUES ('276', '2', '26', '2018-12-22 01:55:46', '2018-12-22 01:55:46');

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1', '4', '2018-12-12 16:42:41', '2018-12-12 16:42:41');
INSERT INTO `sys_role_user` VALUES ('2', '2', '10', '2018-12-20 15:31:16', '2018-12-20 15:31:16');


-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('4', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'admin', 'admin', '', '/upload/25-1.jpeg', '13588888888', 'admin', '\0', '2018-11-13 09:49:52', '2018-12-11 15:39:47');
INSERT INTO `sys_user` VALUES ('10', 'test', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3', 'test', 'test', '\0', '/upload/21.jpeg', '555', 'test', '\0', '2018-12-20 15:31:09', '2018-12-20 15:31:09');