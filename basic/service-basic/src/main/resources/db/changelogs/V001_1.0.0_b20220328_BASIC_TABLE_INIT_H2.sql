--liquibase formatted sql
--changeset huangligui:basic-1 dbms:h2

/*==============================================================*/
/* Table: T_APP                                                 */
/*==============================================================*/
create table T_APP
(
    id                   VARCHAR2(32)         not null,
    name                 VARCHAR2(64),
    code                 VARCHAR2(64),
    type                 VARCHAR2(32),
    index_url            VARCHAR2(128),
    login_url            VARCHAR2(128),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_APP primary key (id)
);

comment on table T_APP is
'应用';

comment on column T_APP.id is
'id';

comment on column T_APP.name is
'应用名称';

comment on column T_APP.code is
'应用代码';

comment on column T_APP.type is
'应用类型';

comment on column T_APP.index_url is
'首页地址';

comment on column T_APP.login_url is
'认证地址';

comment on column T_APP.memo is
'备注';

comment on column T_APP.sort_num is
'排序';

comment on column T_APP.create_time is
'创建时间';

comment on column T_APP.create_user_name is
'创建人名称';

comment on column T_APP.update_time is
'修改时间';

comment on column T_APP.update_user_name is
'修改人名称';

/*==============================================================*/
/* Table: T_APP_ROLE                                            */
/*==============================================================*/
create table T_APP_ROLE
(
    id                   VARCHAR2(32)         not null,
    app_id               VARCHAR2(32),
    role_id              VARCHAR2(32),
    constraint PK_T_APP_ROLE primary key (id)
);

comment on table T_APP_ROLE is
'应用-角色';

comment on column T_APP_ROLE.id is
'id';

comment on column T_APP_ROLE.app_id is
'应用ID';

comment on column T_APP_ROLE.role_id is
'角色ID';

/*==============================================================*/
/* Table: T_CLIENT                                              */
/*==============================================================*/
create table T_CLIENT
(
    id                   VARCHAR2(32)         not null,
    name                 VARCHAR2(64),
    account              VARCHAR2(64),
    passwd               VARCHAR2(64),
    auth_types           VARCHAR2(256),
    auth_callbacks       VARCHAR2(512),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_CLIENT primary key (id)
);

comment on table T_CLIENT is
'客户端';

comment on column T_CLIENT.id is
'id';

comment on column T_CLIENT.name is
'名称';

comment on column T_CLIENT.account is
'账号';

comment on column T_CLIENT.passwd is
'密码';

comment on column T_CLIENT.auth_types is
'授权方式';

comment on column T_CLIENT.auth_callbacks is
'回调地址';

comment on column T_CLIENT.memo is
'备注';

comment on column T_CLIENT.sort_num is
'排序';

comment on column T_CLIENT.create_time is
'创建时间';

comment on column T_CLIENT.create_user_name is
'创建人名称';

comment on column T_CLIENT.update_time is
'修改时间';

comment on column T_CLIENT.update_user_name is
'修改人名称';

/*==============================================================*/
/* Table: T_CLIENT_ROLE                                         */
/*==============================================================*/
create table T_CLIENT_ROLE
(
    id                   VARCHAR2(32)         not null,
    client_id            VARCHAR2(32),
    role_id              VARCHAR2(32),
    is_auto_auth         CHAR(1),
    constraint PK_T_CLIENT_ROLE primary key (id)
);

comment on table T_CLIENT_ROLE is
'客户端-角色';

comment on column T_CLIENT_ROLE.id is
'id';

comment on column T_CLIENT_ROLE.client_id is
'客户端ID';

comment on column T_CLIENT_ROLE.role_id is
'角色ID';

comment on column T_CLIENT_ROLE.is_auto_auth is
'是否自动授权';

/*==============================================================*/
/* Table: T_DICT                                                */
/*==============================================================*/
create table T_DICT
(
    id                   VARCHAR2(32)         not null,
    app_id               VARCHAR2(32),
    parent_dict_id       VARCHAR2(32),
    name                 VARCHAR2(50),
    code                 VARCHAR2(120),
    val                  VARCHAR2(120),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_DICT primary key (id)
);

comment on table T_DICT is
'字典';

comment on column T_DICT.id is
'id';

comment on column T_DICT.app_id is
'应用';

comment on column T_DICT.parent_dict_id is
'上级id';

comment on column T_DICT.name is
'名称';

comment on column T_DICT.code is
'编码';

comment on column T_DICT.val is
'值';

comment on column T_DICT.memo is
'备注';

comment on column T_DICT.sort_num is
'排序';

comment on column T_DICT.create_time is
'创建时间';

comment on column T_DICT.create_user_name is
'创建人名称';

comment on column T_DICT.update_time is
'修改时间';

comment on column T_DICT.update_user_name is
'修改人名称';

/*==============================================================*/
/* Table: T_ORG                                                 */
/*==============================================================*/
create table T_ORG
(
    id                   VARCHAR2(32)         not null,
    name                 VARCHAR2(120),
    code                 VARCHAR2(64),
    short_name           VARCHAR2(120),
    ext1                 VARCHAR2(64),
    ext2                 VARCHAR2(64),
    ext3                 VARCHAR2(64),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_ORG primary key (id)
);

comment on table T_ORG is
'机构';

comment on column T_ORG.id is
'id';

comment on column T_ORG.name is
'名称';

comment on column T_ORG.code is
'编码';

comment on column T_ORG.short_name is
'简称';

comment on column T_ORG.ext1 is
'扩展字段1';

comment on column T_ORG.ext2 is
'扩展字段2';

comment on column T_ORG.ext3 is
'扩展字段3';

comment on column T_ORG.memo is
'备注';

comment on column T_ORG.sort_num is
'排序';

comment on column T_ORG.create_time is
'创建时间';

comment on column T_ORG.create_user_name is
'创建人名称';

comment on column T_ORG.update_time is
'修改时间';

comment on column T_ORG.update_user_name is
'修改人名称';

/*==============================================================*/
/* Table: T_ORG_ROLE                                            */
/*==============================================================*/
create table T_ORG_ROLE
(
    id                   VARCHAR2(32)         not null,
    org_id               VARCHAR2(32),
    role_id              VARCHAR2(32),
    constraint PK_T_ORG_ROLE primary key (id)
);

comment on table T_ORG_ROLE is
'机构-角色';

comment on column T_ORG_ROLE.id is
'id';

comment on column T_ORG_ROLE.org_id is
'机构ID';

comment on column T_ORG_ROLE.role_id is
'角色ID';

/*==============================================================*/
/* Table: T_ORG_TREE                                            */
/*==============================================================*/
create table T_ORG_TREE
(
    id                   VARCHAR2(32)         not null,
    org_tree_type_id     VARCHAR2(32),
    org_id               VARCHAR2(32),
    parent_org_id        VARCHAR2(32),
    tree_level           NUMBER(2),
    location_code        VARCHAR2(200),
    sort_num             NUMBER(3),
    constraint PK_T_ORG_TREE primary key (id)
);

comment on table T_ORG_TREE is
'机构树';

comment on column T_ORG_TREE.id is
'id';

comment on column T_ORG_TREE.org_tree_type_id is
'类型';

comment on column T_ORG_TREE.org_id is
'机构ID';

comment on column T_ORG_TREE.parent_org_id is
'上级机构ID';

comment on column T_ORG_TREE.tree_level is
'级别';

comment on column T_ORG_TREE.location_code is
'定位码';

comment on column T_ORG_TREE.sort_num is
'排序';

/*==============================================================*/
/* Table: T_ORG_TREE_TYPE                                       */
/*==============================================================*/
create table T_ORG_TREE_TYPE
(
    id                   VARCHAR2(32)         not null,
    name                 VARCHAR2(120),
    code                 VARCHAR2(64),
    source_id            VARCHAR2(32),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_ORG_TREE_TYPE primary key (id)
);

comment on table T_ORG_TREE_TYPE is
'机构树类型';

comment on column T_ORG_TREE_TYPE.id is
'id';

comment on column T_ORG_TREE_TYPE.name is
'名称';

comment on column T_ORG_TREE_TYPE.code is
'编码';

comment on column T_ORG_TREE_TYPE.source_id is
'来源id';

comment on column T_ORG_TREE_TYPE.memo is
'备注';

comment on column T_ORG_TREE_TYPE.sort_num is
'排序';

comment on column T_ORG_TREE_TYPE.create_time is
'创建时间';

comment on column T_ORG_TREE_TYPE.create_user_name is
'创建人名称';

comment on column T_ORG_TREE_TYPE.update_time is
'修改时间';

comment on column T_ORG_TREE_TYPE.update_user_name is
'修改人名称';

/*==============================================================*/
/* Table: T_RES                                                 */
/*==============================================================*/
create table T_RES
(
    id                   VARCHAR2(32)         not null,
    app_id               VARCHAR2(32),
    parent_res_id        VARCHAR2(32),
    name                 VARCHAR2(32),
    permission_code      VARCHAR2(64),
    icon                 VARCHAR2(128),
    url                  VARCHAR2(128),
    ype                  VARCHAR2(32),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_RES primary key (id)
);

comment on table T_RES is
'资源';

comment on column T_RES.id is
'id';

comment on column T_RES.app_id is
'所属应用';

comment on column T_RES.parent_res_id is
'上级资源';

comment on column T_RES.name is
'资源名称';

comment on column T_RES.permission_code is
'权限代码';

comment on column T_RES.icon is
'资源图标';

comment on column T_RES.url is
'资源URL';

comment on column T_RES.ype is
'资源类型';

comment on column T_RES.memo is
'备注';

comment on column T_RES.sort_num is
'排序';

comment on column T_RES.create_time is
'创建时间';

comment on column T_RES.create_user_name is
'创建人名称';

comment on column T_RES.update_time is
'修改时间';

comment on column T_RES.update_user_name is
'修改人名称';

/*==============================================================*/
/* Table: T_ROLE                                                */
/*==============================================================*/
create table T_ROLE
(
    id                   VARCHAR2(32)         not null,
    name                 VARCHAR2(64),
    code                 VARCHAR2(64),
    type                 VARCHAR2(32),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_ROLE primary key (id)
);

comment on table T_ROLE is
'角色';

comment on column T_ROLE.id is
'id';

comment on column T_ROLE.name is
'名称';

comment on column T_ROLE.code is
'编码';

comment on column T_ROLE.type is
'类型';

comment on column T_ROLE.memo is
'备注';

comment on column T_ROLE.sort_num is
'排序';

comment on column T_ROLE.create_time is
'创建时间';

comment on column T_ROLE.create_user_name is
'创建人名称';

comment on column T_ROLE.update_time is
'修改时间';

comment on column T_ROLE.update_user_name is
'修改人名称';

/*==============================================================*/
/* Table: T_ROLE_RES                                            */
/*==============================================================*/
create table T_ROLE_RES
(
    id                   VARCHAR2(32)         not null,
    res_id               VARCHAR2(32),
    role_id              VARCHAR2(32),
    constraint PK_T_ROLE_RES primary key (id)
);

comment on table T_ROLE_RES is
'角色资源';

comment on column T_ROLE_RES.id is
'id';

comment on column T_ROLE_RES.res_id is
'资源ID';

comment on column T_ROLE_RES.role_id is
'角色ID';

/*==============================================================*/
/* Table: T_SYS_CONFIG                                          */
/*==============================================================*/
create table T_SYS_CONFIG
(
    ID                   VARCHAR2(32)         not null,
    name                 VARCHAR2(64),
    code                 VARCHAR2(64),
    val                  VARCHAR2(240),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_SYS_CONFIG primary key (ID)
);

comment on table T_SYS_CONFIG is
'系统配置';

comment on column T_SYS_CONFIG.ID is
'ID';

comment on column T_SYS_CONFIG.name is
'名称';

comment on column T_SYS_CONFIG.code is
'编码';

comment on column T_SYS_CONFIG.val is
'值';

comment on column T_SYS_CONFIG.memo is
'备注';

comment on column T_SYS_CONFIG.sort_num is
'排序';

comment on column T_SYS_CONFIG.create_time is
'创建时间';

comment on column T_SYS_CONFIG.create_user_name is
'创建人名称';

comment on column T_SYS_CONFIG.update_time is
'修改时间';

comment on column T_SYS_CONFIG.update_user_name is
'修改人名称';

/*==============================================================*/
/* Table: T_USER                                                */
/*==============================================================*/
create table T_USER
(
    id                   VARCHAR2(32)         not null,
    name                 VARCHAR2(64),
    email                VARCHAR2(64),
    phone                VARCHAR2(32),
    passwd               VARCHAR2(64),
    account              VARCHAR2(32),
    org_id               VARCHAR2(32),
    is_admin             CHAR(1),
    memo                 VARCHAR2(200),
    sort_num             NUMBER(3),
    create_time          DATE,
    create_user_name     VARCHAR2(50),
    update_time          DATE,
    update_user_name     VARCHAR2(50),
    constraint PK_T_USER_ID primary key (id)
);

comment on table T_USER is
'用户';

comment on column T_USER.id is
'id';

comment on column T_USER.name is
'名称';

comment on column T_USER.email is
'邮箱';

comment on column T_USER.phone is
'电话';

comment on column T_USER.passwd is
'密码';

comment on column T_USER.account is
'账号';

comment on column T_USER.org_id is
'机构ID';

comment on column T_USER.is_admin is
'是否超级管理员';

comment on column T_USER.memo is
'备注';

comment on column T_USER.sort_num is
'排序';

comment on column T_USER.create_time is
'创建时间';

comment on column T_USER.create_user_name is
'创建人名称';

comment on column T_USER.update_time is
'修改时间';

comment on column T_USER.update_user_name is
'修改人名称';

/*==============================================================*/
/* Table: T_USER_ROLE                                           */
/*==============================================================*/
create table T_USER_ROLE
(
    id                   VARCHAR2(32)         not null,
    user_id              VARCHAR2(32),
    role_id              VARCHAR2(32),
    constraint PK_T_USER_ROLE primary key (id)
);

comment on table T_USER_ROLE is
'用户-角色';

comment on column T_USER_ROLE.id is
'id';

comment on column T_USER_ROLE.user_id is
'用户ID';

comment on column T_USER_ROLE.role_id is
'角色ID';

alter table T_APP_ROLE
    add constraint FK_T_APP_RO_REFERENCE_T_APP foreign key (role_id)
        references T_APP (id);

alter table T_APP_ROLE
    add constraint FK_T_APP_RO_REFERENCE_T_ROLE foreign key (app_id)
        references T_ROLE (id);

alter table T_CLIENT_ROLE
    add constraint FK_T_CLIENT_REFERENCE_T_ROLE foreign key (role_id)
        references T_ROLE (id);

alter table T_CLIENT_ROLE
    add constraint FK_T_CLIENT_REFERENCE_T_CLIENT foreign key (client_id)
        references T_CLIENT (id);

alter table T_DICT
    add constraint FK_T_DICT_REFERENCE_T_DICT foreign key (parent_dict_id)
        references T_DICT (id);

alter table T_DICT
    add constraint FK_T_DICT_REFERENCE_T_APP foreign key (app_id)
        references T_APP (id);

alter table T_ORG_ROLE
    add constraint FK_T_ORG_RO_REFERENCE_T_ORG foreign key (org_id)
        references T_ORG (id);

alter table T_ORG_ROLE
    add constraint FK_T_ORG_RO_REFERENCE_T_ROLE foreign key (role_id)
        references T_ROLE (id);

alter table T_ORG_TREE
    add constraint FK_T_ORG_TR_REFERENCE_T_ORG_C foreign key (org_id)
        references T_ORG (id);

alter table T_ORG_TREE
    add constraint FK_T_ORG_TR_REF_T_ORG_TR_TYPE foreign key (org_tree_type_id)
        references T_ORG_TREE_TYPE (id);

alter table T_ORG_TREE
    add constraint FK_T_ORG_TR_REFERENCE_T_ORG_P foreign key (parent_org_id)
        references T_ORG (id);

alter table T_ORG_TREE_TYPE
    add constraint FK_T_ORG_TR_REF_T_ORG_TR_P foreign key (source_id)
        references T_ORG_TREE_TYPE (id);

alter table T_RES
    add constraint FK_T_RES_REFERENCE_T_RES foreign key (parent_res_id)
        references T_RES (id);

alter table T_ROLE_RES
    add constraint FK_T_ROLE_R_REFERENCE_T_RES foreign key (res_id)
        references T_RES (id);

alter table T_ROLE_RES
    add constraint FK_T_ROLE_R_REFERENCE_T_ROLE foreign key (role_id)
        references T_ROLE (id);

alter table T_USER_ROLE
    add constraint FK_T_USER_R_REFERENCE_T_USER foreign key (user_id)
        references T_USER (id);

alter table T_USER_ROLE
    add constraint FK_T_USER_R_REFERENCE_T_ROLE foreign key (role_id)
        references T_ROLE (id);

