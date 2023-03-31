create table fund_gs
(
    id       bigint auto_increment
        primary key,
    fundcode varchar(255) not null comment '基金代码',
    name     varchar(255) null comment '基金名称',
    type     varchar(255) null comment '基金类型',
    gszzl    float        null comment '估算涨跌幅',
    gztime   char(8)      not null comment '时间'
)
    comment '基金涨幅估算表';

create unique index fund_gs_fundcode_gztime_uindex
    on fund_gs (fundcode, gztime);