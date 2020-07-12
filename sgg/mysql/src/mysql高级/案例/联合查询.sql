2.5.3 关联查询优化
建表SQL

CREATE TABLE IF NOT EXISTS `class` (
`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`card` INT(10) UNSIGNED NOT NULL,
PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `book` (
`bookid` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
`card` INT(10) UNSIGNED NOT NULL,
PRIMARY KEY (`bookid`)
);

INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO class(card) VALUES(FLOOR(1 + (RAND() * 20)));

INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));
INSERT INTO book(card) VALUES(FLOOR(1 + (RAND() * 20)));

案例

# 下面开始explain分析
EXPLAIN SELECT * FROM class LEFT JOIN book ON class.card = book.card;
#结论：type 有All

# 添加索引优化
ALTER TABLE `book` ADD INDEX Y ( `card`);

# 第2次explain
EXPLAIN SELECT * FROM class LEFT JOIN book ON class.card = book.card;
#可以看到第二行的 type 变为了 ref,rows 也变成了优化比较明显。
#这是由左连接特性决定的。LEFT JOIN 条件用于确定如何从右表搜索行,左边一定都有,
#所以右边是我们的关键点,一定需要建立索引。

# 删除旧索引 + 新建 + 第3次explain
DROP INDEX Y ON book;
ALTER TABLE class ADD INDEX X (card);
EXPLAIN SELECT * FROM class LEFT JOIN book ON class.card = book.card;

建议
1、保证被驱动表的join字段已经被索引
被驱动表  join 后的表为被驱动表  (需要被查询)

2、left join 时，选择小表作为驱动表，大表作为被驱动表。


但是 left join 时一定是左边是驱动表，右边是被驱动表

3、inner join 时，mysql会自己帮你把小结果集的表选为驱动表。
mysql 自动选择。小表作为驱动表。因为 驱动表无论如何都会被全表扫描？。所以扫描次数越少越好

4、子查询尽量不要放在被驱动表，有可能使用不到索引。
select a.name ,bc.name from t_emp a left join
         (select b.id , c.name from t_dept b
         inner join t_emp c on b.ceo = c.id)bc
         on bc.id = a.deptid.
上段查询中用到了子查询，必然 bc 表没有索引。肯定会进行全表扫描
上段查询 可以直接使用 两个 left join 优化
select a.name , c.name from t_emp a
    left outer join t_dept b on a.deptid = b.id
    left outer join t_emp c on b.ceo=c.id
所有条件都可以使用到索引

若必须用到子查询，可将子查询设置为驱动表，，因为驱动表的type 肯定是 all，而子查询返回的结果表没有索引，必定也是all