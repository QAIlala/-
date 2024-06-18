/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50712 (5.7.12-log)
 Source Host           : localhost:3306
 Source Schema         : helpfarmers

 Target Server Type    : MySQL
 Target Server Version : 50712 (5.7.12-log)
 File Encoding         : 65001

 Date: 31/03/2024 19:18:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_action
-- ----------------------------
DROP TABLE IF EXISTS `t_action`;
CREATE TABLE `t_action`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '动作权限id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动作权限名称',
  `state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点类型（1为父节点，0为字节点）',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限路径',
  `p_id` int(11) NULL DEFAULT NULL COMMENT '上级菜单',
  `acl_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限码',
  `ban_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否禁用',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_action
-- ----------------------------
INSERT INTO `t_action` VALUES (1, '进销存管理', '1', '', 0, 'sys:jxc-action', '0', '0');
INSERT INTO `t_action` VALUES (2, '仓库管理', '0', '/jxc-warehouse', 1, 'sys:jxc-action:warehouse', '0', '0');
INSERT INTO `t_action` VALUES (3, '仓库-查询', '0', '/jxc-warehouse/warehouseList', 2, 'sys:jxc-action:warehouse:query', '0', '0');
INSERT INTO `t_action` VALUES (4, '仓库-更新', '0', '/jxc-warehouse/upd', 2, 'sys:jxc-action:warehouse:upd', '0', '0');
INSERT INTO `t_action` VALUES (5, '仓库-删除', '0', '/jxc-warehouse', 2, 'sys:jxc-action:warehouse:del', '0', '0');
INSERT INTO `t_action` VALUES (6, '仓库-添加', '0', '/jxc-warehouse/add', 2, 'sys:jxc-action:warehouse:save', '0', '0');
INSERT INTO `t_action` VALUES (7, '入库管理', '0', '/jxc-storage', 1, 'sys:jxc-action:storage', '0', '0');
INSERT INTO `t_action` VALUES (8, '入库-查询', '0', '/jxc-storage/storageList', 7, 'sys:jxc-action:storage:query', '0', '0');
INSERT INTO `t_action` VALUES (9, '入库-添加', '0', '/jxc-storage/add', 7, 'sys:jxc-action:storage:save', '0', '0');
INSERT INTO `t_action` VALUES (10, '入库-删除', '0', '/jxc-storage/del', 7, 'sys:jxc-action:storage:del', '0', '0');
INSERT INTO `t_action` VALUES (11, '出库管理', '0', '/jxc-output', 1, 'sys:jxc-action:output', '0', '0');
INSERT INTO `t_action` VALUES (12, '出库-查询', '0', '/jxc-output/outputList', 11, 'sys:jxc-action:output:query', '0', '0');
INSERT INTO `t_action` VALUES (13, '出库-添加', '0', '/jxc-output/add', 11, 'sys:jxc-action:output:save', '0', '0');
INSERT INTO `t_action` VALUES (14, '出库-删除', '0', '/jxc-output/del', 11, 'sys:jxc-action:output:del', '0', '0');
INSERT INTO `t_action` VALUES (15, '库存管理', '0', '/jxc-inventory', 1, 'sys:jxc-action:inventory', '0', '0');
INSERT INTO `t_action` VALUES (16, '库存-查询', '0', '/jxc-inventory/inventoryList', 15, 'sys:jxc-action:inventory:query', '0', '0');
INSERT INTO `t_action` VALUES (17, '库存-更新', '0', '/jxc-inventory/upd', 15, 'sys:jxc-action:inventory:upd', '0', '0');
INSERT INTO `t_action` VALUES (18, '基础资料', '1', NULL, 0, 'sys:base-action', '0', '0');
INSERT INTO `t_action` VALUES (24, '农产品类型管理', '0', '/base-farm-products-type', 18, 'sys:base-action:farmproductstype', '0', '0');
INSERT INTO `t_action` VALUES (25, '农产品类型-查询', '0', '/base-farm-products-type/typeList', 24, 'sys:base-action:farmproductstype:query', '0', '0');
INSERT INTO `t_action` VALUES (26, '农产品类型-更新', '0', '/base-farm-products-type/upd', 24, 'sys:base-action:farmproductstype:upd', '0', '0');
INSERT INTO `t_action` VALUES (27, '农产品类型-删除', '0', '/base-farm-products-type/', 24, 'sys:base-action:farmproductstype:del', '0', '0');
INSERT INTO `t_action` VALUES (28, '农产品类型-添加', '0', '/base-farm-products-type/save', 24, 'sys:base-action:farmproductstype:save', '0', '0');
INSERT INTO `t_action` VALUES (29, '农产品管理', '0', '/base-farm-products', 18, 'sys:base-action:farmproducts', '0', '0');
INSERT INTO `t_action` VALUES (30, '农产品管理-查询', '0', '/base-farm-products/farmProductsList', 29, 'sys:base-action:farmproducts:query', '0', '0');
INSERT INTO `t_action` VALUES (31, '农产品管理-更新', '0', '/base-farm-products/upd', 29, 'sys:base-action:farmproducts:upd', '0', '0');
INSERT INTO `t_action` VALUES (32, '农产品管理-删除', '0', '/base-farm-products', 29, 'sys:base-action:farmproducts:del', '0', '0');
INSERT INTO `t_action` VALUES (33, '农产品管理-添加', '0', '/base-farm-products/save', 29, 'sys:base-action:farmproducts:save', '0', '0');
INSERT INTO `t_action` VALUES (39, '系统设置', '1', NULL, 0, 'sys:sys-set-action', '0', '0');
INSERT INTO `t_action` VALUES (40, '用户管理', '0', '/user', 39, 'sys:sys-set-action:user', '0', '0');
INSERT INTO `t_action` VALUES (41, '用户管理-查询', '0', '/user/userList', 40, 'sys:sys-set-action:user:query', '0', '0');
INSERT INTO `t_action` VALUES (42, '用户管理-更新', '0', '/user/updUser', 40, 'sys:sys-set-action:user:upd', '0', '0');
INSERT INTO `t_action` VALUES (43, '用户管理-删除', '0', '/user/delUser', 40, 'sys:sys-set-action:user:del', '0', '0');
INSERT INTO `t_action` VALUES (44, '用户管理-添加', '0', '/user/saveUser', 40, 'sys:sys-set-action:user:save', '0', '0');
INSERT INTO `t_action` VALUES (45, '角色管理', '0', '/role', 39, 'sys:sys-set-action:role', '0', '0');
INSERT INTO `t_action` VALUES (46, '角色管理-查询', '0', '/role/getAllNotDel', 45, 'sys:sys-set-action:role:query', '0', '0');
INSERT INTO `t_action` VALUES (47, '角色管理-更新', '0', '/role/upd', 45, 'sys:sys-set-action:role:upd', '0', '0');
INSERT INTO `t_action` VALUES (48, '角色管理-删除', '0', '/role', 45, 'sys:sys-set-action:role:del', '0', '0');
INSERT INTO `t_action` VALUES (49, '角色管理-添加', '0', '/role/save', 45, 'sys:sys-set-action:role:save', '0', '0');
INSERT INTO `t_action` VALUES (50, '订单管理', '1', '/order', 0, 'sys:sys-order-action', '0', '0');
INSERT INTO `t_action` VALUES (51, '订单管理-审核', '0', '/order/audit', 50, 'sys:sys-order-action:audit', '0', '0');
INSERT INTO `t_action` VALUES (52, '订单管理-删除', '0', '/order/del', 50, 'sys:sys-order-action:del', '0', '0');
INSERT INTO `t_action` VALUES (53, '订单管理-添加', '0', '/order/save', 50, 'sys:sys-order-action:save', '0', '0');
INSERT INTO `t_action` VALUES (54, '订单管理-查询', '0', '/order/orderList', 50, 'sys:sys-order-action:query', '0', '0');
INSERT INTO `t_action` VALUES (55, '订单管理-审核（财务）', '0', '/order/account', 50, 'sys:sys-order-action:account', '0', '0');
INSERT INTO `t_action` VALUES (56, '权限管理', '0', NULL, 0, 'sys:sys-authority', '0', '0');
INSERT INTO `t_action` VALUES (57, '订单管理-操作（客户）', '0', '/order/operate', 50, 'sys:sys-order-action:operate', '0', '0');

-- ----------------------------
-- Table structure for t_base_farm_products
-- ----------------------------
DROP TABLE IF EXISTS `t_base_farm_products`;
CREATE TABLE `t_base_farm_products`  (
  `number` bigint(20) NOT NULL COMMENT '编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '农产品名称',
  `in_price` decimal(13, 2) NULL DEFAULT NULL COMMENT '入库价格',
  `out_price` decimal(13, 2) NULL DEFAULT NULL COMMENT '出库价格',
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位',
  `farm_products_type_id` int(11) NULL DEFAULT NULL COMMENT '农产品类型id',
  `remarks` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_base_farm_products
-- ----------------------------
INSERT INTO `t_base_farm_products` VALUES (1001, '西红柿', 5000.00, 6000.00, '吨', 1, 'tomato', '2024-03-14 19:09:28', 'admin', '2024-03-23 18:16:30', 'admin', '0');
INSERT INTO `t_base_farm_products` VALUES (1002, '芫荽', 18000.00, 18500.00, '吨', 3, 'yansui', '2024-03-30 21:58:03', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (1003, '莲藕', 3000.00, 3200.00, '吨', 1, 'xxx', '2024-03-23 16:48:57', 'admin', '2024-03-23 16:49:08', 'admin', '0');
INSERT INTO `t_base_farm_products` VALUES (1004, '豆角', 1800.00, 1950.00, '吨', 1, NULL, '2024-03-30 22:04:30', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (2001, '马铃薯', 2000.00, 3000.00, '吨', 2, 'potato', '2024-03-14 19:09:28', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (2002, '五常稻米', 2000.00, 2500.00, '吨', 2, NULL, '2024-03-30 22:03:59', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (2003, '青稞', 3400.00, 4700.00, '吨', 2, NULL, '2024-03-30 22:06:20', 'cangguan', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (2004, '苏湖大米', 1000.00, 1200.00, '吨', 2, NULL, '2024-03-30 22:07:03', 'cangguan', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (3001, '火龙果', 8000.00, 8500.00, '吨', 3, 'hlg', '2024-03-22 20:20:39', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (3002, '猕猴桃', 12000.00, 13000.00, '吨', 3, 'mihoutao', '2024-03-30 22:00:15', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (3003, '烟台苹果', 2500.00, 2700.00, '吨', 3, NULL, '2024-03-30 22:02:00', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (3004, '椰子', 3000.00, 3500.00, '吨', 3, NULL, '2024-03-30 22:02:22', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (3005, '哈密瓜', 2700.00, 3700.00, '吨', 3, NULL, '2024-03-30 22:04:47', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (3006, '甘蔗', 1900.00, 2000.00, '吨', 3, NULL, '2024-03-30 22:05:05', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (3007, '红枣', 2400.00, 2800.00, '吨', 3, NULL, '2024-03-30 22:06:43', 'cangguan', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (4001, '蜂蜜', 9000.00, 10000.00, '吨', 4, 'fengmi', '2024-03-30 22:01:00', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (4002, '羊毛', 16000.00, 16500.00, '吨', 4, 'yangmao', '2024-03-30 22:01:31', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (4003, '辣椒', 8000.00, 8700.00, '吨', 4, NULL, '2024-03-30 22:02:48', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (4004, '花椒', 9000.00, 9400.00, '吨', 4, NULL, '2024-03-30 22:03:05', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products` VALUES (4005, '牦牛奶', 2400.00, 3400.00, '吨', 4, NULL, '2024-03-30 22:06:02', 'cangguan', NULL, NULL, '0');

-- ----------------------------
-- Table structure for t_base_farm_products_type
-- ----------------------------
DROP TABLE IF EXISTS `t_base_farm_products_type`;
CREATE TABLE `t_base_farm_products_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '农产品类型id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '农产品类型名称',
  `remarks` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_base_farm_products_type
-- ----------------------------
INSERT INTO `t_base_farm_products_type` VALUES (1, '蔬菜', '蔬菜', '2024-03-14 19:09:28', 'admin', '2024-03-23 19:54:23', 'admin', '0');
INSERT INTO `t_base_farm_products_type` VALUES (2, '粮食', '粮食', '2024-03-14 19:09:28', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products_type` VALUES (3, '水果', '水果', '2024-03-14 19:09:28', 'admin', NULL, NULL, '0');
INSERT INTO `t_base_farm_products_type` VALUES (4, '物产', '物产', '2024-03-14 19:09:28', 'admin', NULL, NULL, '0');

-- ----------------------------
-- Table structure for t_jxc_inventory
-- ----------------------------
DROP TABLE IF EXISTS `t_jxc_inventory`;
CREATE TABLE `t_jxc_inventory`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '库存id',
  `farm_products_number` bigint(20) NULL DEFAULT NULL COMMENT '农产品编号',
  `w_id` int(11) NULL DEFAULT NULL COMMENT '仓库id',
  `iy_num` int(11) NULL DEFAULT NULL COMMENT '库存数量',
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计量单位',
  `warning_value` int(11) NULL DEFAULT NULL COMMENT '预警值',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_jxc_inventory
-- ----------------------------
INSERT INTO `t_jxc_inventory` VALUES (1, 1001, 1, 100, '吨', 20, '2024-03-24 01:42:23', 'admin', '2024-03-24 02:20:47', 'admin', '0');
INSERT INTO `t_jxc_inventory` VALUES (2, 2001, 2, 3500, '吨', 200, '2024-03-24 01:44:05', 'admin', NULL, NULL, '0');
INSERT INTO `t_jxc_inventory` VALUES (3, 1001, 2, 170, '吨', 500, '2024-03-24 15:54:09', 'admin', '2024-03-24 16:39:55', 'admin', '0');
INSERT INTO `t_jxc_inventory` VALUES (4, 3001, 3, 180, '吨', 50, '2024-03-24 16:29:57', 'admin', '2024-03-24 16:37:48', 'admin', '0');
INSERT INTO `t_jxc_inventory` VALUES (5, 1002, 2, 40, '吨', 10, '2024-03-30 22:50:28', 'cangguan', '2024-03-30 22:50:36', 'cangguan', '0');

-- ----------------------------
-- Table structure for t_jxc_output
-- ----------------------------
DROP TABLE IF EXISTS `t_jxc_output`;
CREATE TABLE `t_jxc_output`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '出库id',
  `farm_products_number` bigint(20) NULL DEFAULT NULL COMMENT '农产品编号',
  `batch_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `o_num` int(11) NULL DEFAULT NULL COMMENT '出库数量',
  `o_total_price` decimal(19, 2) NULL DEFAULT NULL COMMENT '合计价格',
  `w_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库id',
  `o_people` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出库人',
  `o_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出库人联系方式',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_jxc_output
-- ----------------------------
INSERT INTO `t_jxc_output` VALUES (1, 1001, '13415646556', 100, 600000.00, '2', 'admin', '13265986532', 'xxxxxxxxx', '2024-03-24 21:27:03', 'admin', '0');
INSERT INTO `t_jxc_output` VALUES (2, 1001, '154165161561', 30, 180000.00, '2', 'admin', '15663656985', 'xxxxxxxxxxxx', '2024-03-24 21:27:42', 'admin', '0');

-- ----------------------------
-- Table structure for t_jxc_storage
-- ----------------------------
DROP TABLE IF EXISTS `t_jxc_storage`;
CREATE TABLE `t_jxc_storage`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '入库id',
  `farm_products_number` bigint(20) NULL DEFAULT NULL COMMENT '农产品编号',
  `batch_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批号',
  `s_num` int(11) NULL DEFAULT NULL COMMENT '入库数量',
  `s_total_price` decimal(19, 2) NULL DEFAULT NULL COMMENT '合计价格',
  `w_id` int(11) NULL DEFAULT NULL COMMENT '仓库id',
  `s_people` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库人',
  `s_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入库人联系方式',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_jxc_storage
-- ----------------------------
INSERT INTO `t_jxc_storage` VALUES (1, 1001, '123456789', 100, 500000.00, 1, 'admin', '13579632154', 'xxxxxxxxxx', '2024-03-24 14:12:16', 'admin', '0');
INSERT INTO `t_jxc_storage` VALUES (2, 2001, '123654861', 3500, 7000000.00, 2, 'admin', '13579632154', 'xxxxxxxxxx', '2024-03-24 14:13:55', 'admin', '0');
INSERT INTO `t_jxc_storage` VALUES (3, 1001, '132562611', 300, 1500000.00, 2, 'admin', '13523654865', 'xxxxxxxxxx', '2024-03-24 15:54:09', 'admin', '0');
INSERT INTO `t_jxc_storage` VALUES (4, 3001, '316546163516', 180, 1440000.00, 3, 'admin', '15321516354', 'xxxxxxxxxxxxx', '2024-03-24 16:29:57', 'admin', '0');
INSERT INTO `t_jxc_storage` VALUES (5, 1002, '541564156', 40, 720000.00, 2, '仓库管理员', '13652369655', NULL, '2024-03-30 22:50:28', 'cangguan', '0');

-- ----------------------------
-- Table structure for t_jxc_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `t_jxc_warehouse`;
CREATE TABLE `t_jxc_warehouse`  (
  `w_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '仓库id',
  `w_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库编号',
  `w_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `w_contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库联系人',
  `w_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库联系电话',
  `w_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库地址',
  `w_postcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库邮编',
  `w_created_time` datetime NULL DEFAULT NULL COMMENT '仓库建成时间',
  `w_used_time` datetime NULL DEFAULT NULL COMMENT '仓库投入使用时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`w_id`) USING BTREE,
  INDEX `idx_w_id`(`w_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_jxc_warehouse
-- ----------------------------
INSERT INTO `t_jxc_warehouse` VALUES (1, '101', '东北库', '张三', '100001', '长春', '13000', '2023-02-10 00:00:00', '2023-02-28 00:00:00', '2024-03-23 17:44:38', 'admin', '2024-03-24 16:34:29', 'admin', '0');
INSERT INTO `t_jxc_warehouse` VALUES (2, '102', '华北库', '赵四', '100002', '郑州', '23000', '2023-10-15 00:00:00', '2023-11-24 00:00:00', '2024-03-23 19:41:11', 'admin', NULL, NULL, '0');
INSERT INTO `t_jxc_warehouse` VALUES (3, '103', '南海库', '王五', '100003', '海口', '85000', '2024-03-17 00:00:00', '2024-03-28 00:00:00', '2024-03-23 19:47:26', 'admin', NULL, NULL, '0');
INSERT INTO `t_jxc_warehouse` VALUES (4, '201', '西藏库', '王五', '10004', '拉萨', '75000', '2024-05-07 00:00:00', '2024-05-28 00:00:00', '2024-03-30 22:22:36', 'cangguan', NULL, NULL, '0');
INSERT INTO `t_jxc_warehouse` VALUES (5, '202', '西北库', '卢索拉买买提', '100005', '乌鲁木齐', '52000', '2024-01-27 00:00:00', '2024-02-14 00:00:00', '2024-03-30 22:23:42', 'cangguan', NULL, NULL, '0');
INSERT INTO `t_jxc_warehouse` VALUES (6, '103', '华东库', '冯九', '100006', '南京', '11000', '2022-04-16 00:00:00', '2023-04-16 00:00:00', '2024-03-30 22:39:27', 'cangguan', NULL, NULL, '0');

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志信息主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作名称',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人用户名',
  `create_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '逻辑删除字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_log
-- ----------------------------
INSERT INTO `t_log` VALUES (1, '取消订单', 'admin---超级管理员', '2024-03-27 15:59:04', '0');
INSERT INTO `t_log` VALUES (2, '取消订单', 'admin---超级管理员', '2024-03-27 16:03:21', '0');
INSERT INTO `t_log` VALUES (3, '支付', 'admin---超级管理员', '2024-03-28 11:18:39', '0');
INSERT INTO `t_log` VALUES (4, '退款', 'admin---超级管理员', '2024-03-28 11:40:39', '0');
INSERT INTO `t_log` VALUES (5, '退款', 'admin---超级管理员', '2024-03-28 11:42:08', '0');
INSERT INTO `t_log` VALUES (6, '确认收货', 'admin---超级管理员', '2024-03-28 14:11:49', '0');
INSERT INTO `t_log` VALUES (7, '确认收货', 'cangguan---仓库管理员', '2024-03-28 14:16:51', '0');
INSERT INTO `t_log` VALUES (8, '接单', 'lucy---lucy', '2024-03-28 15:36:41', '0');
INSERT INTO `t_log` VALUES (9, '取消订单', 'admin---超级管理员', '2024-03-28 15:39:58', '0');
INSERT INTO `t_log` VALUES (10, '拒单', 'lucy---lucy', '2024-03-28 15:40:22', '0');
INSERT INTO `t_log` VALUES (11, '接单', 'lucy---lucy', '2024-03-28 16:11:32', '0');
INSERT INTO `t_log` VALUES (12, '支付', 'admin---超级管理员', '2024-03-28 16:12:04', '0');
INSERT INTO `t_log` VALUES (13, '发货', 'cangguan---仓库管理员', '2024-03-28 16:13:55', '0');
INSERT INTO `t_log` VALUES (14, '确认收货', 'admin---超级管理员', '2024-03-28 16:20:50', '0');
INSERT INTO `t_log` VALUES (15, '退款', 'admin---超级管理员', '2024-03-28 16:21:34', '0');
INSERT INTO `t_log` VALUES (16, '接单', 'lucy---lucy', '2024-03-28 16:24:34', '0');
INSERT INTO `t_log` VALUES (17, '支付', 'admin---超级管理员', '2024-03-28 16:24:43', '0');
INSERT INTO `t_log` VALUES (18, '发货', 'cangguan---仓库管理员', '2024-03-28 16:25:25', '0');
INSERT INTO `t_log` VALUES (19, '确认收货', 'admin---超级管理员', '2024-03-28 16:29:36', '0');
INSERT INTO `t_log` VALUES (20, '确认收货', 'cangguan---仓库管理员', '2024-03-28 16:35:31', '0');
INSERT INTO `t_log` VALUES (21, '同意退款', 'marry---玛丽', '2024-03-28 16:58:01', '0');
INSERT INTO `t_log` VALUES (22, '驳回', 'marry---玛丽', '2024-03-28 16:58:22', '0');
INSERT INTO `t_log` VALUES (23, '同意退款', 'marry---玛丽', '2024-03-28 17:00:36', '0');
INSERT INTO `t_log` VALUES (24, '接单', 'lucy---lucy', '2024-03-28 22:16:07', '0');
INSERT INTO `t_log` VALUES (25, '支付', 'admin---超级管理员', '2024-03-28 22:16:17', '0');
INSERT INTO `t_log` VALUES (26, '发货', 'cangguan---仓库管理员', '2024-03-28 22:16:39', '0');
INSERT INTO `t_log` VALUES (27, '确认收货', 'admin---超级管理员', '2024-03-28 22:16:46', '0');
INSERT INTO `t_log` VALUES (28, '退货退款', 'admin---超级管理员', '2024-03-28 22:16:56', '0');
INSERT INTO `t_log` VALUES (29, '确认收货', 'cangguan---仓库管理员', '2024-03-28 22:17:14', '0');
INSERT INTO `t_log` VALUES (30, '同意退款', 'marry---玛丽', '2024-03-28 22:17:23', '0');
INSERT INTO `t_log` VALUES (31, '接单', 'lucy---lucy', '2024-03-28 22:49:09', '0');
INSERT INTO `t_log` VALUES (32, '接单', 'lucy---lucy', '2024-03-28 22:49:10', '0');
INSERT INTO `t_log` VALUES (33, '支付', 'admin---超级管理员', '2024-03-28 22:49:17', '0');
INSERT INTO `t_log` VALUES (34, '支付', 'admin---超级管理员', '2024-03-28 22:49:21', '0');
INSERT INTO `t_log` VALUES (35, '发货', 'cangguan---仓库管理员', '2024-03-28 22:49:32', '0');
INSERT INTO `t_log` VALUES (36, '退货退款', 'admin---超级管理员', '2024-03-28 22:49:46', '0');
INSERT INTO `t_log` VALUES (37, '仅退款', 'admin---超级管理员', '2024-03-28 22:51:36', '0');
INSERT INTO `t_log` VALUES (38, '拒单', 'lucy---lucy', '2024-03-29 01:20:47', '0');
INSERT INTO `t_log` VALUES (39, '接单', 'lucy---lucy', '2024-03-29 01:21:17', '0');
INSERT INTO `t_log` VALUES (40, '支付', 'jack---jack', '2024-03-29 01:21:41', '0');
INSERT INTO `t_log` VALUES (41, '发货', 'cangguan---仓库管理员', '2024-03-29 01:22:22', '0');
INSERT INTO `t_log` VALUES (42, '确认收货', 'jack---jack', '2024-03-29 01:22:27', '0');
INSERT INTO `t_log` VALUES (43, '退货退款', 'jack---jack', '2024-03-29 01:22:36', '0');
INSERT INTO `t_log` VALUES (44, '确认收货', 'cangguan---仓库管理员', '2024-03-29 01:22:46', '0');
INSERT INTO `t_log` VALUES (45, '驳回', 'marry---玛丽', '2024-03-29 01:23:03', '0');
INSERT INTO `t_log` VALUES (46, '同意退款', 'marry---玛丽', '2024-03-29 01:23:16', '0');
INSERT INTO `t_log` VALUES (47, '确认收货', 'cangguan---仓库管理员', '2024-03-29 01:23:28', '0');
INSERT INTO `t_log` VALUES (48, '发货', 'cangguan---仓库管理员', '2024-03-29 01:23:29', '0');
INSERT INTO `t_log` VALUES (49, '删除日志', 'admin---超级管理员', '2024-03-29 15:22:49', '0');
INSERT INTO `t_log` VALUES (50, '确认收货', 'admin---超级管理员', '2024-03-30 10:44:03', '0');
INSERT INTO `t_log` VALUES (51, '确认收货', 'admin---超级管理员', '2024-03-30 10:44:05', '0');
INSERT INTO `t_log` VALUES (52, '删除仓库', 'cangguan---仓库管理员', '2024-03-30 22:07:30', '0');
INSERT INTO `t_log` VALUES (53, '删除农产品类型', 'cangguan---仓库管理员', '2024-03-30 22:10:00', '0');
INSERT INTO `t_log` VALUES (54, '删除仓库', 'cangguan---仓库管理员', '2024-03-30 22:11:22', '0');
INSERT INTO `t_log` VALUES (55, '接单', 'lucy---lucy', '2024-03-31 02:17:26', '0');
INSERT INTO `t_log` VALUES (56, '支付', 'jack---jack', '2024-03-31 02:17:44', '0');
INSERT INTO `t_log` VALUES (57, '接单', 'lucy---lucy', '2024-03-31 02:19:26', '0');
INSERT INTO `t_log` VALUES (58, '支付', 'jack---jack', '2024-03-31 02:19:49', '0');
INSERT INTO `t_log` VALUES (59, '拒单', 'lucy---lucy', '2024-03-31 10:15:19', '0');
INSERT INTO `t_log` VALUES (60, '接单', 'lucy---lucy', '2024-03-31 10:15:32', '0');
INSERT INTO `t_log` VALUES (61, '支付', 'jack---jack', '2024-03-31 10:15:53', '0');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `state` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点类型（1为父节点，0为子节点）',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单url',
  `p_id` int(11) NULL DEFAULT NULL COMMENT '上级菜单id',
  `acl_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限码',
  `grade` int(11) NULL DEFAULT NULL COMMENT '菜单层级',
  `ban_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '\0' COMMENT '是否禁用',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '\0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKsni20f28wjqrmpp44uawa2ky4`(`p_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, 'el-icon-shopping-cart-2', '进销存管理', '1', '/jxc', 0, 'sys:jxc', 0, '0', '0');
INSERT INTO `t_menu` VALUES (2, '', '仓库管理', '0', '/jxc/warehouse', 1, 'sys:jxc:warehouse', 1, '0', '0');
INSERT INTO `t_menu` VALUES (3, '', '入库管理', '0', '/jxc/storage', 1, 'sys:jxc:storage', 1, '0', '0');
INSERT INTO `t_menu` VALUES (4, '', '出库管理', '0', '/jxc/output', 1, 'sys:jxc:output', 1, '0', '0');
INSERT INTO `t_menu` VALUES (5, '', '库存管理', '0', '/jxc/inventory', 1, 'sys:jxc:inventory', 1, '0', '0');
INSERT INTO `t_menu` VALUES (6, 'el-icon-menu', '基础资料', '1', '/basicinfor', 0, 'sys:basicinfor', 0, '0', '0');
INSERT INTO `t_menu` VALUES (7, NULL, '农产品管理', '0', '/basicinfor/farmproduct', 6, 'sys:basicinfor:farmerproduct', 1, '0', '0');
INSERT INTO `t_menu` VALUES (8, NULL, '农产品分类管理', '0', '/basicinfor/farmproducttype', 6, 'sys:basicinfor:farmerproducttype', 1, '0', '0');
INSERT INTO `t_menu` VALUES (9, 'el-icon-setting', '设置', '1', '/set', 0, 'sys:set', 0, '0', '0');
INSERT INTO `t_menu` VALUES (10, 'el-icon-setting', '系统设置', '1', '/set/system', 9, 'sys:set:sys', 1, '0', '0');
INSERT INTO `t_menu` VALUES (11, NULL, '用户管理', '0', '/set/system/userManager', 10, 'sys:set:sys:userManager', 2, '0', '0');
INSERT INTO `t_menu` VALUES (12, NULL, '角色管理', '0', '/set/system/roleManager', 10, 'sys:set:sys:roleManager', 2, '0', '0');
INSERT INTO `t_menu` VALUES (13, 'el-icon-setting', '我的设置', '1', '/set/my', 9, 'sys:set:my', 1, '0', '0');
INSERT INTO `t_menu` VALUES (14, NULL, '我的资料', '0', '/set/my/userInfo', 13, 'sys:set:my:userInfo', 2, '0', '0');
INSERT INTO `t_menu` VALUES (15, 'el-icon-s-order', '订单管理', '0', '/orders', 0, 'sys:orders', 0, '0', '0');
INSERT INTO `t_menu` VALUES (16, 'el-icon-monitor', '操作日志', '0', '/log', 0, 'sys:log', 0, '0', '0');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `status` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0待接单	1待付款	2待发货	3已发货	4已结清	5已取消	6拒单	7待退款	8待收货	9已驳回 10退款完毕',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `order_time` datetime NULL DEFAULT NULL COMMENT '下单时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '付款时间',
  `pay_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0未支付\r\n1已支付\r\n2退款',
  `amount` decimal(19, 2) NULL DEFAULT NULL COMMENT '订单金额',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `consignee` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址信息',
  `cancel_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单取消原因',
  `rejection_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拒单原因',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '订单取消时间',
  `refund_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款原因',
  `back_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '驳回原因',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (1, '969783490717552640', '9', 1, '2024-03-25 23:34:48', '2024-03-25 23:34:50', '1', 800000.00, 'xxxxxxxxxxxx', '13563245632', 'admin', 'xxxxxxxxx', '不需要了', NULL, '2024-03-25 23:34:48', '不想要了', '货物不合格', '0');
INSERT INTO `t_order` VALUES (2, '604512519852032', '10', 1, '2024-03-27 15:36:35', NULL, '2', 90000.00, 'xxxxxxxxxxxxxxxxx', '13165986532', 'admin', '山东省淄博市xxxxxxxxxxx', '不想要了', NULL, '2024-03-27 16:03:21', '', NULL, '0');
INSERT INTO `t_order` VALUES (3, '958294973878272', '5', 1, '2024-03-28 15:02:23', NULL, '0', 220000.00, 'xxxxxxxxxxxxxxx', '13165986532', 'admin', '山东省淄博市xxxxxxxxxxx', '不想要了', NULL, '2024-03-28 15:39:58', NULL, NULL, '0');
INSERT INTO `t_order` VALUES (4, '967632358608896', '6', 1, '2024-03-28 15:39:29', NULL, '0', 210000.00, 'xxxxxxxxxxxxxxx', '13165986532', 'admin', '山东省淄博市xxxxxxxxxxx', NULL, '库存不足', NULL, NULL, NULL, '0');
INSERT INTO `t_order` VALUES (5, '974886956830720', '0', 6, '2024-03-28 16:08:19', NULL, '0', 60000.00, 'xxxxxxxxxxxxxx', '13165986532', 'admin', '山东省淄博市xxxxxxxxxxx', NULL, NULL, NULL, NULL, NULL, '1');
INSERT INTO `t_order` VALUES (6, '975654971641856', '10', 1, '2024-03-28 16:11:22', NULL, '2', 60000.00, 'xxxxxxxxxxxx', '13165986532', 'admin', '山东省淄博市xxxxxxxxxxx', NULL, NULL, NULL, '不想要了', NULL, '0');
INSERT INTO `t_order` VALUES (8, '1067166975791104', '10', 1, '2024-03-28 22:15:00', NULL, '2', 92000.00, '1111111111', '13522136579', 'admin', 'aaaaaaaaa', NULL, NULL, NULL, '不需要了', NULL, '0');
INSERT INTO `t_order` VALUES (9, '1075660990844928', '4', 1, '2024-03-28 22:48:45', NULL, '1', 60000.00, '11111111111', '1111111111', '11111111111', '111111111111', NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `t_order` VALUES (10, '1075693345705984', '4', 1, '2024-03-28 22:48:53', NULL, '1', 60000.00, '111111111111111', '11111111111', '1111111111', '11111111111', NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `t_order` VALUES (11, '1113848702701568', '6', 2, '2024-03-29 01:20:30', NULL, '0', 90000.00, '111111111111111', '13569874563', 'jack', 'xxxxxxxxxx', NULL, '库存不足', NULL, NULL, NULL, '0');
INSERT INTO `t_order` VALUES (12, '1113994320547840', '9', 2, '2024-03-29 01:21:04', NULL, '1', 90000.00, 'xxxxxxxxxxxx', '13569874563', 'jack', 'xxxxxxxxxx', NULL, NULL, NULL, '', '不退', '0');
INSERT INTO `t_order` VALUES (13, '1852858018435072', '2', 2, '2024-03-31 02:17:03', NULL, '1', 60000.00, NULL, '13569874563', 'jack', 'xxxxxxxxxx', NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `t_order` VALUES (14, '1853438875013120', '2', 2, '2024-03-31 02:19:22', NULL, '1', 370000.00, NULL, '13569874563', 'jack', 'xxxxxxxxxx', NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `t_order` VALUES (15, '1973192981876736', '6', 2, '2024-03-31 10:15:13', NULL, '0', 60000.00, NULL, '13569874563', 'jack', 'xxxxxxxxxx', NULL, '', NULL, NULL, NULL, '0');
INSERT INTO `t_order` VALUES (16, '1973254189355008', '2', 2, '2024-03-31 10:15:28', NULL, '1', 60000.00, NULL, '13569874563', 'jack', 'xxxxxxxxxx', NULL, NULL, NULL, NULL, NULL, '0');

-- ----------------------------
-- Table structure for t_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单详情id',
  `farm_products_number` bigint(20) NULL DEFAULT NULL COMMENT '农产品编号',
  `farm_products_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '农产品名称',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `in_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '进货价',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `number` int(11) NULL DEFAULT NULL COMMENT '数量',
  `total_price` decimal(19, 2) NULL DEFAULT NULL COMMENT '合计价格',
  `profit` decimal(19, 2) NULL DEFAULT NULL COMMENT '利润',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '逻辑删除字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order_detail
-- ----------------------------
INSERT INTO `t_order_detail` VALUES (1, 1001, '西红柿', 1, 5000.00, 6000.00, 50, 300000.00, 50000.00, '0');
INSERT INTO `t_order_detail` VALUES (2, 1003, '莲藕', 1, 3000.00, 3200.00, 50, 16000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (3, 1001, '西红柿', 2, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (4, 2001, '马铃薯', 2, 2000.00, 3000.00, 10, 30000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (5, 1001, '西红柿', 3, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (6, 1003, '莲藕', 3, 3000.00, 3200.00, 50, 160000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (7, 1001, '西红柿', 4, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (8, 2001, '马铃薯', 4, 2000.00, 3000.00, 50, 150000.00, 50000.00, '0');
INSERT INTO `t_order_detail` VALUES (9, 1001, '西红柿', 5, 5000.00, 6000.00, 10, 60000.00, 10000.00, '1');
INSERT INTO `t_order_detail` VALUES (10, 1001, '西红柿', 6, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (11, 1001, '西红柿', 7, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (12, 1001, '西红柿', 8, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (13, 1003, '莲藕', 8, 3000.00, 3200.00, 10, 32000.00, 2000.00, '0');
INSERT INTO `t_order_detail` VALUES (14, 1001, '西红柿', 9, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (15, 1001, '西红柿', 10, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (16, 1001, '西红柿', 11, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (17, 2001, '马铃薯', 11, 2000.00, 3000.00, 10, 30000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (18, 1001, '西红柿', 12, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (19, 2001, '马铃薯', 12, 2000.00, 3000.00, 10, 30000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (20, 1001, '西红柿', 13, 5000.00, 6000.00, 10, 60000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (21, 1002, '芫荽', 14, 18000.00, 18500.00, 20, 370000.00, 10000.00, '0');
INSERT INTO `t_order_detail` VALUES (22, 2001, '马铃薯', 15, 2000.00, 3000.00, 20, 60000.00, 20000.00, '0');
INSERT INTO `t_order_detail` VALUES (23, 2001, '马铃薯', 16, 2000.00, 3000.00, 20, 60000.00, 20000.00, '0');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bz` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `remarks` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `ban_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否禁用',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '\0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '系统管理员 最高权限', '超级管理员', '', '0', '0');
INSERT INTO `t_role` VALUES (2, '财务人员', '财务', '', '0', '0');
INSERT INTO `t_role` VALUES (3, '仓库管理员', '仓库管理员', NULL, '0', '0');
INSERT INTO `t_role` VALUES (4, '客服', '客服', '', '0', '0');
INSERT INTO `t_role` VALUES (5, '客户', '客户', '', '0', '0');

-- ----------------------------
-- Table structure for t_role_action
-- ----------------------------
DROP TABLE IF EXISTS `t_role_action`;
CREATE TABLE `t_role_action`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `action_id` int(11) NULL DEFAULT NULL COMMENT '动作权限id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 397 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role_action
-- ----------------------------
INSERT INTO `t_role_action` VALUES (182, 20, 9);
INSERT INTO `t_role_action` VALUES (183, 21, 9);
INSERT INTO `t_role_action` VALUES (184, 18, 9);
INSERT INTO `t_role_action` VALUES (185, 19, 9);
INSERT INTO `t_role_action` VALUES (200, 1, 12);
INSERT INTO `t_role_action` VALUES (201, 2, 12);
INSERT INTO `t_role_action` VALUES (202, 5, 12);
INSERT INTO `t_role_action` VALUES (203, 6, 12);
INSERT INTO `t_role_action` VALUES (204, 15, 12);
INSERT INTO `t_role_action` VALUES (205, 16, 12);
INSERT INTO `t_role_action` VALUES (206, 41, 12);
INSERT INTO `t_role_action` VALUES (207, 42, 12);
INSERT INTO `t_role_action` VALUES (208, 39, 12);
INSERT INTO `t_role_action` VALUES (209, 40, 12);
INSERT INTO `t_role_action` VALUES (210, 46, 10);
INSERT INTO `t_role_action` VALUES (211, 39, 10);
INSERT INTO `t_role_action` VALUES (212, 45, 10);
INSERT INTO `t_role_action` VALUES (259, 1, 1);
INSERT INTO `t_role_action` VALUES (260, 2, 1);
INSERT INTO `t_role_action` VALUES (261, 3, 1);
INSERT INTO `t_role_action` VALUES (262, 4, 1);
INSERT INTO `t_role_action` VALUES (263, 5, 1);
INSERT INTO `t_role_action` VALUES (264, 6, 1);
INSERT INTO `t_role_action` VALUES (265, 7, 1);
INSERT INTO `t_role_action` VALUES (266, 8, 1);
INSERT INTO `t_role_action` VALUES (267, 9, 1);
INSERT INTO `t_role_action` VALUES (268, 10, 1);
INSERT INTO `t_role_action` VALUES (269, 11, 1);
INSERT INTO `t_role_action` VALUES (270, 12, 1);
INSERT INTO `t_role_action` VALUES (271, 13, 1);
INSERT INTO `t_role_action` VALUES (272, 14, 1);
INSERT INTO `t_role_action` VALUES (273, 15, 1);
INSERT INTO `t_role_action` VALUES (274, 16, 1);
INSERT INTO `t_role_action` VALUES (275, 17, 1);
INSERT INTO `t_role_action` VALUES (276, 18, 1);
INSERT INTO `t_role_action` VALUES (277, 24, 1);
INSERT INTO `t_role_action` VALUES (278, 25, 1);
INSERT INTO `t_role_action` VALUES (279, 26, 1);
INSERT INTO `t_role_action` VALUES (280, 27, 1);
INSERT INTO `t_role_action` VALUES (281, 28, 1);
INSERT INTO `t_role_action` VALUES (282, 29, 1);
INSERT INTO `t_role_action` VALUES (283, 30, 1);
INSERT INTO `t_role_action` VALUES (284, 31, 1);
INSERT INTO `t_role_action` VALUES (285, 32, 1);
INSERT INTO `t_role_action` VALUES (286, 33, 1);
INSERT INTO `t_role_action` VALUES (287, 39, 1);
INSERT INTO `t_role_action` VALUES (288, 40, 1);
INSERT INTO `t_role_action` VALUES (289, 41, 1);
INSERT INTO `t_role_action` VALUES (290, 42, 1);
INSERT INTO `t_role_action` VALUES (291, 43, 1);
INSERT INTO `t_role_action` VALUES (292, 44, 1);
INSERT INTO `t_role_action` VALUES (293, 45, 1);
INSERT INTO `t_role_action` VALUES (294, 46, 1);
INSERT INTO `t_role_action` VALUES (295, 47, 1);
INSERT INTO `t_role_action` VALUES (296, 48, 1);
INSERT INTO `t_role_action` VALUES (297, 49, 1);
INSERT INTO `t_role_action` VALUES (298, 50, 1);
INSERT INTO `t_role_action` VALUES (299, 51, 1);
INSERT INTO `t_role_action` VALUES (300, 52, 1);
INSERT INTO `t_role_action` VALUES (301, 53, 1);
INSERT INTO `t_role_action` VALUES (302, 54, 1);
INSERT INTO `t_role_action` VALUES (303, 55, 1);
INSERT INTO `t_role_action` VALUES (304, 57, 1);
INSERT INTO `t_role_action` VALUES (305, 56, 1);
INSERT INTO `t_role_action` VALUES (306, 54, 2);
INSERT INTO `t_role_action` VALUES (307, 55, 2);
INSERT INTO `t_role_action` VALUES (308, 50, 2);
INSERT INTO `t_role_action` VALUES (309, 2, 3);
INSERT INTO `t_role_action` VALUES (310, 3, 3);
INSERT INTO `t_role_action` VALUES (311, 4, 3);
INSERT INTO `t_role_action` VALUES (312, 5, 3);
INSERT INTO `t_role_action` VALUES (313, 6, 3);
INSERT INTO `t_role_action` VALUES (314, 8, 3);
INSERT INTO `t_role_action` VALUES (315, 9, 3);
INSERT INTO `t_role_action` VALUES (316, 12, 3);
INSERT INTO `t_role_action` VALUES (317, 13, 3);
INSERT INTO `t_role_action` VALUES (318, 15, 3);
INSERT INTO `t_role_action` VALUES (319, 16, 3);
INSERT INTO `t_role_action` VALUES (320, 17, 3);
INSERT INTO `t_role_action` VALUES (321, 18, 3);
INSERT INTO `t_role_action` VALUES (322, 24, 3);
INSERT INTO `t_role_action` VALUES (323, 25, 3);
INSERT INTO `t_role_action` VALUES (324, 26, 3);
INSERT INTO `t_role_action` VALUES (325, 27, 3);
INSERT INTO `t_role_action` VALUES (326, 28, 3);
INSERT INTO `t_role_action` VALUES (327, 29, 3);
INSERT INTO `t_role_action` VALUES (328, 30, 3);
INSERT INTO `t_role_action` VALUES (329, 31, 3);
INSERT INTO `t_role_action` VALUES (330, 32, 3);
INSERT INTO `t_role_action` VALUES (331, 33, 3);
INSERT INTO `t_role_action` VALUES (332, 51, 3);
INSERT INTO `t_role_action` VALUES (333, 54, 3);
INSERT INTO `t_role_action` VALUES (334, 1, 3);
INSERT INTO `t_role_action` VALUES (335, 7, 3);
INSERT INTO `t_role_action` VALUES (336, 11, 3);
INSERT INTO `t_role_action` VALUES (337, 50, 3);
INSERT INTO `t_role_action` VALUES (365, 51, 4);
INSERT INTO `t_role_action` VALUES (366, 54, 4);
INSERT INTO `t_role_action` VALUES (367, 50, 4);
INSERT INTO `t_role_action` VALUES (388, 25, 5);
INSERT INTO `t_role_action` VALUES (389, 30, 5);
INSERT INTO `t_role_action` VALUES (390, 50, 5);
INSERT INTO `t_role_action` VALUES (391, 53, 5);
INSERT INTO `t_role_action` VALUES (392, 54, 5);
INSERT INTO `t_role_action` VALUES (393, 57, 5);
INSERT INTO `t_role_action` VALUES (394, 18, 5);
INSERT INTO `t_role_action` VALUES (395, 24, 5);
INSERT INTO `t_role_action` VALUES (396, 29, 5);

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKsonb0rbt2u99hbrqqvv3r0wse`(`role_id`) USING BTREE,
  INDEX `FKhayg4ib6v7h1wyeyxhq6xlddq`(`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 536 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (37, 1, 11);
INSERT INTO `t_role_menu` VALUES (38, 2, 11);
INSERT INTO `t_role_menu` VALUES (39, 3, 11);
INSERT INTO `t_role_menu` VALUES (40, 4, 11);
INSERT INTO `t_role_menu` VALUES (41, 5, 11);
INSERT INTO `t_role_menu` VALUES (42, 6, 11);
INSERT INTO `t_role_menu` VALUES (43, 7, 11);
INSERT INTO `t_role_menu` VALUES (44, 8, 11);
INSERT INTO `t_role_menu` VALUES (45, 9, 11);
INSERT INTO `t_role_menu` VALUES (46, 10, 11);
INSERT INTO `t_role_menu` VALUES (47, 11, 11);
INSERT INTO `t_role_menu` VALUES (48, 12, 11);
INSERT INTO `t_role_menu` VALUES (49, 13, 11);
INSERT INTO `t_role_menu` VALUES (50, 14, 11);
INSERT INTO `t_role_menu` VALUES (51, 15, 11);
INSERT INTO `t_role_menu` VALUES (52, 16, 11);
INSERT INTO `t_role_menu` VALUES (53, 17, 11);
INSERT INTO `t_role_menu` VALUES (54, 18, 11);
INSERT INTO `t_role_menu` VALUES (437, 1, 10);
INSERT INTO `t_role_menu` VALUES (438, 2, 10);
INSERT INTO `t_role_menu` VALUES (439, 5, 10);
INSERT INTO `t_role_menu` VALUES (440, 6, 10);
INSERT INTO `t_role_menu` VALUES (453, 6, 12);
INSERT INTO `t_role_menu` VALUES (454, 7, 12);
INSERT INTO `t_role_menu` VALUES (455, 10, 12);
INSERT INTO `t_role_menu` VALUES (456, 11, 12);
INSERT INTO `t_role_menu` VALUES (457, 12, 12);
INSERT INTO `t_role_menu` VALUES (458, 9, 12);
INSERT INTO `t_role_menu` VALUES (466, 13, 2);
INSERT INTO `t_role_menu` VALUES (467, 14, 2);
INSERT INTO `t_role_menu` VALUES (468, 15, 2);
INSERT INTO `t_role_menu` VALUES (469, 9, 2);
INSERT INTO `t_role_menu` VALUES (479, 13, 4);
INSERT INTO `t_role_menu` VALUES (480, 14, 4);
INSERT INTO `t_role_menu` VALUES (481, 15, 4);
INSERT INTO `t_role_menu` VALUES (482, 9, 4);
INSERT INTO `t_role_menu` VALUES (483, 1, 3);
INSERT INTO `t_role_menu` VALUES (484, 2, 3);
INSERT INTO `t_role_menu` VALUES (485, 3, 3);
INSERT INTO `t_role_menu` VALUES (486, 4, 3);
INSERT INTO `t_role_menu` VALUES (487, 5, 3);
INSERT INTO `t_role_menu` VALUES (488, 6, 3);
INSERT INTO `t_role_menu` VALUES (489, 7, 3);
INSERT INTO `t_role_menu` VALUES (490, 8, 3);
INSERT INTO `t_role_menu` VALUES (491, 9, 3);
INSERT INTO `t_role_menu` VALUES (492, 13, 3);
INSERT INTO `t_role_menu` VALUES (493, 14, 3);
INSERT INTO `t_role_menu` VALUES (494, 15, 3);
INSERT INTO `t_role_menu` VALUES (495, 9, 5);
INSERT INTO `t_role_menu` VALUES (496, 13, 5);
INSERT INTO `t_role_menu` VALUES (497, 14, 5);
INSERT INTO `t_role_menu` VALUES (498, 15, 5);
INSERT INTO `t_role_menu` VALUES (520, 1, 1);
INSERT INTO `t_role_menu` VALUES (521, 2, 1);
INSERT INTO `t_role_menu` VALUES (522, 3, 1);
INSERT INTO `t_role_menu` VALUES (523, 4, 1);
INSERT INTO `t_role_menu` VALUES (524, 5, 1);
INSERT INTO `t_role_menu` VALUES (525, 6, 1);
INSERT INTO `t_role_menu` VALUES (526, 7, 1);
INSERT INTO `t_role_menu` VALUES (527, 8, 1);
INSERT INTO `t_role_menu` VALUES (528, 9, 1);
INSERT INTO `t_role_menu` VALUES (529, 10, 1);
INSERT INTO `t_role_menu` VALUES (530, 11, 1);
INSERT INTO `t_role_menu` VALUES (531, 12, 1);
INSERT INTO `t_role_menu` VALUES (532, 13, 1);
INSERT INTO `t_role_menu` VALUES (533, 14, 1);
INSERT INTO `t_role_menu` VALUES (534, 15, 1);
INSERT INTO `t_role_menu` VALUES (535, 16, 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `true_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `remarks` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `ban_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否禁用',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '\0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '$2a$10$Ctqch.qe5NJJHWNZ87A5rOXCQSnLpWqsP1F12.thG7dZmKIOxKFMW', '超级管理员', '123456789', 'xxxxxxxxxxxxxxx', 'project', '2024-01-26 15:22:35', 'xxxxxxxxxxxxxxxx', '0', '0');
INSERT INTO `t_user` VALUES (2, 'jack', '$2a$10$NmlewJN4.OYJNiHweSyzwu/lZVC4M7hY7gPrDAal7A4YxOIx5zeUa', 'jack', '13569874563', 'xxxxxxxxxx', NULL, NULL, 'jack', '0', '0');
INSERT INTO `t_user` VALUES (3, 'marry', '$2a$10$c5dXmIcBzaiSoNcTF.nMTe8brGCOvAj9ayEgGJ1VPMxQPHnGXYhmi', '玛丽', NULL, NULL, NULL, NULL, '123', '0', '0');
INSERT INTO `t_user` VALUES (4, '112233', '$2a$10$4FgJezLqv1DB3kr9MnJICuwZQTIRU3H2zOJCfL/Hcfo32JP/IQ83O', 'xxx', NULL, NULL, 'admin', '2024-02-01 22:13:35', '123', '1', '1');
INSERT INTO `t_user` VALUES (5, '1122334455', '$2a$10$pf2yBy2/SXlahXV3.TFpKOdSb1TIgOvOU6FHbHmh7jtxZBJw3QREO', '111', '', '', 'admin', '2024-02-03 00:01:15', '333', '0', '1');
INSERT INTO `t_user` VALUES (6, 'lucy', '$2a$10$/LEJx9eAVxqd7mLW8.aO7Ow1vrrXKDkVkm85zOCW6QtUuPueR0h7i', 'lucy', '13589624652', 'xxxxxxxx', 'admin', '2024-03-14 19:09:28', 'xxxxxxxx', '0', '0');
INSERT INTO `t_user` VALUES (7, 'cangguan', '$2a$10$GJxXewRG6I6bmoWj7q3OnuMbPs2PIrdg.mu/fL8EMr1W/9UK2brui', '仓库管理员', '13652369655', 'xxxxxxxxxxxx', 'admin', '2024-03-28 14:14:51', 'xxxxxxxxxxxx', '0', '0');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKa9c8iiy6ut0gnx491fqx4pxam`(`role_id`) USING BTREE,
  INDEX `FKq5un6x7ecoef5w1n39cop66kl`(`user_id`) USING BTREE,
  CONSTRAINT `t_user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (22, 2, 3);
INSERT INTO `t_user_role` VALUES (24, 4, 6);
INSERT INTO `t_user_role` VALUES (28, 1, 1);
INSERT INTO `t_user_role` VALUES (29, 3, 7);
INSERT INTO `t_user_role` VALUES (30, 5, 2);

SET FOREIGN_KEY_CHECKS = 1;
