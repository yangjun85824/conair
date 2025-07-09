CREATE TABLE `blade_visual_fun`  (
     `id` bigint NOT NULL COMMENT '主键',
     `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码名称',
     `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码类型',
     `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '代码内容',
     `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否已删除',
     PRIMARY KEY (`id`)
) COMMENT = '可视化代码库表';
