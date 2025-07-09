CREATE TABLE `blade_visual_ai`  (
     `id` bigint NOT NULL COMMENT '主键',
     `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编号',
     `idx` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类别',
     `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
     `temp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模版',
     `exam` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '示例',
     `rules` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '规则',
     `placeholder` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
     `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否已删除',
     PRIMARY KEY (`id`)
) COMMENT = '可视化大模型表';

INSERT INTO `blade_visual_ai` (`id`, `code`, `idx`, `name`, `temp`, `exam`, `rules`, `placeholder`, `is_deleted`) VALUES (1, '', 'vue', 'vue组件', 'vue', '用element-ui写一个按钮，点击弹窗，弹窗内容为avue-data', 'vue模板的写法|不要引用三方资源|只要代码部分,代码部分用```vue包裹,只能出现一次|style中不加scoped', '请输入对vue组件功能的描述', 0);
INSERT INTO `blade_visual_ai` (`id`, `code`, `idx`, `name`, `temp`, `exam`, `rules`, `placeholder`, `is_deleted`) VALUES (2, '', 'html', 'htm组件', 'html', '用css3写闪闪发红光的文字，内容为avue-data', 'html的写法|只要html部分代码|样式全部用行内样式,不要单独style|html代码片段，只要body标签里的内容|body和 header标签不要', '请输入对html代码样式描述', 0);
INSERT INTO `blade_visual_ai` (`id`, `code`, `idx`, `name`, `temp`, `exam`, `rules`, `placeholder`, `is_deleted`) VALUES (3, '', 'annotation', '代码注释', 'javascript', '<div style=\" font-size: 24px; color: red; text-shadow: 0 0 5px #ff0000, 0 0 10px #ff0000, 0 0 20px #ff0000, 0 0 30px #ff0000, 0 0 40px #ff0000; animation: blinker 1s linear infinite; \"> avue-data </div>', '代码的作用，加上注释|代码用```javascript包裹起来,只能出现一次|注释加到代码里', '代码注释', 0);
INSERT INTO `blade_visual_ai` (`id`, `code`, `idx`, `name`, `temp`, `exam`, `rules`, `placeholder`, `is_deleted`) VALUES (4, '', 'format', '代码格式化', 'javascript', '{\"objectId\":\"e95e4214f8\",\"updatedAt\":\"2022-02-22 19:33:55\",\"createdAt\":\"2022-02-22 19:33:55\",\"code\":\"210000\",\"id\":\"6\",\"name\":\"辽宁省\"}', '代码格式化|代码部分用```javascript包裹,只能出现一次', '请输入需要格式化的代码', 0);
INSERT INTO `blade_visual_ai` (`id`, `code`, `idx`, `name`, `temp`, `exam`, `rules`, `placeholder`, `is_deleted`) VALUES (5, '', 'echart', 'echart组件', 'javascript', '男生50人,女生60人,用饼图展示,文字为50px,男生用红色表示,女生用绿色表示', '用echart来写|模拟数据的变量用result|没给你发数据需要你自己模拟|已经初始化的ECharts实例this.myChart|已经初始化的ECharts实例this.myChart|只要echart相关的代码,代码部分用```javascript包裹|echart配置用option变量，并且是let|全部代码需要放入function(data, params) {}中|结尾要加上return {}', '请输入对echart组件构建的描述，颜色，字体等', 0);
