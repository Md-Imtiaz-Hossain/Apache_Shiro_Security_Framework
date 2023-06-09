SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`       int                                                   NOT NULL AUTO_INCREMENT,
    `email`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, 'test@test.com', '123');
INSERT INTO `user`
VALUES (4, 'aaa@a.com', '789');
INSERT INTO `user`
VALUES (5, '123@123.com', '123456');
INSERT INTO `user`
VALUES (6, '789@789.com', '123456');

SET
FOREIGN_KEY_CHECKS = 1;