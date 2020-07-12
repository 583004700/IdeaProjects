2.5.2 单表查询优化
建表SQL

CREATE TABLE IF NOT EXISTS `article` (
`id` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`author_id` INT(10) UNSIGNED NOT NULL,
`category_id` INT(10) UNSIGNED NOT NULL,
`views` INT(10) UNSIGNED NOT NULL,
`comments` INT(10) UNSIGNED NOT NULL,
`title` VARBINARY(255) NOT NULL,
`content` TEXT NOT NULL
);

INSERT INTO `article`(`author_id`, `category_id`, `views`, `comments`, `title`, `content`) VALUES
(1, 1, 1, 1, '1', '1'),
(2, 2, 2, 2, '2', '2'),
(1, 1, 3, 3, '3', '3');

SELECT * FROM article;

案例

#查询 category_id 为1 且  comments 大于 1 的情况下,views 最多的 article_id。

EXPLAIN SELECT id,author_id FROM article WHERE category_id = 1 AND comments > 1 ORDER BY views DESC LIMIT 1;

#结论：很显然,type 是 ALL,即最坏的情况。Extra 里还出现了 Using filesort,也是最坏的情况。优化是必须的。

#开始优化：
# 1.1 新建索引+删除索引
#ALTER TABLE `article` ADD INDEX idx_article_ccv ( `category_id` , `comments`, `views` );
create index idx_article_ccv on article(category_id,comments,views);
DROP INDEX idx_article_ccv ON article

# 1.2 第2次EXPLAIN
EXPLAIN SELECT id,author_id FROM `article` WHERE category_id = 1 AND comments >1 ORDER BY views DESC LIMIT 1;

#结论：
#type 变成了 range,这是可以忍受的。但是 extra 里使用 Using filesort 仍是无法接受的。
#但是我们已经建立了索引,为啥没用呢?
#这是因为按照 BTree 索引的工作原理,
# 先排序 category_id,
# 如果遇到相同的 category_id 则再排序 comments,如果遇到相同的 comments 则再排序 views。
#当 comments 字段在联合索引里处于中间位置时,
#因comments > 1 条件是一个范围值(所谓 range),
#MySQL 无法利用索引再对后面的 views 部分进行检索,即 range 类型查询字段后面的索引无效。


# 1.3 删除第一次建立的索引
DROP INDEX idx_article_ccv ON article;

# 1.4 第2次新建索引
#ALTER TABLE `article` ADD INDEX idx_article_cv ( `category_id` , `views` ) ;
create index idx_article_cv on article(category_id,views);

# 1.5 第3次EXPLAIN
EXPLAIN SELECT id,author_id FROM article WHERE category_id = 1 AND comments > 1 ORDER BY views DESC LIMIT 1;
#结论：可以看到,type 变为了 ref,Extra 中的 Using filesort 也消失了,结果非常理想。
DROP INDEX idx_article_cv ON article;