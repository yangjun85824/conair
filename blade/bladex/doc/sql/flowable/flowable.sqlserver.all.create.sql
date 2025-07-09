/*
 Navicat Premium Data Transfer

 Source Server         : sqlserver_localhost
 Source Server Type    : SQL Server
 Source Server Version : 15004355 (15.00.4355)
 Source Host           : 127.0.0.1:1433
 Source Catalog        : bladex
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 15004355 (15.00.4355)
 File Encoding         : 65001

 Date: 14/03/2024 23:56:25
*/


-- ----------------------------
-- Table structure for ACT_APP_APPDEF
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_APP_APPDEF]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_APP_APPDEF]
GO

CREATE TABLE [dbo].[ACT_APP_APPDEF] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [KEY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [VERSION_] int  NOT NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_NAME_] varchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION_] varchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_APP_APPDEF] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_APP_APPDEF
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_APP_DATABASECHANGELOG
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_APP_DATABASECHANGELOG]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_APP_DATABASECHANGELOG]
GO

CREATE TABLE [dbo].[ACT_APP_DATABASECHANGELOG] (
  [ID] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [AUTHOR] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [FILENAME] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [DATEEXECUTED] datetime2(3)  NOT NULL,
  [ORDEREXECUTED] int  NOT NULL,
  [EXECTYPE] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [MD5SUM] nvarchar(35) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [COMMENTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TAG] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LIQUIBASE] nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTEXTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LABELS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_APP_DATABASECHANGELOG] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_APP_DATABASECHANGELOG
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_APP_DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS], [DEPLOYMENT_ID]) VALUES (N'1', N'flowable', N'org/flowable/app/db/liquibase/flowable-app-db-changelog.xml', N'2024-03-14 15:52:53.623', N'1', N'EXECUTED', N'9:959783069c0c7ce80320a0617aa48969', N'createTable tableName=ACT_APP_DEPLOYMENT; createTable tableName=ACT_APP_DEPLOYMENT_RESOURCE; addForeignKeyConstraint baseTableName=ACT_APP_DEPLOYMENT_RESOURCE, constraintName=ACT_FK_APP_RSRC_DPL, referencedTableName=ACT_APP_DEPLOYMENT; createIndex...', N'', NULL, N'4.3.5', NULL, NULL, N'1644004287'), (N'3', N'flowable', N'org/flowable/app/db/liquibase/flowable-app-db-changelog.xml', N'2024-03-14 15:52:53.627', N'2', N'EXECUTED', N'9:c05b79a3b00e95136533085718361208', N'createIndex indexName=ACT_IDX_APP_DEF_UNIQ, tableName=ACT_APP_APPDEF', N'', NULL, N'4.3.5', NULL, NULL, N'1644004287')
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_APP_DATABASECHANGELOGLOCK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_APP_DATABASECHANGELOGLOCK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_APP_DATABASECHANGELOGLOCK]
GO

CREATE TABLE [dbo].[ACT_APP_DATABASECHANGELOGLOCK] (
  [ID] int  NOT NULL,
  [LOCKED] bit  NOT NULL,
  [LOCKGRANTED] datetime2(3)  NULL,
  [LOCKEDBY] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_APP_DATABASECHANGELOGLOCK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_APP_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_APP_DATABASECHANGELOGLOCK] ([ID], [LOCKED], [LOCKGRANTED], [LOCKEDBY]) VALUES (N'1', N'0', NULL, NULL)
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_APP_DEPLOYMENT
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_APP_DEPLOYMENT]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_APP_DEPLOYMENT]
GO

CREATE TABLE [dbo].[ACT_APP_DEPLOYMENT] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [KEY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOY_TIME_] datetime  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_APP_DEPLOYMENT] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_APP_DEPLOYMENT
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_APP_DEPLOYMENT_RESOURCE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_APP_DEPLOYMENT_RESOURCE]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_APP_DEPLOYMENT_RESOURCE]
GO

CREATE TABLE [dbo].[ACT_APP_DEPLOYMENT_RESOURCE] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_BYTES_] varbinary(max)  NULL
)
GO

ALTER TABLE [dbo].[ACT_APP_DEPLOYMENT_RESOURCE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_APP_DEPLOYMENT_RESOURCE
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_CASEDEF
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_CASEDEF]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_CASEDEF]
GO

CREATE TABLE [dbo].[ACT_CMMN_CASEDEF] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [KEY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [VERSION_] int  NOT NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_NAME_] varchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION_] varchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HAS_GRAPHICAL_NOTATION_] bit  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [DGRM_RESOURCE_NAME_] varchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HAS_START_FORM_KEY_] bit  NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_CASEDEF] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_CASEDEF
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_DATABASECHANGELOG
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_DATABASECHANGELOG]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_DATABASECHANGELOG]
GO

CREATE TABLE [dbo].[ACT_CMMN_DATABASECHANGELOG] (
  [ID] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [AUTHOR] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [FILENAME] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [DATEEXECUTED] datetime2(3)  NOT NULL,
  [ORDEREXECUTED] int  NOT NULL,
  [EXECTYPE] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [MD5SUM] nvarchar(35) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [COMMENTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TAG] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LIQUIBASE] nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTEXTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LABELS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_DATABASECHANGELOG] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_DATABASECHANGELOG
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_CMMN_DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS], [DEPLOYMENT_ID]) VALUES (N'1', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.693', N'1', N'EXECUTED', N'9:d0cc0aaadf0e4ef70c5b412cd05fadc4', N'createTable tableName=ACT_CMMN_DEPLOYMENT; createTable tableName=ACT_CMMN_DEPLOYMENT_RESOURCE; addForeignKeyConstraint baseTableName=ACT_CMMN_DEPLOYMENT_RESOURCE, constraintName=ACT_FK_CMMN_RSRC_DPL, referencedTableName=ACT_CMMN_DEPLOYMENT; create...', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'2', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.707', N'2', N'EXECUTED', N'9:8095a5a8a222a100c2d0310cacbda5e7', N'addColumn tableName=ACT_CMMN_CASEDEF; addColumn tableName=ACT_CMMN_DEPLOYMENT_RESOURCE; addColumn tableName=ACT_CMMN_RU_CASE_INST; addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'3', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.717', N'3', N'EXECUTED', N'9:f031b4f0ae67bc5a640736b379049b12', N'addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_RU_CASE_INST; createIndex indexName=ACT_IDX_PLAN_ITEM_STAGE_INST, tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableNam...', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'4', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.727', N'4', N'EXECUTED', N'9:c484ecfb08719feccac2f80fc962dda9', N'createTable tableName=ACT_CMMN_HI_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_RU_MIL_INST; addColumn tableName=ACT_CMMN_HI_MIL_INST', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'6', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.727', N'5', N'EXECUTED', N'9:7343ab247d959e5add9278b5386de833', N'createIndex indexName=ACT_IDX_CASE_DEF_UNIQ, tableName=ACT_CMMN_CASEDEF', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'7', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.830', N'6', N'EXECUTED', N'9:d73200db684b6cdb748cc03570d5d2e9', N'renameColumn newColumnName=CREATE_TIME_, oldColumnName=START_TIME_, tableName=ACT_CMMN_RU_PLAN_ITEM_INST; renameColumn newColumnName=CREATE_TIME_, oldColumnName=CREATED_TIME_, tableName=ACT_CMMN_HI_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_RU_P...', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'8', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.830', N'7', N'EXECUTED', N'9:eda5e43816221f2d8554bfcc90f1c37e', N'addColumn tableName=ACT_CMMN_HI_PLAN_ITEM_INST', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'9', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.837', N'8', N'EXECUTED', N'9:c34685611779075a73caf8c380f078ea', N'addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_HI_PLAN_ITEM_INST', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'10', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.847', N'9', N'EXECUTED', N'9:368e9472ad2348206205170d6c52d58e', N'addColumn tableName=ACT_CMMN_RU_CASE_INST; addColumn tableName=ACT_CMMN_RU_CASE_INST; createIndex indexName=ACT_IDX_CASE_INST_REF_ID_, tableName=ACT_CMMN_RU_CASE_INST; addColumn tableName=ACT_CMMN_HI_CASE_INST; addColumn tableName=ACT_CMMN_HI_CASE...', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'11', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.850', N'10', N'EXECUTED', N'9:e54b50ceb2bcd5355ae4dfb56d9ff3ad', N'addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_HI_PLAN_ITEM_INST', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'12', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.853', N'11', N'EXECUTED', N'9:f53f262768d04e74529f43fcd93429b0', N'addColumn tableName=ACT_CMMN_RU_CASE_INST', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'13', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.857', N'12', N'EXECUTED', N'9:64e7eafbe97997094654e83caea99895', N'addColumn tableName=ACT_CMMN_RU_PLAN_ITEM_INST; addColumn tableName=ACT_CMMN_HI_PLAN_ITEM_INST', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'14', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.867', N'13', N'EXECUTED', N'9:ab7d934abde497eac034701542e0a281', N'addColumn tableName=ACT_CMMN_RU_CASE_INST; addColumn tableName=ACT_CMMN_HI_CASE_INST', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'16', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.870', N'14', N'EXECUTED', N'9:03928d422e510959770e7a9daa5a993f', N'addColumn tableName=ACT_CMMN_RU_CASE_INST; addColumn tableName=ACT_CMMN_HI_CASE_INST', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'17', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:52:53.873', N'15', N'EXECUTED', N'9:f30304cf001d6eac78c793ea88cd5781', N'createIndex indexName=ACT_IDX_HI_CASE_INST_END, tableName=ACT_CMMN_HI_CASE_INST', N'', NULL, N'4.3.5', NULL, NULL, N'1644004724'), (N'18', N'flowable', N'org/flowable/cmmn/db/liquibase/flowable-cmmn-db-changelog.xml', N'2024-03-14 15:54:50.283', N'16', N'EXECUTED', N'9:d782865087d6c0c3dc033ac20e783008', N'createIndex indexName=ACT_IDX_HI_PLAN_ITEM_INST_CASE, tableName=ACT_CMMN_HI_PLAN_ITEM_INST', N'', NULL, N'4.24.0', NULL, NULL, N'0431690008')
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_DATABASECHANGELOGLOCK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_DATABASECHANGELOGLOCK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_DATABASECHANGELOGLOCK]
GO

CREATE TABLE [dbo].[ACT_CMMN_DATABASECHANGELOGLOCK] (
  [ID] int  NOT NULL,
  [LOCKED] bit  NOT NULL,
  [LOCKGRANTED] datetime2(3)  NULL,
  [LOCKEDBY] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_DATABASECHANGELOGLOCK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_CMMN_DATABASECHANGELOGLOCK] ([ID], [LOCKED], [LOCKGRANTED], [LOCKEDBY]) VALUES (N'1', N'0', NULL, NULL)
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_DEPLOYMENT
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_DEPLOYMENT]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_DEPLOYMENT]
GO

CREATE TABLE [dbo].[ACT_CMMN_DEPLOYMENT] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [KEY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOY_TIME_] datetime  NULL,
  [PARENT_DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_DEPLOYMENT] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_DEPLOYMENT
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_DEPLOYMENT_RESOURCE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_DEPLOYMENT_RESOURCE]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_DEPLOYMENT_RESOURCE]
GO

CREATE TABLE [dbo].[ACT_CMMN_DEPLOYMENT_RESOURCE] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_BYTES_] varbinary(max)  NULL,
  [GENERATED_] bit  NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_DEPLOYMENT_RESOURCE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_DEPLOYMENT_RESOURCE
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_HI_CASE_INST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_HI_CASE_INST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_HI_CASE_INST]
GO

CREATE TABLE [dbo].[ACT_CMMN_HI_CASE_INST] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NOT NULL,
  [BUSINESS_KEY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CASE_DEF_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [STATE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [START_TIME_] datetime  NULL,
  [END_TIME_] datetime  NULL,
  [START_USER_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CALLBACK_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CALLBACK_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [REFERENCE_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REFERENCE_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LAST_REACTIVATION_TIME_] datetime  NULL,
  [LAST_REACTIVATION_USER_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [BUSINESS_STATUS_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_HI_CASE_INST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_HI_CASE_INST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_HI_MIL_INST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_HI_MIL_INST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_HI_MIL_INST]
GO

CREATE TABLE [dbo].[ACT_CMMN_HI_MIL_INST] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [TIME_STAMP_] datetime  NOT NULL,
  [CASE_INST_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [CASE_DEF_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [ELEMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_HI_MIL_INST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_HI_MIL_INST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_HI_PLAN_ITEM_INST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_HI_PLAN_ITEM_INST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_HI_PLAN_ITEM_INST]
GO

CREATE TABLE [dbo].[ACT_CMMN_HI_PLAN_ITEM_INST] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [STATE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CASE_DEF_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CASE_INST_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [STAGE_INST_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IS_STAGE_] bit  NULL,
  [ELEMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ITEM_DEFINITION_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ITEM_DEFINITION_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime  NULL,
  [LAST_AVAILABLE_TIME_] datetime  NULL,
  [LAST_ENABLED_TIME_] datetime  NULL,
  [LAST_DISABLED_TIME_] datetime  NULL,
  [LAST_STARTED_TIME_] datetime  NULL,
  [LAST_SUSPENDED_TIME_] datetime  NULL,
  [COMPLETED_TIME_] datetime  NULL,
  [OCCURRED_TIME_] datetime  NULL,
  [TERMINATED_TIME_] datetime  NULL,
  [EXIT_TIME_] datetime  NULL,
  [ENDED_TIME_] datetime  NULL,
  [LAST_UPDATED_TIME_] datetime  NULL,
  [START_USER_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REFERENCE_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REFERENCE_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [ENTRY_CRITERION_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXIT_CRITERION_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SHOW_IN_OVERVIEW_] bit  NULL,
  [EXTRA_VALUE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DERIVED_CASE_DEF_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LAST_UNAVAILABLE_TIME_] datetime  NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_HI_PLAN_ITEM_INST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_HI_PLAN_ITEM_INST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_RU_CASE_INST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_RU_CASE_INST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_RU_CASE_INST]
GO

CREATE TABLE [dbo].[ACT_CMMN_RU_CASE_INST] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NOT NULL,
  [BUSINESS_KEY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CASE_DEF_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [STATE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [START_TIME_] datetime  NULL,
  [START_USER_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CALLBACK_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CALLBACK_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [LOCK_TIME_] datetime  NULL,
  [IS_COMPLETEABLE_] bit  NULL,
  [REFERENCE_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REFERENCE_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LOCK_OWNER_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LAST_REACTIVATION_TIME_] datetime  NULL,
  [LAST_REACTIVATION_USER_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [BUSINESS_STATUS_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_RU_CASE_INST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_RU_CASE_INST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_RU_MIL_INST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_RU_MIL_INST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_RU_MIL_INST]
GO

CREATE TABLE [dbo].[ACT_CMMN_RU_MIL_INST] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [TIME_STAMP_] datetime  NOT NULL,
  [CASE_INST_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [CASE_DEF_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [ELEMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_RU_MIL_INST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_RU_MIL_INST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_RU_PLAN_ITEM_INST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_RU_PLAN_ITEM_INST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_RU_PLAN_ITEM_INST]
GO

CREATE TABLE [dbo].[ACT_CMMN_RU_PLAN_ITEM_INST] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NOT NULL,
  [CASE_DEF_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CASE_INST_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [STAGE_INST_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IS_STAGE_] bit  NULL,
  [ELEMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [STATE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime  NULL,
  [START_USER_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REFERENCE_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REFERENCE_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [ITEM_DEFINITION_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ITEM_DEFINITION_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IS_COMPLETEABLE_] bit  NULL,
  [IS_COUNT_ENABLED_] bit  NULL,
  [VAR_COUNT_] int  NULL,
  [SENTRY_PART_INST_COUNT_] int  NULL,
  [LAST_AVAILABLE_TIME_] datetime  NULL,
  [LAST_ENABLED_TIME_] datetime  NULL,
  [LAST_DISABLED_TIME_] datetime  NULL,
  [LAST_STARTED_TIME_] datetime  NULL,
  [LAST_SUSPENDED_TIME_] datetime  NULL,
  [COMPLETED_TIME_] datetime  NULL,
  [OCCURRED_TIME_] datetime  NULL,
  [TERMINATED_TIME_] datetime  NULL,
  [EXIT_TIME_] datetime  NULL,
  [ENDED_TIME_] datetime  NULL,
  [ENTRY_CRITERION_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXIT_CRITERION_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXTRA_VALUE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DERIVED_CASE_DEF_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LAST_UNAVAILABLE_TIME_] datetime  NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_RU_PLAN_ITEM_INST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_RU_PLAN_ITEM_INST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CMMN_RU_SENTRY_PART_INST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CMMN_RU_SENTRY_PART_INST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CMMN_RU_SENTRY_PART_INST]
GO

CREATE TABLE [dbo].[ACT_CMMN_RU_SENTRY_PART_INST] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NOT NULL,
  [CASE_DEF_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CASE_INST_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PLAN_ITEM_INST_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ON_PART_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IF_PART_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TIME_STAMP_] datetime  NULL
)
GO

ALTER TABLE [dbo].[ACT_CMMN_RU_SENTRY_PART_INST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CMMN_RU_SENTRY_PART_INST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CO_CONTENT_ITEM
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CO_CONTENT_ITEM]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CO_CONTENT_ITEM]
GO

CREATE TABLE [dbo].[ACT_CO_CONTENT_ITEM] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [MIME_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTENT_STORE_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTENT_STORE_NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [FIELD_] varchar(400) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTENT_AVAILABLE_] bit DEFAULT 0 NULL,
  [CREATED_] datetime  NULL,
  [CREATED_BY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LAST_MODIFIED_] datetime  NULL,
  [LAST_MODIFIED_BY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTENT_SIZE_] bigint DEFAULT 0 NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_CO_CONTENT_ITEM] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CO_CONTENT_ITEM
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CO_DATABASECHANGELOG
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CO_DATABASECHANGELOG]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CO_DATABASECHANGELOG]
GO

CREATE TABLE [dbo].[ACT_CO_DATABASECHANGELOG] (
  [ID] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [AUTHOR] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [FILENAME] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [DATEEXECUTED] datetime2(3)  NOT NULL,
  [ORDEREXECUTED] int  NOT NULL,
  [EXECTYPE] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [MD5SUM] nvarchar(35) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [COMMENTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TAG] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LIQUIBASE] nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTEXTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LABELS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_CO_DATABASECHANGELOG] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CO_DATABASECHANGELOG
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_CO_DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS], [DEPLOYMENT_ID]) VALUES (N'1', N'activiti', N'org/flowable/content/db/liquibase/flowable-content-db-changelog.xml', N'2024-03-14 15:52:54.050', N'1', N'EXECUTED', N'8:7644d7165cfe799200a2abdd3419e8b6', N'createTable tableName=ACT_CO_CONTENT_ITEM; createIndex indexName=idx_contitem_taskid, tableName=ACT_CO_CONTENT_ITEM; createIndex indexName=idx_contitem_procid, tableName=ACT_CO_CONTENT_ITEM', N'', NULL, N'4.3.5', NULL, NULL, N'1644006759'), (N'2', N'flowable', N'org/flowable/content/db/liquibase/flowable-content-db-changelog.xml', N'2024-03-14 15:52:54.057', N'2', N'EXECUTED', N'8:fe7b11ac7dbbf9c43006b23bbab60bab', N'addColumn tableName=ACT_CO_CONTENT_ITEM; createIndex indexName=idx_contitem_scope, tableName=ACT_CO_CONTENT_ITEM', N'', NULL, N'4.3.5', NULL, NULL, N'1644006759')
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_CO_DATABASECHANGELOGLOCK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_CO_DATABASECHANGELOGLOCK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_CO_DATABASECHANGELOGLOCK]
GO

CREATE TABLE [dbo].[ACT_CO_DATABASECHANGELOGLOCK] (
  [ID] int  NOT NULL,
  [LOCKED] bit  NOT NULL,
  [LOCKGRANTED] datetime2(3)  NULL,
  [LOCKEDBY] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_CO_DATABASECHANGELOGLOCK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_CO_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_CO_DATABASECHANGELOGLOCK] ([ID], [LOCKED], [LOCKGRANTED], [LOCKEDBY]) VALUES (N'1', N'0', NULL, NULL)
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DE_DATABASECHANGELOG
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DE_DATABASECHANGELOG]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DE_DATABASECHANGELOG]
GO

CREATE TABLE [dbo].[ACT_DE_DATABASECHANGELOG] (
  [ID] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [AUTHOR] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [FILENAME] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [DATEEXECUTED] datetime2(3)  NOT NULL,
  [ORDEREXECUTED] int  NOT NULL,
  [EXECTYPE] nvarchar(10) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [MD5SUM] nvarchar(35) COLLATE Chinese_PRC_CI_AS  NULL,
  [DESCRIPTION] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [COMMENTS] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [TAG] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [LIQUIBASE] nvarchar(20) COLLATE Chinese_PRC_CI_AS  NULL,
  [CONTEXTS] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [LABELS] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [DEPLOYMENT_ID] nvarchar(10) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_DE_DATABASECHANGELOG] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DE_DATABASECHANGELOG
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_DE_DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS], [DEPLOYMENT_ID]) VALUES (N'1', N'flowable', N'META-INF/liquibase/flowable-modeler-app-db-changelog.xml', N'2020-07-22 21:25:47.840', N'1', N'EXECUTED', N'8:e70d1d9d3899a734296b2514ccc71501', N'createTable tableName=ACT_DE_MODEL; createIndex indexName=idx_proc_mod_created, tableName=ACT_DE_MODEL; createTable tableName=ACT_DE_MODEL_HISTORY; createIndex indexName=idx_proc_mod_history_proc, tableName=ACT_DE_MODEL_HISTORY; createTable tableN...', N'', NULL, N'3.6.3', NULL, NULL, N'5424402462'), (N'2', N'flowable', N'META-INF/liquibase/flowable-modeler-app-db-changelog.xml', N'2020-07-22 21:25:48.050', N'2', N'EXECUTED', N'8:f08070bcdfbc61d089df76c8f2327d50', N'modifyDataType columnName=created, tableName=ACT_DE_MODEL; modifyDataType columnName=last_updated, tableName=ACT_DE_MODEL; modifyDataType columnName=created, tableName=ACT_DE_MODEL_HISTORY; modifyDataType columnName=last_updated, tableName=ACT_DE_...', N'', NULL, N'3.6.3', NULL, NULL, N'5424402462'), (N'3', N'flowable', N'META-INF/liquibase/flowable-modeler-app-db-changelog.xml', N'2020-07-22 21:25:48.170', N'3', N'EXECUTED', N'8:3a9143bef2e45f2316231cc1369138b6', N'addColumn tableName=ACT_DE_MODEL; addColumn tableName=ACT_DE_MODEL_HISTORY', N'', NULL, N'3.6.3', NULL, NULL, N'5424402462')
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DE_DATABASECHANGELOGLOCK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DE_DATABASECHANGELOGLOCK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DE_DATABASECHANGELOGLOCK]
GO

CREATE TABLE [dbo].[ACT_DE_DATABASECHANGELOGLOCK] (
  [ID] int  NOT NULL,
  [LOCKED] bit  NOT NULL,
  [LOCKGRANTED] datetime2(3)  NULL,
  [LOCKEDBY] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_DE_DATABASECHANGELOGLOCK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DE_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_DE_DATABASECHANGELOGLOCK] ([ID], [LOCKED], [LOCKGRANTED], [LOCKEDBY]) VALUES (N'1', N'0', NULL, NULL)
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DE_MODEL
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DE_MODEL]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DE_MODEL]
GO

CREATE TABLE [dbo].[ACT_DE_MODEL] (
  [id] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [name] varchar(400) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [model_key] varchar(400) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [description] varchar(4000) COLLATE Chinese_PRC_CI_AS  NULL,
  [model_comment] varchar(4000) COLLATE Chinese_PRC_CI_AS  NULL,
  [created] datetime2(7)  NULL,
  [created_by] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [last_updated] datetime2(7)  NULL,
  [last_updated_by] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [version] int  NULL,
  [model_editor_json] varchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [model_editor_xml] varchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [thumbnail] varbinary(max)  NULL,
  [model_type] int  NULL,
  [tenant_id] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_DE_MODEL] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DE_MODEL
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DE_MODEL_HISTORY
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DE_MODEL_HISTORY]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DE_MODEL_HISTORY]
GO

CREATE TABLE [dbo].[ACT_DE_MODEL_HISTORY] (
  [id] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [name] varchar(400) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [model_key] varchar(400) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [description] varchar(4000) COLLATE Chinese_PRC_CI_AS  NULL,
  [model_comment] varchar(4000) COLLATE Chinese_PRC_CI_AS  NULL,
  [created] datetime2(7)  NULL,
  [created_by] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [last_updated] datetime2(7)  NULL,
  [last_updated_by] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [removal_date] datetime2(7)  NULL,
  [version] int  NULL,
  [model_editor_json] varchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [model_id] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [model_type] int  NULL,
  [tenant_id] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_DE_MODEL_HISTORY] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DE_MODEL_HISTORY
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DE_MODEL_RELATION
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DE_MODEL_RELATION]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DE_MODEL_RELATION]
GO

CREATE TABLE [dbo].[ACT_DE_MODEL_RELATION] (
  [id] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [parent_model_id] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [model_id] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [relation_type] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_DE_MODEL_RELATION] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DE_MODEL_RELATION
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DMN_DATABASECHANGELOG
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DMN_DATABASECHANGELOG]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DMN_DATABASECHANGELOG]
GO

CREATE TABLE [dbo].[ACT_DMN_DATABASECHANGELOG] (
  [ID] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [AUTHOR] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [FILENAME] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [DATEEXECUTED] datetime2(3)  NOT NULL,
  [ORDEREXECUTED] int  NOT NULL,
  [EXECTYPE] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [MD5SUM] nvarchar(35) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [COMMENTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TAG] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LIQUIBASE] nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTEXTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LABELS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_DMN_DATABASECHANGELOG] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DMN_DATABASECHANGELOG
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_DMN_DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS], [DEPLOYMENT_ID]) VALUES (N'1', N'activiti', N'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', N'2024-03-14 15:52:53.923', N'1', N'EXECUTED', N'9:5b36e70aee5a2e42f6e7a62ea5fa681b', N'createTable tableName=ACT_DMN_DEPLOYMENT; createTable tableName=ACT_DMN_DEPLOYMENT_RESOURCE; createTable tableName=ACT_DMN_DECISION_TABLE', N'', NULL, N'4.3.5', NULL, NULL, N'1644005973'), (N'2', N'flowable', N'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', N'2024-03-14 15:52:53.930', N'2', N'EXECUTED', N'9:fd13fa3f7af55d2b72f763fc261da30d', N'createTable tableName=ACT_DMN_HI_DECISION_EXECUTION', N'', NULL, N'4.3.5', NULL, NULL, N'1644005973'), (N'3', N'flowable', N'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', N'2024-03-14 15:52:53.930', N'3', N'EXECUTED', N'9:9f30e6a3557d4b4c713dbb2dcc141782', N'addColumn tableName=ACT_DMN_HI_DECISION_EXECUTION', N'', NULL, N'4.3.5', NULL, NULL, N'1644005973'), (N'4', N'flowable', N'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', N'2024-03-14 15:52:53.953', N'4', N'EXECUTED', N'9:41085fbde807dba96104ee75a2fcc4cc', N'dropColumn columnName=PARENT_DEPLOYMENT_ID_, tableName=ACT_DMN_DECISION_TABLE', N'', NULL, N'4.3.5', NULL, NULL, N'1644005973'), (N'6', N'flowable', N'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', N'2024-03-14 15:52:53.957', N'5', N'EXECUTED', N'9:f00f92f3ef1af3fc1604f0323630f9b1', N'createIndex indexName=ACT_IDX_DEC_TBL_UNIQ, tableName=ACT_DMN_DECISION_TABLE', N'', NULL, N'4.3.5', NULL, NULL, N'1644005973'), (N'7', N'flowable', N'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', N'2024-03-14 15:52:53.973', N'6', N'EXECUTED', N'9:d24d4c5f44083b4edf1231a7a682a2cd', N'dropIndex indexName=ACT_IDX_DEC_TBL_UNIQ, tableName=ACT_DMN_DECISION_TABLE; renameTable newTableName=ACT_DMN_DECISION, oldTableName=ACT_DMN_DECISION_TABLE; createIndex indexName=ACT_IDX_DMN_DEC_UNIQ, tableName=ACT_DMN_DECISION', N'', NULL, N'4.3.5', NULL, NULL, N'1644005973'), (N'8', N'flowable', N'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', N'2024-03-14 15:52:53.977', N'7', N'EXECUTED', N'9:3998ef0958b46fe9c19458183952d2a0', N'addColumn tableName=ACT_DMN_DECISION', N'', NULL, N'4.3.5', NULL, NULL, N'1644005973'), (N'9', N'flowable', N'org/flowable/dmn/db/liquibase/flowable-dmn-db-changelog.xml', N'2024-03-14 15:52:53.980', N'8', N'EXECUTED', N'9:5c9dc65601456faa1aa12f8d3afe0e9e', N'createIndex indexName=ACT_IDX_DMN_INSTANCE_ID, tableName=ACT_DMN_HI_DECISION_EXECUTION', N'', NULL, N'4.3.5', NULL, NULL, N'1644005973')
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DMN_DATABASECHANGELOGLOCK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DMN_DATABASECHANGELOGLOCK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DMN_DATABASECHANGELOGLOCK]
GO

CREATE TABLE [dbo].[ACT_DMN_DATABASECHANGELOGLOCK] (
  [ID] int  NOT NULL,
  [LOCKED] bit  NOT NULL,
  [LOCKGRANTED] datetime2(3)  NULL,
  [LOCKEDBY] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_DMN_DATABASECHANGELOGLOCK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DMN_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_DMN_DATABASECHANGELOGLOCK] ([ID], [LOCKED], [LOCKGRANTED], [LOCKEDBY]) VALUES (N'1', N'0', NULL, NULL)
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DMN_DECISION
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DMN_DECISION]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DMN_DECISION]
GO

CREATE TABLE [dbo].[ACT_DMN_DECISION] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [VERSION_] int  NULL,
  [KEY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DECISION_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_DMN_DECISION] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DMN_DECISION
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DMN_DEPLOYMENT
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DMN_DEPLOYMENT]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DMN_DEPLOYMENT]
GO

CREATE TABLE [dbo].[ACT_DMN_DEPLOYMENT] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOY_TIME_] datetime  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_DMN_DEPLOYMENT] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DMN_DEPLOYMENT
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DMN_DEPLOYMENT_RESOURCE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DMN_DEPLOYMENT_RESOURCE]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DMN_DEPLOYMENT_RESOURCE]
GO

CREATE TABLE [dbo].[ACT_DMN_DEPLOYMENT_RESOURCE] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_BYTES_] varbinary(max)  NULL
)
GO

ALTER TABLE [dbo].[ACT_DMN_DEPLOYMENT_RESOURCE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DMN_DEPLOYMENT_RESOURCE
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_DMN_HI_DECISION_EXECUTION
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_DMN_HI_DECISION_EXECUTION]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_DMN_HI_DECISION_EXECUTION]
GO

CREATE TABLE [dbo].[ACT_DMN_HI_DECISION_EXECUTION] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [DECISION_DEFINITION_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [START_TIME_] datetime  NULL,
  [END_TIME_] datetime  NULL,
  [INSTANCE_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXECUTION_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ACTIVITY_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [FAILED_] bit DEFAULT 0 NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXECUTION_JSON_] varchar(max) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_DMN_HI_DECISION_EXECUTION] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_DMN_HI_DECISION_EXECUTION
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_EVT_LOG
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_EVT_LOG]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_EVT_LOG]
GO

CREATE TABLE [dbo].[ACT_EVT_LOG] (
  [LOG_NR_] numeric(19)  IDENTITY(1,1) NOT NULL,
  [TYPE_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TIME_STAMP_] datetime  NOT NULL,
  [USER_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DATA_] varbinary(max)  NULL,
  [LOCK_OWNER_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LOCK_TIME_] datetime  NULL,
  [IS_PROCESSED_] tinyint DEFAULT 0 NULL
)
GO

ALTER TABLE [dbo].[ACT_EVT_LOG] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_EVT_LOG
-- ----------------------------
BEGIN TRANSACTION
GO

SET IDENTITY_INSERT [dbo].[ACT_EVT_LOG] ON
GO

SET IDENTITY_INSERT [dbo].[ACT_EVT_LOG] OFF
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_FO_DATABASECHANGELOG
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_FO_DATABASECHANGELOG]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_FO_DATABASECHANGELOG]
GO

CREATE TABLE [dbo].[ACT_FO_DATABASECHANGELOG] (
  [ID] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [AUTHOR] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [FILENAME] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [DATEEXECUTED] datetime2(3)  NOT NULL,
  [ORDEREXECUTED] int  NOT NULL,
  [EXECTYPE] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [MD5SUM] nvarchar(35) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [COMMENTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TAG] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LIQUIBASE] nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTEXTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LABELS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_FO_DATABASECHANGELOG] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_FO_DATABASECHANGELOG
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_FO_DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS], [DEPLOYMENT_ID]) VALUES (N'1', N'activiti', N'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', N'2024-03-14 15:52:54.000', N'1', N'EXECUTED', N'8:033ebf9380889aed7c453927ecc3250d', N'createTable tableName=ACT_FO_FORM_DEPLOYMENT; createTable tableName=ACT_FO_FORM_RESOURCE; createTable tableName=ACT_FO_FORM_DEFINITION; createTable tableName=ACT_FO_FORM_INSTANCE', N'', NULL, N'4.3.5', NULL, NULL, N'1644006420'), (N'2', N'flowable', N'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', N'2024-03-14 15:52:54.007', N'2', N'EXECUTED', N'8:986365ceb40445ce3b27a8e6b40f159b', N'addColumn tableName=ACT_FO_FORM_INSTANCE', N'', NULL, N'4.3.5', NULL, NULL, N'1644006420'), (N'3', N'flowable', N'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', N'2024-03-14 15:52:54.020', N'3', N'EXECUTED', N'8:abf482518ceb09830ef674e52c06bf15', N'dropColumn columnName=PARENT_DEPLOYMENT_ID_, tableName=ACT_FO_FORM_DEFINITION', N'', NULL, N'4.3.5', NULL, NULL, N'1644006420'), (N'5', N'flowable', N'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', N'2024-03-14 15:52:54.023', N'4', N'EXECUTED', N'8:b4be732b89e5ca028bdd520c6ad4d446', N'createIndex indexName=ACT_IDX_FORM_DEF_UNIQ, tableName=ACT_FO_FORM_DEFINITION', N'', NULL, N'4.3.5', NULL, NULL, N'1644006420'), (N'6', N'flowable', N'org/flowable/form/db/liquibase/flowable-form-db-changelog.xml', N'2024-03-14 15:52:54.030', N'5', N'EXECUTED', N'8:384bbd364a649b67c3ca1bcb72fe537f', N'createIndex indexName=ACT_IDX_FORM_TASK, tableName=ACT_FO_FORM_INSTANCE; createIndex indexName=ACT_IDX_FORM_PROC, tableName=ACT_FO_FORM_INSTANCE; createIndex indexName=ACT_IDX_FORM_SCOPE, tableName=ACT_FO_FORM_INSTANCE', N'', NULL, N'4.3.5', NULL, NULL, N'1644006420')
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_FO_DATABASECHANGELOGLOCK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_FO_DATABASECHANGELOGLOCK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_FO_DATABASECHANGELOGLOCK]
GO

CREATE TABLE [dbo].[ACT_FO_DATABASECHANGELOGLOCK] (
  [ID] int  NOT NULL,
  [LOCKED] bit  NOT NULL,
  [LOCKGRANTED] datetime2(3)  NULL,
  [LOCKEDBY] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_FO_DATABASECHANGELOGLOCK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_FO_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_FO_DATABASECHANGELOGLOCK] ([ID], [LOCKED], [LOCKGRANTED], [LOCKEDBY]) VALUES (N'1', N'0', NULL, NULL)
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_FO_FORM_DEFINITION
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_FO_FORM_DEFINITION]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_FO_FORM_DEFINITION]
GO

CREATE TABLE [dbo].[ACT_FO_FORM_DEFINITION] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [VERSION_] int  NULL,
  [KEY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_FO_FORM_DEFINITION] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_FO_FORM_DEFINITION
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_FO_FORM_DEPLOYMENT
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_FO_FORM_DEPLOYMENT]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_FO_FORM_DEPLOYMENT]
GO

CREATE TABLE [dbo].[ACT_FO_FORM_DEPLOYMENT] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOY_TIME_] datetime  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_FO_FORM_DEPLOYMENT] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_FO_FORM_DEPLOYMENT
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_FO_FORM_INSTANCE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_FO_FORM_INSTANCE]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_FO_FORM_INSTANCE]
GO

CREATE TABLE [dbo].[ACT_FO_FORM_INSTANCE] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [FORM_DEFINITION_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [TASK_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUBMITTED_DATE_] datetime  NULL,
  [SUBMITTED_BY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [FORM_VALUES_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_FO_FORM_INSTANCE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_FO_FORM_INSTANCE
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_FO_FORM_RESOURCE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_FO_FORM_RESOURCE]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_FO_FORM_RESOURCE]
GO

CREATE TABLE [dbo].[ACT_FO_FORM_RESOURCE] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_BYTES_] varbinary(max)  NULL
)
GO

ALTER TABLE [dbo].[ACT_FO_FORM_RESOURCE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_FO_FORM_RESOURCE
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_GE_BYTEARRAY
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_GE_BYTEARRAY]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_GE_BYTEARRAY]
GO

CREATE TABLE [dbo].[ACT_GE_BYTEARRAY] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [BYTES_] varbinary(max)  NULL,
  [GENERATED_] tinyint  NULL
)
GO

ALTER TABLE [dbo].[ACT_GE_BYTEARRAY] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_GE_BYTEARRAY
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_GE_PROPERTY
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_GE_PROPERTY]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_GE_PROPERTY]
GO

CREATE TABLE [dbo].[ACT_GE_PROPERTY] (
  [NAME_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [VALUE_] nvarchar(300) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REV_] int  NULL
)
GO

ALTER TABLE [dbo].[ACT_GE_PROPERTY] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_GE_PROPERTY
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_GE_PROPERTY] ([NAME_], [VALUE_], [REV_]) VALUES (N'batch.schema.version', N'7.0.1.1', N'1'), (N'cfg.execution-related-entities-count', N'true', N'1'), (N'cfg.task-related-entities-count', N'true', N'1'), (N'common.schema.version', N'7.0.1.1', N'1'), (N'entitylink.schema.version', N'7.0.1.1', N'1'), (N'eventsubscription.schema.version', N'7.0.1.1', N'1'), (N'identitylink.schema.version', N'7.0.1.1', N'1'), (N'job.schema.version', N'7.0.1.1', N'1'), (N'next.dbid', N'1', N'1'), (N'schema.history', N'upgrade(7.0.0.0->7.0.1.1)', N'2'), (N'schema.version', N'7.0.1.1', N'2'), (N'task.schema.version', N'7.0.1.1', N'1'), (N'variable.schema.version', N'7.0.1.1', N'1')
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_HI_ACTINST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_HI_ACTINST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_HI_ACTINST]
GO

CREATE TABLE [dbo].[ACT_HI_ACTINST] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int DEFAULT 1 NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [ACT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CALL_PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ACT_NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ACT_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [ASSIGNEE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [START_TIME_] datetime  NOT NULL,
  [END_TIME_] datetime  NULL,
  [TRANSACTION_ORDER_] int  NULL,
  [DURATION_] numeric(19)  NULL,
  [DELETE_REASON_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_HI_ACTINST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_HI_ACTINST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_HI_ATTACHMENT
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_HI_ATTACHMENT]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_HI_ATTACHMENT]
GO

CREATE TABLE [dbo].[ACT_HI_ATTACHMENT] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [USER_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [URL_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTENT_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TIME_] datetime  NULL
)
GO

ALTER TABLE [dbo].[ACT_HI_ATTACHMENT] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_HI_ATTACHMENT
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_HI_COMMENT
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_HI_COMMENT]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_HI_COMMENT]
GO

CREATE TABLE [dbo].[ACT_HI_COMMENT] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TIME_] datetime  NOT NULL,
  [USER_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ACTION_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [MESSAGE_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [FULL_MSG_] varbinary(max)  NULL
)
GO

ALTER TABLE [dbo].[ACT_HI_COMMENT] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_HI_COMMENT
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_HI_DETAIL
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_HI_DETAIL]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_HI_DETAIL]
GO

CREATE TABLE [dbo].[ACT_HI_DETAIL] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ACT_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [VAR_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REV_] int  NULL,
  [TIME_] datetime  NOT NULL,
  [BYTEARRAY_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DOUBLE_] float(53)  NULL,
  [LONG_] numeric(19)  NULL,
  [TEXT_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TEXT2_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_HI_DETAIL] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_HI_DETAIL
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_HI_ENTITYLINK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_HI_ENTITYLINK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_HI_ENTITYLINK]
GO

CREATE TABLE [dbo].[ACT_HI_ENTITYLINK] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [LINK_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_ELEMENT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REF_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REF_SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REF_SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ROOT_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ROOT_SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HIERARCHY_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_HI_ENTITYLINK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_HI_ENTITYLINK
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_HI_IDENTITYLINK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_HI_IDENTITYLINK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_HI_IDENTITYLINK]
GO

CREATE TABLE [dbo].[ACT_HI_IDENTITYLINK] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [GROUP_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [USER_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_HI_IDENTITYLINK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_HI_IDENTITYLINK
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_HI_PROCINST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_HI_PROCINST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_HI_PROCINST]
GO

CREATE TABLE [dbo].[ACT_HI_PROCINST] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int DEFAULT 1 NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [BUSINESS_KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [START_TIME_] datetime  NOT NULL,
  [END_TIME_] datetime  NULL,
  [DURATION_] numeric(19)  NULL,
  [START_USER_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [START_ACT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [END_ACT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUPER_PROCESS_INSTANCE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DELETE_REASON_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CALLBACK_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CALLBACK_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REFERENCE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REFERENCE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROPAGATED_STAGE_INST_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [BUSINESS_STATUS_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_HI_PROCINST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_HI_PROCINST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_HI_TASKINST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_HI_TASKINST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_HI_TASKINST]
GO

CREATE TABLE [dbo].[ACT_HI_TASKINST] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int DEFAULT 1 NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_DEF_KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROPAGATED_STAGE_INST_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [OWNER_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ASSIGNEE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [START_TIME_] datetime  NOT NULL,
  [CLAIM_TIME_] datetime  NULL,
  [END_TIME_] datetime  NULL,
  [DURATION_] numeric(19)  NULL,
  [DELETE_REASON_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PRIORITY_] int  NULL,
  [DUE_DATE_] datetime  NULL,
  [FORM_KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [LAST_UPDATED_TIME_] datetime2(7)  NULL,
  [STATE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IN_PROGRESS_TIME_] datetime  NULL,
  [IN_PROGRESS_STARTED_BY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CLAIMED_BY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUSPENDED_TIME_] datetime  NULL,
  [SUSPENDED_BY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [COMPLETED_BY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IN_PROGRESS_DUE_DATE_] datetime  NULL
)
GO

ALTER TABLE [dbo].[ACT_HI_TASKINST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_HI_TASKINST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_HI_TSK_LOG
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_HI_TSK_LOG]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_HI_TSK_LOG]
GO

CREATE TABLE [dbo].[ACT_HI_TSK_LOG] (
  [ID_] numeric(19)  IDENTITY(1,1) NOT NULL,
  [TYPE_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [TIME_STAMP_] datetime  NOT NULL,
  [USER_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DATA_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_HI_TSK_LOG] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_HI_TSK_LOG
-- ----------------------------
BEGIN TRANSACTION
GO

SET IDENTITY_INSERT [dbo].[ACT_HI_TSK_LOG] ON
GO

SET IDENTITY_INSERT [dbo].[ACT_HI_TSK_LOG] OFF
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_HI_VARINST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_HI_VARINST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_HI_VARINST]
GO

CREATE TABLE [dbo].[ACT_HI_VARINST] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int DEFAULT 1 NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [VAR_TYPE_] nvarchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [BYTEARRAY_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DOUBLE_] float(53)  NULL,
  [LONG_] numeric(19)  NULL,
  [TEXT_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TEXT2_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime  NULL,
  [LAST_UPDATED_TIME_] datetime2(7)  NULL,
  [META_INFO_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_HI_VARINST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_HI_VARINST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_ID_BYTEARRAY
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_ID_BYTEARRAY]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_ID_BYTEARRAY]
GO

CREATE TABLE [dbo].[ACT_ID_BYTEARRAY] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [BYTES_] varbinary(max)  NULL
)
GO

ALTER TABLE [dbo].[ACT_ID_BYTEARRAY] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_ID_BYTEARRAY
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_ID_GROUP
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_ID_GROUP]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_ID_GROUP]
GO

CREATE TABLE [dbo].[ACT_ID_GROUP] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_ID_GROUP] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_ID_GROUP
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_ID_INFO
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_ID_INFO]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_ID_INFO]
GO

CREATE TABLE [dbo].[ACT_ID_INFO] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [USER_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [VALUE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PASSWORD_] varbinary(max)  NULL,
  [PARENT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_ID_INFO] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_ID_INFO
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_ID_MEMBERSHIP
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_ID_MEMBERSHIP]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_ID_MEMBERSHIP]
GO

CREATE TABLE [dbo].[ACT_ID_MEMBERSHIP] (
  [USER_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [GROUP_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[ACT_ID_MEMBERSHIP] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_ID_MEMBERSHIP
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_ID_PRIV
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_ID_PRIV]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_ID_PRIV]
GO

CREATE TABLE [dbo].[ACT_ID_PRIV] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[ACT_ID_PRIV] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_ID_PRIV
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_ID_PRIV_MAPPING
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_ID_PRIV_MAPPING]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_ID_PRIV_MAPPING]
GO

CREATE TABLE [dbo].[ACT_ID_PRIV_MAPPING] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [PRIV_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [USER_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [GROUP_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_ID_PRIV_MAPPING] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_ID_PRIV_MAPPING
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_ID_PROPERTY
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_ID_PROPERTY]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_ID_PROPERTY]
GO

CREATE TABLE [dbo].[ACT_ID_PROPERTY] (
  [NAME_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [VALUE_] nvarchar(300) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REV_] int  NULL
)
GO

ALTER TABLE [dbo].[ACT_ID_PROPERTY] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_ID_PROPERTY
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[ACT_ID_PROPERTY] ([NAME_], [VALUE_], [REV_]) VALUES (N'schema.version', N'7.0.1.1', N'1')
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_ID_TOKEN
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_ID_TOKEN]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_ID_TOKEN]
GO

CREATE TABLE [dbo].[ACT_ID_TOKEN] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [TOKEN_VALUE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TOKEN_DATE_] datetime  NULL,
  [IP_ADDRESS_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [USER_AGENT_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [USER_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TOKEN_DATA_] nvarchar(2000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_ID_TOKEN] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_ID_TOKEN
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_ID_USER
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_ID_USER]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_ID_USER]
GO

CREATE TABLE [dbo].[ACT_ID_USER] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [FIRST_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LAST_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DISPLAY_NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EMAIL_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PWD_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PICTURE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_ID_USER] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_ID_USER
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_PROCDEF_INFO
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_PROCDEF_INFO]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_PROCDEF_INFO]
GO

CREATE TABLE [dbo].[ACT_PROCDEF_INFO] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [INFO_JSON_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_PROCDEF_INFO] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_PROCDEF_INFO
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RE_DEPLOYMENT
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RE_DEPLOYMENT]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RE_DEPLOYMENT]
GO

CREATE TABLE [dbo].[ACT_RE_DEPLOYMENT] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [DEPLOY_TIME_] datetime  NULL,
  [DERIVED_FROM_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DERIVED_FROM_ROOT_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_DEPLOYMENT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ENGINE_VERSION_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_RE_DEPLOYMENT] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RE_DEPLOYMENT
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RE_MODEL
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RE_MODEL]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RE_MODEL]
GO

CREATE TABLE [dbo].[ACT_RE_MODEL] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime  NULL,
  [LAST_UPDATE_TIME_] datetime  NULL,
  [VERSION_] int  NULL,
  [META_INFO_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EDITOR_SOURCE_VALUE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EDITOR_SOURCE_EXTRA_VALUE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_RE_MODEL] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RE_MODEL
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RE_PROCDEF
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RE_PROCDEF]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RE_PROCDEF]
GO

CREATE TABLE [dbo].[ACT_RE_PROCDEF] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [CATEGORY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [VERSION_] int  NOT NULL,
  [DEPLOYMENT_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_NAME_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DGRM_RESOURCE_NAME_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HAS_START_FORM_KEY_] tinyint  NULL,
  [HAS_GRAPHICAL_NOTATION_] tinyint  NULL,
  [SUSPENSION_STATE_] tinyint  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [DERIVED_FROM_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DERIVED_FROM_ROOT_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DERIVED_VERSION_] int DEFAULT 0 NOT NULL,
  [ENGINE_VERSION_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_RE_PROCDEF] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RE_PROCDEF
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_ACTINST
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_ACTINST]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_ACTINST]
GO

CREATE TABLE [dbo].[ACT_RU_ACTINST] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int DEFAULT 1 NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [ACT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CALL_PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ACT_NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ACT_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [ASSIGNEE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [START_TIME_] datetime  NOT NULL,
  [END_TIME_] datetime  NULL,
  [DURATION_] numeric(19)  NULL,
  [TRANSACTION_ORDER_] int  NULL,
  [DELETE_REASON_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_ACTINST] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_ACTINST
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_DEADLETTER_JOB
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_DEADLETTER_JOB]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_DEADLETTER_JOB]
GO

CREATE TABLE [dbo].[ACT_RU_DEADLETTER_JOB] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [EXCLUSIVE_] bit  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROCESS_INSTANCE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ELEMENT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ELEMENT_NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CORRELATION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXCEPTION_STACK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXCEPTION_MSG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DUEDATE_] datetime  NULL,
  [REPEAT_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_CFG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CUSTOM_VALUES_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime2(7)  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_DEADLETTER_JOB] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_DEADLETTER_JOB
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_ENTITYLINK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_ENTITYLINK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_ENTITYLINK]
GO

CREATE TABLE [dbo].[ACT_RU_ENTITYLINK] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [CREATE_TIME_] datetime  NULL,
  [LINK_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_ELEMENT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REF_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REF_SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REF_SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ROOT_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ROOT_SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HIERARCHY_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_ENTITYLINK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_ENTITYLINK
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_EVENT_SUBSCR
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_EVENT_SUBSCR]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_EVENT_SUBSCR]
GO

CREATE TABLE [dbo].[ACT_RU_EVENT_SUBSCR] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [EVENT_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [EVENT_NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ACTIVITY_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONFIGURATION_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATED_] datetime  NOT NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LOCK_TIME_] datetime  NULL,
  [LOCK_OWNER_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [SCOPE_DEFINITION_KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_EVENT_SUBSCR] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_EVENT_SUBSCR
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_EXECUTION
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_EXECUTION]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_EXECUTION]
GO

CREATE TABLE [dbo].[ACT_RU_EXECUTION] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [BUSINESS_KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUPER_EXEC_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ROOT_PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ACT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IS_ACTIVE_] tinyint  NULL,
  [IS_CONCURRENT_] tinyint  NULL,
  [IS_SCOPE_] tinyint  NULL,
  [IS_EVENT_SCOPE_] tinyint  NULL,
  [IS_MI_ROOT_] tinyint  NULL,
  [SUSPENSION_STATE_] tinyint  NULL,
  [CACHED_ENT_STATE_] int  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [START_ACT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [START_TIME_] datetime  NULL,
  [START_USER_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LOCK_TIME_] datetime  NULL,
  [LOCK_OWNER_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IS_COUNT_ENABLED_] tinyint  NULL,
  [EVT_SUBSCR_COUNT_] int  NULL,
  [TASK_COUNT_] int  NULL,
  [JOB_COUNT_] int  NULL,
  [TIMER_JOB_COUNT_] int  NULL,
  [SUSP_JOB_COUNT_] int  NULL,
  [DEADLETTER_JOB_COUNT_] int  NULL,
  [EXTERNAL_WORKER_JOB_COUNT_] int  NULL,
  [VAR_COUNT_] int  NULL,
  [ID_LINK_COUNT_] int  NULL,
  [CALLBACK_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CALLBACK_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REFERENCE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [REFERENCE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROPAGATED_STAGE_INST_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [BUSINESS_STATUS_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_EXECUTION] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_EXECUTION
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_EXTERNAL_JOB
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_EXTERNAL_JOB]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_EXTERNAL_JOB]
GO

CREATE TABLE [dbo].[ACT_RU_EXTERNAL_JOB] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [LOCK_EXP_TIME_] datetime  NULL,
  [LOCK_OWNER_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXCLUSIVE_] bit  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROCESS_INSTANCE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ELEMENT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ELEMENT_NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CORRELATION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RETRIES_] int  NULL,
  [EXCEPTION_STACK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXCEPTION_MSG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DUEDATE_] datetime  NULL,
  [REPEAT_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_CFG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CUSTOM_VALUES_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime2(7)  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_EXTERNAL_JOB] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_EXTERNAL_JOB
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_HISTORY_JOB
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_HISTORY_JOB]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_HISTORY_JOB]
GO

CREATE TABLE [dbo].[ACT_RU_HISTORY_JOB] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [LOCK_EXP_TIME_] datetime  NULL,
  [LOCK_OWNER_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RETRIES_] int  NULL,
  [EXCEPTION_STACK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXCEPTION_MSG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_CFG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CUSTOM_VALUES_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ADV_HANDLER_CFG_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime2(7)  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_HISTORY_JOB] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_HISTORY_JOB
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_IDENTITYLINK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_IDENTITYLINK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_IDENTITYLINK]
GO

CREATE TABLE [dbo].[ACT_RU_IDENTITYLINK] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [GROUP_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [USER_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_IDENTITYLINK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_IDENTITYLINK
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_JOB
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_JOB]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_JOB]
GO

CREATE TABLE [dbo].[ACT_RU_JOB] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [LOCK_EXP_TIME_] datetime  NULL,
  [LOCK_OWNER_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXCLUSIVE_] bit  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROCESS_INSTANCE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ELEMENT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ELEMENT_NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CORRELATION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RETRIES_] int  NULL,
  [EXCEPTION_STACK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXCEPTION_MSG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DUEDATE_] datetime  NULL,
  [REPEAT_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_CFG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CUSTOM_VALUES_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime2(7)  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_JOB] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_JOB
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_SUSPENDED_JOB
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_SUSPENDED_JOB]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_SUSPENDED_JOB]
GO

CREATE TABLE [dbo].[ACT_RU_SUSPENDED_JOB] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [EXCLUSIVE_] bit  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROCESS_INSTANCE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ELEMENT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ELEMENT_NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CORRELATION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RETRIES_] int  NULL,
  [EXCEPTION_STACK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXCEPTION_MSG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DUEDATE_] datetime  NULL,
  [REPEAT_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_CFG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CUSTOM_VALUES_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime2(7)  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_SUSPENDED_JOB] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_SUSPENDED_JOB
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_TASK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_TASK]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_TASK]
GO

CREATE TABLE [dbo].[ACT_RU_TASK] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROPAGATED_STAGE_INST_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_DEF_KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [OWNER_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ASSIGNEE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DELEGATION_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PRIORITY_] int  NULL,
  [CREATE_TIME_] datetime  NULL,
  [DUE_DATE_] datetime  NULL,
  [CATEGORY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUSPENSION_STATE_] int  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL,
  [FORM_KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CLAIM_TIME_] datetime  NULL,
  [IS_COUNT_ENABLED_] tinyint  NULL,
  [VAR_COUNT_] int  NULL,
  [ID_LINK_COUNT_] int  NULL,
  [SUB_TASK_COUNT_] int  NULL,
  [STATE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IN_PROGRESS_TIME_] datetime  NULL,
  [IN_PROGRESS_STARTED_BY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CLAIMED_BY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUSPENDED_TIME_] datetime  NULL,
  [SUSPENDED_BY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IN_PROGRESS_DUE_DATE_] datetime  NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_TASK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_TASK
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_TIMER_JOB
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_TIMER_JOB]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_TIMER_JOB]
GO

CREATE TABLE [dbo].[ACT_RU_TIMER_JOB] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [LOCK_EXP_TIME_] datetime  NULL,
  [LOCK_OWNER_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXCLUSIVE_] bit  NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROCESS_INSTANCE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_DEF_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ELEMENT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [ELEMENT_NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_DEFINITION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CORRELATION_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RETRIES_] int  NULL,
  [EXCEPTION_STACK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [EXCEPTION_MSG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DUEDATE_] datetime  NULL,
  [REPEAT_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [HANDLER_CFG_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CUSTOM_VALUES_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime2(7)  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_TIMER_JOB] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_TIMER_JOB
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for ACT_RU_VARIABLE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[ACT_RU_VARIABLE]') AND type IN ('U'))
	DROP TABLE [dbo].[ACT_RU_VARIABLE]
GO

CREATE TABLE [dbo].[ACT_RU_VARIABLE] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [EXECUTION_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PROC_INST_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TASK_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [BYTEARRAY_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DOUBLE_] float(53)  NULL,
  [LONG_] numeric(19)  NULL,
  [TEXT_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TEXT2_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [META_INFO_] nvarchar(4000) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[ACT_RU_VARIABLE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of ACT_RU_VARIABLE
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for FLW_CHANNEL_DEFINITION
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[FLW_CHANNEL_DEFINITION]') AND type IN ('U'))
	DROP TABLE [dbo].[FLW_CHANNEL_DEFINITION]
GO

CREATE TABLE [dbo].[FLW_CHANNEL_DEFINITION] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [VERSION_] int  NULL,
  [KEY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [IMPLEMENTATION_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[FLW_CHANNEL_DEFINITION] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of FLW_CHANNEL_DEFINITION
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for FLW_EVENT_DEFINITION
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[FLW_EVENT_DEFINITION]') AND type IN ('U'))
	DROP TABLE [dbo].[FLW_EVENT_DEFINITION]
GO

CREATE TABLE [dbo].[FLW_EVENT_DEFINITION] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [VERSION_] int  NULL,
  [KEY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[FLW_EVENT_DEFINITION] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of FLW_EVENT_DEFINITION
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for FLW_EVENT_DEPLOYMENT
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[FLW_EVENT_DEPLOYMENT]') AND type IN ('U'))
	DROP TABLE [dbo].[FLW_EVENT_DEPLOYMENT]
GO

CREATE TABLE [dbo].[FLW_EVENT_DEPLOYMENT] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CATEGORY_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOY_TIME_] datetime  NULL,
  [TENANT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [PARENT_DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[FLW_EVENT_DEPLOYMENT] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of FLW_EVENT_DEPLOYMENT
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for FLW_EVENT_RESOURCE
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[FLW_EVENT_RESOURCE]') AND type IN ('U'))
	DROP TABLE [dbo].[FLW_EVENT_RESOURCE]
GO

CREATE TABLE [dbo].[FLW_EVENT_RESOURCE] (
  [ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [NAME_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID_] varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESOURCE_BYTES_] varbinary(max)  NULL
)
GO

ALTER TABLE [dbo].[FLW_EVENT_RESOURCE] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of FLW_EVENT_RESOURCE
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for FLW_EV_DATABASECHANGELOG
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[FLW_EV_DATABASECHANGELOG]') AND type IN ('U'))
	DROP TABLE [dbo].[FLW_EV_DATABASECHANGELOG]
GO

CREATE TABLE [dbo].[FLW_EV_DATABASECHANGELOG] (
  [ID] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [AUTHOR] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [FILENAME] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [DATEEXECUTED] datetime2(3)  NOT NULL,
  [ORDEREXECUTED] int  NOT NULL,
  [EXECTYPE] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [MD5SUM] nvarchar(35) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DESCRIPTION] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [COMMENTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TAG] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LIQUIBASE] nvarchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CONTEXTS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [LABELS] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [DEPLOYMENT_ID] nvarchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[FLW_EV_DATABASECHANGELOG] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of FLW_EV_DATABASECHANGELOG
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[FLW_EV_DATABASECHANGELOG] ([ID], [AUTHOR], [FILENAME], [DATEEXECUTED], [ORDEREXECUTED], [EXECTYPE], [MD5SUM], [DESCRIPTION], [COMMENTS], [TAG], [LIQUIBASE], [CONTEXTS], [LABELS], [DEPLOYMENT_ID]) VALUES (N'1', N'flowable', N'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', N'2024-03-14 15:52:53.897', N'1', N'EXECUTED', N'9:63268f536c469325acef35970312551b', N'createTable tableName=FLW_EVENT_DEPLOYMENT; createTable tableName=FLW_EVENT_RESOURCE; createTable tableName=FLW_EVENT_DEFINITION; createIndex indexName=ACT_IDX_EVENT_DEF_UNIQ, tableName=FLW_EVENT_DEFINITION; createTable tableName=FLW_CHANNEL_DEFIN...', N'', NULL, N'4.3.5', NULL, NULL, N'1644005653'), (N'2', N'flowable', N'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', N'2024-03-14 15:52:53.900', N'2', N'EXECUTED', N'9:dcb58b7dfd6dbda66939123a96985536', N'addColumn tableName=FLW_CHANNEL_DEFINITION; addColumn tableName=FLW_CHANNEL_DEFINITION', N'', NULL, N'4.3.5', NULL, NULL, N'1644005653'), (N'3', N'flowable', N'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', N'2024-03-14 15:52:53.903', N'3', N'EXECUTED', N'9:d0c05678d57af23ad93699991e3bf4f6', N'customChange', N'', NULL, N'4.3.5', NULL, NULL, N'1644005653')
GO

COMMIT
GO


-- ----------------------------
-- Table structure for FLW_EV_DATABASECHANGELOGLOCK
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[FLW_EV_DATABASECHANGELOGLOCK]') AND type IN ('U'))
	DROP TABLE [dbo].[FLW_EV_DATABASECHANGELOGLOCK]
GO

CREATE TABLE [dbo].[FLW_EV_DATABASECHANGELOGLOCK] (
  [ID] int  NOT NULL,
  [LOCKED] bit  NOT NULL,
  [LOCKGRANTED] datetime2(3)  NULL,
  [LOCKEDBY] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[FLW_EV_DATABASECHANGELOGLOCK] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of FLW_EV_DATABASECHANGELOGLOCK
-- ----------------------------
BEGIN TRANSACTION
GO

INSERT INTO [dbo].[FLW_EV_DATABASECHANGELOGLOCK] ([ID], [LOCKED], [LOCKGRANTED], [LOCKEDBY]) VALUES (N'1', N'0', NULL, NULL)
GO

COMMIT
GO


-- ----------------------------
-- Table structure for FLW_RU_BATCH
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[FLW_RU_BATCH]') AND type IN ('U'))
	DROP TABLE [dbo].[FLW_RU_BATCH]
GO

CREATE TABLE [dbo].[FLW_RU_BATCH] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [TYPE_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [SEARCH_KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SEARCH_KEY2_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime  NOT NULL,
  [COMPLETE_TIME_] datetime  NULL,
  [STATUS_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [BATCH_DOC_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[FLW_RU_BATCH] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of FLW_RU_BATCH
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Table structure for FLW_RU_BATCH_PART
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[FLW_RU_BATCH_PART]') AND type IN ('U'))
	DROP TABLE [dbo].[FLW_RU_BATCH_PART]
GO

CREATE TABLE [dbo].[FLW_RU_BATCH_PART] (
  [ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [REV_] int  NULL,
  [BATCH_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TYPE_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NOT NULL,
  [SCOPE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SUB_SCOPE_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SCOPE_TYPE_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SEARCH_KEY_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [SEARCH_KEY2_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [CREATE_TIME_] datetime  NOT NULL,
  [COMPLETE_TIME_] datetime  NULL,
  [STATUS_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [RESULT_DOC_ID_] nvarchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS  NULL,
  [TENANT_ID_] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS DEFAULT '' NULL
)
GO

ALTER TABLE [dbo].[FLW_RU_BATCH_PART] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of FLW_RU_BATCH_PART
-- ----------------------------
BEGIN TRANSACTION
GO

COMMIT
GO


-- ----------------------------
-- Indexes structure for table ACT_APP_APPDEF
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_APP_DEF_DPLY]
ON [dbo].[ACT_APP_APPDEF] (
  [DEPLOYMENT_ID_] ASC
)
GO

CREATE UNIQUE NONCLUSTERED INDEX [ACT_IDX_APP_DEF_UNIQ]
ON [dbo].[ACT_APP_APPDEF] (
  [KEY_] ASC,
  [VERSION_] ASC,
  [TENANT_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_APP_APPDEF
-- ----------------------------
ALTER TABLE [dbo].[ACT_APP_APPDEF] ADD CONSTRAINT [PK_ACT_APP_APPDEF] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_APP_DATABASECHANGELOGLOCK
-- ----------------------------
ALTER TABLE [dbo].[ACT_APP_DATABASECHANGELOGLOCK] ADD CONSTRAINT [PK_ACT_APP_DATABASECHANGELOGLOCK] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_APP_DEPLOYMENT
-- ----------------------------
ALTER TABLE [dbo].[ACT_APP_DEPLOYMENT] ADD CONSTRAINT [PK_ACT_APP_DEPLOYMENT] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_APP_DEPLOYMENT_RESOURCE
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_APP_RSRC_DPL]
ON [dbo].[ACT_APP_DEPLOYMENT_RESOURCE] (
  [DEPLOYMENT_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_APP_DEPLOYMENT_RESOURCE
-- ----------------------------
ALTER TABLE [dbo].[ACT_APP_DEPLOYMENT_RESOURCE] ADD CONSTRAINT [PK_APP_DEPLOYMENT_RESOURCE] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_CMMN_CASEDEF
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_CASE_DEF_DPLY]
ON [dbo].[ACT_CMMN_CASEDEF] (
  [DEPLOYMENT_ID_] ASC
)
GO

CREATE UNIQUE NONCLUSTERED INDEX [ACT_IDX_CASE_DEF_UNIQ]
ON [dbo].[ACT_CMMN_CASEDEF] (
  [KEY_] ASC,
  [VERSION_] ASC,
  [TENANT_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_CASEDEF
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_CASEDEF] ADD CONSTRAINT [PK_ACT_CMMN_CASEDEF] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_DATABASECHANGELOGLOCK
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_DATABASECHANGELOGLOCK] ADD CONSTRAINT [PK_ACT_CMMN_DATABASECHANGELOGLOCK] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_DEPLOYMENT
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_DEPLOYMENT] ADD CONSTRAINT [PK_ACT_CMMN_DEPLOYMENT] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_CMMN_DEPLOYMENT_RESOURCE
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_CMMN_RSRC_DPL]
ON [dbo].[ACT_CMMN_DEPLOYMENT_RESOURCE] (
  [DEPLOYMENT_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_DEPLOYMENT_RESOURCE
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_DEPLOYMENT_RESOURCE] ADD CONSTRAINT [PK_CMMN_DEPLOYMENT_RESOURCE] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_CMMN_HI_CASE_INST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_HI_CASE_INST_END]
ON [dbo].[ACT_CMMN_HI_CASE_INST] (
  [END_TIME_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_HI_CASE_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_HI_CASE_INST] ADD CONSTRAINT [PK_ACT_CMMN_HI_CASE_INST] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_HI_MIL_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_HI_MIL_INST] ADD CONSTRAINT [PK_ACT_CMMN_HI_MIL_INST] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_CMMN_HI_PLAN_ITEM_INST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_HI_PLAN_ITEM_INST_CASE]
ON [dbo].[ACT_CMMN_HI_PLAN_ITEM_INST] (
  [CASE_INST_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_HI_PLAN_ITEM_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_HI_PLAN_ITEM_INST] ADD CONSTRAINT [PK_ACT_CMMN_HI_PLAN_ITEM_INST] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_CMMN_RU_CASE_INST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_CASE_INST_CASE_DEF]
ON [dbo].[ACT_CMMN_RU_CASE_INST] (
  [CASE_DEF_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_CASE_INST_PARENT]
ON [dbo].[ACT_CMMN_RU_CASE_INST] (
  [PARENT_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_CASE_INST_REF_ID_]
ON [dbo].[ACT_CMMN_RU_CASE_INST] (
  [REFERENCE_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_RU_CASE_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_RU_CASE_INST] ADD CONSTRAINT [PK_ACT_CMMN_RU_CASE_INST] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_CMMN_RU_MIL_INST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_MIL_CASE_DEF]
ON [dbo].[ACT_CMMN_RU_MIL_INST] (
  [CASE_DEF_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_MIL_CASE_INST]
ON [dbo].[ACT_CMMN_RU_MIL_INST] (
  [CASE_INST_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_RU_MIL_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_RU_MIL_INST] ADD CONSTRAINT [PK_ACT_CMMN_RU_MIL_INST] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_CMMN_RU_PLAN_ITEM_INST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_PLAN_ITEM_CASE_DEF]
ON [dbo].[ACT_CMMN_RU_PLAN_ITEM_INST] (
  [CASE_DEF_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_PLAN_ITEM_CASE_INST]
ON [dbo].[ACT_CMMN_RU_PLAN_ITEM_INST] (
  [CASE_INST_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_PLAN_ITEM_STAGE_INST]
ON [dbo].[ACT_CMMN_RU_PLAN_ITEM_INST] (
  [STAGE_INST_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_RU_PLAN_ITEM_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_RU_PLAN_ITEM_INST] ADD CONSTRAINT [PK_CMMN_PLAN_ITEM_INST] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_CMMN_RU_SENTRY_PART_INST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_SENTRY_CASE_DEF]
ON [dbo].[ACT_CMMN_RU_SENTRY_PART_INST] (
  [CASE_DEF_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_SENTRY_CASE_INST]
ON [dbo].[ACT_CMMN_RU_SENTRY_PART_INST] (
  [CASE_INST_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_SENTRY_PLAN_ITEM]
ON [dbo].[ACT_CMMN_RU_SENTRY_PART_INST] (
  [PLAN_ITEM_INST_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_CMMN_RU_SENTRY_PART_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_RU_SENTRY_PART_INST] ADD CONSTRAINT [PK_CMMN_SENTRY_PART_INST] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_CO_CONTENT_ITEM
-- ----------------------------
CREATE NONCLUSTERED INDEX [idx_contitem_taskid]
ON [dbo].[ACT_CO_CONTENT_ITEM] (
  [TASK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [idx_contitem_procid]
ON [dbo].[ACT_CO_CONTENT_ITEM] (
  [PROC_INST_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [idx_contitem_scope]
ON [dbo].[ACT_CO_CONTENT_ITEM] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_CO_CONTENT_ITEM
-- ----------------------------
ALTER TABLE [dbo].[ACT_CO_CONTENT_ITEM] ADD CONSTRAINT [PK_ACT_CO_CONTENT_ITEM] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_CO_DATABASECHANGELOGLOCK
-- ----------------------------
ALTER TABLE [dbo].[ACT_CO_DATABASECHANGELOGLOCK] ADD CONSTRAINT [PK_ACT_CO_DATABASECHANGELOGLOCK] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_DE_DATABASECHANGELOGLOCK
-- ----------------------------
ALTER TABLE [dbo].[ACT_DE_DATABASECHANGELOGLOCK] ADD CONSTRAINT [PK_ACT_DE_DATABASECHANGELOGLOCK] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_DE_MODEL
-- ----------------------------
CREATE NONCLUSTERED INDEX [idx_proc_mod_created]
ON [dbo].[ACT_DE_MODEL] (
  [created_by] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_DE_MODEL
-- ----------------------------
ALTER TABLE [dbo].[ACT_DE_MODEL] ADD CONSTRAINT [PK_ACT_DE_MODEL] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_DE_MODEL_HISTORY
-- ----------------------------
CREATE NONCLUSTERED INDEX [idx_proc_mod_history_proc]
ON [dbo].[ACT_DE_MODEL_HISTORY] (
  [model_id] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_DE_MODEL_HISTORY
-- ----------------------------
ALTER TABLE [dbo].[ACT_DE_MODEL_HISTORY] ADD CONSTRAINT [PK_ACT_DE_MODEL_HISTORY] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_DE_MODEL_RELATION
-- ----------------------------
ALTER TABLE [dbo].[ACT_DE_MODEL_RELATION] ADD CONSTRAINT [PK_ACT_DE_MODEL_RELATION] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_DMN_DATABASECHANGELOGLOCK
-- ----------------------------
ALTER TABLE [dbo].[ACT_DMN_DATABASECHANGELOGLOCK] ADD CONSTRAINT [PK_ACT_DMN_DATABASECHANGELOGLOCK] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_DMN_DECISION
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [ACT_IDX_DMN_DEC_UNIQ]
ON [dbo].[ACT_DMN_DECISION] (
  [KEY_] ASC,
  [VERSION_] ASC,
  [TENANT_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_DMN_DECISION
-- ----------------------------
ALTER TABLE [dbo].[ACT_DMN_DECISION] ADD CONSTRAINT [PK_ACT_DMN_DECISION_TABLE] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_DMN_DEPLOYMENT
-- ----------------------------
ALTER TABLE [dbo].[ACT_DMN_DEPLOYMENT] ADD CONSTRAINT [PK_ACT_DMN_DEPLOYMENT] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_DMN_DEPLOYMENT_RESOURCE
-- ----------------------------
ALTER TABLE [dbo].[ACT_DMN_DEPLOYMENT_RESOURCE] ADD CONSTRAINT [PK_ACT_DMN_DEPLOYMENT_RESOURCE] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_DMN_HI_DECISION_EXECUTION
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_DMN_INSTANCE_ID]
ON [dbo].[ACT_DMN_HI_DECISION_EXECUTION] (
  [INSTANCE_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_DMN_HI_DECISION_EXECUTION
-- ----------------------------
ALTER TABLE [dbo].[ACT_DMN_HI_DECISION_EXECUTION] ADD CONSTRAINT [PK_ACT_DMN_HI_DECISION_EXECUTION] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for ACT_EVT_LOG
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[ACT_EVT_LOG]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table ACT_EVT_LOG
-- ----------------------------
ALTER TABLE [dbo].[ACT_EVT_LOG] ADD CONSTRAINT [PK__ACT_EVT___DE8852D86ADC7D73] PRIMARY KEY CLUSTERED ([LOG_NR_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_FO_DATABASECHANGELOGLOCK
-- ----------------------------
ALTER TABLE [dbo].[ACT_FO_DATABASECHANGELOGLOCK] ADD CONSTRAINT [PK_ACT_FO_DATABASECHANGELOGLOCK] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_FO_FORM_DEFINITION
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [ACT_IDX_FORM_DEF_UNIQ]
ON [dbo].[ACT_FO_FORM_DEFINITION] (
  [KEY_] ASC,
  [VERSION_] ASC,
  [TENANT_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_FO_FORM_DEFINITION
-- ----------------------------
ALTER TABLE [dbo].[ACT_FO_FORM_DEFINITION] ADD CONSTRAINT [PK_ACT_FO_FORM_DEFINITION] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_FO_FORM_DEPLOYMENT
-- ----------------------------
ALTER TABLE [dbo].[ACT_FO_FORM_DEPLOYMENT] ADD CONSTRAINT [PK_ACT_FO_FORM_DEPLOYMENT] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_FO_FORM_INSTANCE
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_FORM_TASK]
ON [dbo].[ACT_FO_FORM_INSTANCE] (
  [TASK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_FORM_PROC]
ON [dbo].[ACT_FO_FORM_INSTANCE] (
  [PROC_INST_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_FORM_SCOPE]
ON [dbo].[ACT_FO_FORM_INSTANCE] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_FO_FORM_INSTANCE
-- ----------------------------
ALTER TABLE [dbo].[ACT_FO_FORM_INSTANCE] ADD CONSTRAINT [PK_ACT_FO_FORM_INSTANCE] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_FO_FORM_RESOURCE
-- ----------------------------
ALTER TABLE [dbo].[ACT_FO_FORM_RESOURCE] ADD CONSTRAINT [PK_ACT_FO_FORM_RESOURCE] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_GE_BYTEARRAY
-- ----------------------------
ALTER TABLE [dbo].[ACT_GE_BYTEARRAY] ADD CONSTRAINT [PK__ACT_GE_B__C4971C0FD30493FA] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_GE_PROPERTY
-- ----------------------------
ALTER TABLE [dbo].[ACT_GE_PROPERTY] ADD CONSTRAINT [PK__ACT_GE_P__A7BE44DE49135FFB] PRIMARY KEY CLUSTERED ([NAME_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_HI_ACTINST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_HI_ACT_INST_START]
ON [dbo].[ACT_HI_ACTINST] (
  [START_TIME_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_ACT_INST_END]
ON [dbo].[ACT_HI_ACTINST] (
  [END_TIME_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_ACT_INST_PROCINST]
ON [dbo].[ACT_HI_ACTINST] (
  [PROC_INST_ID_] ASC,
  [ACT_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_ACT_INST_EXEC]
ON [dbo].[ACT_HI_ACTINST] (
  [EXECUTION_ID_] ASC,
  [ACT_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_HI_ACTINST
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_ACTINST] ADD CONSTRAINT [PK__ACT_HI_A__C4971C0F2451D6E3] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_HI_ATTACHMENT
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_ATTACHMENT] ADD CONSTRAINT [PK__ACT_HI_A__C4971C0F200B2D6C] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_HI_COMMENT
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_COMMENT] ADD CONSTRAINT [PK__ACT_HI_C__C4971C0F2E06F7E2] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_HI_DETAIL
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_HI_DETAIL_PROC_INST]
ON [dbo].[ACT_HI_DETAIL] (
  [PROC_INST_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_DETAIL_ACT_INST]
ON [dbo].[ACT_HI_DETAIL] (
  [ACT_INST_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_DETAIL_TIME]
ON [dbo].[ACT_HI_DETAIL] (
  [TIME_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_DETAIL_NAME]
ON [dbo].[ACT_HI_DETAIL] (
  [NAME_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_DETAIL_TASK_ID]
ON [dbo].[ACT_HI_DETAIL] (
  [TASK_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_HI_DETAIL
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_DETAIL] ADD CONSTRAINT [PK__ACT_HI_D__C4971C0F40A48B1A] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_HI_ENTITYLINK
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_HI_ENT_LNK_SCOPE]
ON [dbo].[ACT_HI_ENTITYLINK] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC,
  [LINK_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_ENT_LNK_REF_SCOPE]
ON [dbo].[ACT_HI_ENTITYLINK] (
  [REF_SCOPE_ID_] ASC,
  [REF_SCOPE_TYPE_] ASC,
  [LINK_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_ENT_LNK_ROOT_SCOPE]
ON [dbo].[ACT_HI_ENTITYLINK] (
  [ROOT_SCOPE_ID_] ASC,
  [ROOT_SCOPE_TYPE_] ASC,
  [LINK_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_ENT_LNK_SCOPE_DEF]
ON [dbo].[ACT_HI_ENTITYLINK] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC,
  [LINK_TYPE_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_HI_ENTITYLINK
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_ENTITYLINK] ADD CONSTRAINT [PK__ACT_HI_E__C4971C0FEED8EF51] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_HI_IDENTITYLINK
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_HI_IDENT_LNK_USER]
ON [dbo].[ACT_HI_IDENTITYLINK] (
  [USER_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_IDENT_LNK_SCOPE]
ON [dbo].[ACT_HI_IDENTITYLINK] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_IDENT_LNK_SUB_SCOPE]
ON [dbo].[ACT_HI_IDENTITYLINK] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_IDENT_LNK_SCOPE_DEF]
ON [dbo].[ACT_HI_IDENTITYLINK] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_IDENT_LNK_TASK]
ON [dbo].[ACT_HI_IDENTITYLINK] (
  [TASK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_IDENT_LNK_PROCINST]
ON [dbo].[ACT_HI_IDENTITYLINK] (
  [PROC_INST_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_HI_IDENTITYLINK
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_IDENTITYLINK] ADD CONSTRAINT [PK__ACT_HI_I__C4971C0FA127DE68] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_HI_PROCINST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_HI_PRO_INST_END]
ON [dbo].[ACT_HI_PROCINST] (
  [END_TIME_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_PRO_I_BUSKEY]
ON [dbo].[ACT_HI_PROCINST] (
  [BUSINESS_KEY_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_PRO_SUPER_PROCINST]
ON [dbo].[ACT_HI_PROCINST] (
  [SUPER_PROCESS_INSTANCE_ID_] ASC
)
GO


-- ----------------------------
-- Uniques structure for table ACT_HI_PROCINST
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_PROCINST] ADD CONSTRAINT [UQ__ACT_HI_P__C0341572F5DEF7EA] UNIQUE NONCLUSTERED ([PROC_INST_ID_] ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_HI_PROCINST
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_PROCINST] ADD CONSTRAINT [PK__ACT_HI_P__C4971C0F9DC73153] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_HI_TASKINST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_HI_TASK_SCOPE]
ON [dbo].[ACT_HI_TASKINST] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_TASK_SUB_SCOPE]
ON [dbo].[ACT_HI_TASKINST] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_TASK_SCOPE_DEF]
ON [dbo].[ACT_HI_TASKINST] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_TASK_INST_PROCINST]
ON [dbo].[ACT_HI_TASKINST] (
  [PROC_INST_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_HI_TASKINST
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_TASKINST] ADD CONSTRAINT [PK__ACT_HI_T__C4971C0FDDE6C3CA] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for ACT_HI_TSK_LOG
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[ACT_HI_TSK_LOG]', RESEED, 1)
GO


-- ----------------------------
-- Primary Key structure for table ACT_HI_TSK_LOG
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_TSK_LOG] ADD CONSTRAINT [PK__ACT_HI_T__C4971C0FE3DE5C63] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_HI_VARINST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_HI_PROCVAR_NAME_TYPE]
ON [dbo].[ACT_HI_VARINST] (
  [NAME_] ASC,
  [VAR_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_VAR_SCOPE_ID_TYPE]
ON [dbo].[ACT_HI_VARINST] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_VAR_SUB_ID_TYPE]
ON [dbo].[ACT_HI_VARINST] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_PROCVAR_PROC_INST]
ON [dbo].[ACT_HI_VARINST] (
  [PROC_INST_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_PROCVAR_TASK_ID]
ON [dbo].[ACT_HI_VARINST] (
  [TASK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_HI_PROCVAR_EXE]
ON [dbo].[ACT_HI_VARINST] (
  [EXECUTION_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_HI_VARINST
-- ----------------------------
ALTER TABLE [dbo].[ACT_HI_VARINST] ADD CONSTRAINT [PK__ACT_HI_V__C4971C0F899A46DE] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_ID_BYTEARRAY
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_BYTEARRAY] ADD CONSTRAINT [PK__ACT_ID_B__C4971C0FE3B999DC] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_ID_GROUP
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_GROUP] ADD CONSTRAINT [PK__ACT_ID_G__C4971C0F2B119228] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_ID_INFO
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_INFO] ADD CONSTRAINT [PK__ACT_ID_I__C4971C0F0E8CE406] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_ID_MEMBERSHIP
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_MEMBERSHIP] ADD CONSTRAINT [PK__ACT_ID_M__C2371B0F230E010D] PRIMARY KEY CLUSTERED ([USER_ID_], [GROUP_ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Uniques structure for table ACT_ID_PRIV
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_PRIV] ADD CONSTRAINT [ACT_UNIQ_PRIV_NAME] UNIQUE NONCLUSTERED ([NAME_] ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_ID_PRIV
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_PRIV] ADD CONSTRAINT [PK__ACT_ID_P__C4971C0F1B9A75B0] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_ID_PRIV_MAPPING
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_PRIV_USER]
ON [dbo].[ACT_ID_PRIV_MAPPING] (
  [USER_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_PRIV_GROUP]
ON [dbo].[ACT_ID_PRIV_MAPPING] (
  [GROUP_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_ID_PRIV_MAPPING
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_PRIV_MAPPING] ADD CONSTRAINT [PK__ACT_ID_P__C4971C0F113C0DF7] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_ID_PROPERTY
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_PROPERTY] ADD CONSTRAINT [PK__ACT_ID_P__A7BE44DE00D2EAEF] PRIMARY KEY CLUSTERED ([NAME_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_ID_TOKEN
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_TOKEN] ADD CONSTRAINT [PK__ACT_ID_T__C4971C0F0C6DA3E7] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_ID_USER
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_USER] ADD CONSTRAINT [PK__ACT_ID_U__C4971C0FFB7FB36B] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_PROCDEF_INFO
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_INFO_PROCDEF]
ON [dbo].[ACT_PROCDEF_INFO] (
  [PROC_DEF_ID_] ASC
)
GO


-- ----------------------------
-- Uniques structure for table ACT_PROCDEF_INFO
-- ----------------------------
ALTER TABLE [dbo].[ACT_PROCDEF_INFO] ADD CONSTRAINT [ACT_UNIQ_INFO_PROCDEF] UNIQUE NONCLUSTERED ([PROC_DEF_ID_] ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_PROCDEF_INFO
-- ----------------------------
ALTER TABLE [dbo].[ACT_PROCDEF_INFO] ADD CONSTRAINT [PK__ACT_PROC__C4971C0F74F78A2D] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_RE_DEPLOYMENT
-- ----------------------------
ALTER TABLE [dbo].[ACT_RE_DEPLOYMENT] ADD CONSTRAINT [PK__ACT_RE_D__C4971C0F364A6578] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_RE_MODEL
-- ----------------------------
ALTER TABLE [dbo].[ACT_RE_MODEL] ADD CONSTRAINT [PK__ACT_RE_M__C4971C0FC6153643] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Uniques structure for table ACT_RE_PROCDEF
-- ----------------------------
ALTER TABLE [dbo].[ACT_RE_PROCDEF] ADD CONSTRAINT [ACT_UNIQ_PROCDEF] UNIQUE NONCLUSTERED ([KEY_] ASC, [VERSION_] ASC, [DERIVED_VERSION_] ASC, [TENANT_ID_] ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_RE_PROCDEF
-- ----------------------------
ALTER TABLE [dbo].[ACT_RE_PROCDEF] ADD CONSTRAINT [PK__ACT_RE_P__C4971C0FF3F16E59] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_ACTINST
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_RU_ACTI_START]
ON [dbo].[ACT_RU_ACTINST] (
  [START_TIME_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_RU_ACTI_END]
ON [dbo].[ACT_RU_ACTINST] (
  [END_TIME_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_RU_ACTI_PROC]
ON [dbo].[ACT_RU_ACTINST] (
  [PROC_INST_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_RU_ACTI_PROC_ACT]
ON [dbo].[ACT_RU_ACTINST] (
  [PROC_INST_ID_] ASC,
  [ACT_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_RU_ACTI_EXEC]
ON [dbo].[ACT_RU_ACTINST] (
  [EXECUTION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_RU_ACTI_EXEC_ACT]
ON [dbo].[ACT_RU_ACTINST] (
  [EXECUTION_ID_] ASC,
  [ACT_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_RU_ACTI_TASK]
ON [dbo].[ACT_RU_ACTINST] (
  [TASK_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_ACTINST
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_ACTINST] ADD CONSTRAINT [PK__ACT_RU_A__C4971C0F9AFE0002] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_DEADLETTER_JOB
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_DEADLETTER_JOB_EXCEPTION_STACK_ID]
ON [dbo].[ACT_RU_DEADLETTER_JOB] (
  [EXCEPTION_STACK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_DEADLETTER_JOB_CUSTOM_VALUES_ID]
ON [dbo].[ACT_RU_DEADLETTER_JOB] (
  [CUSTOM_VALUES_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_DEADLETTER_JOB_CORRELATION_ID]
ON [dbo].[ACT_RU_DEADLETTER_JOB] (
  [CORRELATION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_DJOB_SCOPE]
ON [dbo].[ACT_RU_DEADLETTER_JOB] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_DJOB_SUB_SCOPE]
ON [dbo].[ACT_RU_DEADLETTER_JOB] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_DJOB_SCOPE_DEF]
ON [dbo].[ACT_RU_DEADLETTER_JOB] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_DEADLETTER_JOB_EXECUTION_ID]
ON [dbo].[ACT_RU_DEADLETTER_JOB] (
  [EXECUTION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_DEADLETTER_JOB_PROCESS_INSTANCE_ID]
ON [dbo].[ACT_RU_DEADLETTER_JOB] (
  [PROCESS_INSTANCE_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_DEADLETTER_JOB_PROC_DEF_ID]
ON [dbo].[ACT_RU_DEADLETTER_JOB] (
  [PROC_DEF_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_DEADLETTER_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_DEADLETTER_JOB] ADD CONSTRAINT [PK__ACT_RU_D__C4971C0F802873D8] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_ENTITYLINK
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_ENT_LNK_SCOPE]
ON [dbo].[ACT_RU_ENTITYLINK] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC,
  [LINK_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_ENT_LNK_REF_SCOPE]
ON [dbo].[ACT_RU_ENTITYLINK] (
  [REF_SCOPE_ID_] ASC,
  [REF_SCOPE_TYPE_] ASC,
  [LINK_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_ENT_LNK_ROOT_SCOPE]
ON [dbo].[ACT_RU_ENTITYLINK] (
  [ROOT_SCOPE_ID_] ASC,
  [ROOT_SCOPE_TYPE_] ASC,
  [LINK_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_ENT_LNK_SCOPE_DEF]
ON [dbo].[ACT_RU_ENTITYLINK] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC,
  [LINK_TYPE_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_ENTITYLINK
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_ENTITYLINK] ADD CONSTRAINT [PK__ACT_RU_E__C4971C0F1837003C] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_EVENT_SUBSCR
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_EVENT_SUBSCR_CONFIG_]
ON [dbo].[ACT_RU_EVENT_SUBSCR] (
  [CONFIGURATION_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EVENT_SUBSCR_EXEC_ID]
ON [dbo].[ACT_RU_EVENT_SUBSCR] (
  [EXECUTION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EVENT_SUBSCR_SCOPEREF_]
ON [dbo].[ACT_RU_EVENT_SUBSCR] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_EVENT_SUBSCR
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_EVENT_SUBSCR] ADD CONSTRAINT [PK__ACT_RU_E__C4971C0FD068F467] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_EXECUTION
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_EXEC_BUSKEY]
ON [dbo].[ACT_RU_EXECUTION] (
  [BUSINESS_KEY_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EXEC_ROOT]
ON [dbo].[ACT_RU_EXECUTION] (
  [ROOT_PROC_INST_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EXEC_REF_ID_]
ON [dbo].[ACT_RU_EXECUTION] (
  [REFERENCE_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EXECUTION_PROC]
ON [dbo].[ACT_RU_EXECUTION] (
  [PROC_DEF_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EXECUTION_PARENT]
ON [dbo].[ACT_RU_EXECUTION] (
  [PARENT_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EXECUTION_SUPER]
ON [dbo].[ACT_RU_EXECUTION] (
  [SUPER_EXEC_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EXECUTION_IDANDREV]
ON [dbo].[ACT_RU_EXECUTION] (
  [ID_] ASC,
  [REV_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EXEC_PROC_INST_ID]
ON [dbo].[ACT_RU_EXECUTION] (
  [PROC_INST_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_EXECUTION
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_EXECUTION] ADD CONSTRAINT [PK__ACT_RU_E__C4971C0F23EDB8A1] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_EXTERNAL_JOB
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_EXTERNAL_JOB_EXCEPTION_STACK_ID]
ON [dbo].[ACT_RU_EXTERNAL_JOB] (
  [EXCEPTION_STACK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EXTERNAL_JOB_CUSTOM_VALUES_ID]
ON [dbo].[ACT_RU_EXTERNAL_JOB] (
  [CUSTOM_VALUES_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EXTERNAL_JOB_CORRELATION_ID]
ON [dbo].[ACT_RU_EXTERNAL_JOB] (
  [CORRELATION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EJOB_SCOPE]
ON [dbo].[ACT_RU_EXTERNAL_JOB] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EJOB_SUB_SCOPE]
ON [dbo].[ACT_RU_EXTERNAL_JOB] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_EJOB_SCOPE_DEF]
ON [dbo].[ACT_RU_EXTERNAL_JOB] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_EXTERNAL_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_EXTERNAL_JOB] ADD CONSTRAINT [PK__ACT_RU_E__C4971C0F34A40685] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_HISTORY_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_HISTORY_JOB] ADD CONSTRAINT [PK__ACT_RU_H__C4971C0F01BB5820] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_IDENTITYLINK
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_IDENT_LNK_USER]
ON [dbo].[ACT_RU_IDENTITYLINK] (
  [USER_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_IDENT_LNK_GROUP]
ON [dbo].[ACT_RU_IDENTITYLINK] (
  [GROUP_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_IDENT_LNK_SCOPE]
ON [dbo].[ACT_RU_IDENTITYLINK] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_IDENT_LNK_SUB_SCOPE]
ON [dbo].[ACT_RU_IDENTITYLINK] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_IDENT_LNK_SCOPE_DEF]
ON [dbo].[ACT_RU_IDENTITYLINK] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_ATHRZ_PROCEDEF]
ON [dbo].[ACT_RU_IDENTITYLINK] (
  [PROC_DEF_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_IDENT_LNK_TASK]
ON [dbo].[ACT_RU_IDENTITYLINK] (
  [TASK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_IDENT_LNK_PROCINST]
ON [dbo].[ACT_RU_IDENTITYLINK] (
  [PROC_INST_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_IDENTITYLINK
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_IDENTITYLINK] ADD CONSTRAINT [PK__ACT_RU_I__C4971C0F84681884] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_JOB
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_JOB_EXCEPTION_STACK_ID]
ON [dbo].[ACT_RU_JOB] (
  [EXCEPTION_STACK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_JOB_CUSTOM_VALUES_ID]
ON [dbo].[ACT_RU_JOB] (
  [CUSTOM_VALUES_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_JOB_CORRELATION_ID]
ON [dbo].[ACT_RU_JOB] (
  [CORRELATION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_JOB_SCOPE]
ON [dbo].[ACT_RU_JOB] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_JOB_SUB_SCOPE]
ON [dbo].[ACT_RU_JOB] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_JOB_SCOPE_DEF]
ON [dbo].[ACT_RU_JOB] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_JOB_EXECUTION_ID]
ON [dbo].[ACT_RU_JOB] (
  [EXECUTION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_JOB_PROCESS_INSTANCE_ID]
ON [dbo].[ACT_RU_JOB] (
  [PROCESS_INSTANCE_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_JOB_PROC_DEF_ID]
ON [dbo].[ACT_RU_JOB] (
  [PROC_DEF_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_JOB] ADD CONSTRAINT [PK__ACT_RU_J__C4971C0FA45441B1] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_SUSPENDED_JOB
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_SUSPENDED_JOB_EXCEPTION_STACK_ID]
ON [dbo].[ACT_RU_SUSPENDED_JOB] (
  [EXCEPTION_STACK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_SUSPENDED_JOB_CUSTOM_VALUES_ID]
ON [dbo].[ACT_RU_SUSPENDED_JOB] (
  [CUSTOM_VALUES_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_SUSPENDED_JOB_CORRELATION_ID]
ON [dbo].[ACT_RU_SUSPENDED_JOB] (
  [CORRELATION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_SJOB_SCOPE]
ON [dbo].[ACT_RU_SUSPENDED_JOB] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_SJOB_SUB_SCOPE]
ON [dbo].[ACT_RU_SUSPENDED_JOB] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_SJOB_SCOPE_DEF]
ON [dbo].[ACT_RU_SUSPENDED_JOB] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_SUSPENDED_JOB_EXECUTION_ID]
ON [dbo].[ACT_RU_SUSPENDED_JOB] (
  [EXECUTION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_SUSPENDED_JOB_PROCESS_INSTANCE_ID]
ON [dbo].[ACT_RU_SUSPENDED_JOB] (
  [PROCESS_INSTANCE_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_SUSPENDED_JOB_PROC_DEF_ID]
ON [dbo].[ACT_RU_SUSPENDED_JOB] (
  [PROC_DEF_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_SUSPENDED_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_SUSPENDED_JOB] ADD CONSTRAINT [PK__ACT_RU_S__C4971C0F2256B43D] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_TASK
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_TASK_CREATE]
ON [dbo].[ACT_RU_TASK] (
  [CREATE_TIME_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TASK_SCOPE]
ON [dbo].[ACT_RU_TASK] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TASK_SUB_SCOPE]
ON [dbo].[ACT_RU_TASK] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TASK_SCOPE_DEF]
ON [dbo].[ACT_RU_TASK] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TASK_EXEC]
ON [dbo].[ACT_RU_TASK] (
  [EXECUTION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TASK_PROCINST]
ON [dbo].[ACT_RU_TASK] (
  [PROC_INST_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TASK_PROC_DEF_ID]
ON [dbo].[ACT_RU_TASK] (
  [PROC_DEF_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_TASK
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_TASK] ADD CONSTRAINT [PK__ACT_RU_T__C4971C0FCFBADCC4] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_TIMER_JOB
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_TIMER_JOB_EXCEPTION_STACK_ID]
ON [dbo].[ACT_RU_TIMER_JOB] (
  [EXCEPTION_STACK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TIMER_JOB_CUSTOM_VALUES_ID]
ON [dbo].[ACT_RU_TIMER_JOB] (
  [CUSTOM_VALUES_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TIMER_JOB_CORRELATION_ID]
ON [dbo].[ACT_RU_TIMER_JOB] (
  [CORRELATION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TIMER_JOB_DUEDATE]
ON [dbo].[ACT_RU_TIMER_JOB] (
  [DUEDATE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TJOB_SCOPE]
ON [dbo].[ACT_RU_TIMER_JOB] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TJOB_SUB_SCOPE]
ON [dbo].[ACT_RU_TIMER_JOB] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TJOB_SCOPE_DEF]
ON [dbo].[ACT_RU_TIMER_JOB] (
  [SCOPE_DEFINITION_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TIMER_JOB_EXECUTION_ID]
ON [dbo].[ACT_RU_TIMER_JOB] (
  [EXECUTION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TIMER_JOB_PROCESS_INSTANCE_ID]
ON [dbo].[ACT_RU_TIMER_JOB] (
  [PROCESS_INSTANCE_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_TIMER_JOB_PROC_DEF_ID]
ON [dbo].[ACT_RU_TIMER_JOB] (
  [PROC_DEF_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_TIMER_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_TIMER_JOB] ADD CONSTRAINT [PK__ACT_RU_T__C4971C0FE21B8629] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table ACT_RU_VARIABLE
-- ----------------------------
CREATE NONCLUSTERED INDEX [ACT_IDX_RU_VAR_SCOPE_ID_TYPE]
ON [dbo].[ACT_RU_VARIABLE] (
  [SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_RU_VAR_SUB_ID_TYPE]
ON [dbo].[ACT_RU_VARIABLE] (
  [SUB_SCOPE_ID_] ASC,
  [SCOPE_TYPE_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_VARIABLE_BA]
ON [dbo].[ACT_RU_VARIABLE] (
  [BYTEARRAY_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_VARIABLE_TASK_ID]
ON [dbo].[ACT_RU_VARIABLE] (
  [TASK_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_VARIABLE_EXEC]
ON [dbo].[ACT_RU_VARIABLE] (
  [EXECUTION_ID_] ASC
)
GO

CREATE NONCLUSTERED INDEX [ACT_IDX_VARIABLE_PROCINST]
ON [dbo].[ACT_RU_VARIABLE] (
  [PROC_INST_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table ACT_RU_VARIABLE
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_VARIABLE] ADD CONSTRAINT [PK__ACT_RU_V__C4971C0F1A05E3A9] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table FLW_CHANNEL_DEFINITION
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [ACT_IDX_CHANNEL_DEF_UNIQ]
ON [dbo].[FLW_CHANNEL_DEFINITION] (
  [KEY_] ASC,
  [VERSION_] ASC,
  [TENANT_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table FLW_CHANNEL_DEFINITION
-- ----------------------------
ALTER TABLE [dbo].[FLW_CHANNEL_DEFINITION] ADD CONSTRAINT [PK_FLW_CHANNEL_DEFINITION] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table FLW_EVENT_DEFINITION
-- ----------------------------
CREATE UNIQUE NONCLUSTERED INDEX [ACT_IDX_EVENT_DEF_UNIQ]
ON [dbo].[FLW_EVENT_DEFINITION] (
  [KEY_] ASC,
  [VERSION_] ASC,
  [TENANT_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table FLW_EVENT_DEFINITION
-- ----------------------------
ALTER TABLE [dbo].[FLW_EVENT_DEFINITION] ADD CONSTRAINT [PK_FLW_EVENT_DEFINITION] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table FLW_EVENT_DEPLOYMENT
-- ----------------------------
ALTER TABLE [dbo].[FLW_EVENT_DEPLOYMENT] ADD CONSTRAINT [PK_FLW_EVENT_DEPLOYMENT] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table FLW_EVENT_RESOURCE
-- ----------------------------
ALTER TABLE [dbo].[FLW_EVENT_RESOURCE] ADD CONSTRAINT [PK_FLW_EVENT_RESOURCE] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table FLW_EV_DATABASECHANGELOGLOCK
-- ----------------------------
ALTER TABLE [dbo].[FLW_EV_DATABASECHANGELOGLOCK] ADD CONSTRAINT [PK_FLW_EV_DATABASECHANGELOGLOCK] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table FLW_RU_BATCH
-- ----------------------------
ALTER TABLE [dbo].[FLW_RU_BATCH] ADD CONSTRAINT [PK__FLW_RU_B__C4971C0FFD4930D4] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table FLW_RU_BATCH_PART
-- ----------------------------
CREATE NONCLUSTERED INDEX [FLW_IDX_BATCH_PART]
ON [dbo].[FLW_RU_BATCH_PART] (
  [BATCH_ID_] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table FLW_RU_BATCH_PART
-- ----------------------------
ALTER TABLE [dbo].[FLW_RU_BATCH_PART] ADD CONSTRAINT [PK__FLW_RU_B__C4971C0F47364816] PRIMARY KEY CLUSTERED ([ID_])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_APP_APPDEF
-- ----------------------------
ALTER TABLE [dbo].[ACT_APP_APPDEF] ADD CONSTRAINT [ACT_FK_APP_DEF_DPLY] FOREIGN KEY ([DEPLOYMENT_ID_]) REFERENCES [dbo].[ACT_APP_DEPLOYMENT] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_APP_DEPLOYMENT_RESOURCE
-- ----------------------------
ALTER TABLE [dbo].[ACT_APP_DEPLOYMENT_RESOURCE] ADD CONSTRAINT [ACT_FK_APP_RSRC_DPL] FOREIGN KEY ([DEPLOYMENT_ID_]) REFERENCES [dbo].[ACT_APP_DEPLOYMENT] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_CMMN_CASEDEF
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_CASEDEF] ADD CONSTRAINT [ACT_FK_CASE_DEF_DPLY] FOREIGN KEY ([DEPLOYMENT_ID_]) REFERENCES [dbo].[ACT_CMMN_DEPLOYMENT] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_CMMN_DEPLOYMENT_RESOURCE
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_DEPLOYMENT_RESOURCE] ADD CONSTRAINT [ACT_FK_CMMN_RSRC_DPL] FOREIGN KEY ([DEPLOYMENT_ID_]) REFERENCES [dbo].[ACT_CMMN_DEPLOYMENT] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_CMMN_RU_CASE_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_RU_CASE_INST] ADD CONSTRAINT [ACT_FK_CASE_INST_CASE_DEF] FOREIGN KEY ([CASE_DEF_ID_]) REFERENCES [dbo].[ACT_CMMN_CASEDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_CMMN_RU_MIL_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_RU_MIL_INST] ADD CONSTRAINT [ACT_FK_MIL_CASE_DEF] FOREIGN KEY ([CASE_DEF_ID_]) REFERENCES [dbo].[ACT_CMMN_CASEDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_CMMN_RU_MIL_INST] ADD CONSTRAINT [ACT_FK_MIL_CASE_INST] FOREIGN KEY ([CASE_INST_ID_]) REFERENCES [dbo].[ACT_CMMN_RU_CASE_INST] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_CMMN_RU_PLAN_ITEM_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_RU_PLAN_ITEM_INST] ADD CONSTRAINT [ACT_FK_PLAN_ITEM_CASE_DEF] FOREIGN KEY ([CASE_DEF_ID_]) REFERENCES [dbo].[ACT_CMMN_CASEDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_CMMN_RU_PLAN_ITEM_INST] ADD CONSTRAINT [ACT_FK_PLAN_ITEM_CASE_INST] FOREIGN KEY ([CASE_INST_ID_]) REFERENCES [dbo].[ACT_CMMN_RU_CASE_INST] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_CMMN_RU_SENTRY_PART_INST
-- ----------------------------
ALTER TABLE [dbo].[ACT_CMMN_RU_SENTRY_PART_INST] ADD CONSTRAINT [ACT_FK_SENTRY_CASE_DEF] FOREIGN KEY ([CASE_DEF_ID_]) REFERENCES [dbo].[ACT_CMMN_CASEDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_CMMN_RU_SENTRY_PART_INST] ADD CONSTRAINT [ACT_FK_SENTRY_CASE_INST] FOREIGN KEY ([CASE_INST_ID_]) REFERENCES [dbo].[ACT_CMMN_RU_CASE_INST] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_CMMN_RU_SENTRY_PART_INST] ADD CONSTRAINT [ACT_FK_SENTRY_PLAN_ITEM] FOREIGN KEY ([PLAN_ITEM_INST_ID_]) REFERENCES [dbo].[ACT_CMMN_RU_PLAN_ITEM_INST] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_DE_MODEL_RELATION
-- ----------------------------
ALTER TABLE [dbo].[ACT_DE_MODEL_RELATION] ADD CONSTRAINT [fk_relation_parent] FOREIGN KEY ([parent_model_id]) REFERENCES [dbo].[ACT_DE_MODEL] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_DE_MODEL_RELATION] ADD CONSTRAINT [fk_relation_child] FOREIGN KEY ([model_id]) REFERENCES [dbo].[ACT_DE_MODEL] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_GE_BYTEARRAY
-- ----------------------------
ALTER TABLE [dbo].[ACT_GE_BYTEARRAY] ADD CONSTRAINT [ACT_FK_BYTEARR_DEPL] FOREIGN KEY ([DEPLOYMENT_ID_]) REFERENCES [dbo].[ACT_RE_DEPLOYMENT] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_ID_MEMBERSHIP
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_MEMBERSHIP] ADD CONSTRAINT [ACT_FK_MEMB_GROUP] FOREIGN KEY ([GROUP_ID_]) REFERENCES [dbo].[ACT_ID_GROUP] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_ID_MEMBERSHIP] ADD CONSTRAINT [ACT_FK_MEMB_USER] FOREIGN KEY ([USER_ID_]) REFERENCES [dbo].[ACT_ID_USER] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_ID_PRIV_MAPPING
-- ----------------------------
ALTER TABLE [dbo].[ACT_ID_PRIV_MAPPING] ADD CONSTRAINT [ACT_FK_PRIV_MAPPING] FOREIGN KEY ([PRIV_ID_]) REFERENCES [dbo].[ACT_ID_PRIV] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_PROCDEF_INFO
-- ----------------------------
ALTER TABLE [dbo].[ACT_PROCDEF_INFO] ADD CONSTRAINT [ACT_FK_INFO_JSON_BA] FOREIGN KEY ([INFO_JSON_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_PROCDEF_INFO] ADD CONSTRAINT [ACT_FK_INFO_PROCDEF] FOREIGN KEY ([PROC_DEF_ID_]) REFERENCES [dbo].[ACT_RE_PROCDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RE_MODEL
-- ----------------------------
ALTER TABLE [dbo].[ACT_RE_MODEL] ADD CONSTRAINT [ACT_FK_MODEL_SOURCE] FOREIGN KEY ([EDITOR_SOURCE_VALUE_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RE_MODEL] ADD CONSTRAINT [ACT_FK_MODEL_SOURCE_EXTRA] FOREIGN KEY ([EDITOR_SOURCE_EXTRA_VALUE_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RE_MODEL] ADD CONSTRAINT [ACT_FK_MODEL_DEPLOYMENT] FOREIGN KEY ([DEPLOYMENT_ID_]) REFERENCES [dbo].[ACT_RE_DEPLOYMENT] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RU_DEADLETTER_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_DEADLETTER_JOB] ADD CONSTRAINT [ACT_FK_DEADLETTER_JOB_EXCEPTION] FOREIGN KEY ([EXCEPTION_STACK_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_DEADLETTER_JOB] ADD CONSTRAINT [ACT_FK_DEADLETTER_JOB_CUSTOM_VALUES] FOREIGN KEY ([CUSTOM_VALUES_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_DEADLETTER_JOB] ADD CONSTRAINT [ACT_FK_DEADLETTER_JOB_EXECUTION] FOREIGN KEY ([EXECUTION_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_DEADLETTER_JOB] ADD CONSTRAINT [ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE] FOREIGN KEY ([PROCESS_INSTANCE_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_DEADLETTER_JOB] ADD CONSTRAINT [ACT_FK_DEADLETTER_JOB_PROC_DEF] FOREIGN KEY ([PROC_DEF_ID_]) REFERENCES [dbo].[ACT_RE_PROCDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RU_EVENT_SUBSCR
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_EVENT_SUBSCR] ADD CONSTRAINT [ACT_FK_EVENT_EXEC] FOREIGN KEY ([EXECUTION_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RU_EXECUTION
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_EXECUTION] ADD CONSTRAINT [ACT_FK_EXE_PARENT] FOREIGN KEY ([PARENT_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_EXECUTION] ADD CONSTRAINT [ACT_FK_EXE_SUPER] FOREIGN KEY ([SUPER_EXEC_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_EXECUTION] ADD CONSTRAINT [ACT_FK_EXE_PROCDEF] FOREIGN KEY ([PROC_DEF_ID_]) REFERENCES [dbo].[ACT_RE_PROCDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RU_EXTERNAL_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_EXTERNAL_JOB] ADD CONSTRAINT [ACT_FK_EXTERNAL_JOB_EXCEPTION] FOREIGN KEY ([EXCEPTION_STACK_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_EXTERNAL_JOB] ADD CONSTRAINT [ACT_FK_EXTERNAL_JOB_CUSTOM_VALUES] FOREIGN KEY ([CUSTOM_VALUES_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RU_IDENTITYLINK
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_IDENTITYLINK] ADD CONSTRAINT [ACT_FK_TSKASS_TASK] FOREIGN KEY ([TASK_ID_]) REFERENCES [dbo].[ACT_RU_TASK] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_IDENTITYLINK] ADD CONSTRAINT [ACT_FK_ATHRZ_PROCEDEF] FOREIGN KEY ([PROC_DEF_ID_]) REFERENCES [dbo].[ACT_RE_PROCDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_IDENTITYLINK] ADD CONSTRAINT [ACT_FK_IDL_PROCINST] FOREIGN KEY ([PROC_INST_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RU_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_JOB] ADD CONSTRAINT [ACT_FK_JOB_EXCEPTION] FOREIGN KEY ([EXCEPTION_STACK_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_JOB] ADD CONSTRAINT [ACT_FK_JOB_CUSTOM_VALUES] FOREIGN KEY ([CUSTOM_VALUES_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_JOB] ADD CONSTRAINT [ACT_FK_JOB_EXECUTION] FOREIGN KEY ([EXECUTION_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_JOB] ADD CONSTRAINT [ACT_FK_JOB_PROCESS_INSTANCE] FOREIGN KEY ([PROCESS_INSTANCE_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_JOB] ADD CONSTRAINT [ACT_FK_JOB_PROC_DEF] FOREIGN KEY ([PROC_DEF_ID_]) REFERENCES [dbo].[ACT_RE_PROCDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RU_SUSPENDED_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_SUSPENDED_JOB] ADD CONSTRAINT [ACT_FK_SUSPENDED_JOB_EXCEPTION] FOREIGN KEY ([EXCEPTION_STACK_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_SUSPENDED_JOB] ADD CONSTRAINT [ACT_FK_SUSPENDED_JOB_CUSTOM_VALUES] FOREIGN KEY ([CUSTOM_VALUES_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_SUSPENDED_JOB] ADD CONSTRAINT [ACT_FK_SUSPENDED_JOB_EXECUTION] FOREIGN KEY ([EXECUTION_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_SUSPENDED_JOB] ADD CONSTRAINT [ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE] FOREIGN KEY ([PROCESS_INSTANCE_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_SUSPENDED_JOB] ADD CONSTRAINT [ACT_FK_SUSPENDED_JOB_PROC_DEF] FOREIGN KEY ([PROC_DEF_ID_]) REFERENCES [dbo].[ACT_RE_PROCDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RU_TASK
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_TASK] ADD CONSTRAINT [ACT_FK_TASK_EXE] FOREIGN KEY ([EXECUTION_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_TASK] ADD CONSTRAINT [ACT_FK_TASK_PROCINST] FOREIGN KEY ([PROC_INST_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_TASK] ADD CONSTRAINT [ACT_FK_TASK_PROCDEF] FOREIGN KEY ([PROC_DEF_ID_]) REFERENCES [dbo].[ACT_RE_PROCDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RU_TIMER_JOB
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_TIMER_JOB] ADD CONSTRAINT [ACT_FK_TIMER_JOB_EXCEPTION] FOREIGN KEY ([EXCEPTION_STACK_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_TIMER_JOB] ADD CONSTRAINT [ACT_FK_TIMER_JOB_CUSTOM_VALUES] FOREIGN KEY ([CUSTOM_VALUES_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_TIMER_JOB] ADD CONSTRAINT [ACT_FK_TIMER_JOB_EXECUTION] FOREIGN KEY ([EXECUTION_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_TIMER_JOB] ADD CONSTRAINT [ACT_FK_TIMER_JOB_PROCESS_INSTANCE] FOREIGN KEY ([PROCESS_INSTANCE_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_TIMER_JOB] ADD CONSTRAINT [ACT_FK_TIMER_JOB_PROC_DEF] FOREIGN KEY ([PROC_DEF_ID_]) REFERENCES [dbo].[ACT_RE_PROCDEF] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table ACT_RU_VARIABLE
-- ----------------------------
ALTER TABLE [dbo].[ACT_RU_VARIABLE] ADD CONSTRAINT [ACT_FK_VAR_BYTEARRAY] FOREIGN KEY ([BYTEARRAY_ID_]) REFERENCES [dbo].[ACT_GE_BYTEARRAY] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_VARIABLE] ADD CONSTRAINT [ACT_FK_VAR_EXE] FOREIGN KEY ([EXECUTION_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

ALTER TABLE [dbo].[ACT_RU_VARIABLE] ADD CONSTRAINT [ACT_FK_VAR_PROCINST] FOREIGN KEY ([PROC_INST_ID_]) REFERENCES [dbo].[ACT_RU_EXECUTION] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO


-- ----------------------------
-- Foreign Keys structure for table FLW_RU_BATCH_PART
-- ----------------------------
ALTER TABLE [dbo].[FLW_RU_BATCH_PART] ADD CONSTRAINT [FLW_FK_BATCH_PART_PARENT] FOREIGN KEY ([BATCH_ID_]) REFERENCES [dbo].[FLW_RU_BATCH] ([ID_]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

