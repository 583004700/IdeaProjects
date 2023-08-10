package main

import (
	"fmt"
	"time"
)

// 定义User结构体
type User struct {
	//成员变量
	Id         int
	Score      float32
	enrollment time.Time
	Name, addr string //多个字段类型相同时可以简写到一行里
}

func init_struct() {
	var u User                                                                //声明，会用相应类型的默认值初始化struct里的每一个字段
	fmt.Printf("id=%d, enrollment=%v, name=%s\n", u.Id, u.enrollment, u.Name) //访问结构体的成员变量
	u = User{}                                                                //相应类型的默认值初始化struct里的每一个字段
	fmt.Printf("id=%d, enrollment=%v, name=%s\n", u.Id, u.enrollment, u.Name)
	u = User{Id: 3, Name: "zcy"} //赋值初始化
	fmt.Printf("id=%d, enrollment=%v, name=%s\n", u.Id, u.enrollment, u.Name)
	u.enrollment = time.Now()                                                 //给结构体的成员变量赋值
	fmt.Printf("id=%d, enrollment=%v, name=%s\n", u.Id, u.enrollment, u.Name) //访问结构体的成员变量
	u = User{4, 100.0, time.Now(), "zcy", "beijing"}                          //赋值初始化，可以不写字段，但需要跟结构体定义里的字段顺序一致
	fmt.Printf("id=%d, enrollment=%v, name=%s\n", u.Id, u.enrollment, u.Name)
}

// 成员函数。可以把user理解为hello函数的参数，即hello(u user, man string)
// 值实现的方法，指针也实现
func (u User) hello(man string) {
	u.Name = "杰克"
	fmt.Println("hi " + man + ", my name is " + u.Name)
}

// user传的是值，即传的是整个结构体的拷贝。在函数里修改结构体不会影响原来的结构体
func hello(u User, man string) {
	u.Name = "杰克"
	fmt.Println("hi " + man + ", my name is " + u.Name)
}

// 可以理解为hello2(u *user, man string)
func (u *User) hello2(man string) {
	u.Name = "杰克"
	fmt.Println("hi " + man + ", my name is " + u.Name)
}

// 传的是user指针，在函数里修改user的成员会影响原来的结构体
func hello2(u *User, man string) {
	u.Name = "杰克"
	fmt.Println("hi " + man + ", my name is " + u.Name)
}

// 函数里不需要访问user的成员，可以传匿名
func (_ User) think(man string) {
	fmt.Println("hi " + man + ", do you know my name?")
}

func init_struct_ptr() {
	var u User
	user := &u    //通过取址符&得到指针
	user = &User{ //直接创建结构体指针
		Id: 3, Name: "zcy", addr: "beijing",
	}
	user = new(User) //通过new()函数实体化一个结构体，并返回其指针
	user.Name = "zcy"
	fmt.Println(user.Name)
}

// 构造函数。返回指针是为了避免值拷贝
func NewUser(id int, name string) *User {
	return &User{
		Id:    id,
		Name:  name,
		addr:  "China",
		Score: 59,
	}
}

type UserMap map[int]User //自定义类型

// 可以给自定义类型添加任意方法
func (um UserMap) GetUser(id int) User {
	return um[id]
}

// 匿名结构体
func anonymous_struct() {
	var stu struct { //声明stu是一个结构体，但这个结构体是匿名的
		Name string
		Addr string
	}
	stu.Name = "zcy"
	stu.Addr = "bj"
	//匿名结构体通常用于只使用一次的情况
	fmt.Printf("anonymous_struct name is %s\n", stu.Name)
}

// 结构体的匿名成员
func anonymous_member() {
	type Student struct {
		Id      int
		string  //匿名字段
		float32 //直接使用数据类型作为字段名，所以匿名字段中不能出现重复的数据类型
	}
	var stu = Student{Id: 1, string: "zcy", float32: 79.5}
	fmt.Printf("anonymous_member string member=%s float member=%f\n", stu.string, stu.float32)
}

// 结构体嵌套
func nest_struct() {
	type user struct {
		name string
		sex  byte
	}
	type paper struct {
		name   string
		auther user //结构体嵌套
	}
	p := new(paper)
	p.name = "论文标题"
	p.auther.name = "作者姓名"
	p.auther.sex = 0
	fmt.Println()
	type vedio struct {
		length int
		name   string
		user   //匿名字段，可能直接使用数据类型当字段名
	}
	v := new(vedio)
	v.length = 13
	v.name = "视频名称"
	v.user.sex = 0       //通过字段名逐级访问
	v.sex = 0            //对于匿名字段也可以跳过中间字段名，直接访问内部的字段名
	v.user.name = "作者姓名" //由于内部、外部结构体都有name这个字段，名字冲突了，所以需要指定中间字段名
}

func deep_copy() {
	type User struct {
		Name string
		Age  int
	}
	type Vedio struct {
		Length int
		Name   string
		Author User //如果改成指针类型，对于Author字段就是浅拷贝
	}
	v1 := Vedio{Length: 13, Name: "西游记", Author: User{Name: "吴承恩", Age: 37}}
	v2 := v1      //深拷贝，会为v2申请一块新的内存空间，把v1的成员变量的值都拷贝过去
	v2.Length = 5 //修改v2的成员不会影响到v1
	v2.Author.Name = "李白"
	fmt.Printf("length of v1 is %d, author of v1 is %s\n", v1.Length, v1.Author.Name)
	fmt.Printf("v1 address %p, v2 address %p\n", &v1, &v2) //v1跟v2的内存地址不一样
}

func shallow_copy() {
	type User struct {
		Name string
		Age  int
	}
	type Vedio struct {
		Length int
		Name   string
		Author User //如果改成指针类型，对于Author字段就是浅拷贝
	}
	v1 := &Vedio{Length: 13, Name: "西游记", Author: User{Name: "吴承恩", Age: 37}}
	v2 := v1      //v1是个指针，其值是一个地址，把这个地址赋给了v2，所发是浅拷贝，没有发生内存拷贝
	v2.Length = 5 //通过v2（指针）直接修改原内存空间里存的数据，v1也指向这块内存
	v2.Author.Name = "李白"
	fmt.Printf("length of v1 is %d, author of v1 is %s\n", v1.Length, v1.Author.Name)
	fmt.Printf("v1 address %p, v2 address %p\n", v1, v2) //v1跟v2的内存地址是一样的
}

// 传的是值，深拷贝
func update_user1(user User) {
	user.Name = "光绪"
}

// 传的是指针，浅拷贝
func update_user2(user *User) {
	user.Name = "光绪"
}

// 传slice，对sclice的3个字段进行了拷贝，拷贝的是底层数组的指针，所以修改底层数组的元素会反应到原数组上
func update_users1(user []User) {
	user[0].Name = "光绪"
}

func update_users2(user []*User) {
	user[0].Name = "光绪"
}

func update_users3(user *[]User) {
	(*user)[0].Name = "光绪"
}

func update_users4(user *[]*User) {
	(*user)[0].Name = "光绪"
}

func main() {
	init_struct()
	fmt.Println()

	var u User
	u.Name = "zcy"
	u.hello("Tom") //调用成员函数
	hello(u, "Tom")
	fmt.Println(u.Name)
	u.hello2("Tom")
	fmt.Println(u.Name)
	hello2(&u, "Tom")
	u.think("Tome")
	fmt.Println("===================")
	init_struct_ptr()
	fmt.Println("===================")
	um := make(UserMap, 10)
	um[2] = User{Id: 2, Name: "zcy", enrollment: time.Now()}
	fmt.Println(um.GetUser(2).Name)
	fmt.Println("===================")
	anonymous_struct()
	anonymous_member()
	fmt.Println("===================")
	nest_struct()
	fmt.Println("===================")
	deep_copy()
	fmt.Println("===================")
	shallow_copy()
	fmt.Println("===================")
	user := User{Name: "康熙"}
	update_user1(user)
	fmt.Println(user.Name)
	user = User{Name: "康熙"}
	update_user2(&user)
	fmt.Println(user.Name)
	users := []User{{Name: "康熙"}}
	update_users1(users)
	fmt.Println(users[0].Name)
	users = []User{{Name: "康熙"}}
	update_users3(&users)
	fmt.Println(users[0].Name)
	usersPtr := []*User{&User{Name: "康熙"}}
	update_users2(usersPtr)
	fmt.Println(usersPtr[0].Name)
	usersPtr = []*User{&User{Name: "康熙"}}
	update_users4(&usersPtr)
	fmt.Println(usersPtr[0].Name)
}
