# go数据库编程
## SQL语法简介
&#8195;&#8195;SQL(i/ˈsiːkwəl/; Structured Query Language)是一套语法标准，不区分大小写。MySQL、sql-server和Oracle都是关系型数据库，在一些高级语法上跟标准SQL略有出入。  
Linux用户安装MySQL服务端
```Shell
yum install mysql-server
```
安装MySQL客户端  
```Shell
yum install mysql
```
启动MySQL服务端  
```Shell
service mysqld start
```
以管理员登录  
```Shell
mysql -h localhost -P 3306 -u root -p'123456'
```
* -h：mysql server host，不写时默认是localhost。
* -P：mysql server port，不写时默认是3306。
* -u：user name，-u后可以加空格也可以不加。
* -p：password，密码中可能包含空格，所以要加引号。高版本的mysql不允许在命令行中直接输入密码，此时只输入-p后面不要写密码即可。
创建账号  
```SQL
create user 'tester' identified by '123456'
```
创建database  
```SQL
create database test
```
把特定database的操作权限授予一个普通用户
```SQL
grant all on test.* to tester;
```
以普通用户登录
```Shell
mysql -utester -p'123456'
```
使用database  
```SQL
use test
```
创建表  
```SQL
create table if not exists student(
    id int not null auto_increment comment '主键自增id',
    name char(10) not null comment '姓名',
    province char(6) not null comment '省',
    city char(10) not null comment '城市',
    addr varchar(100) default '' comment '地址',
    score float not null default 0 comment '考试成绩',
    enrollment date not null comment '入学时间',
    primary key (id),  unique key idx_name (name),  
    key idx_location (province,city)
)default charset=utf8 comment '学员基本信息';
```
新增记录，必须给not null且无default值的列赋值。    
```SQL
insert into student (name,province,city,enrollment) values
    ('张三','北京','北京','2021-03-05'),
    ('李四','河南','郑州','2021-04-25'),
    ('小丽','四川','成都','2021-03-10');
```
查询  
```SQL
select id,name from student where id>0;

select province,avg(score) as avg_score from student 
    where score>0 
    group by province having avg_score>50 
    order by avg_score desc;
```
修改  
```SQL
update student set score=score+10,addr='海淀' where province='北京';

update student set
    score=case province
        when '北京' then score+10     
        when '四川' then score+5 
        else score+7
    end,
    addr=case province
        when '北京' then '东城区'        
        when '四川' then '幸福里'        
        else '朝阳区'    
    end
where id>0;
```
删除  
```SQL
delete from student where city= '郑州';
delete from student;	--删除表里的所有行
drop table student;	    --删除表
```
## MySQL最佳实践
- 写sql时一律使用小写。
- 建表时先判断表是否已存在if not exists。 
- 所有的列和表都加comment。
- 字符串长度比较短时尽量使用char，定长有利于内存对齐，读写性能更好，而varchar字段频繁修改时容易产生内存碎片。
- 满足需求的前提下尽量使用短的数据类型，如tinyint vs int, float vs double, date vs datetime。  

**null** 
- default null有别于default ''和default 0
- is null, is not null有别于!= '', !=0
- 尽量设为not null
    - 有些DB索引列不允许包含null
    - 对含有null的列进行统计，结果可能不符合预期
    - null值有时候会严重拖慢系统性能

**索引**  

<img src=img/b_tree.png width=400 />     

1. B即Balance，对于m叉树每个节点上最多有m个数据，最少有m/2个数据（根节点除外）。
2. 叶节点上存储了所有数据，把叶节点链接起来可以顺序遍历所有数据。
3. 每个节点设计成内存页的整倍数。MySQL的m=1200，树的前两层放在内存中。  

- MySQL索引默认使用B+树。（思考：为什么不用hash table？）
- 主键默认会加索引。按主键构建的B+树里包含所有列的数据，而普通索引的B+树里只存储了主键，还需要再查一次主键对应的B+树（回表）。
- 联合索引的前缀同样具有索引的效果。
- sql语句前加explain可以查看索引使用情况。
- 如果MySQL没有选择最优的索引方案，可以在where前force index (index_name)。  

规避慢查询  
- 大部分的慢查询都是因为没有正确地使用索引。查看一条SQL语句使用索引的情况只需要在SQL前加个explain。
- 一次select不要超过1000行。
- 分页查询limit m,n会检索前m+n行，只是返回后n行，通常用id>x来代替这种分页方式（stmt一节会展示遍历整个table的正确姿势）。
- 批量操作时最好一条sql语句搞定；其次打包成一个事务，一次性提交(高并发情况下减少对共享资源的争用)。
- 不要使用连表操作，join逻辑在业务代码里完成。
## Go SQL驱动接口解读
&#8195;&#8195;Go官方没有提供数据库驱动，而是为开发数据库驱动定义了一些标准接口（即database/sql ），开发者可以根据定义的接口来开发相应的数据库驱动。Go中支持MySQL的驱动比较多，如
- github.com/go-sql-driver/mysql 支持 database/sql
- github.com/ziutek/mymysql 支持 database/sql，也支持自定义的接口
- github.com/Philio/GoMySQL 不支持 database/sql，自定义接口

**Driver**  
```Go
type Driver interface { 
    Open(name string) (Conn, error) 
}
var d = Driver{proto: "tcp", raddr: "127.0.0.1:3306"}
sql.Register("mysql", &d)    //注册数据库驱动
```
**Conn**  
```Go
type Conn interface {
    Prepare(query string) (Stmt, error)  //把一个查询query传给Prepare，返回Stmt(statement)
    Close() error  //关闭数据库连接
    Begin() (Tx, error)  //返回一个事务Tx(transaction)
}
```
**Stmt**
```Go
type Stmt interface {
    Close() error  //关闭当前的链接状态
    NumInput() int  //返回当前预留参数的个数
    Exec(args []Value) (Result, error)  //执行Prepare准备好的 sql，传入参数执行 update/insert 等操作，返回 Result 数据
    Query(args []Value) (Rows, error)  //执行Prepare准备好的 sql，传入需要的参数执行 select 操作，返回 Rows 结果集
}
```
**Tx**
```Go
type Tx interface {
    Commit() error  //提交事务
    Rollback() error  //回滚事务
}
```
**Result**
```Go
type Result interface {
    LastInsertId() (int64, error)  //返回由数据库执行插入操作得到的自增ID号。如果使用单个INSERT将多行插入到表中，则LastInsertId是第一条数据使用的id
    RowsAffected() (int64, error)  //返回操作影响的数据条目数
}
```
**RowsAffected**  
&#8195;&#8195;RowsAffected是int64的别名，它实现了Result接口。  
```Go
type RowsAffected int64
func (RowsAffected) LastInsertId() (int64, error)
func (v RowsAffected) RowsAffected() (int64, error)
```
**Rows**
```Go
type Rows interface {
    Columns() []string  //查询所需要的表字段
    Close() error  //关闭迭代器
    Next(dest []Value) error  //返回下一条数据，把数据赋值给dest，dest里面的元素必须是 driver.Value的值。如果最后没数据了，Next 函数返回 io.EOF
}
```
**Value**
```Go
type Value interface{}
```
&#8195;&#8195;Value 要么是 nil，要么是下面的任意一种  
- int64 
- float64 
- bool 
- []byte 
- string 
- time.Time
**ValueConverter**
```Go
type ValueConverter interface {
    //把数据库里的数据类型转换成Value允许的数据类型
    ConvertValue(v interface{}) (Value, error)
}
```
## 数据库增删改查
下载第三方库  
```Shell
go get github.com/go-sql-driver/mysql
```
连接数据库  
```Go
db, err := sql.Open("mysql", "root:@tcp(localhost:3306)/test?charset=utf8")
```
&#8195;&#8195;DSN(data source name)格式：  
[username[:password]@][protocol[(address)]]/dbname[?param1=value1&...&paramN=valueN]  
&#8195;&#8195;例如user:password@tcp(localhost:5555)/dbname?charset=utf8mb4&parseTime=True  
&#8195;&#8195;如果是本地MySQl，且采用默认的3306端口，可简写为：user:password@/dbname  
连接参数  
&#8195;&#8195;要支持完整的UTF-8编码，您需要将charset=utf8更改为charset=utf8mb4
&#8195;&#8195;想要正确的处理time.Time ，您需要带上parseTime参数
增删改  
```Go
func (*sql.DB).Exec(sql string) (sql.Result, error)
```
查  
```Go
func (*sql.DB).Query(sql string) (*sql.Rows, error)
```
crud.go  
```Go

import (
	"database/sql"
	"fmt"
	"golearn/day012/database"
	"time"

	_ "github.com/go-sql-driver/mysql"
)

const TIME_LAYOUT = "2006-01-02"

var (
	loc *time.Location
)

func init() {
	loc, _ = time.LoadLocation("Asia/Shanghai")
}

//insert 插入数据
func insert(db *sql.DB) {
	//一条sql，插入2行记录
	res, err := db.Exec("insert into student (name,province,city,enrollment) values ('小明', '深圳', '深圳', '2021-04-18'), ('小红', '上海', '上海', '2021-04-26')")
	database.CheckError(err)
	lastId, err := res.LastInsertId() //ID自增，用过的id（即使对应的行已delete）不会重复使用。如果使用单个INSERT语句将多行插入到表中，则LastInsertId是第一条数据使用的id
	database.CheckError(err)
	fmt.Printf("after insert last id %d\n", lastId)
	rows, err := res.RowsAffected() //插入2行，所以影响了2行
	database.CheckError(err)
	fmt.Printf("insert affect %d row\n", rows)
}

//replace 插入(覆盖)数据
func replace(db *sql.DB) {
	//由于name字段上有唯一索引，insert重复的name会报错。而使用replace会先删除，再插入
	res, err := db.Exec("replace into student (name,province,city,enrollment) values ('小明', '深圳', '深圳', '2021-04-18'), ('小红', '上海', '上海', '2021-04-26')")
	database.CheckError(err)
	lastId, err := res.LastInsertId() //ID自增，用过的id（即使对应的行已delete）不会重复使用
	database.CheckError(err)
	fmt.Printf("after insert last id %d\n", lastId)
	rows, err := res.RowsAffected() //先删除，后插入，影响了4行
	database.CheckError(err)
	fmt.Printf("insert affect %d row\n", rows)
}

//update 修改数据
func update(db *sql.DB) {
	//不同的city加不同的分数
	res, err := db.Exec("update student set score=score+10 where city='上海'") //上海加10分
	database.CheckError(err)
	lastId, err := res.LastInsertId() //0, 仅插入操作才会给LastInsertId赋值
	database.CheckError(err)
	fmt.Printf("after update last id %d\n", lastId)
	rows, err := res.RowsAffected() //where city=?命中了几行，就会影响几行
	database.CheckError(err)
	fmt.Printf("update affect %d row\n", rows)
}

//query 查询数据
func query(db *sql.DB) {
	rows, err := db.Query("select id,name,city,score from student where id>2") //查询得分大于2的记录
	database.CheckError(err)
	for rows.Next() { //没有数据或发生error时返回false
		var id int
		var score float32
		var name, city string
		err = rows.Scan(&id, &name, &city, &score) //通过scan把db里的数据赋给go变量
		database.CheckError(err)
		fmt.Printf("id=%d, score=%.2f, name=%s, city=%s \n", id, score, name, city)
	}
}

//delete 删除数据
func delete(db *sql.DB) {
	res, err := db.Exec("delete from student where id>13") //删除得分大于13的记录
	database.CheckError(err)
	rows, err := res.RowsAffected() //where id>13命中了几行，就会影响几行
	database.CheckError(err)
	fmt.Printf("delete affect %d row\n", rows)
}
```
## stmt
&#8195;&#8195;首先看两个sql注入攻击的例子。  
```Go
sql = "select username,password from user where username='" + username + "' and password='" + password + "'"; 
```
&#8195;&#8195;变量username和password从前端输入框获取，如果用户输入的username为lily， password为aaa' or '1'='1，则完整的sql为select username,password from user where username='lily' and password='aaa' or '1'='1'。会返回表里的所有记录，如果记录数大于0就允许登录，则lily的账号被盗。  
```Go
sql="insert into student (name) values ('"+username+" ') ";
```
&#8195;&#8195;变量username从前端输入框获取，如果用户输入的username为lily'); drop table student;--。完整sql为insert into student (name) values ('lily'); drop table student;--')。通过注释符--屏蔽掉了末尾的')，删除了整个表。  
防止sql注入的方法：  
- 前端输入要加正则校验、长度限制。
- 对特殊符号(<>&*; '"等)进行转义或编码转换，Go的text/template 包里面的HTMLEscapeString函数可以对字符串进行转义处理。
- 不要将用户输入直接嵌入到sql语句中，而应该使用参数化查询接口，如Prepare、Query、Exec(query string, args ...interface{})。
- 使用专业的SQL注入检测工具进行检测，如sqlmap、SQLninja。
- 避免网站打印出SQL错误信息，以防止攻击者利用这些错误信息进行SQL注入。  

参数化查询
```Go
db.Where("merchant_id = ?", merchantId)
```
拼接sql  
```Go
db.Where(fmt.Sprintf("merchant_id = %s", merchantId))
```
定义一个sql模板  
```Go
stmt, err := db.Prepare("update student set score=score+? where city=?")
```
多次使用模板  
```Go
res, err := stmt.Exec(10, "上海")
res, err = stmt.Exec(9, "深圳") 
```
**SQL预编译**  
&#8195;&#8195;DB执行sql分为3步：
1. 词法和语义解析。
2. 优化SQL语句，制定执行计划。
3. 执行并返回结果。  

&#8195;&#8195;SQL预编译技术是指将用户输入用占位符?代替，先对这个模板化的sql进行预编译，实际运行时再将用户输入代入。除了可以防止SQL注入，还可以对预编译的SQL语句进行缓存，之后的运行就省去了解析优化SQL语句的过程。  

stmt_demo.go  
```Go
//update 通过stmt修改数据
func update(db *sql.DB) {
	//不同的city加不同的分数
	stmt, err := db.Prepare("update student set score=score+? where city=?")
	database.CheckError(err)
	//执行修改操作通过stmt.Exec，执行查询操作通过stmt.Query
	res, err := stmt.Exec(10, "上海") //上海加10分
	database.CheckError(err)
	res, err = stmt.Exec(9, "深圳") //深圳加9分
	database.CheckError(err)
	lastId, err := res.LastInsertId() //0, 仅插入操作才会给LastInsertId赋值
	database.CheckError(err)
	fmt.Printf("after update last id %d\n", lastId)
	rows, err := res.RowsAffected() //where city=?命中了几行，就会影响几行
	database.CheckError(err)
	fmt.Printf("update affect %d row\n", rows)
}

//query 通过stmt查询数据
func query(db *sql.DB) {
	stmt, err := db.Prepare("select id,name,city,score from student where id>?")
	database.CheckError(err)
	//执行修改操作通过stmt.Exec，执行查询操作通过stmt.Query
	rows, err := stmt.Query(2) //查询得分大于2的记录
	database.CheckError(err)
	for rows.Next() { //没有数据或发生error时返回false
		var id int
		var score float32
		var name, city string
		err = rows.Scan(&id, &name, &city, &score) //通过scan把db里的数据赋给go变量
		database.CheckError(err)
		fmt.Printf("id=%d, score=%.2f, name=%s, city=%s \n", id, score, name, city)
	}
}
```
遍历一张表的正确姿势：  
```Go
//traverse 借助于主健自增ID，通过where id>maxid遍历表
func traverse(db *sql.DB) {
	var maxid int
	begin := time.Now()
	stmt, _ := db.Prepare("select id,name,province from student where id>? limit 100") //limit m,n  limit 0,n
	for i := 0; i < 100; i++ {
		t0 := time.Now()
		rows, _ := stmt.Query(maxid)
		fmt.Println(i, time.Since(t0))

		for rows.Next() {
			var id int
			var name string
			var province string
			rows.Scan(&id, &name, &province)
			if id > maxid {
				maxid = id
			}
		}
	}
	fmt.Println("total", time.Since(begin))
}
```
## SQLBuilder
### Go-SQLBuilder
&#8195;&#8195;Go-SQLBuilder是一个用于创建SQL语句的工具函数库，提供一系列灵活的、与原生SQL语法一致的链式函数。归属于艾润物联公司。安装方式
```Shell
go get -u github.com/parkingwang/go-sqlbuilder
```
&#8195;&#8195;Go-SQLBuilder通过函数链来构造sql语句，比如select语句的构造
```Go
func query() {
	sql := gsb.NewContext().Select("id", "name", "score", "city").
		From("student").
		OrderBy("score").DESC().
		Column("name").ASC().
		Limit(10).Offset(20).
		ToSQL()
	fmt.Println(sql)
}
```
为什么需要SQLBuilder？  
1. 写一句很长的sql容易出错，且出错后不好定位。
2. 函数式编程可以直接定位到是哪个函数的问题。
3. 函数式编程比一长串sql更容易编写和理解。

### Gendry
&#8195;&#8195;Gendry是一个用于辅助操作数据库的Go包。基于go-sql-driver /mysql，它提供了一系列的方法来为你调用标准库database/sql中的方法准备参数。安装方式
```Shell
go get –u github.com/didi/gendry
```
&#8195;&#8195;Gendry倾向于把复杂的筛选条件放在map中，并且跟stmt技术结合得比较紧密。  
```Go
func query(db *sql.DB) {
	where := map[string]interface{}{
		"city":     []string{"北京", "上海", "杭州"},
		"score<":   30,
		"addr":     builder.IsNotNull,
		"_orderby": "score desc",
	}
	table := "student"
	fields := []string{"id", "name", "city", "score"}
	//准备stmt模板
	template, values, err := builder.BuildSelect(table, where, fields)
	database.CheckError(err)
	//执行stmt模板
	rows, err := db.Query(template, values...)
	database.CheckError(err)
	for rows.Next() {
		var id int
		var name, city string
		var score float32
		err := rows.Scan(&id, &name, &city, &score)
		database.CheckError(err)
		fmt.Printf("%d %s %s %.2f\n", id, name, city, score)
	}
}
```
### 自行实现SQLBuilder
&#8195;&#8195;作为练习，我们自行实现一个SQLBuilder，它最终应该支持如下函数链式的编程风格。  
```Go
sql := NewSelectBuilder("student").Column("id,name,city").
	Where("id>0").
	And("city='郑州'").
	Or("city='北京'").
	OrderBy("score").Desc().
	Limit(0, 10).ToString()`
```
&#8195;&#8195;Builder设计模式的精髓在于**Builder对象的方法还是返回一个Builder**。首先定义一个Builder接口。  
```Go
type Builder interface {
	toString() string
	getPrev() Builder
}
```
&#8195;&#8195;select、where、limit、orderby这些都是Builder，这里详细讲解WhereBuilder的设计与实现。  
```Go
type WhereBuilder struct {
	sb strings.Builder		//拼接where条件字符串
	orderby *OrderByBuilder//where后面可能会接order by
	limit *LimitBuilder	// where后面可能会接limit
	prev Builder		//where前面是select
}
```
&#8195;&#8195;WhereBuilder中的sb负责当下，orderby和limit负责维护后面节点，prev负责维护前面的节点。  
&#8195;&#8195;where表达式中可能包含and和or，把它们定义为WhereBuilder的方法，并且这两个方法依赖返回WhereBuilder自身。  
```Go
func (self *WhereBuilder) And(condition string) *WhereBuilder {
	self.sb.WriteString(" and ")
	self.sb.WriteString(condition)
	return self
}
func (self *WhereBuilder) Or(condition string) *WhereBuilder {
	self.sb.WriteString(" or ")
	self.sb.WriteString(condition)
	return self
}
```
&#8195;&#8195;where表达式后面可能会跟order by表达式，把OrderBy定义为WhereBuilder的方法，该方法返回OrderByBuilder。  
```Go
func (self *WhereBuilder) OrderBy(column string) *OrderByBuilder {
	orderby := newOrderByBuilder(column)
	self.orderby = orderby
	orderby.prev = self
	return orderby
}
```
&#8195;&#8195;函数链上的最后一个Builder调用ToString()方法生成写成的sql语句。  
```Go
func (self *LimitBuilder) ToString() string {
	var root Builder
	root = self
	for root.getPrev() != nil {
		root = root.getPrev()    //递归找到最前面的Builder
	}
	return root.toString()    //在最前面的Builder（即SelectBuilder）上调用toString()
}
```
&#8195;&#8195;每个Builder都有toString()方法，以WhereBuilder为例，它在构造函数里把where表达式放入sb成员变量里，WhereBuilder在toString()方法里调用where后面的节点的toString()方法。  
```Go
func newWhereBuilder(condition string) *WhereBuilder {
	builder := &WhereBuilder{}
	builder.sb.WriteString(" where ")
	builder.sb.WriteString(condition)
	return builder
}
func (self *WhereBuilder) toString() string {
	//递归调用后续Builder的ToString()
	if self.orderby != nil {
		self.sb.WriteString(self.orderby.toString())
	}
	if self.limit != nil {
		self.sb.WriteString(self.limit.toString())
	}
	return self.sb.String()
}
```
## GORM
&#8195;&#8195;ORM即Object Relational Mapping，对象关系映射。Relational指各种sql类的关系型数据库。Object指面向对象编程(object-oriented programming)中的对象。ORM在数据库记录和程序对象之间做一层映射转换，使程序中不用再去编写原生SQL，而是面向对象的思想去编写类、对象、调用相应的方法来完成数据库操作。  
```Shell
go get -u gorm.io/gorm
go get -u gorm.io/driver/mysql
```
&#8195;&#8195;GORM是一个全能的、友好的、基于golang的ORM库。
GORM 倾向于约定，而不是配置。默认情况下，GORM 使用ID作为主键，使用结构体名的【蛇形复数】作为表名，字段名的【蛇形】作为列名，并使用CreatedAt、UpdatedAt字段追踪创建、更新时间。  
&#8195;&#8195;GORM完全是在操作struct，看不到sql的影子。
```Go
type Student struct {
	Id         int    `gorm:"column:id;primaryKey"`
	Name       string `gorm:"column:name"`
	Province   string
	City       string    `gorm:"column:city"`
	Address    string    `gorm:"column:addr"`
	Score      float32   `gorm:"column:score"`
	Enrollment time.Time `gorm:"column:enrollment;type:date"`
}
student := Student{
	Name: "光绪", 
	Province: "北京", 
	City: "北京", 
	Score: 38, 
	Enrollment: time.Now()
}
db.Create(&student)
```
&#8195;&#8195;GORM同时支持使用函数链的方式写sql语句。  
```Go
func query(db *gorm.DB) {
	//返回一条记录
	var student Student
	db.Where("city=?", "郑州").First(&student) //有First就有Last
	fmt.Println(student.Name)
	fmt.Println()
	//返回多条记录
	var students []Student
	db.Where("city=?", "郑州").Find(&students)
	for _, ele := range students {
		fmt.Printf("id=%d, name=%s\n", ele.Id, ele.Name)
	}
	fmt.Println()
	students = []Student{} //清空student，防止前后影响
	db.Where("city in ?", []string{"郑州", "北京"}).Find(&students)
	for _, ele := range students {
		fmt.Printf("id=%d, name=%s\n", ele.Id, ele.Name)
	}
	fmt.Println("============where end============")
	//根据主键查询
	student = Student{} //清空student，防止前后影响
	students = []Student{}
	db.First(&student, 1)
	fmt.Println(student.Name)
	fmt.Println()
	db.Find(&students, []int{1, 2, 3})
	for _, ele := range students {
		fmt.Printf("id=%d, name=%s\n", ele.Id, ele.Name)
	}
	fmt.Println("============primary key end============")
	//根据map查询
	student = Student{}
	students = []Student{}
	db.Where(map[string]interface{}{"city": "郑州", "score": 0}).Find(&students)
	for _, ele := range students {
		fmt.Printf("id=%d, name=%s\n", ele.Id, ele.Name)
	}
	fmt.Println("============map end============")
	//OR查询
	student = Student{}
	students = []Student{}
	db.Where("city=?", "郑州").Or("city=?", "北京").Find(&students)
	for _, ele := range students {
		fmt.Printf("id=%d, name=%s\n", ele.Id, ele.Name)
	}
	fmt.Println("============or end============")
	//order by
	student = Student{}
	students = []Student{}
	db.Where("city=?", "郑州").Order("score").Find(&students)
	for _, ele := range students {
		fmt.Printf("id=%d, name=%s\n", ele.Id, ele.Name)
	}
	fmt.Println("============order end============")
	//limit
	student = Student{}
	students = []Student{}
	db.Where("city=?", "郑州").Order("score").Limit(1).Offset(0).Find(&students)
	for _, ele := range students {
		fmt.Printf("id=%d, name=%s\n", ele.Id, ele.Name)
	}
	fmt.Println("============limit end============")
	//选择特定的字段
	student = Student{}
	db.Select("name").Take(&student)                                     //Take从结果中取一个，不保证是第一个或最后一个
	fmt.Printf("name=%s, province=%s\n", student.Name, student.Province) //只select了name，所以province是空的
}
```
## Go操作MongoDB
&#8195;&#8195;NoSQL泛指非关系型数据库，如mongo,redis,HBase。mongo使用高效的二进制数据存储，文件存储格式为 BSON （ 一种json的扩展，比json性能更好，功能更强大）。MySQL中表的概念在mongo里叫集合(collection)， MySQL中行的概念在mongo中叫文档(document)，一个文档看上去像一个json。  
&#8195;&#8195;安装mongo前先配置yum源: vim /etc/yum.repos.d/mongodb-org-4.2.repo  
```
[mongodb-org-4.2] 
name=MongoDB Repository baseurl=https://repo.mongodb.org/yum/redhat/$releasever/mongodb-org/4.2/x86_64/ gpgcheck=1 enabled=1 gpgkey=https://www.mongodb.org/static/pgp/server-4.2.asc
```
&#8195;&#8195;一键安装mongo: sudo yum install -y mongodb-org  
&#8195;&#8195;启动mongo: systemctl start mongod  
mongo常用命令  
```
use test;  切换到test库，如果没有则（创建集合时）会自动创建
db.createCollection("student");  创建collection
db.createUser({user: "tester",pwd: "123456", roles: [{role: "dbAdmin", db: "test"}]});创建用户
登录mongo --port 27017 -u "tester" -p "123456" --authenticationDatabase "test"
db.student.createIndex({"name":1});在name上创建索引,不是唯一索引
 db.student.insertOne({name:"张三",city:"北京"});
db.student.find({name:"张三"});
db.student.update({name:"张三"},{name:"张三",city:"上海"})
db.student.deleteOne({name:"张三"});
```
安装go mongo-driver  
```
go get go.mongodb.org/mongo-driver
go get go.mongodb.org/mongo-driver/x/bsonx/bsoncore@v1.7.1
go get go.mongodb.org/mongo-driver/x/mongo/driver@v1.7.1
go get go.mongodb.org/mongo-driver/mongo/options@v1.7.1
go get go.mongodb.org/mongo-driver/x/mongo/driver/topology@v1.7.1
go get go.mongodb.org/mongo-driver/mongo@v1.7.1
```
连接db  
```Go
option := options.Client().ApplyURI("mongodb://127.0.0.1:27017").
SetConnectTimeout(time.Second).//连接超时时长
SetAuth(options.Credential{Username: "tester", Password: "123456", AuthSource: "test"}) //指定用户名和密码，AuthSource代表Database
client, err := mongo.Connect(context.Background(), option)
err = client.Ping(ctx, nil) 
```
&#8195;&#8195;注意Ping成功才代表连接成功。  
查询mongo  
```Go
sort := bson.D{{"name", 1}} //1升序，-1降序
filter := bson.D{{"score", bson.D{{"$gt", 3}}}} //score>3
findOption := options.Find()
findOption.SetSort(sort)//按name排序
findOption.SetLimit(10) //最多返回10个
findOption.SetSkip(3) //跳过前3个
cursor, err := collection.Find(ctx, filter, findOption)
defer cursor.Close(ctx) //关闭迭代器
for cursor.Next(ctx) {
	var doc Student
	err := cursor.Decode(&doc)
	database.CheckError(err)
	fmt.Printf("%s %s %.2f\n", doc.Name, doc.City, doc.Score)
}
```
