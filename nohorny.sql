/*
 Navicat Premium Data Transfer

 Source Server         : demospl
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3307
 Source Schema         : nohorny

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 02/11/2022 19:50:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for spring_session
-- ----------------------------
DROP TABLE IF EXISTS `spring_session`;
CREATE TABLE `spring_session`  (
  `PRIMARY_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `SESSION_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`) USING BTREE,
  UNIQUE INDEX `SPRING_SESSION_IX1`(`SESSION_ID` ASC) USING BTREE,
  INDEX `SPRING_SESSION_IX2`(`EXPIRY_TIME` ASC) USING BTREE,
  INDEX `SPRING_SESSION_IX3`(`PRINCIPAL_NAME` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for spring_session_attributes
-- ----------------------------
DROP TABLE IF EXISTS `spring_session_attributes`;
CREATE TABLE `spring_session_attributes`  (
  `SESSION_PRIMARY_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`) USING BTREE,
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_deposit
-- ----------------------------
DROP TABLE IF EXISTS `t_deposit`;
CREATE TABLE `t_deposit`  (
  `deposit_id` int NOT NULL,
  `uid` int NULL DEFAULT NULL,
  `order_id` int NULL DEFAULT NULL,
  `deposit_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`deposit_id`) USING BTREE,
  INDEX `fk_deposit_trans`(`order_id` ASC) USING BTREE,
  CONSTRAINT `fk_deposit_trans` FOREIGN KEY (`order_id`) REFERENCES `t_transaction` (`order_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_district
-- ----------------------------
DROP TABLE IF EXISTS `t_district`;
CREATE TABLE `t_district`  (
  `districts _id` smallint NOT NULL,
  `district` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`districts _id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_hobby
-- ----------------------------
DROP TABLE IF EXISTS `t_hobby`;
CREATE TABLE `t_hobby`  (
  `hobby_id` smallint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`hobby_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_item
-- ----------------------------
DROP TABLE IF EXISTS `t_item`;
CREATE TABLE `t_item`  (
  `item_id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `unit_rate` smallint NOT NULL,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_pm
-- ----------------------------
DROP TABLE IF EXISTS `t_pm`;
CREATE TABLE `t_pm`  (
  `pm_id` int NOT NULL,
  `from_uid` int NULL DEFAULT NULL,
  `to_uid` int NULL DEFAULT NULL,
  PRIMARY KEY (`pm_id`) USING BTREE,
  INDEX `fk_pmf_user`(`from_uid` ASC) USING BTREE,
  INDEX `fk_pmt_user`(`to_uid` ASC) USING BTREE,
  CONSTRAINT `fk_pmf_user` FOREIGN KEY (`from_uid`) REFERENCES `t_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_pmt_user` FOREIGN KEY (`to_uid`) REFERENCES `t_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_pm_content
-- ----------------------------
DROP TABLE IF EXISTS `t_pm_content`;
CREATE TABLE `t_pm_content`  (
  `pm_id` int NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pm_id`) USING BTREE,
  CONSTRAINT `fk_pmcontent_pm` FOREIGN KEY (`pm_id`) REFERENCES `t_pm` (`pm_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_pm_status
-- ----------------------------
DROP TABLE IF EXISTS `t_pm_status`;
CREATE TABLE `t_pm_status`  (
  `pm_id` int NOT NULL,
  `to_read` tinyint NOT NULL DEFAULT 0,
  `from_alive` tinyint NOT NULL DEFAULT 1,
  `to_alive` tinyint NOT NULL DEFAULT 1,
  `send_time` datetime NOT NULL,
  `read_time` datetime NOT NULL,
  `replyto_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`pm_id`) USING BTREE,
  CONSTRAINT `fk_pmstatus_pm` FOREIGN KEY (`pm_id`) REFERENCES `t_pm` (`pm_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_transaction
-- ----------------------------
DROP TABLE IF EXISTS `t_transaction`;
CREATE TABLE `t_transaction`  (
  `order_id` int NOT NULL,
  `uid` int NOT NULL,
  `item_id` int NOT NULL,
  `qty` int NOT NULL,
  `trans_time` datetime NOT NULL,
  `coins_change` int NULL DEFAULT NULL,
  `discount_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `fk_trans_item`(`item_id` ASC) USING BTREE,
  CONSTRAINT `fk_trans_item` FOREIGN KEY (`item_id`) REFERENCES `t_item` (`item_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `uid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `roles` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `shapassword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_user_detail`;
CREATE TABLE `t_user_detail`  (
  `uid` int NOT NULL,
  `hobby_id` smallint NOT NULL,
  PRIMARY KEY (`uid`, `hobby_id`) USING BTREE,
  INDEX `fk_detail_hobby`(`hobby_id` ASC) USING BTREE,
  CONSTRAINT `fk_detail_hobby` FOREIGN KEY (`hobby_id`) REFERENCES `t_hobby` (`hobby_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_detail_user` FOREIGN KEY (`uid`) REFERENCES `t_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info`  (
  `uid` int NOT NULL,
  `gender` smallint NOT NULL,
  `age` int NOT NULL,
  `sexal` smallint NOT NULL,
  `district_id` smallint NOT NULL,
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  INDEX `fk_info_district`(`district_id` ASC) USING BTREE,
  CONSTRAINT `fk_info_district` FOREIGN KEY (`district_id`) REFERENCES `t_district` (`districts _id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_info_user` FOREIGN KEY (`uid`) REFERENCES `t_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_login
-- ----------------------------
DROP TABLE IF EXISTS `t_user_login`;
CREATE TABLE `t_user_login`  (
  `uid` int NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  CONSTRAINT `fk_login_user` FOREIGN KEY (`uid`) REFERENCES `t_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
