CREATE database if NOT EXISTS `fund` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `fund`;

SET NAMES utf8mb4;
create table fund_gs
(
    id           bigint auto_increment
        primary key,
    fundcode     varchar(255) not null comment '基金代码',
    name         varchar(255) null comment '基金名称',
    type         varchar(255) null comment '基金类型',
    gszzl        float        null comment '估算涨跌幅',
    gzdate       char(8)      not null comment '时间',
    gztime       datetime     null comment '涨跌幅估算时间',
    updated_time datetime     null comment '数据更新时间',
    constraint fund_gs_fundcode_gztime_uindex
        unique (fundcode, gzdate)
)
    comment '基金涨幅估算表';

create table fund_a
(
    fundcode varchar(255) not null
        primary key
)
    comment '非海外的基金';

