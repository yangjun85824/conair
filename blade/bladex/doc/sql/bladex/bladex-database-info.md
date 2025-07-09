Table: blade_attach（附件表）

| Field         | Type          | Null | Key | Default | Remarks |
| ------------- | ------------- | ---- | --- | ------- | ------- |
| id            | BIGINT(20)    | NO   | PRI |         | 主键      |
| tenant_id     | VARCHAR(12)   | YES  |     | 000000  | 租户ID    |
| link          | VARCHAR(1000) | YES  |     |         | 附件地址    |
| domain_url    | VARCHAR(500)  | YES  |     |         | 附件域名    |
| name          | VARCHAR(500)  | YES  |     |         | 附件名称    |
| original_name | VARCHAR(500)  | YES  |     |         | 附件原名    |
| extension     | VARCHAR(12)   | YES  |     |         | 附件拓展名   |
| attach_size   | BIGINT(20)    | YES  |     |         | 附件大小    |
| create_user   | BIGINT(20)    | YES  |     |         | 创建人     |
| create_dept   | BIGINT(20)    | YES  |     |         | 创建部门    |
| create_time   | DATETIME(19)  | YES  |     |         | 创建时间    |
| update_user   | BIGINT(20)    | YES  |     |         | 修改人     |
| update_time   | DATETIME(19)  | YES  |     |         | 修改时间    |
| status        | INT(10)       | YES  |     |         | 状态      |
| is_deleted    | INT(10)       | YES  |     |         | 是否已删除   |

Table: blade_client（客户端表）

| Field                   | Type          | Null | Key | Default | Remarks |
| ----------------------- | ------------- | ---- | --- | ------- | ------- |
| id                      | BIGINT(20)    | NO   | PRI |         | 主键      |
| client_id               | VARCHAR(48)   | NO   |     |         | 客户端id   |
| client_secret           | VARCHAR(256)  | NO   |     |         | 客户端密钥   |
| resource_ids            | VARCHAR(256)  | YES  |     |         | 资源集合    |
| scope                   | VARCHAR(256)  | NO   |     |         | 授权范围    |
| authorized_grant_types  | VARCHAR(256)  | NO   |     |         | 授权类型    |
| web_server_redirect_uri | VARCHAR(256)  | YES  |     |         | 回调地址    |
| authorities             | VARCHAR(256)  | YES  |     |         | 权限      |
| access_token_validity   | INT(10)       | NO   |     |         | 令牌过期秒数  |
| refresh_token_validity  | INT(10)       | NO   |     |         | 刷新令牌过期秒数|
| additional_information  | VARCHAR(4096) | YES  |     |         | 附加说明    |
| autoapprove             | VARCHAR(256)  | YES  |     |         | 自动授权    |
| create_user             | BIGINT(20)    | YES  |     |         | 创建人     |
| create_dept             | BIGINT(20)    | YES  |     |         | 创建部门    |
| create_time             | DATETIME(19)  | YES  |     |         | 创建时间    |
| update_user             | BIGINT(20)    | YES  |     |         | 修改人     |
| update_time             | DATETIME(19)  | YES  |     |         | 修改时间    |
| status                  | INT(10)       | NO   |     |         | 状态      |
| is_deleted              | INT(10)       | NO   |     |         | 是否已删除   |

Table: blade_code（代码生成表）

| Field         | Type          | Null | Key | Default | Remarks |
| ------------- | ------------- | ---- | --- | ------- | ------- |
| id            | BIGINT(20)    | NO   | PRI |         | 主键      |
| model_id      | BIGINT(20)    | YES  |     |         | 数据模型主键 |
| service_name  | VARCHAR(64)   | YES  |     |         | 服务名称    |
| code_name     | VARCHAR(64)   | YES  |     |         | 模块名称    |
| table_name    | VARCHAR(64)   | YES  |     |         | 表名      |
| table_prefix  | VARCHAR(64)   | YES  |     |         | 表前缀     |
| pk_name       | VARCHAR(32)   | YES  |     |         | 主键名     |
| package_name  | VARCHAR(500)  | YES  |     |         | 后端包名    |
| template_type | VARCHAR(32)   | YES  |     |         | 模版类型    |
| author        | VARCHAR(32)   | YES  |     |         | 作者信息    |
| sub_model_id  | VARCHAR(32)   | YES  |     |         | 子表模型主键 |
| sub_fk_id     | VARCHAR(32)   | YES  |     |         | 子表绑定外键 |
| tree_id       | VARCHAR(32)   | YES  |     |         | 树主键字段   |
| tree_pid      | VARCHAR(32)   | YES  |     |         | 树父主键字段  |
| tree_name     | VARCHAR(64)   | YES  |     |         | 树名称字段   |
| base_mode     | INT(10)       | YES  |     |         | 基础业务模式  |
| wrap_mode     | INT(10)       | YES  |     |         | 包装器模式   |
| feign_mode    | INT(10)       | YES  |     |         | 远程调用模式 |
| code_style    | VARCHAR(32)   | YES  |     |         | 代码风格    |
| api_path      | VARCHAR(2000) | YES  |     |         | 后端路径    |
| web_path      | VARCHAR(2000) | YES  |     |         | 前端路径    |
| is_deleted    | INT(10)       | YES  |     | 0       | 是否已删除  |

Table: blade_datasource（数据源配置表）

| Field           | Type         | Null | Key | Default | Remarks    |
|-----------------|--------------| ---- | --- | ------- |------------|
| id              | BIGINT(20)   | NO   | PRI |         | 主键         |
| category        | INT(2)       | YES  |     |         | 数据源类型      |
| name            | VARCHAR(100) | YES  |     |         | 名称         |
| driver_class    | VARCHAR(100) | YES  |     |         | 驱动类        |
| url             | VARCHAR(500) | YES  |     |         | 连接地址       |
| username        | VARCHAR(50)  | YES  |     |         | 用户名        |
| password        | VARCHAR(50)  | YES  |     |         | 密码         |
| sharding_config | LONGTEXT     | YES  |     |         | 分库分表配置 |
| remark          | VARCHAR(255) | YES  |     |         | 备注         |
| create_user     | BIGINT(20)   | YES  |     |         | 创建人        |
| create_dept     | BIGINT(20)   | YES  |     |         | 创建部门       |
| create_time     | DATETIME(19) | YES  |     |         | 创建时间       |
| update_user     | BIGINT(20)   | YES  |     |         | 修改人        |
| update_time     | DATETIME(19) | YES  |     |         | 修改时间       |
| status          | INT(10)      | YES  |     |         | 状态         |
| is_deleted      | INT(10)      | YES  |     |         | 是否已删除      |

Table: blade_dept（机构表）

| Field         | Type          | Null | Key | Default | Remarks |
| ------------- | ------------- | ---- | --- | ------- | ------- |
| id            | BIGINT(20)    | NO   | PRI |         | 主键      |
| tenant_id     | VARCHAR(12)   | YES  |     | 000000  | 租户ID    |
| parent_id     | BIGINT(20)    | YES  |     | 0       | 父主键     |
| ancestors     | VARCHAR(2000) | YES  |     |         | 祖级列表    |
| dept_category | INT(10)       | YES  |     |         | 部门类型    |
| dept_name     | VARCHAR(45)   | YES  |     |         | 部门名     |
| full_name     | VARCHAR(45)   | YES  |     |         | 部门全称    |
| sort          | INT(10)       | YES  |     |         | 排序      |
| remark        | VARCHAR(255)  | YES  |     |         | 备注      |
| status        | INT(10)       | YES  |     |         | 状态      |
| is_deleted    | INT(10)       | YES  |     | 0       | 是否已删除   |

Table: blade_dict（字典表）

| Field      | Type         | Null | Key | Default | Remarks |
| ---------- | ------------ | ---- | --- | ------- | ------- |
| id         | BIGINT(20)   | NO   | PRI |         | 主键      |
| parent_id  | BIGINT(20)   | YES  |     | 0       | 父主键     |
| code       | VARCHAR(255) | YES  |     |         | 字典码     |
| dict_key   | VARCHAR(255) | YES  |     |         | 字典值     |
| dict_value | VARCHAR(255) | YES  |     |         | 字典名称    |
| sort       | INT(10)      | YES  |     |         | 排序      |
| remark     | VARCHAR(255) | YES  |     |         | 字典备注    |
| is_sealed  | INT(10)      | YES  |     | 0       | 是否已封存   |
| status     | INT(10)       | YES  |     |         | 状态      |
| is_deleted | INT(10)      | YES  |     | 0       | 是否已删除   |

Table: blade_dict_biz（业务字典表）

| Field      | Type         | Null | Key | Default | Remarks |
| ---------- | ------------ | ---- | --- | ------- | ------- |
| id         | BIGINT(20)   | NO   | PRI |         | 主键      |
| tenant_id  | VARCHAR(12)  | YES  |     |         | 租户ID    |
| parent_id  | BIGINT(20)   | YES  |     | 0       | 父主键     |
| code       | VARCHAR(255) | YES  |     |         | 字典码     |
| dict_key   | VARCHAR(255) | YES  |     |         | 字典值     |
| dict_value | VARCHAR(255) | YES  |     |         | 字典名称    |
| sort       | INT(10)      | YES  |     |         | 排序      |
| remark     | VARCHAR(255) | YES  |     |         | 字典备注    |
| is_sealed  | INT(10)      | YES  |     | 0       | 是否已封存   |
| status     | INT(10)       | YES  |     |         | 状态      |
| is_deleted | INT(10)      | YES  |     | 0       | 是否已删除   |

Table: blade_log_api（接口日志表）

| Field        | Type          | Null | Key | Default           | Remarks |
| ------------ | ------------- | ---- | --- | ----------------- | ------- |
| id           | BIGINT(20)    | NO   | PRI |                   | 编号      |
| tenant_id    | VARCHAR(12)   | YES  |     | 000000            | 租户ID    |
| service_id   | VARCHAR(32)   | YES  |     |                   | 服务ID    |
| server_host  | VARCHAR(255)  | YES  |     |                   | 服务器名    |
| server_ip    | VARCHAR(255)  | YES  |     |                   | 服务器IP地址 |
| env          | VARCHAR(255)  | YES  |     |                   | 服务器环境   |
| type         | CHAR(1)       | YES  |     | 1                 | 日志类型    |
| title        | VARCHAR(255)  | YES  |     |                   | 日志标题    |
| method       | VARCHAR(10)   | YES  |     |                   | 操作方式    |
| request_uri  | VARCHAR(255)  | YES  |     |                   | 请求URI   |
| user_agent   | VARCHAR(1000) | YES  |     |                   | 用户代理    |
| remote_ip    | VARCHAR(255)  | YES  |     |                   | 操作IP地址  |
| method_class | VARCHAR(255)  | YES  |     |                   | 方法类     |
| method_name  | VARCHAR(255)  | YES  |     |                   | 方法名     |
| params       | TEXT(65535)   | YES  |     |                   | 操作提交的数据 |
| time         | VARCHAR(64)   | YES  |     |                   | 执行时间    |
| create_by    | VARCHAR(64)   | YES  |     |                   | 创建者     |
| create_time  | DATETIME(19)  | YES  |     | CURRENT_TIMESTAMP | 创建时间    |

Table: blade_log_error（错误日志表）

| Field          | Type          | Null | Key | Default           | Remarks |
| -------------- | ------------- | ---- | --- | ----------------- | ------- |
| id             | BIGINT(20)    | NO   | PRI |                   | 编号      |
| tenant_id      | VARCHAR(12)   | YES  |     | 000000            | 租户ID    |
| service_id     | VARCHAR(32)   | YES  |     |                   | 服务ID    |
| server_host    | VARCHAR(255)  | YES  |     |                   | 服务器名    |
| server_ip      | VARCHAR(255)  | YES  |     |                   | 服务器IP地址 |
| env            | VARCHAR(255)  | YES  |     |                   | 系统环境    |
| method         | VARCHAR(10)   | YES  |     |                   | 操作方式    |
| request_uri    | VARCHAR(255)  | YES  |     |                   | 请求URI   |
| user_agent     | VARCHAR(1000) | YES  |     |                   | 用户代理    |
| stack_trace    | TEXT(65535)   | YES  |     |                   | 堆栈      |
| exception_name | VARCHAR(255)  | YES  |     |                   | 异常名     |
| message        | TEXT(65535)   | YES  |     |                   | 异常信息    |
| line_number    | INT(10)       | YES  |     |                   | 错误行数    |
| remote_ip      | VARCHAR(255)  | YES  |     |                   | 操作IP地址  |
| method_class   | VARCHAR(255)  | YES  |     |                   | 方法类     |
| file_name      | VARCHAR(1000) | YES  |     |                   | 文件名     |
| method_name    | VARCHAR(255)  | YES  |     |                   | 方法名     |
| params         | TEXT(65535)   | YES  |     |                   | 操作提交的数据 |
| create_by      | VARCHAR(64)   | YES  |     |                   | 创建者     |
| create_time    | DATETIME(19)  | YES  |     | CURRENT_TIMESTAMP | 创建时间    |

Table: blade_log_usual（通用日志表）

| Field        | Type          | Null | Key | Default           | Remarks |
| ------------ | ------------- | ---- | --- | ----------------- | ------- |
| id           | BIGINT(20)    | NO   | PRI |                   | 编号      |
| tenant_id    | VARCHAR(12)   | YES  |     | 000000            | 租户ID    |
| service_id   | VARCHAR(32)   | YES  |     |                   | 服务ID    |
| server_host  | VARCHAR(255)  | YES  |     |                   | 服务器名    |
| server_ip    | VARCHAR(255)  | YES  |     |                   | 服务器IP地址 |
| env          | VARCHAR(255)  | YES  |     |                   | 系统环境    |
| log_level    | VARCHAR(10)   | YES  |     |                   | 日志级别    |
| log_id       | VARCHAR(100)  | YES  |     |                   | 日志业务id  |
| log_data     | TEXT(65535)   | YES  |     |                   | 日志数据    |
| method       | VARCHAR(10)   | YES  |     |                   | 操作方式    |
| request_uri  | VARCHAR(255)  | YES  |     |                   | 请求URI   |
| remote_ip    | VARCHAR(255)  | YES  |     |                   | 操作IP地址  |
| method_class | VARCHAR(255)  | YES  |     |                   | 方法类     |
| method_name  | VARCHAR(255)  | YES  |     |                   | 方法名     |
| user_agent   | VARCHAR(1000) | YES  |     |                   | 用户代理    |
| params       | TEXT(65535)   | YES  |     |                   | 操作提交的数据 |
| create_by    | VARCHAR(64)   | YES  |     |                   | 创建者     |
| create_time  | DATETIME(19)  | YES  |     | CURRENT_TIMESTAMP | 创建时间    |

Table: blade_menu（菜单表）

| Field      | Type         | Null | Key | Default | Remarks |
| ---------- | ------------ | ---- | --- | ------- | ------- |
| id         | BIGINT(20)   | NO   | PRI |         | 主键      |
| parent_id  | BIGINT(20)   | YES  |     | 0       | 父级菜单    |
| code       | VARCHAR(255) | YES  |     |         | 菜单编号    |
| name       | VARCHAR(255) | YES  |     |         | 菜单名称    |
| alias      | VARCHAR(255) | YES  |     |         | 菜单别名    |
| path       | VARCHAR(255) | YES  |     |         | 请求地址    |
| source     | VARCHAR(255) | YES  |     |         | 菜单资源    |
| sort       | INT(10)      | YES  |     |         | 排序      |
| category   | INT(10)      | YES  |     |         | 菜单类型    |
| action     | INT(10)      | YES  |     | 0       | 操作按钮类型  |
| is_open    | INT(10)      | YES  |     | 1       | 是否打开新页面 |
| remark     | VARCHAR(255) | YES  |     |         | 备注      |
| is_deleted | INT(10)      | YES  |     | 0       | 是否已删除   |


Table: blade_model（数据模型表）

| Field         | Type          | Null | Key | Default | Remarks |
| ------------- | ------------- | ---- | --- | ------- | ------- |
| id            | BIGINT(20)    | NO   | PRI |         | 主键      |
| datasource_id | BIGINT(20)    | YES  |     |         | 数据源主键 |
| model_name    | VARCHAR(50)   | YES  |     |         | 模型名称   |
| model_code    | VARCHAR(50)   | YES  |     |         | 模型编号   |
| model_table   | VARCHAR(100)  | YES  |     |         | 物理表名   |
| model_class   | VARCHAR(100)  | YES  |     |         | 模型类名   |
| model_remark  | VARCHAR(500)  | YES  |     |         | 模型备注   |
| create_user   | BIGINT(20)    | YES  |     |         | 创建人     |
| create_dept   | BIGINT(20)    | YES  |     |         | 创建部门    |
| create_time   | DATETIME(19)  | YES  |     |         | 创建时间    |
| update_user   | BIGINT(20)    | YES  |     |         | 修改人     |
| update_time   | DATETIME(19)  | YES  |     |         | 修改时间    |
| status        | INT(10)       | YES  |     |         | 状态      |
| is_deleted    | INT(10)       | YES  |     |         | 是否已删除  |


Table: blade_model_prototype（数据原型表）

| Field          | Type          | Null | Key | Default | Remarks |
| -------------- | ------------- | ---- | --- | ------- | ------- |
| id             | BIGINT(20)    | NO   | PRI |         | 主键      |
| model_id       | BIGINT(20)    | YES  |     |         | 模型主键   |
| jdbc_name      | VARCHAR(50)   | YES  |     |         | 物理列名   |
| jdbc_type      | VARCHAR(20)   | YES  |     |         | 物理类型   |
| jdbc_comment   | VARCHAR(500)  | YES  |     |         | 注释说明   |
| property_type  | VARCHAR(20)   | YES  |     |         | 实体类型   |
| property_entity| VARCHAR(500)  | YES  |     |         | 实体类型引用|
| property_name  | VARCHAR(50)   | YES  |     |         | 实体列名   |
| is_list        | INT(10)       | YES  |     |         | 列表显示   |
| is_form        | INT(10)       | YES  |     |         | 表单显示   |
| is_row         | INT(10)       | YES  |     |         | 独占一行   |
| component_type | VARCHAR(50)   | YES  |     |         | 组件类型   |
| dict_code      | VARCHAR(50)   | YES  |     |         | 字典编码   |
| is_required    | INT(10)       | YES  |     |         | 是否必填   |
| is_query       | INT(10)       | YES  |     |         | 查询配置   |
| query_type     | VARCHAR(50)   | YES  |     |         | 查询配置   |
| create_user    | BIGINT(20)    | YES  |     |         | 创建人     |
| create_dept    | BIGINT(20)    | YES  |     |         | 创建部门    |
| create_time    | DATETIME(19)  | YES  |     |         | 创建时间    |
| update_user    | BIGINT(20)    | YES  |     |         | 修改人     |
| update_time    | DATETIME(19)  | YES  |     |         | 修改时间    |
| status         | INT(10)       | YES  |     |         | 状态      |
| is_deleted     | INT(10)       | YES  |     |         | 是否已删除  |


Table: blade_notice（通知公告表）

| Field        | Type          | Null | Key | Default | Remarks |
| ------------ | ------------- | ---- | --- | ------- | ------- |
| id           | BIGINT(20)    | NO   | PRI |         | 主键      |
| tenant_id    | VARCHAR(12)   | YES  |     | 000000  | 租户ID    |
| title        | VARCHAR(255)  | YES  |     |         | 标题      |
| category     | INT(10)       | YES  |     |         | 类型      |
| release_time | DATETIME(19)  | YES  |     |         | 发布时间    |
| content      | VARCHAR(2000) | YES  |     |         | 内容      |
| create_user  | BIGINT(20)    | YES  |     |         | 创建人     |
| create_dept  | BIGINT(20)    | YES  |     |         | 创建部门    |
| create_time  | DATETIME(19)  | YES  |     |         | 创建时间    |
| update_user  | BIGINT(20)    | YES  |     |         | 修改人     |
| update_time  | DATETIME(19)  | YES  |     |         | 修改时间    |
| status       | INT(10)       | YES  |     |         | 状态      |
| is_deleted   | INT(10)       | YES  |     |         | 是否已删除   |

Table: blade_oss（对象存储表）

| Field              | Type         | Null | Key | Default | Remarks |
|--------------------| ------------ | ---- | --- | ------- | ------- |
| id                 | BIGINT(20)   | NO   | PRI |         | 主键      |
| tenant_id          | VARCHAR(12)  | YES  |     | 000000  | 租户ID    |
| category           | INT(10)      | YES  |     |         | 分类      |
| oss_code           | VARCHAR(32)  | YES  |     |         | 资源编号    |
| endpoint           | VARCHAR(255) | YES  |     |         | 资源地址    |
| transform_endpoint | VARCHAR(255) | YES  |     |         | 外网资源地址    |
| access_key         | VARCHAR(255) | YES  |     |         | accessKey|
| secret_key         | VARCHAR(255) | YES  |     |         | secretKey|
| bucket_name        | VARCHAR(255) | YES  |     |         | 空间名     |
| app_id             | VARCHAR(255) | YES  |     |         | 应用ID    |
| region             | VARCHAR(255) | YES  |     |         | 地域简称    |
| remark             | VARCHAR(255) | YES  |     |         | 备注      |
| create_user        | BIGINT(20)   | YES  |     |         | 创建人     |
| create_dept        | BIGINT(20)   | YES  |     |         | 创建部门    |
| create_time        | DATETIME(19) | YES  |     |         | 创建时间    |
| update_user        | BIGINT(20)   | YES  |     |         | 修改人     |
| update_time        | DATETIME(19) | YES  |     |         | 修改时间    |
| status             | INT(10)      | YES  |     |         | 状态      |
| is_deleted         | INT(10)      | YES  |     | 0       | 是否已删除   |

Table: blade_param（参数表）

| Field       | Type         | Null | Key | Default | Remarks |
| ----------- | ------------ | ---- | --- | ------- | ------- |
| id          | BIGINT(20)   | NO   | PRI |         | 主键      |
| param_name  | VARCHAR(255) | YES  |     |         | 参数名     |
| param_key   | VARCHAR(255) | YES  |     |         | 参数键     |
| param_value | VARCHAR(255) | YES  |     |         | 参数值     |
| remark      | VARCHAR(255) | YES  |     |         | 备注      |
| create_user | BIGINT(20)   | YES  |     |         | 创建人     |
| create_dept | BIGINT(20)   | YES  |     |         | 创建部门    |
| create_time | DATETIME(19) | YES  |     |         | 创建时间    |
| update_user | BIGINT(20)   | YES  |     |         | 修改人     |
| update_time | DATETIME(19) | YES  |     |         | 修改时间    |
| status      | INT(10)      | YES  |     |         | 状态      |
| is_deleted  | INT(10)      | YES  |     | 0       | 是否已删除   |

Table: blade_post（岗位表）

| Field       | Type         | Null | Key | Default | Remarks |
| ----------- | ------------ | ---- | --- | ------- | ------- |
| id          | BIGINT(20)   | NO   | PRI |         | 主键      |
| tenant_id   | VARCHAR(12)  | YES  |     | 000000  | 租户ID    |
| category    | INT(10)      | YES  |     |         | 岗位类型    |
| post_code   | VARCHAR(12)  | YES  |     |         | 岗位编号    |
| post_name   | VARCHAR(64)  | YES  |     |         | 岗位名称    |
| sort        | INT(10)      | YES  |     |         | 岗位排序    |
| remark      | VARCHAR(255) | YES  |     |         | 岗位描述    |
| create_user | BIGINT(20)   | YES  |     |         | 创建人     |
| create_dept | BIGINT(20)   | YES  |     |         | 创建部门    |
| create_time | DATETIME(19) | YES  |     |         | 创建时间    |
| update_user | BIGINT(20)   | YES  |     |         | 修改人     |
| update_time | DATETIME(19) | YES  |     |         | 修改时间    |
| status      | INT(10)      | YES  |     |         | 状态      |
| is_deleted  | INT(10)      | YES  |     |         | 是否已删除   |

Table: blade_process_leave（流程请假业务表）

| Field                 | Type         | Null | Key | Default | Remarks |
| --------------------- | ------------ | ---- | --- | ------- | ------- |
| id                    | BIGINT(20)   | NO   | PRI |         | 编号      |
| process_definition_id | VARCHAR(64)  | YES  |     |         | 流程定义主键  |
| process_instance_id   | VARCHAR(64)  | YES  |     |         | 流程实例主键  |
| start_time            | DATETIME(19) | YES  |     |         | 开始时间    |
| end_time              | DATETIME(19) | YES  |     |         | 结束时间    |
| reason                | VARCHAR(255) | YES  |     |         | 请假理由    |
| task_user             | VARCHAR(255) | YES  |     |         | 第一级审批人  |
| apply_time            | DATETIME(19) | YES  |     |         | 申请时间    |
| create_user           | BIGINT(20)   | YES  |     |         | 创建人     |
| create_dept           | BIGINT(20)   | YES  |     |         | 创建部门    |
| create_time           | DATETIME(19) | YES  |     |         | 创建时间    |
| update_user           | BIGINT(20)   | YES  |     |         | 修改人     |
| update_time           | DATETIME(19) | YES  |     |         | 修改时间    |
| status                | INT(10)      | YES  |     |         | 状态      |
| is_deleted            | INT(10)      | YES  |     |         | 是否已删除   |

Table: blade_region（行政区划表）

| Field         | Type         | Null | Key | Default | Remarks |
| ------------- | ------------ | ---- | --- | ------- | ------- |
| code          | VARCHAR(12)  | NO   | PRI |         | 区划编号    |
| parent_code   | VARCHAR(12)  | YES  |     |         | 父区划编号   |
| ancestors     | VARCHAR(255) | YES  |     |         | 祖区划编号   |
| name          | VARCHAR(32)  | YES  |     |         | 区划名称    |
| province_code | VARCHAR(12)  | YES  |     |         | 省级区划编号  |
| province_name | VARCHAR(32)  | YES  |     |         | 省级名称    |
| city_code     | VARCHAR(12)  | YES  |     |         | 市级区划编号  |
| city_name     | VARCHAR(32)  | YES  |     |         | 市级名称    |
| district_code | VARCHAR(12)  | YES  |     |         | 区级区划编号  |
| district_name | VARCHAR(32)  | YES  |     |         | 区级名称    |
| town_code     | VARCHAR(12)  | YES  |     |         | 镇级区划编号  |
| town_name     | VARCHAR(32)  | YES  |     |         | 镇级名称    |
| village_code  | VARCHAR(12)  | YES  |     |         | 村级区划编号  |
| village_name  | VARCHAR(32)  | YES  |     |         | 村级名称    |
| region_level  | INT(10)      | YES  |     |         | 层级      |
| sort          | INT(10)      | YES  |     |         | 排序      |
| remark        | VARCHAR(255) | YES  |     |         | 备注      |

Table: blade_report_file（报表文件表）

| Field       | Type                 | Null | Key | Default | Remarks |
| ----------- | -------------------- | ---- | --- | ------- | ------- |
| id          | BIGINT(20)           | NO   | PRI |         | 主键      |
| name        | VARCHAR(100)         | NO   |     |         | 文件名     |
| content     | MEDIUMBLOB(16777215) | YES  |     |         | 文件内容    |
| create_time | DATETIME(19)         | YES  |     |         | 创建时间    |
| update_time | DATETIME(19)         | YES  |     |         | 更新时间    |
| is_deleted  | INT(10)              | YES  |     | 0       | 是否已删除   |

Table: blade_role（角色表）

| Field      | Type         | Null | Key | Default | Remarks |
| ---------- | ------------ | ---- | --- | ------- | ------- |
| id         | BIGINT(20)   | NO   | PRI |         | 主键      |
| tenant_id  | VARCHAR(12)  | YES  |     | 000000  | 租户ID    |
| parent_id  | BIGINT(20)   | YES  |     | 0       | 父主键     |
| role_name  | VARCHAR(255) | YES  |     |         | 角色名     |
| sort       | INT(10)      | YES  |     |         | 排序      |
| role_alias | VARCHAR(255) | YES  |     |         | 角色别名    |
| status     | INT(10)       | YES  |     |         | 状态      |
| is_deleted | INT(10)      | YES  |     | 0       | 是否已删除   |

Table: blade_role_menu（角色菜单关联表）

| Field   | Type       | Null | Key | Default | Remarks |
| ------- | ---------- | ---- | --- | ------- | ------- |
| id      | BIGINT(20) | NO   | PRI |         | 主键      |
| menu_id | BIGINT(20) | YES  |     |         | 菜单id    |
| role_id | BIGINT(20) | YES  |     |         | 角色id    |

Table: blade_role_scope（角色数据权限关联表）

| Field          | Type       | Null | Key | Default | Remarks |
| -------------- | ---------- | ---- | --- | ------- | ------- |
| id             | BIGINT(20) | NO   | PRI |         | 主键      |
| scope_category | INT(10)    | YES  |     |         | 权限类型(1:数据权限、2:接口权限)|
| scope_id       | BIGINT(20) | YES  |     |         | 权限id    |
| role_id        | BIGINT(20) | YES  |     |         | 角色id    |

Table: blade_scope_api（接口权限表）

| Field         | Type         | Null | Key | Default | Remarks |
| ------------- | ------------ | ---- | --- | ------- | ------- |
| id            | BIGINT(20)   | NO   | PRI |         | 主键      |
| menu_id       | BIGINT(20)   | YES  |     |         | 菜单主键    |
| resource_code | VARCHAR(255) | YES  |     |         | 资源编号    |
| scope_name    | VARCHAR(255) | YES  |     |         | 接口权限名   |
| scope_path    | VARCHAR(255) | YES  |     |         | 接口权限地址  |
| scope_type    | INT(10)      | YES  |     |         | 接口权限类型  |
| remark        | VARCHAR(255) | YES  |     |         | 接口权限备注  |
| create_user   | BIGINT(20)   | YES  |     |         | 创建人     |
| create_dept   | BIGINT(20)   | YES  |     |         | 创建部门    |
| create_time   | DATETIME(19) | YES  |     |         | 创建时间    |
| update_user   | BIGINT(20)   | YES  |     |         | 修改人     |
| update_time   | DATETIME(19) | YES  |     |         | 修改时间    |
| status        | INT(10)      | YES  |     |         | 状态      |
| is_deleted    | INT(10)      | YES  |     |         | 是否已删除   |

Table: blade_scope_data（数据权限表）

| Field         | Type          | Null | Key | Default | Remarks |
| ------------- | ------------- | ---- | --- | ------- | ------- |
| id            | BIGINT(20)    | NO   | PRI |         | 主键      |
| menu_id       | BIGINT(20)    | YES  |     |         | 菜单主键    |
| resource_code | VARCHAR(255)  | YES  |     |         | 资源编号    |
| scope_name    | VARCHAR(255)  | YES  |     |         | 数据权限名称  |
| scope_field   | VARCHAR(255)  | YES  |     |         | 数据权限字段  |
| scope_class   | VARCHAR(500)  | YES  |     |         | 数据权限类名  |
| scope_column  | VARCHAR(255)  | YES  |     |         | 数据权限字段  |
| scope_type    | INT(10)       | YES  |     |         | 数据权限类型  |
| scope_value   | VARCHAR(2000) | YES  |     |         | 数据权限值域  |
| remark        | VARCHAR(255)  | YES  |     |         | 数据权限备注  |
| create_user   | BIGINT(20)    | YES  |     |         | 创建人     |
| create_dept   | BIGINT(20)    | YES  |     |         | 创建部门    |
| create_time   | DATETIME(19)  | YES  |     |         | 创建时间    |
| update_user   | BIGINT(20)    | YES  |     |         | 修改人     |
| update_time   | DATETIME(19)  | YES  |     |         | 修改时间    |
| status        | INT(10)       | YES  |     |         | 状态      |
| is_deleted    | INT(10)       | YES  |     |         | 是否已删除   |

Table: blade_sms（短信配置表）

| Field       | Type         | Null | Key | Default | Remarks |
| ----------- | ------------ | ---- | --- | ------- | ------- |
| id          | BIGINT(20)   | NO   | PRI |         | 主键      |
| tenant_id   | VARCHAR(12)  | YES  |     | 000000  | 租户ID    |
| category    | INT(10)      | YES  |     |         | 分类      |
| sms_code    | VARCHAR(32)  | YES  |     |         | 资源编号    |
| template_id | VARCHAR(64)  | YES  |     |         | 模板ID    |
| access_key  | VARCHAR(255) | YES  |     |         | accessKey|
| secret_key  | VARCHAR(255) | YES  |     |         | secretKey|
| region_id   | VARCHAR(255) | YES  |     |         | regionId|
| sign_name   | VARCHAR(64)  | YES  |     |         | 短信签名    |
| remark      | VARCHAR(255) | YES  |     |         | 备注      |
| create_user | BIGINT(20)   | YES  |     |         | 创建人     |
| create_dept | BIGINT(20)   | YES  |     |         | 创建部门    |
| create_time | DATETIME(19) | YES  |     |         | 创建时间    |
| update_user | BIGINT(20)   | YES  |     |         | 修改人     |
| update_time | DATETIME(19) | YES  |     |         | 修改时间    |
| status      | INT(10)      | YES  |     |         | 状态      |
| is_deleted  | INT(10)      | YES  |     | 0       | 是否已删除   |

Table: blade_tenant（租户表）

| Field          | Type          | Null | Key | Default | Remarks |
| -------------- | ------------- | ---- | --- | ------- | ------- |
| id             | BIGINT(20)    | NO   | PRI |         | 主键      |
| tenant_id      | VARCHAR(12)   | YES  |     | 000000  | 租户ID    |
| tenant_name    | VARCHAR(50)   | NO   |     |         | 租户名称    |
| domain_url     | VARCHAR(255)  | YES  |     |         | 域名地址    |
| background_url | VARCHAR(1000) | YES  |     |         | 系统背景    |
| linkman        | VARCHAR(20)   | YES  |     |         | 联系人     |
| contact_number | VARCHAR(20)   | YES  |     |         | 联系电话    |
| address        | VARCHAR(255)  | YES  |     |         | 联系地址    |
| account_number | INT(10)       | YES  |     | -1      | 账号额度    |
| expire_time    | DATETIME(19)  | YES  |     |         | 过期时间    |
| datasource     | BIGINT(20)    | YES  |     |         | 数据源ID   |
| license_key    | VARCHAR(1000) | YES  |     |         | 授权码     |
| create_user    | BIGINT(20)    | YES  |     |         | 创建人     |
| create_dept    | BIGINT(20)    | YES  |     |         | 创建部门    |
| create_time    | DATETIME(19)  | YES  |     |         | 创建时间    |
| update_user    | BIGINT(20)    | YES  |     |         | 修改人     |
| update_time    | DATETIME(19)  | YES  |     |         | 修改时间    |
| status         | INT(10)       | YES  |     |         | 状态      |
| is_deleted     | INT(10)       | YES  |     | 0       | 是否已删除   |

Table: blade_top_menu（顶部菜单表）

| Field       | Type         | Null | Key | Default | Remarks |
| ----------- | ------------ | ---- | --- | ------- | ------- |
| id          | BIGINT(20)   | NO   | PRI |         | 主键      |
| tenant_id   | VARCHAR(12)  | YES  |     |         | 租户id    |
| code        | VARCHAR(255) | YES  |     |         | 顶部菜单编号  |
| name        | VARCHAR(255) | YES  |     |         | 顶部菜单名   |
| source      | VARCHAR(255) | YES  |     |         | 顶部菜单资源  |
| sort        | INT(10)      | YES  |     |         | 顶部菜单排序  |
| create_user | BIGINT(20)   | YES  |     |         | 创建人     |
| create_dept | BIGINT(20)   | YES  |     |         | 创建部门    |
| create_time | DATETIME(19) | YES  |     |         | 创建时间    |
| update_user | BIGINT(20)   | YES  |     |         | 修改人     |
| update_time | DATETIME(19) | YES  |     |         | 修改时间    |
| status      | INT(10)      | YES  |     |         | 状态      |
| is_deleted  | INT(10)      | YES  |     |         | 是否已删除   |

Table: blade_top_menu_setting（顶部菜单配置表）

| Field       | Type       | Null | Key | Default | Remarks |
| ----------- | ---------- | ---- | --- | ------- | ------- |
| id          | BIGINT(20) | NO   | PRI |         | 主键      |
| top_menu_id | BIGINT(20) | YES  |     |         | 顶部菜单主键  |
| menu_id     | BIGINT(20) | YES  |     |         | 菜单主键    |

Table: blade_user（用户表）

| Field       | Type          | Null | Key | Default | Remarks |
| ----------- | ------------- | ---- | --- | ------- | ------- |
| id          | BIGINT(20)    | NO   | PRI |         | 主键      |
| tenant_id   | VARCHAR(12)   | YES  |     | 000000  | 租户ID    |
| code        | VARCHAR(12)   | YES  |     |         | 用户编号    |
| user_type   | SMALLINT(5)   | YES  |     |         | 用户平台    |
| account     | VARCHAR(45)   | YES  |     |         | 账号      |
| password    | VARCHAR(45)   | YES  |     |         | 密码      |
| name        | VARCHAR(20)   | YES  |     |         | 昵称      |
| real_name   | VARCHAR(10)   | YES  |     |         | 真名      |
| avatar      | VARCHAR(500)  | YES  |     |         | 头像      |
| email       | VARCHAR(45)   | YES  |     |         | 邮箱      |
| phone       | VARCHAR(45)   | YES  |     |         | 手机      |
| birthday    | DATETIME(19)  | YES  |     |         | 生日      |
| sex         | SMALLINT(5)   | YES  |     |         | 性别      |
| role_id     | VARCHAR(1000) | YES  |     |         | 角色id    |
| dept_id     | VARCHAR(1000) | YES  |     |         | 部门id    |
| post_id     | VARCHAR(1000) | YES  |     |         | 岗位id    |
| create_user | BIGINT(20)    | YES  |     |         | 创建人     |
| create_dept | BIGINT(20)    | YES  |     |         | 创建部门    |
| create_time | DATETIME(19)  | YES  |     |         | 创建时间    |
| update_user | BIGINT(20)    | YES  |     |         | 修改人     |
| update_time | DATETIME(19)  | YES  |     |         | 修改时间    |
| status      | INT(10)       | YES  |     |         | 状态      |
| is_deleted  | INT(10)       | YES  |     | 0       | 是否已删除   |

Table: blade_user_dept（用户部门表）

| Field   | Type       | Null | Key | Default | Remarks |
| ------- | ---------- | ---- | --- | ------- | ------- |
| id      | BIGINT(20) | NO   | PRI |         | 主键      |
| user_id | BIGINT(20) | YES  |     | 0       | 用户ID    |
| dept_id | BIGINT(20) | YES  |     | 0       | 部门ID    |
| status        | INT(10)       | YES  |     |         | 状态      |
| is_deleted    | INT(10)       | YES  |     |         | 是否已删除   |

Table: blade_user_oauth（用户第三方认证表）

| Field     | Type          | Null | Key | Default | Remarks |
| --------- | ------------- | ---- | --- | ------- | ------- |
| id        | BIGINT(20)    | NO   | PRI |         | 主键      |
| tenant_id | VARCHAR(12)   | YES  |     |         | 租户ID    |
| uuid      | VARCHAR(64)   | YES  |     |         | 第三方系统用户ID|
| user_id   | BIGINT(20)    | YES  |     |         | 用户ID    |
| username  | VARCHAR(32)   | YES  |     |         | 账号      |
| nickname  | VARCHAR(64)   | YES  |     |         | 用户名     |
| avatar    | VARCHAR(1000) | YES  |     |         | 头像      |
| blog      | VARCHAR(50)   | YES  |     |         | 应用主页    |
| company   | VARCHAR(255)  | YES  |     |         | 公司名     |
| location  | VARCHAR(255)  | YES  |     |         | 地址      |
| email     | VARCHAR(255)  | YES  |     |         | 邮件      |
| remark    | VARCHAR(255)  | YES  |     |         | 备注      |
| gender    | VARCHAR(16)   | YES  |     |         | 性别      |
| source    | VARCHAR(16)   | YES  |     |         | 来源      |
| status        | INT(10)       | YES  |     |         | 状态      |
| is_deleted    | INT(10)       | YES  |     |         | 是否已删除   |

Table: blade_user_web（用户平台拓展表）

| Field     | Type          | Null | Key | Default | Remarks |
| -------   | ----------    | ---- | --- | ------- | ------- |
| id        | BIGINT(20)    | NO   | PRI |         | 主键      |
| user_id   | BIGINT(20)    | YES  |     | 0       | 用户ID    |
| user_ext  | VARCHAR(255)  | YES  |     |         | 用户拓展信息   |
| status        | INT(10)       | YES  |     |         | 状态      |
| is_deleted    | INT(10)       | YES  |     |         | 是否已删除   |

Table: blade_user_app（用户平台拓展表）

| Field     | Type          | Null | Key | Default | Remarks |
| -------   | ----------    | ---- | --- | ------- | ------- |
| id        | BIGINT(20)    | NO   | PRI |         | 主键      |
| user_id   | BIGINT(20)    | YES  |     | 0       | 用户ID    |
| user_ext  | VARCHAR(255)  | YES  |     |         | 用户拓展信息   |
| status        | INT(10)       | YES  |     |         | 状态      |
| is_deleted    | INT(10)       | YES  |     |         | 是否已删除   |

Table: blade_user_other（用户平台拓展表）

| Field     | Type          | Null | Key | Default | Remarks |
| -------   | ----------    | ---- | --- | ------- | ------- |
| id        | BIGINT(20)    | NO   | PRI |         | 主键      |
| user_id   | BIGINT(19)    | YES  |     | 0       | 用户ID    |
| user_ext  | VARCHAR(255)  | YES  |     |         | 用户拓展信息   |
| status        | INT(10)       | YES  |     |         | 状态      |
| is_deleted    | INT(10)       | YES  |     |         | 是否已删除   |

Table: blade_job_server (任务服务表)

| Field             | Type         | Null | Key | Default | Remarks              |
|-------------------|--------------|------|-----|---------|----------------------|
| id                | BIGINT(20)   | NO   | PRI |         | 主键                 |
| job_server_name   | VARCHAR(50)  | YES  |     | NULL    | 任务服务名称         |
| job_server_url    | VARCHAR(255) | YES  |     | NULL    | 任务服务器地址       |
| job_app_name      | VARCHAR(20)  | YES  |     | NULL    | 任务应用名称         |
| job_app_password  | VARCHAR(100) | YES  |     | NULL    | 任务应用密码         |
| job_remark        | VARCHAR(255) | YES  |     | NULL    | 任务备注             |
| create_user       | BIGINT(20)   | YES  |     | NULL    | 创建人               |
| create_dept       | BIGINT(20)   | YES  |     | NULL    | 创建部门             |
| create_time       | DATETIME     | YES  |     | NULL    | 创建时间             |
| update_user       | BIGINT(20)   | YES  |     | NULL    | 修改人               |
| update_time       | DATETIME     | YES  |     | NULL    | 修改时间             |
| status            | INT(2)       | YES  |     | NULL    | 状态                 |
| is_deleted        | INT(2)       | YES  |     | 0       | 是否已删除           |

Table: blade_job_info (任务信息表)

| Field                  | Type                   | Null | Key | Default | Remarks                           |
|------------------------|------------------------|------|-----|---------|-----------------------------------|
| id                     | BIGINT(20)             | NO   | PRI |         | 主键                              |
| job_server_id          | BIGINT(20)             | YES  |     | NULL    | 任务服务ID                        |
| job_id                 | BIGINT(20)             | YES  |     | NULL    | 任务 ID                           |
| job_name               | VARCHAR(50)            | YES  |     | NULL    | 任务名称                          |
| job_description        | VARCHAR(255)           | YES  |     | NULL    | 任务描述                          |
| job_params             | TEXT                   | YES  |     |         | 任务参数                          |
| time_expression_type   | INT(2)                 | YES  |     | NULL    | 时间表达式类型                    |
| time_expression        | VARCHAR(255)           | YES  |     | NULL    | 时间表达式                        |
| execute_type           | INT(2)                 | YES  |     | NULL    | 执行类型                          |
| processor_type         | INT(2)                 | YES  |     | NULL    | 处理器类型                        |
| processor_info         | VARCHAR(255)           | YES  |     | NULL    | 处理器参数                        |
| max_instance_num       | INT(11)                | YES  |     | NULL    | 最大实例数                        |
| concurrency            | INT(11)                | YES  |     | NULL    | 单机线程并发数                    |
| instance_time_limit    | BIGINT(20)             | YES  |     | NULL    | 任务实例运行时间限制              |
| instance_retry_num     | INT(11)                | YES  |     | NULL    | 任务实例重试次数                  |
| task_retry_num         | INT(11)                | YES  |     | NULL    | Task 重试次数                     |
| min_cpu_cores          | DOUBLE                 | YES  |     | NULL    | 最小可用 CPU 核心数               |
| min_memory_space       | DOUBLE                 | YES  |     | NULL    | 最小内存大小（GB）                |
| min_disk_space         | DOUBLE                 | YES  |     | NULL    | 最小磁盘大小（GB）                |
| designated_workers     | VARCHAR(255)           | YES  |     | NULL    | 指定机器执行                      |
| max_worker_count       | INT(2)                 | YES  |     | NULL    | 最大执行机器数量                  |
| notify_user_ids        | VARCHAR(2000)          | YES  |     | NULL    | 接收报警的用户 ID 列表            |
| enable                 | INT(2)                 | YES  |     | NULL    | 是否启用该任务                    |
| dispatch_strategy      | INT(2)                 | YES  |     | NULL    | 调度策略                          |
| lifecycle              | VARCHAR(255)           | YES  |     | NULL    | 生命周期                          |
| alert_threshold        | INT(2)                 | YES  |     | NULL    | 错误阈值                          |
| statistic_window_len   | INT(2)                 | YES  |     | NULL    | 统计的窗口长度(s)                 |
| silence_window_len     | INT(2)                 | YES  |     | NULL    | 沉默时间窗口(s)                   |
| log_type               | INT(2)                 | YES  |     | NULL    | 日志配置                          |
| log_level              | INT(2)                 | YES  |     | NULL    | 日志级别                          |
| extra                  | VARCHAR(255)           | YES  |     | NULL    | 扩展字段                          |
| create_user            | BIGINT(20)             | YES  |     | NULL    | 创建人                            |
| create_dept            | BIGINT(20)             | YES  |     | NULL    | 创建部门                          |
| create_time            | DATETIME               | YES  |     | NULL    | 创建时间                          |
| update_user            | BIGINT(20)             | YES  |     | NULL    | 修改人                            |
| update_time            | DATETIME               | YES  |     | NULL    | 修改时间                          |
| status                 | INT(2)                 | YES  |     | NULL    | 状态                              |
| is_deleted             | INT(2)                 | YES  |     | 0       | 是否已删除                        |
