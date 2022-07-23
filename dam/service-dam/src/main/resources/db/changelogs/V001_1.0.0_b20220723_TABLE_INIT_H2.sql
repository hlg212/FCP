--liquibase formatted sql
--changeset hlg:dam-1 dbms:h2

/*==============================================================*/
/* Table: T_DAM_CONFIG                                          */
/*==============================================================*/
create table T_DAM_CONFIG
(
    id                   VARCHAR2(32)         not null,
    app_code             VARCHAR2(32),
    name                 VARCHAR2(50),
    code                 VARCHAR2(50),
    is_Apply_Add         CHAR(1),
    is_Apply_Update      CHAR(1),
    is_Apply_Delete      CHAR(1),
    is_Apply_Query       CHAR(1),
    is_enabled           CHAR(1),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_DAM_CONFIG primary key (id)
);

comment on column T_DAM_CONFIG.is_enabled is
'是否启用';

comment on column T_DAM_CONFIG.memo is
'备注';

comment on column T_DAM_CONFIG.sort_num is
'排序';

comment on column T_DAM_CONFIG.create_time is
'创建时间';

comment on column T_DAM_CONFIG.create_user_name is
'创建人名称';

comment on column T_DAM_CONFIG.update_time is
'修改时间';

comment on column T_DAM_CONFIG.update_user_name is
'修改人名称';


/*==============================================================*/
/* Table: T_DAM_SCOPE_CONDITION                                 */
/*==============================================================*/
create table T_DAM_SCOPE_CONDITION
(
    ID                   VARCHAR2(32)         not null,
    config_Id            VARCHAR2(32),
    name                 VARCHAR2(50),
    code                 VARCHAR2(50),
    condition_Type       VARCHAR2(50),
    property_Name        VARCHAR2(50),
    operation            VARCHAR2(32),
    param_Code           VARCHAR2(100),
    value_Type           VARCHAR2(200),
    scope_Type           VARCHAR2(32),
    scope_Value          VARCHAR2(500),
    is_Apply_Add         CHAR(1),
    is_Apply_Update      CHAR(1),
    is_Apply_Delete      CHAR(1),
    is_Apply_Query       CHAR(1),
    is_enabled           CHAR(1),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_DAM_SCOPE_CONDITION primary key (ID)
);

comment on column T_DAM_SCOPE_CONDITION.is_enabled is
'是否启用';

comment on column T_DAM_SCOPE_CONDITION.memo is
'备注';

comment on column T_DAM_SCOPE_CONDITION.sort_num is
'排序';

comment on column T_DAM_SCOPE_CONDITION.create_time is
'创建时间';

comment on column T_DAM_SCOPE_CONDITION.create_user_name is
'创建人名称';

comment on column T_DAM_SCOPE_CONDITION.update_time is
'修改时间';

comment on column T_DAM_SCOPE_CONDITION.update_user_name is
'修改人名称';
