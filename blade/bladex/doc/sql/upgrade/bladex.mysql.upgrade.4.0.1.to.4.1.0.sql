-- -----------------------------------
-- 修改应用表saber的访问地址
-- -----------------------------------
UPDATE `blade_client` SET authorized_grant_types = 'authorization_code,password,refresh_token,captcha,social,sms_code,register' where id < 1123598811738675205;

-- -----------------------------------
-- 各表增加状态和是否已删除字段
-- -----------------------------------
ALTER TABLE `blade_user_app`
    ADD COLUMN `status` int(2) NULL DEFAULT 1 COMMENT '状态' AFTER `user_ext`,
    ADD COLUMN `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除' AFTER `status`;

ALTER TABLE `blade_user_web`
    ADD COLUMN `status` int(2) NULL DEFAULT 1 COMMENT '状态' AFTER `user_ext`,
    ADD COLUMN `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除' AFTER `status`;

ALTER TABLE `blade_user_other`
    ADD COLUMN `status` int(2) NULL DEFAULT 1 COMMENT '状态' AFTER `user_ext`,
    ADD COLUMN `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除' AFTER `status`;

ALTER TABLE `blade_user_dept`
    ADD COLUMN `status` int(2) NULL DEFAULT 1 COMMENT '状态' AFTER `dept_id`,
    ADD COLUMN `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除' AFTER `status`;

ALTER TABLE `blade_user_oauth`
    ADD COLUMN `status` int(2) NULL DEFAULT 1 COMMENT '状态' AFTER `source`,
    ADD COLUMN `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除' AFTER `status`;

ALTER TABLE `blade_dept`
    ADD COLUMN `status` int(2) NULL DEFAULT 1 COMMENT '状态' AFTER `remark`;

ALTER TABLE `blade_role`
    ADD COLUMN `status` int(2) NULL DEFAULT 1 COMMENT '状态' AFTER `role_alias`;

ALTER TABLE `blade_dict`
    ADD COLUMN `status` int(2) NULL DEFAULT 1 COMMENT '状态' AFTER `is_sealed`;

ALTER TABLE `blade_dict_biz`
    ADD COLUMN `status` int(2) NULL DEFAULT 1 COMMENT '状态' AFTER `is_sealed`;
