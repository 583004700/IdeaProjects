create table if not exists ACT_EVT_LOG
(
	LOG_NR_ bigint auto_increment
		primary key,
	TYPE_ varchar(64) null,
	PROC_DEF_ID_ varchar(64) null,
	PROC_INST_ID_ varchar(64) null,
	EXECUTION_ID_ varchar(64) null,
	TASK_ID_ varchar(64) null,
	TIME_STAMP_ timestamp(3) not null,
	USER_ID_ varchar(255) null,
	DATA_ longblob null,
	LOCK_OWNER_ varchar(255) null,
	LOCK_TIME_ timestamp(3) null,
	IS_PROCESSED_ tinyint default 0 null
)
collate=utf8_bin;

create table if not exists ACT_GE_PROPERTY
(
	NAME_ varchar(64) not null
		primary key,
	VALUE_ varchar(300) null,
	REV_ int null
)
collate=utf8_bin;

create table if not exists ACT_HI_ACTINST
(
	ID_ varchar(64) not null
		primary key,
	PROC_DEF_ID_ varchar(64) not null,
	PROC_INST_ID_ varchar(64) not null,
	EXECUTION_ID_ varchar(64) not null,
	ACT_ID_ varchar(255) not null,
	TASK_ID_ varchar(64) null,
	CALL_PROC_INST_ID_ varchar(64) null,
	ACT_NAME_ varchar(255) null,
	ACT_TYPE_ varchar(255) not null,
	ASSIGNEE_ varchar(255) null,
	START_TIME_ datetime(3) not null,
	END_TIME_ datetime(3) null,
	DURATION_ bigint null,
	DELETE_REASON_ varchar(4000) null,
	TENANT_ID_ varchar(255) default '' null
)
collate=utf8_bin;

create index ACT_IDX_HI_ACT_INST_END
	on ACT_HI_ACTINST (END_TIME_);

create index ACT_IDX_HI_ACT_INST_EXEC
	on ACT_HI_ACTINST (EXECUTION_ID_, ACT_ID_);

create index ACT_IDX_HI_ACT_INST_PROCINST
	on ACT_HI_ACTINST (PROC_INST_ID_, ACT_ID_);

create index ACT_IDX_HI_ACT_INST_START
	on ACT_HI_ACTINST (START_TIME_);

create table if not exists ACT_HI_ATTACHMENT
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	USER_ID_ varchar(255) null,
	NAME_ varchar(255) null,
	DESCRIPTION_ varchar(4000) null,
	TYPE_ varchar(255) null,
	TASK_ID_ varchar(64) null,
	PROC_INST_ID_ varchar(64) null,
	URL_ varchar(4000) null,
	CONTENT_ID_ varchar(64) null,
	TIME_ datetime(3) null
)
collate=utf8_bin;

create table if not exists ACT_HI_COMMENT
(
	ID_ varchar(64) not null
		primary key,
	TYPE_ varchar(255) null,
	TIME_ datetime(3) not null,
	USER_ID_ varchar(255) null,
	TASK_ID_ varchar(64) null,
	PROC_INST_ID_ varchar(64) null,
	ACTION_ varchar(255) null,
	MESSAGE_ varchar(4000) null,
	FULL_MSG_ longblob null
)
collate=utf8_bin;

create table if not exists ACT_HI_DETAIL
(
	ID_ varchar(64) not null
		primary key,
	TYPE_ varchar(255) not null,
	PROC_INST_ID_ varchar(64) null,
	EXECUTION_ID_ varchar(64) null,
	TASK_ID_ varchar(64) null,
	ACT_INST_ID_ varchar(64) null,
	NAME_ varchar(255) not null,
	VAR_TYPE_ varchar(255) null,
	REV_ int null,
	TIME_ datetime(3) not null,
	BYTEARRAY_ID_ varchar(64) null,
	DOUBLE_ double null,
	LONG_ bigint null,
	TEXT_ varchar(4000) null,
	TEXT2_ varchar(4000) null
)
collate=utf8_bin;

create index ACT_IDX_HI_DETAIL_ACT_INST
	on ACT_HI_DETAIL (ACT_INST_ID_);

create index ACT_IDX_HI_DETAIL_NAME
	on ACT_HI_DETAIL (NAME_);

create index ACT_IDX_HI_DETAIL_PROC_INST
	on ACT_HI_DETAIL (PROC_INST_ID_);

create index ACT_IDX_HI_DETAIL_TASK_ID
	on ACT_HI_DETAIL (TASK_ID_);

create index ACT_IDX_HI_DETAIL_TIME
	on ACT_HI_DETAIL (TIME_);

create table if not exists ACT_HI_IDENTITYLINK
(
	ID_ varchar(64) not null
		primary key,
	GROUP_ID_ varchar(255) null,
	TYPE_ varchar(255) null,
	USER_ID_ varchar(255) null,
	TASK_ID_ varchar(64) null,
	PROC_INST_ID_ varchar(64) null
)
collate=utf8_bin;

create index ACT_IDX_HI_IDENT_LNK_PROCINST
	on ACT_HI_IDENTITYLINK (PROC_INST_ID_);

create index ACT_IDX_HI_IDENT_LNK_TASK
	on ACT_HI_IDENTITYLINK (TASK_ID_);

create index ACT_IDX_HI_IDENT_LNK_USER
	on ACT_HI_IDENTITYLINK (USER_ID_);

create table if not exists ACT_HI_PROCINST
(
	ID_ varchar(64) not null
		primary key,
	PROC_INST_ID_ varchar(64) not null,
	BUSINESS_KEY_ varchar(255) null,
	PROC_DEF_ID_ varchar(64) not null,
	START_TIME_ datetime(3) not null,
	END_TIME_ datetime(3) null,
	DURATION_ bigint null,
	START_USER_ID_ varchar(255) null,
	START_ACT_ID_ varchar(255) null,
	END_ACT_ID_ varchar(255) null,
	SUPER_PROCESS_INSTANCE_ID_ varchar(64) null,
	DELETE_REASON_ varchar(4000) null,
	TENANT_ID_ varchar(255) default '' null,
	NAME_ varchar(255) null,
	constraint PROC_INST_ID_
		unique (PROC_INST_ID_)
)
collate=utf8_bin;

create index ACT_IDX_HI_PRO_INST_END
	on ACT_HI_PROCINST (END_TIME_);

create index ACT_IDX_HI_PRO_I_BUSKEY
	on ACT_HI_PROCINST (BUSINESS_KEY_);

create table if not exists ACT_HI_TASKINST
(
	ID_ varchar(64) not null
		primary key,
	PROC_DEF_ID_ varchar(64) null,
	TASK_DEF_KEY_ varchar(255) null,
	PROC_INST_ID_ varchar(64) null,
	EXECUTION_ID_ varchar(64) null,
	NAME_ varchar(255) null,
	PARENT_TASK_ID_ varchar(64) null,
	DESCRIPTION_ varchar(4000) null,
	OWNER_ varchar(255) null,
	ASSIGNEE_ varchar(255) null,
	START_TIME_ datetime(3) not null,
	CLAIM_TIME_ datetime(3) null,
	END_TIME_ datetime(3) null,
	DURATION_ bigint null,
	DELETE_REASON_ varchar(4000) null,
	PRIORITY_ int null,
	DUE_DATE_ datetime(3) null,
	FORM_KEY_ varchar(255) null,
	CATEGORY_ varchar(255) null,
	TENANT_ID_ varchar(255) default '' null
)
collate=utf8_bin;

create index ACT_IDX_HI_TASK_INST_PROCINST
	on ACT_HI_TASKINST (PROC_INST_ID_);

create table if not exists ACT_HI_VARINST
(
	ID_ varchar(64) not null
		primary key,
	PROC_INST_ID_ varchar(64) null,
	EXECUTION_ID_ varchar(64) null,
	TASK_ID_ varchar(64) null,
	NAME_ varchar(255) not null,
	VAR_TYPE_ varchar(100) null,
	REV_ int null,
	BYTEARRAY_ID_ varchar(64) null,
	DOUBLE_ double null,
	LONG_ bigint null,
	TEXT_ varchar(4000) null,
	TEXT2_ varchar(4000) null,
	CREATE_TIME_ datetime(3) null,
	LAST_UPDATED_TIME_ datetime(3) null
)
collate=utf8_bin;

create index ACT_IDX_HI_PROCVAR_NAME_TYPE
	on ACT_HI_VARINST (NAME_, VAR_TYPE_);

create index ACT_IDX_HI_PROCVAR_PROC_INST
	on ACT_HI_VARINST (PROC_INST_ID_);

create index ACT_IDX_HI_PROCVAR_TASK_ID
	on ACT_HI_VARINST (TASK_ID_);

create table if not exists ACT_RE_DEPLOYMENT
(
	ID_ varchar(64) not null
		primary key,
	NAME_ varchar(255) null,
	CATEGORY_ varchar(255) null,
	KEY_ varchar(255) null,
	TENANT_ID_ varchar(255) default '' null,
	DEPLOY_TIME_ timestamp(3) null,
	ENGINE_VERSION_ varchar(255) null
)
collate=utf8_bin;

create table if not exists ACT_GE_BYTEARRAY
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	NAME_ varchar(255) null,
	DEPLOYMENT_ID_ varchar(64) null,
	BYTES_ longblob null,
	GENERATED_ tinyint null,
	constraint ACT_FK_BYTEARR_DEPL
		foreign key (DEPLOYMENT_ID_) references ACT_RE_DEPLOYMENT (ID_)
)
collate=utf8_bin;

create table if not exists ACT_RE_MODEL
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	NAME_ varchar(255) null,
	KEY_ varchar(255) null,
	CATEGORY_ varchar(255) null,
	CREATE_TIME_ timestamp(3) null,
	LAST_UPDATE_TIME_ timestamp(3) null,
	VERSION_ int null,
	META_INFO_ varchar(4000) null,
	DEPLOYMENT_ID_ varchar(64) null,
	EDITOR_SOURCE_VALUE_ID_ varchar(64) null,
	EDITOR_SOURCE_EXTRA_VALUE_ID_ varchar(64) null,
	TENANT_ID_ varchar(255) default '' null,
	constraint ACT_FK_MODEL_DEPLOYMENT
		foreign key (DEPLOYMENT_ID_) references ACT_RE_DEPLOYMENT (ID_),
	constraint ACT_FK_MODEL_SOURCE
		foreign key (EDITOR_SOURCE_VALUE_ID_) references ACT_GE_BYTEARRAY (ID_),
	constraint ACT_FK_MODEL_SOURCE_EXTRA
		foreign key (EDITOR_SOURCE_EXTRA_VALUE_ID_) references ACT_GE_BYTEARRAY (ID_)
)
collate=utf8_bin;

create table if not exists ACT_RE_PROCDEF
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	CATEGORY_ varchar(255) null,
	NAME_ varchar(255) null,
	KEY_ varchar(255) not null,
	VERSION_ int not null,
	DEPLOYMENT_ID_ varchar(64) null,
	RESOURCE_NAME_ varchar(4000) null,
	DGRM_RESOURCE_NAME_ varchar(4000) null,
	DESCRIPTION_ varchar(4000) null,
	HAS_START_FORM_KEY_ tinyint null,
	HAS_GRAPHICAL_NOTATION_ tinyint null,
	SUSPENSION_STATE_ int null,
	TENANT_ID_ varchar(255) default '' null,
	ENGINE_VERSION_ varchar(255) null,
	constraint ACT_UNIQ_PROCDEF
		unique (KEY_, VERSION_, TENANT_ID_)
)
collate=utf8_bin;

create table if not exists ACT_PROCDEF_INFO
(
	ID_ varchar(64) not null
		primary key,
	PROC_DEF_ID_ varchar(64) not null,
	REV_ int null,
	INFO_JSON_ID_ varchar(64) null,
	constraint ACT_UNIQ_INFO_PROCDEF
		unique (PROC_DEF_ID_),
	constraint ACT_FK_INFO_JSON_BA
		foreign key (INFO_JSON_ID_) references ACT_GE_BYTEARRAY (ID_),
	constraint ACT_FK_INFO_PROCDEF
		foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_)
)
collate=utf8_bin;

create index ACT_IDX_INFO_PROCDEF
	on ACT_PROCDEF_INFO (PROC_DEF_ID_);

create table if not exists ACT_RU_EXECUTION
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	PROC_INST_ID_ varchar(64) null,
	BUSINESS_KEY_ varchar(255) null,
	PARENT_ID_ varchar(64) null,
	PROC_DEF_ID_ varchar(64) null,
	SUPER_EXEC_ varchar(64) null,
	ROOT_PROC_INST_ID_ varchar(64) null,
	ACT_ID_ varchar(255) null,
	IS_ACTIVE_ tinyint null,
	IS_CONCURRENT_ tinyint null,
	IS_SCOPE_ tinyint null,
	IS_EVENT_SCOPE_ tinyint null,
	IS_MI_ROOT_ tinyint null,
	SUSPENSION_STATE_ int null,
	CACHED_ENT_STATE_ int null,
	TENANT_ID_ varchar(255) default '' null,
	NAME_ varchar(255) null,
	START_TIME_ datetime(3) null,
	START_USER_ID_ varchar(255) null,
	LOCK_TIME_ timestamp(3) null,
	IS_COUNT_ENABLED_ tinyint null,
	EVT_SUBSCR_COUNT_ int null,
	TASK_COUNT_ int null,
	JOB_COUNT_ int null,
	TIMER_JOB_COUNT_ int null,
	SUSP_JOB_COUNT_ int null,
	DEADLETTER_JOB_COUNT_ int null,
	VAR_COUNT_ int null,
	ID_LINK_COUNT_ int null,
	constraint ACT_FK_EXE_PARENT
		foreign key (PARENT_ID_) references ACT_RU_EXECUTION (ID_)
			on delete cascade,
	constraint ACT_FK_EXE_PROCDEF
		foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_),
	constraint ACT_FK_EXE_PROCINST
		foreign key (PROC_INST_ID_) references ACT_RU_EXECUTION (ID_)
			on update cascade on delete cascade,
	constraint ACT_FK_EXE_SUPER
		foreign key (SUPER_EXEC_) references ACT_RU_EXECUTION (ID_)
			on delete cascade
)
collate=utf8_bin;

create table if not exists ACT_RU_DEADLETTER_JOB
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	TYPE_ varchar(255) not null,
	EXCLUSIVE_ tinyint(1) null,
	EXECUTION_ID_ varchar(64) null,
	PROCESS_INSTANCE_ID_ varchar(64) null,
	PROC_DEF_ID_ varchar(64) null,
	EXCEPTION_STACK_ID_ varchar(64) null,
	EXCEPTION_MSG_ varchar(4000) null,
	DUEDATE_ timestamp(3) null,
	REPEAT_ varchar(255) null,
	HANDLER_TYPE_ varchar(255) null,
	HANDLER_CFG_ varchar(4000) null,
	TENANT_ID_ varchar(255) default '' null,
	constraint ACT_FK_DEADLETTER_JOB_EXCEPTION
		foreign key (EXCEPTION_STACK_ID_) references ACT_GE_BYTEARRAY (ID_),
	constraint ACT_FK_DEADLETTER_JOB_EXECUTION
		foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE
		foreign key (PROCESS_INSTANCE_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_DEADLETTER_JOB_PROC_DEF
		foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_)
)
collate=utf8_bin;

create table if not exists ACT_RU_EVENT_SUBSCR
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	EVENT_TYPE_ varchar(255) not null,
	EVENT_NAME_ varchar(255) null,
	EXECUTION_ID_ varchar(64) null,
	PROC_INST_ID_ varchar(64) null,
	ACTIVITY_ID_ varchar(64) null,
	CONFIGURATION_ varchar(255) null,
	CREATED_ timestamp(3) default CURRENT_TIMESTAMP(3) not null,
	PROC_DEF_ID_ varchar(64) null,
	TENANT_ID_ varchar(255) default '' null,
	constraint ACT_FK_EVENT_EXEC
		foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION (ID_)
)
collate=utf8_bin;

create index ACT_IDX_EVENT_SUBSCR_CONFIG_
	on ACT_RU_EVENT_SUBSCR (CONFIGURATION_);

create index ACT_IDC_EXEC_ROOT
	on ACT_RU_EXECUTION (ROOT_PROC_INST_ID_);

create index ACT_IDX_EXEC_BUSKEY
	on ACT_RU_EXECUTION (BUSINESS_KEY_);

create table if not exists ACT_RU_INTEGRATION
(
	ID_ varchar(64) not null
		primary key,
	EXECUTION_ID_ varchar(64) null,
	PROCESS_INSTANCE_ID_ varchar(64) null,
	PROC_DEF_ID_ varchar(64) null,
	FLOW_NODE_ID_ varchar(64) null,
	CREATED_DATE_ timestamp(3) null,
	constraint ACT_FK_INT_EXECUTION
		foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION (ID_)
			on delete cascade,
	constraint ACT_FK_INT_PROC_DEF
		foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_),
	constraint ACT_FK_INT_PROC_INST
		foreign key (PROCESS_INSTANCE_ID_) references ACT_RU_EXECUTION (ID_)
)
collate=utf8_bin;

create table if not exists ACT_RU_JOB
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	TYPE_ varchar(255) not null,
	LOCK_EXP_TIME_ timestamp(3) null,
	LOCK_OWNER_ varchar(255) null,
	EXCLUSIVE_ tinyint(1) null,
	EXECUTION_ID_ varchar(64) null,
	PROCESS_INSTANCE_ID_ varchar(64) null,
	PROC_DEF_ID_ varchar(64) null,
	RETRIES_ int null,
	EXCEPTION_STACK_ID_ varchar(64) null,
	EXCEPTION_MSG_ varchar(4000) null,
	DUEDATE_ timestamp(3) null,
	REPEAT_ varchar(255) null,
	HANDLER_TYPE_ varchar(255) null,
	HANDLER_CFG_ varchar(4000) null,
	TENANT_ID_ varchar(255) default '' null,
	constraint ACT_FK_JOB_EXCEPTION
		foreign key (EXCEPTION_STACK_ID_) references ACT_GE_BYTEARRAY (ID_),
	constraint ACT_FK_JOB_EXECUTION
		foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_JOB_PROCESS_INSTANCE
		foreign key (PROCESS_INSTANCE_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_JOB_PROC_DEF
		foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_)
)
collate=utf8_bin;

create table if not exists ACT_RU_SUSPENDED_JOB
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	TYPE_ varchar(255) not null,
	EXCLUSIVE_ tinyint(1) null,
	EXECUTION_ID_ varchar(64) null,
	PROCESS_INSTANCE_ID_ varchar(64) null,
	PROC_DEF_ID_ varchar(64) null,
	RETRIES_ int null,
	EXCEPTION_STACK_ID_ varchar(64) null,
	EXCEPTION_MSG_ varchar(4000) null,
	DUEDATE_ timestamp(3) null,
	REPEAT_ varchar(255) null,
	HANDLER_TYPE_ varchar(255) null,
	HANDLER_CFG_ varchar(4000) null,
	TENANT_ID_ varchar(255) default '' null,
	constraint ACT_FK_SUSPENDED_JOB_EXCEPTION
		foreign key (EXCEPTION_STACK_ID_) references ACT_GE_BYTEARRAY (ID_),
	constraint ACT_FK_SUSPENDED_JOB_EXECUTION
		foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE
		foreign key (PROCESS_INSTANCE_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_SUSPENDED_JOB_PROC_DEF
		foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_)
)
collate=utf8_bin;

create table if not exists ACT_RU_TASK
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	EXECUTION_ID_ varchar(64) null,
	PROC_INST_ID_ varchar(64) null,
	PROC_DEF_ID_ varchar(64) null,
	NAME_ varchar(255) null,
	PARENT_TASK_ID_ varchar(64) null,
	DESCRIPTION_ varchar(4000) null,
	TASK_DEF_KEY_ varchar(255) null,
	OWNER_ varchar(255) null,
	ASSIGNEE_ varchar(255) null,
	DELEGATION_ varchar(64) null,
	PRIORITY_ int null,
	CREATE_TIME_ timestamp(3) null,
	DUE_DATE_ datetime(3) null,
	CATEGORY_ varchar(255) null,
	SUSPENSION_STATE_ int null,
	TENANT_ID_ varchar(255) default '' null,
	FORM_KEY_ varchar(255) null,
	CLAIM_TIME_ datetime(3) null,
	constraint ACT_FK_TASK_EXE
		foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_TASK_PROCDEF
		foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_),
	constraint ACT_FK_TASK_PROCINST
		foreign key (PROC_INST_ID_) references ACT_RU_EXECUTION (ID_)
)
collate=utf8_bin;

create table if not exists ACT_RU_IDENTITYLINK
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	GROUP_ID_ varchar(255) null,
	TYPE_ varchar(255) null,
	USER_ID_ varchar(255) null,
	TASK_ID_ varchar(64) null,
	PROC_INST_ID_ varchar(64) null,
	PROC_DEF_ID_ varchar(64) null,
	constraint ACT_FK_ATHRZ_PROCEDEF
		foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_),
	constraint ACT_FK_IDL_PROCINST
		foreign key (PROC_INST_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_TSKASS_TASK
		foreign key (TASK_ID_) references ACT_RU_TASK (ID_)
)
collate=utf8_bin;

create index ACT_IDX_ATHRZ_PROCEDEF
	on ACT_RU_IDENTITYLINK (PROC_DEF_ID_);

create index ACT_IDX_IDENT_LNK_GROUP
	on ACT_RU_IDENTITYLINK (GROUP_ID_);

create index ACT_IDX_IDENT_LNK_USER
	on ACT_RU_IDENTITYLINK (USER_ID_);

create index ACT_IDX_TASK_CREATE
	on ACT_RU_TASK (CREATE_TIME_);

create table if not exists ACT_RU_TIMER_JOB
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	TYPE_ varchar(255) not null,
	LOCK_EXP_TIME_ timestamp(3) null,
	LOCK_OWNER_ varchar(255) null,
	EXCLUSIVE_ tinyint(1) null,
	EXECUTION_ID_ varchar(64) null,
	PROCESS_INSTANCE_ID_ varchar(64) null,
	PROC_DEF_ID_ varchar(64) null,
	RETRIES_ int null,
	EXCEPTION_STACK_ID_ varchar(64) null,
	EXCEPTION_MSG_ varchar(4000) null,
	DUEDATE_ timestamp(3) null,
	REPEAT_ varchar(255) null,
	HANDLER_TYPE_ varchar(255) null,
	HANDLER_CFG_ varchar(4000) null,
	TENANT_ID_ varchar(255) default '' null,
	constraint ACT_FK_TIMER_JOB_EXCEPTION
		foreign key (EXCEPTION_STACK_ID_) references ACT_GE_BYTEARRAY (ID_),
	constraint ACT_FK_TIMER_JOB_EXECUTION
		foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_TIMER_JOB_PROCESS_INSTANCE
		foreign key (PROCESS_INSTANCE_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_TIMER_JOB_PROC_DEF
		foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_)
)
collate=utf8_bin;

create table if not exists ACT_RU_VARIABLE
(
	ID_ varchar(64) not null
		primary key,
	REV_ int null,
	TYPE_ varchar(255) not null,
	NAME_ varchar(255) not null,
	EXECUTION_ID_ varchar(64) null,
	PROC_INST_ID_ varchar(64) null,
	TASK_ID_ varchar(64) null,
	BYTEARRAY_ID_ varchar(64) null,
	DOUBLE_ double null,
	LONG_ bigint null,
	TEXT_ varchar(4000) null,
	TEXT2_ varchar(4000) null,
	constraint ACT_FK_VAR_BYTEARRAY
		foreign key (BYTEARRAY_ID_) references ACT_GE_BYTEARRAY (ID_),
	constraint ACT_FK_VAR_EXE
		foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION (ID_),
	constraint ACT_FK_VAR_PROCINST
		foreign key (PROC_INST_ID_) references ACT_RU_EXECUTION (ID_)
)
collate=utf8_bin;

create index ACT_IDX_VARIABLE_TASK_ID
	on ACT_RU_VARIABLE (TASK_ID_);

