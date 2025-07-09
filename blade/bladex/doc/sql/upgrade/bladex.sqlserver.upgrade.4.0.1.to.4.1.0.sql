-- -----------------------------------
-- 修改应用表saber的访问地址
-- -----------------------------------
UPDATE [dbo].[blade_client] SET [authorized_grant_types] = 'authorization_code,password,refresh_token,captcha,social,sms_code,register' WHERE id < '1123598811738675205';

-- -----------------------------------
-- 各表增加状态和是否已删除字段
-- -----------------------------------
ALTER TABLE [dbo].[blade_user_app] ADD [status] int NOT NULL DEFAULT 1
GO
ALTER TABLE [dbo].[blade_user_app] ADD [is_deleted] int NOT NULL DEFAULT 0
GO
EXEC sp_addextendedproperty
'MS_Description', N'状态',
'SCHEMA', N'dbo',
'TABLE', N'blade_user_app',
'COLUMN', N'status'
GO
EXEC sp_addextendedproperty
'MS_Description', N'是否已删除',
'SCHEMA', N'dbo',
'TABLE', N'blade_user_app',
'COLUMN', N'is_deleted';

ALTER TABLE [dbo].[blade_user_web] ADD [status] int NOT NULL DEFAULT 1
    GO
ALTER TABLE [dbo].[blade_user_web] ADD [is_deleted] int NOT NULL DEFAULT 0
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'状态',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_user_web',
    'COLUMN', N'status'
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'是否已删除',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_user_web',
    'COLUMN', N'is_deleted';

ALTER TABLE [dbo].[blade_user_other] ADD [status] int NOT NULL DEFAULT 1
    GO
ALTER TABLE [dbo].[blade_user_other] ADD [is_deleted] int NOT NULL DEFAULT 0
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'状态',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_user_other',
    'COLUMN', N'status'
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'是否已删除',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_user_other',
    'COLUMN', N'is_deleted';

ALTER TABLE [dbo].[blade_user_dept] ADD [status] int NOT NULL DEFAULT 1
    GO
ALTER TABLE [dbo].[blade_user_dept] ADD [is_deleted] int NOT NULL DEFAULT 0
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'状态',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_user_dept',
    'COLUMN', N'status'
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'是否已删除',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_user_dept',
    'COLUMN', N'is_deleted';

ALTER TABLE [dbo].[blade_user_oauth] ADD [status] int NOT NULL DEFAULT 1
    GO
ALTER TABLE [dbo].[blade_user_oauth] ADD [is_deleted] int NOT NULL DEFAULT 0
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'状态',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_user_oauth',
    'COLUMN', N'status'
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'是否已删除',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_user_oauth',
    'COLUMN', N'is_deleted';

ALTER TABLE [dbo].[blade_dept] ADD [status] int NOT NULL DEFAULT 1
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'状态',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_dept',
    'COLUMN', N'status';

ALTER TABLE [dbo].[blade_role] ADD [status] int NOT NULL DEFAULT 1
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'状态',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_role',
    'COLUMN', N'status';

ALTER TABLE [dbo].[blade_dict] ADD [status] int NOT NULL DEFAULT 1
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'状态',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_dict',
    'COLUMN', N'status';

ALTER TABLE [dbo].[blade_dict_biz] ADD [status] int NOT NULL DEFAULT 1
    GO
    EXEC sp_addextendedproperty
    'MS_Description', N'状态',
    'SCHEMA', N'dbo',
    'TABLE', N'blade_dict_biz',
    'COLUMN', N'status';
