package main

import "fmt"

type User struct {
	Name string //""表示未知
	Age  int    //-1表示未知
	Sex  byte   //1男，2女，3未知
}

func NewDefaultUser() *User {
	return &User{
		Name: "",
		Age:  -1,
		Sex:  3,
	}
}

func NewUser(name string, age int, sex byte) *User {
	return &User{
		Name: name,
		Age:  age,
		Sex:  sex,
	}
}

func main() {
	u := User{} //构造一个空的User，各字段都取相应数据类型的默认值
	fmt.Printf("name=%s,age=%d,sex=%d\n", u.Name, u.Age, u.Sex)
	up := new(User) //构造一个空的User，并返回其指针
	fmt.Printf("name=%s,age=%d,sex=%d\n", up.Name, up.Age, up.Sex)
	//通过自定义的构造函数，返回一个User指针
	up = NewDefaultUser()
	fmt.Printf("name=%s,age=%d,sex=%d\n", up.Name, up.Age, up.Sex)
	up = NewUser("张三", 18, 1)
	fmt.Printf("name=%s,age=%d,sex=%d\n", up.Name, up.Age, up.Sex)

	//单例模式，调用GetUserInstance()得到的是同一个User实例
	su1 := GetUserInstance()
	su2 := GetUserInstance()
	//修改su1会影响su2
	su1.Name = "令狐一刀"
	su1.Age = 100
	su1.Sex = 2
	fmt.Printf("name=%s,age=%d,sex=%d\n", su2.Name, su2.Age, su2.Sex)
}

//go run oop/constructor/*.go
