-- -----------------------------------
-- 修改应用表的授权集合
-- -----------------------------------
UPDATE blade_client SET authorized_grant_types = 'authorization_code,password,refresh_token,captcha,social,sms_code,register' where id < 1123598811738675205;

-- -----------------------------------
-- 各表增加状态和是否已删除字段
-- -----------------------------------
ALTER TABLE "blade_user_app"
    ADD COLUMN "status" int4 DEFAULT 1,
    ADD COLUMN "is_deleted" int4 DEFAULT 0;
COMMENT ON COLUMN "blade_user_app"."status" IS '状态';
COMMENT ON COLUMN "blade_user_app"."is_deleted" IS '是否已删除';

ALTER TABLE "blade_user_web"
    ADD COLUMN "status" int4 DEFAULT 1,
    ADD COLUMN "is_deleted" int4 DEFAULT 0;
COMMENT ON COLUMN "blade_user_web"."status" IS '状态';
COMMENT ON COLUMN "blade_user_web"."is_deleted" IS '是否已删除';

ALTER TABLE "blade_user_other"
    ADD COLUMN "status" int4 DEFAULT 1,
    ADD COLUMN "is_deleted" int4 DEFAULT 0;
COMMENT ON COLUMN "blade_user_other"."status" IS '状态';
COMMENT ON COLUMN "blade_user_other"."is_deleted" IS '是否已删除';

ALTER TABLE "blade_user_dept"
    ADD COLUMN "status" int4 DEFAULT 1,
    ADD COLUMN "is_deleted" int4 DEFAULT 0;
COMMENT ON COLUMN "blade_user_dept"."status" IS '状态';
COMMENT ON COLUMN "blade_user_dept"."is_deleted" IS '是否已删除';

ALTER TABLE "blade_user_oauth"
    ADD COLUMN "status" int4 DEFAULT 1,
    ADD COLUMN "is_deleted" int4 DEFAULT 0;
COMMENT ON COLUMN "blade_user_oauth"."status" IS '状态';
COMMENT ON COLUMN "blade_user_oauth"."is_deleted" IS '是否已删除';

ALTER TABLE "blade_dept"
    ADD COLUMN "status" int4 DEFAULT 1;
COMMENT ON COLUMN "blade_dept"."status" IS '状态';

ALTER TABLE "blade_role"
    ADD COLUMN "status" int4 DEFAULT 1;
COMMENT ON COLUMN "blade_role"."status" IS '状态';

ALTER TABLE "blade_dict"
    ADD COLUMN "status" int4 DEFAULT 1;
COMMENT ON COLUMN "blade_dict"."status" IS '状态';

ALTER TABLE "blade_dict_biz"
    ADD COLUMN "status" int4 DEFAULT 1;
COMMENT ON COLUMN "blade_dict_biz"."status" IS '状态';

