create table fund_gs
(
    id       bigint auto_increment
        primary key,
    fundcode varchar(255) null comment '基金代码',
    name     varchar(255) null comment '基金名称',
    type     varchar(255) null comment '基金类型',
    gszzl    float        null comment '估算涨跌幅',
    gztime   datetime     null comment '时间'
)
    comment '基金涨幅估算表';

