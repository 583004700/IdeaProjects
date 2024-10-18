INSERT INTO sys_menu (pid, name, url, authority, type, open_style, icon, sort, version, deleted, creator, create_time, updater, update_time) VALUES (33, '代码生成器', '{{apiUrl}}/maku-generator/index.html', '', 0, 0, 'icon-rocket', 2, 0, 0, 10000, now(), 10000, now());

CREATE TABLE gen_datasource (
    id bigint IDENTITY NOT NULL,
    db_type varchar(200),
    conn_name varchar(200),
    conn_url varchar(500),
    username varchar(200),
    password varchar(200),
    create_time datetime,
    primary key (id)
);

COMMENT ON TABLE gen_datasource IS '数据源管理';
COMMENT ON COLUMN gen_datasource.id IS 'id';
COMMENT ON COLUMN gen_datasource.db_type IS '数据库类型';
COMMENT ON COLUMN gen_datasource.conn_name IS '连接名';
COMMENT ON COLUMN gen_datasource.conn_url IS 'URL';
COMMENT ON COLUMN gen_datasource.username IS '用户名';
COMMENT ON COLUMN gen_datasource.password IS '密码';
COMMENT ON COLUMN gen_datasource.create_time IS '创建时间';

CREATE TABLE gen_config
(
    id             bigint IDENTITY NOT NULL,
    config_key     varchar(200),
    config_value   varchar(2000),
    primary key (id)
);

CREATE TABLE gen_field_type
(
    id           bigint IDENTITY NOT NULL,
    column_type  varchar(200),
    attr_type    varchar(200),
    package_name varchar(200),
    create_time  datetime,
    primary key (id)
);

CREATE UNIQUE INDEX gen_column_type on gen_field_type(column_type);

COMMENT ON TABLE gen_field_type IS '字段类型管理';
COMMENT ON COLUMN gen_field_type.id IS 'id';
COMMENT ON COLUMN gen_field_type.column_type IS '字段类型';
COMMENT ON COLUMN gen_field_type.attr_type IS '属性类型';
COMMENT ON COLUMN gen_field_type.package_name IS '属性包名';
COMMENT ON COLUMN gen_field_type.create_time IS '创建时间';


CREATE TABLE gen_base_class
(
    id           bigint IDENTITY NOT NULL,
    package_name varchar(200),
    code         varchar(200),
    fields       varchar(500),
    remark       varchar(200),
    create_time  datetime,
    primary key (id)
);

COMMENT ON TABLE gen_base_class IS '基类管理';
COMMENT ON COLUMN gen_base_class.id IS 'id';
COMMENT ON COLUMN gen_base_class.package_name IS '基类包名';
COMMENT ON COLUMN gen_base_class.code IS '基类编码';
COMMENT ON COLUMN gen_base_class.fields IS '基类字段，多个用英文逗号分隔';
COMMENT ON COLUMN gen_base_class.remark IS '备注';
COMMENT ON COLUMN gen_base_class.create_time IS '创建时间';

CREATE TABLE gen_table
(
    id             bigint IDENTITY NOT NULL,
    table_name     varchar(200),
    class_name     varchar(200),
    table_comment  varchar(200),
    author         varchar(200),
    email          varchar(200),
    package_name   varchar(200),
    version        varchar(200),
    generator_type int,
    backend_path   varchar(500),
    frontend_path  varchar(500),
    module_name    varchar(200),
    function_name  varchar(200),
    form_layout    int,
    table_type       int,
    sub_table        varchar(4000),
    table_operation  varchar(200),
    auth_level       int,
    open_type        int,
    request_url      varchar(200),
    authority        varchar(200),
    tree_id          varchar(200),
    tree_pid         varchar(200),
    tree_label       varchar(200),
    left_title          varchar(200),
    left_from           int,
    left_table_name     varchar(200),
    left_url            varchar(200),
    left_relation_field varchar(200),
    datasource_id  bigint,
    baseclass_id   bigint,
    create_time    datetime,
    primary key (id)
);
CREATE UNIQUE INDEX gen_table_name on gen_table(table_name);

COMMENT ON TABLE gen_table IS '代码生成表';
COMMENT ON COLUMN gen_table.id IS 'id';
COMMENT ON COLUMN gen_table.table_name IS '表名';
COMMENT ON COLUMN gen_table.class_name IS '类名';
COMMENT ON COLUMN gen_table.table_comment IS '说明';
COMMENT ON COLUMN gen_table.author IS '作者';
COMMENT ON COLUMN gen_table.email IS '邮箱';
COMMENT ON COLUMN gen_table.package_name IS '项目包名';
COMMENT ON COLUMN gen_table.version IS '项目版本号';
COMMENT ON COLUMN gen_table.generator_type IS '生成方式  0：zip压缩包   1：自定义目录';
COMMENT ON COLUMN gen_table.backend_path IS '后端生成路径';
COMMENT ON COLUMN gen_table.frontend_path IS '前端生成路径';
COMMENT ON COLUMN gen_table.module_name IS '模块名';
COMMENT ON COLUMN gen_table.function_name IS '功能名';
COMMENT ON COLUMN gen_table.form_layout IS '表单布局  1：一列   2：两列';
COMMENT ON COLUMN gen_table.datasource_id IS '数据源ID';
COMMENT ON COLUMN gen_table.baseclass_id IS '基类ID';
COMMENT ON COLUMN gen_table.create_time IS '创建时间';


CREATE TABLE gen_table_field
(
    id              bigint IDENTITY NOT NULL,
    table_id        bigint,
    field_name      varchar(200),
    field_type      varchar(200),
    field_comment   varchar(200),
    attr_name       varchar(200),
    attr_type       varchar(200),
    package_name    varchar(200),
    sort            int,
    auto_fill       varchar(20),
    primary_pk      bit,
    base_field      bit,
    form_item       bit,
    form_required   bit,
    form_type       varchar(200),
    form_dict       varchar(200),
    form_validator  varchar(200),
    grid_item       bit,
    grid_sort       bit,
    query_item      bit,
    query_type      varchar(200),
    query_form_type varchar(200),
    create_time     datetime,
    primary key (id)
);

COMMENT ON TABLE gen_table_field IS '代码生成表字段';
COMMENT ON COLUMN gen_table_field.id IS 'id';
COMMENT ON COLUMN gen_table_field.table_id IS '表ID';
COMMENT ON COLUMN gen_table_field.field_name IS '字段名称';
COMMENT ON COLUMN gen_table_field.field_type IS '字段类型';
COMMENT ON COLUMN gen_table_field.field_comment IS '字段说明';
COMMENT ON COLUMN gen_table_field.attr_name IS '属性名';
COMMENT ON COLUMN gen_table_field.attr_type IS '属性类型';
COMMENT ON COLUMN gen_table_field.package_name IS '属性包名';
COMMENT ON COLUMN gen_table_field.sort IS '排序';
COMMENT ON COLUMN gen_table_field.auto_fill IS '自动填充  DEFAULT、INSERT、UPDATE、INSERT_UPDATE';
COMMENT ON COLUMN gen_table_field.primary_pk IS '主键 0：否  1：是';
COMMENT ON COLUMN gen_table_field.base_field IS '基类字段 0：否  1：是';
COMMENT ON COLUMN gen_table_field.form_item IS '表单项 0：否  1：是';
COMMENT ON COLUMN gen_table_field.form_required IS '表单必填 0：否  1：是';
COMMENT ON COLUMN gen_table_field.form_type IS '表单类型';
COMMENT ON COLUMN gen_table_field.form_dict IS '表单字典类型';
COMMENT ON COLUMN gen_table_field.form_validator IS '表单效验';
COMMENT ON COLUMN gen_table_field.grid_item IS '列表项 0：否  1：是';
COMMENT ON COLUMN gen_table_field.grid_sort IS '列表排序 0：否  1：是';
COMMENT ON COLUMN gen_table_field.query_item IS '查询项 0：否  1：是';
COMMENT ON COLUMN gen_table_field.query_type IS '查询方式';
COMMENT ON COLUMN gen_table_field.query_form_type IS '查询表单类型';


CREATE TABLE gen_project_modify
(
    id                     bigint IDENTITY NOT NULL,
    project_name           varchar(100),
    project_code           varchar(100),
    project_package        varchar(100),
    project_path           varchar(200),
    modify_project_name    varchar(100),
    modify_project_code    varchar(100),
    modify_project_package varchar(100),
    exclusions             varchar(200),
    modify_suffix          varchar(200),
    modify_tmp_path        varchar(100),
    create_time            datetime,
    PRIMARY KEY (id)
);

COMMENT ON TABLE gen_project_modify IS '项目名变更';
COMMENT ON COLUMN gen_project_modify.id IS 'id';
COMMENT ON COLUMN gen_project_modify.project_name IS '项目名';
COMMENT ON COLUMN gen_project_modify.project_code IS '项目标识';
COMMENT ON COLUMN gen_project_modify.project_package IS '项目包名';
COMMENT ON COLUMN gen_project_modify.project_path IS '项目路径';
COMMENT ON COLUMN gen_project_modify.modify_project_name IS '变更项目名';
COMMENT ON COLUMN gen_project_modify.modify_project_code IS '变更标识';
COMMENT ON COLUMN gen_project_modify.modify_project_package IS '变更包名';
COMMENT ON COLUMN gen_project_modify.exclusions IS '排除文件';
COMMENT ON COLUMN gen_project_modify.modify_suffix IS '变更文件';
COMMENT ON COLUMN gen_project_modify.modify_tmp_path IS '变更临时路径';
COMMENT ON COLUMN gen_project_modify.create_time IS '创建时间';


-- 用于测试代码生成器的表结构 --
CREATE TABLE gen_test_member
(
    id          bigint IDENTITY NOT NULL,
    name        varchar(50),
    gender      int,
    age         int,
    tenant_id   bigint,
    create_time datetime,
    PRIMARY KEY (id)
);

COMMENT ON TABLE gen_test_member IS '单表测试';
COMMENT ON COLUMN gen_test_member.id IS 'id';
COMMENT ON COLUMN gen_test_member.name IS '姓名';
COMMENT ON COLUMN gen_test_member.gender IS '性别';
 COMMENT ON COLUMN gen_test_member.age IS '年龄';
COMMENT ON COLUMN gen_test_member.tenant_id IS '租户ID';
COMMENT ON COLUMN gen_test_member.create_time IS '创建时间';



CREATE TABLE gen_test_tree
(
    id          bigint IDENTITY NOT NULL,
    parent_id   bigint,
    tree_name   varchar(100),
    tenant_id   bigint,
    create_time datetime,
    PRIMARY KEY (id)
);

COMMENT ON TABLE gen_test_tree IS '树表测试';
COMMENT ON COLUMN gen_test_tree.id IS 'id';
COMMENT ON COLUMN gen_test_tree.parent_id IS '上级ID';
COMMENT ON COLUMN gen_test_tree.tree_name IS '名称';
COMMENT ON COLUMN gen_test_tree.tenant_id IS '租户ID';
COMMENT ON COLUMN gen_test_tree.create_time IS '创建时间';

CREATE TABLE gen_test_product
(
    id          bigint IDENTITY NOT NULL,
    name        varchar(100),
    tenant_id   bigint,
    version     int,
    deleted     int,
    creator     bigint,
    create_time datetime,
    updater     bigint,
    update_time datetime,
    PRIMARY KEY (id)
);

COMMENT ON TABLE gen_test_product IS '产品测试';
COMMENT ON COLUMN gen_test_product.id IS 'id';
COMMENT ON COLUMN gen_test_product.name IS '名称';

CREATE TABLE gen_test_product_info
(
    id          bigint IDENTITY NOT NULL,
    images      varchar(2000),
    intro       varchar(5000),
    product_id  bigint,
    tenant_id   bigint,
    version     int,
    deleted     int,
    creator     bigint,
    create_time datetime,
    updater     bigint,
    update_time datetime,
    PRIMARY KEY (id)
);

COMMENT ON TABLE gen_test_product_info IS '产品信息';
COMMENT ON COLUMN gen_test_product_info.id IS 'id';
COMMENT ON COLUMN gen_test_product_info.images IS '图片';
COMMENT ON COLUMN gen_test_product_info.intro IS '介绍';
COMMENT ON COLUMN gen_test_product_info.product_id IS '产品ID';


CREATE TABLE gen_test_product_param
(
    id          bigint IDENTITY NOT NULL,
    param_name  varchar(200),
    param_value varchar(200),
    product_id  bigint,
    tenant_id   bigint,
    version     int,
    deleted     int,
    creator     bigint,
    create_time datetime,
    updater     bigint,
    update_time datetime,
    PRIMARY KEY (id)
);

COMMENT ON TABLE gen_test_product_param IS '产品参数';
COMMENT ON COLUMN gen_test_product_param.id IS 'id';
COMMENT ON COLUMN gen_test_product_param.param_name IS '参数名称';
COMMENT ON COLUMN gen_test_product_param.param_value IS '参数值';
COMMENT ON COLUMN gen_test_product_param.product_id IS '产品ID';

CREATE TABLE gen_test_goods_category
(
    id          bigint IDENTITY NOT NULL,
    name        varchar(100),
    pid         bigint,
    tenant_id   bigint,
    deleted     int,
    creator     bigint,
    create_time datetime,
    updater     bigint,
    update_time datetime,
    PRIMARY KEY (id)
);

COMMENT ON TABLE gen_test_goods_category IS '商品分类';
COMMENT ON COLUMN gen_test_goods_category.id IS 'id';
COMMENT ON COLUMN gen_test_goods_category.name IS '名称';
COMMENT ON COLUMN gen_test_goods_category.pid IS '上级ID';

CREATE TABLE gen_test_goods
(
    id          bigint IDENTITY NOT NULL,
    name        varchar(100),
    intro       varchar(5000),
    category_id bigint,
    tenant_id   bigint,
    deleted     int,
    creator     bigint,
    create_time datetime,
    updater     bigint,
    update_time datetime,
    PRIMARY KEY (id)
);

COMMENT ON TABLE gen_test_goods IS '商品管理';
COMMENT ON COLUMN gen_test_goods.id IS 'id';
COMMENT ON COLUMN gen_test_goods.name IS '名称';
COMMENT ON COLUMN gen_test_goods.intro IS '介绍';
COMMENT ON COLUMN gen_test_goods.category_id IS '分类ID';



INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('datetime', 'LocalDateTime', 'java.time.LocalDateTime', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('date', 'LocalDateTime', 'java.time.LocalDateTime', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('tinyint', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('smallint', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('mediumint', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('integer', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('bigint', 'Long', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('float', 'Float', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('double', 'Double', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('decimal', 'BigDecimal', 'java.math.BigDecimal', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('bit', 'Boolean', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('char', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('varchar', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('tinytext', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('text', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('mediumtext', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('longtext', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('timestamp', 'LocalDateTime', 'java.time.LocalDateTime', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('NUMBER', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('BINARY_INTEGER', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('BINARY_FLOAT', 'Float', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('BINARY_DOUBLE', 'Double', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('VARCHAR2', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('NVARCHAR', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('NVARCHAR2', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('CLOB', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int8', 'Long', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int4', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int2', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('numeric', 'BigDecimal', 'java.math.BigDecimal', now());

INSERT INTO gen_base_class (package_name, code, fields, remark, create_time) VALUES ('net.maku.framework.mybatis.entity', 'BaseEntity', 'id,creator,create_time,updater,update_time,version,deleted', '使用该基类，则需要表里有这些字段', now());

INSERT INTO gen_project_modify (project_name, project_code, project_package, project_path, modify_project_name, modify_project_code, modify_project_package, exclusions, modify_suffix, create_time) VALUES ('maku-boot', 'maku', 'net.maku', 'D:/makunet/maku-boot', 'baba-boot', 'baba', 'com.baba', '.git,.idea,target,logs', 'java,xml,yml,txt', now());
INSERT INTO gen_project_modify (project_name, project_code, project_package, project_path, modify_project_name, modify_project_code, modify_project_package, exclusions, modify_suffix, create_time) VALUES ('maku-cloud', 'maku', 'net.maku', 'D:/makunet/maku-cloud', 'baba-cloud', 'baba', 'com.baba', '.git,.idea,target,logs', 'java,xml,yml,txt', now());

INSERT INTO gen_config (config_key, config_value) VALUES ('gen_config', '');

INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (20, 'gen_test_member', 'GenTestMember', '单表测试', '阿沐', 'babamu@126.com', 'net.maku', '', 1, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'member', 1, 0, NULL, '[\"query\",\"insert\",\"update\",\"delete\",\"export\",\"import\"]', 0, 0, '/test/member', 'test:member', NULL, NULL, NULL, 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (21, 'gen_test_tree', 'GenTestTree', '树表测试', '阿沐', 'babamu@126.com', 'net.maku', '', 1, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'tree', 1, 1, NULL, '[\"query\",\"insert\",\"update\",\"delete\",\"export\"]', 0, 0, '/test/tree', 'test:tree', 'id', 'parent_id', 'tree_name', 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (22, 'gen_test_goods', 'GenTestGoods', '商品管理', '阿沐', 'babamu@126.com', 'net.maku', '', 1, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'goods', 1, 2, '[]', '[\"query\",\"insert\",\"update\",\"delete\"]', 0, 0, '/test/goods', 'test:goods', NULL, NULL, NULL, 0, NULL, now(), 0, 'gen_test_goods_category', '/test/category/list', 'category_id', '分类列表');
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (23, 'gen_test_goods_category', 'GenTestGoodsCategory', '商品分类', '阿沐', 'babamu@126.com', 'net.maku', '', 1, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'category', 1, 1, '[]', '[\"query\",\"insert\",\"update\",\"delete\"]', 0, 0, '/test/category', 'test:category', 'id', 'pid', 'name', 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (24, 'gen_test_product_info', 'GenTestProductInfo', '产品信息', '阿沐', 'babamu@126.com', 'net.maku', '', 0, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'info', 1, 0, NULL, '[\"query\",\"insert\",\"update\",\"delete\",\"export\"]', 0, 0, '/test/info', 'test:info', NULL, NULL, NULL, 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (25, 'gen_test_product_param', 'GenTestProductParam', '产品参数', '阿沐', 'babamu@126.com', 'net.maku', '', 0, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'param', 1, 0, NULL, '[\"query\",\"insert\",\"update\",\"delete\",\"export\"]', 0, 0, '/test/param', 'test:param', NULL, NULL, NULL, 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);
INSERT INTO gen_table (id, table_name, class_name, table_comment, author, email, package_name, version, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, table_type, sub_table, table_operation, auth_level, open_type, request_url, authority, tree_id, tree_pid, tree_label, datasource_id, baseclass_id, create_time, left_from, left_table_name, left_url, left_relation_field, left_title) VALUES (26, 'gen_test_product', 'GenTestProduct', '产品测试', '阿沐', 'babamu@126.com', 'net.maku', '', 1, '/Users/maku/makunet/maku-boot-enterprise/maku-boot-new', '/Users/maku/makunet/maku-admin-enterprise', 'test', 'product', 1, 0, '[{\"tableName\":\"gen_test_product_info\",\"foreignKey\":\"product_id\",\"tableTitle\":\"产品信息\",\"mainRelation\":1,\"sort\":0},{\"tableName\":\"gen_test_product_param\",\"foreignKey\":\"product_id\",\"tableTitle\":\"产品参数\",\"mainRelation\":2,\"sort\":1}]', '[\"query\",\"insert\",\"update\",\"delete\"]', 0, 1, '/test/product', 'test:product', NULL, NULL, NULL, 0, NULL, now(), NULL, NULL, NULL, NULL, NULL);

INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (220, 20, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (221, 20, 'name', 'varchar', '姓名', 'name', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (222, 20, 'gender', 'tinyint', '性别', 'gender', 'Integer', NULL, 2, 'DEFAULT', 0, 0, 1, 1, 'radio', 'user_gender', NULL, 1, 0, 1, '=', 'select', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (223, 20, 'age', 'int', '年龄', 'age', 'Integer', NULL, 3, 'DEFAULT', 0, 0, 1, 1, 'number', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (224, 20, 'tenant_id', 'bigint', '租户ID', 'tenantId', 'Long', NULL, 4, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (225, 20, 'version', 'int', '版本号', 'version', 'Integer', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (226, 20, 'deleted', 'tinyint', '删除标识', 'deleted', 'Integer', NULL, 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (227, 20, 'create_time', 'datetime', '创建时间', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 7, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (228, 21, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (229, 21, 'tree_name', 'varchar', '名称', 'treeName', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 1, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (230, 21, 'parent_id', 'bigint', '上级ID', 'parentId', 'Long', NULL, 2, 'DEFAULT', 0, 0, 1, 0, 'treeselect', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (231, 21, 'tenant_id', 'bigint', '租户ID', 'tenantId', 'Long', NULL, 3, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (232, 21, 'create_time', 'datetime', '创建时间', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 4, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 1, 0, 1, '=', 'date', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (233, 22, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (234, 22, 'name', 'varchar', '名称', 'name', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 1, 'like', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (235, 22, 'intro', 'varchar', '介绍', 'intro', 'String', NULL, 2, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (236, 22, 'category_id', 'bigint', '分类ID', 'categoryId', 'Long', NULL, 3, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (237, 22, 'tenant_id', 'bigint', '租户ID', 'tenantId', 'Long', NULL, 4, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (238, 22, 'deleted', 'tinyint', '删除标识', 'deleted', 'Integer', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (239, 22, 'creator', 'bigint', '创建者', 'creator', 'Long', NULL, 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (240, 22, 'create_time', 'datetime', '创建时间', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 7, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (241, 22, 'updater', 'bigint', '更新者', 'updater', 'Long', NULL, 8, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (242, 22, 'update_time', 'datetime', '更新时间', 'updateTime', 'LocalDateTime', 'java.time.LocalDateTime', 9, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (243, 23, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (244, 23, 'name', 'varchar', '名称', 'name', 'String', NULL, 2, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 1, 'like', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (245, 23, 'pid', 'bigint', '上级ID', 'pid', 'Long', NULL, 1, 'DEFAULT', 0, 0, 1, 0, 'treeselect', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (246, 23, 'tenant_id', 'bigint', '租户ID', 'tenantId', 'Long', NULL, 3, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (247, 23, 'deleted', 'tinyint', '删除标识', 'deleted', 'Integer', NULL, 4, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (248, 23, 'creator', 'bigint', '创建者', 'creator', 'Long', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (249, 23, 'create_time', 'datetime', '创建时间', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 1, 0, 1, '=', 'datetime', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (250, 23, 'updater', 'bigint', '更新者', 'updater', 'Long', NULL, 7, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (251, 23, 'update_time', 'datetime', '更新时间', 'updateTime', 'LocalDateTime', 'java.time.LocalDateTime', 8, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (252, 24, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (253, 24, 'images', 'varchar', '图片', 'images', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'image', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (254, 24, 'intro', 'varchar', '介绍', 'intro', 'String', NULL, 2, 'DEFAULT', 0, 0, 1, 1, 'editor', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (255, 24, 'product_id', 'bigint', '产品ID', 'productId', 'Long', NULL, 3, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (256, 24, 'tenant_id', 'bigint', '租户ID', 'tenantId', 'Long', NULL, 4, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (257, 24, 'version', 'int', '版本号', 'version', 'Integer', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (258, 24, 'deleted', 'tinyint', '删除标识', 'deleted', 'Integer', NULL, 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (259, 24, 'creator', 'bigint', '创建者', 'creator', 'Long', NULL, 7, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (260, 24, 'create_time', 'datetime', '创建时间', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 8, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (261, 24, 'updater', 'bigint', '更新者', 'updater', 'Long', NULL, 9, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (262, 24, 'update_time', 'datetime', '更新时间', 'updateTime', 'LocalDateTime', 'java.time.LocalDateTime', 10, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (263, 25, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (264, 25, 'param_name', 'varchar', '参数名称', 'paramName', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (265, 25, 'param_value', 'varchar', '参数值', 'paramValue', 'String', NULL, 2, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (266, 25, 'product_id', 'bigint', '产品ID', 'productId', 'Long', NULL, 3, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (267, 25, 'tenant_id', 'bigint', '租户ID', 'tenantId', 'Long', NULL, 4, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (268, 25, 'version', 'int', '版本号', 'version', 'Integer', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (269, 25, 'deleted', 'tinyint', '删除标识', 'deleted', 'Integer', NULL, 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (270, 25, 'creator', 'bigint', '创建者', 'creator', 'Long', NULL, 7, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (271, 25, 'create_time', 'datetime', '创建时间', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 8, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 1, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (272, 25, 'updater', 'bigint', '更新者', 'updater', 'Long', NULL, 9, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (273, 25, 'update_time', 'datetime', '更新时间', 'updateTime', 'LocalDateTime', 'java.time.LocalDateTime', 10, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (274, 26, 'id', 'bigint', 'ID', 'id', 'Long', NULL, 0, 'DEFAULT', 1, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (275, 26, 'name', 'varchar', '名称', 'name', 'String', NULL, 1, 'DEFAULT', 0, 0, 1, 1, 'input', NULL, NULL, 1, 0, 1, 'like', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (276, 26, 'tenant_id', 'bigint', '租户ID', 'tenantId', 'Long', NULL, 2, 'DEFAULT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (277, 26, 'version', 'int', '版本号', 'version', 'Integer', NULL, 3, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (278, 26, 'deleted', 'tinyint', '删除标识', 'deleted', 'Integer', NULL, 4, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (279, 26, 'creator', 'bigint', '创建者', 'creator', 'Long', NULL, 5, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (280, 26, 'create_time', 'datetime', '创建时间', 'createTime', 'LocalDateTime', 'java.time.LocalDateTime', 6, 'INSERT', 0, 0, 0, 0, 'input', NULL, NULL, 1, 0, 1, '=', 'datetime', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (281, 26, 'updater', 'bigint', '更新者', 'updater', 'Long', NULL, 7, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());
INSERT INTO gen_table_field (id, table_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, query_type, query_form_type, create_time) VALUES (282, 26, 'update_time', 'datetime', '更新时间', 'updateTime', 'LocalDateTime', 'java.time.LocalDateTime', 8, 'INSERT_UPDATE', 0, 0, 0, 0, 'input', NULL, NULL, 0, 0, 0, '=', 'input', now());

commit;