CREATE TABLE staffs (
  id INT PRIMARY KEY AUTO_INCREMENT,
  NAME VARCHAR (24)  NULL DEFAULT '' COMMENT '姓名',
  age INT NOT NULL DEFAULT 0 COMMENT '年龄',
  pos VARCHAR (20) NOT NULL DEFAULT '' COMMENT '职位',
  add_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入职时间'
) CHARSET utf8 COMMENT '员工记录表' ;


INSERT INTO staffs(NAME,age,pos,add_time) VALUES('z3',22,'manager',NOW());
INSERT INTO staffs(NAME,age,pos,add_time) VALUES('July',23,'dev',NOW());
INSERT INTO staffs(NAME,age,pos,add_time) VALUES('2000',23,'dev',NOW());
INSERT INTO staffs(NAME,age,pos,add_time) VALUES(null,23,'dev',NOW());
SELECT * FROM staffs;

ALTER TABLE staffs ADD INDEX idx_staffs_nameAgePos(name, age, pos);

案例(索引失效)
索引失效针对的组合索引？
若一个字段上有多种索引呢？某一索引失效，可以继续使用其他索引不影响。

全值匹配我最爱
索引  idx_staffs_nameAgePos 建立索引时 以 name ， age ，pos 的顺序建立的。全值匹配表示 按顺序匹配的
EXPLAIN SELECT * FROM staffs WHERE NAME = 'July';
EXPLAIN SELECT * FROM staffs WHERE NAME = 'July' AND age = 25;
EXPLAIN SELECT * FROM staffs WHERE NAME = 'July' AND age = 25 AND pos = 'dev';

最佳左前缀法则
如果索引了多列，要遵守最左前缀法则。指的是查询从索引的最左前列开始并且不跳过索引中的列。
and 忽略左右关系。既即使没有没有按顺序 由于优化器的存在，会自动优化。
经过试验结论  建立了 idx_nameAge 索引  id 为主键
    1.当使用覆盖索引的方式时，(select name/age/id from staffs where age=10 (后面没有其他没有索引的字段条件))，即使不是以 name 开头，也会使用 idx_nameAge 索引。
    既 select 后的字段 有索引，where 后的字段也有索引，则无关执行顺序。
    2.除开上述条件 才满足最左前缀法则。

EXPLAIN SELECT * FROM staffs WHERE age = 25 AND pos = 'dev';
EXPLAIN SELECT * FROM staffs WHERE pos = 'dev';

1. 不在索引列上做任何操作（计算、函数、(自动or手动)类型转换），会导致索引失效而转向全表扫描
EXPLAIN SELECT * FROM staffs WHERE left(NAME,4) = 'July';

2. 存储引擎不能使用索引中范围条件右边(联合索引的顺序，不是sql语句的顺序)的列
范围 若有索引则能使用到索引，范围条件右边的索引会失效(范围条件右边与范围条件使用的同一个组合索引，右边的才会失效。若是不同索引则不会失效)

3. 尽量使用覆盖索引(只访问索引的查询(索引列和查询列一致))，减少select *

4. mysql 在使用不等于(!= 或者<>)的时候无法使用索引会导致全表扫描
索引  idx_nameAgeJob
         idx_name
使用 != 和 <> 的字段索引失效( != 针对数值类型。 <> 针对字符类型
前提 where and 后的字段在混合索引中的位置比比当前字段靠后  where age != 10 and name='xxx'  ,这种情况下，mysql自动优化，将 name='xxx' 放在 age ！=10 之前，name 依然能使用索引。只是 age 的索引失效)

5. is not null 也无法使用索引,但是is null是可以使用索引的

6. like以通配符开头('%abc...')mysql索引失效会变成全表扫描的操作（可以使用覆盖索引解决）
like ‘%abc%’  type 类型会变成 all
like ‘abc%’ type 类型为 range ，算是范围，可以使用索引

7.字符串不加单引号索引失效
底层进行转换使索引失效，使用了函数造成索引失效

8.少用or,用它来连接时会索引失效

小总结
假设index(a,b,c)
Where语句	索引是否被使用
where a = 3	Y,使用到a
where a = 3 and b = 5	Y,使用到a，b
where a = 3 and b = 5 and c = 4	Y,使用到a,b,c
where b = 3 或者 where b = 3 and c = 4  或者 where c = 4	N
where a = 3 and c = 5	使用到a， 但是c不可以，b中间断了
where a = 3 and b > 4 and c = 5	使用到a和b， c不能用在范围之后，b后断了
where a = 3 and b like 'kk%' and c = 4	Y,使用到a,b,c
where a = 3 and b like '%kk' and c = 4	Y,只用到a
where a = 3 and b like '%kk%' and c = 4	Y,只用到a
where a = 3 and b like 'k%kk%' and c = 4	Y,使用到a,b,c

