package main

import (
	"fmt"
	"time"
)

type user struct {
	id    int64
	name  string
	time  time.Time
	user2 user2
}

type user2 struct {
	age int
}

func (u user) say() {
	fmt.Printf("my name is %s\n", u.name)
}

func say2(u user) {
	fmt.Printf("my name is %s\n", u.name)
}

// 这样不能修改 u 的值
func (u user) update_user() {
	u.name = "update"
	u.user2.age = 100
}

// 这样才能修改 u 的值
func (u *user) real_update_user() {
	u.name = "update"
}

func main() {
	var u1 user
	fmt.Println(u1.id)
	fmt.Println(u1.name)
	var u2 = user{1, "张三", time.Now(), user2{80}}
	fmt.Println(u2.id)
	fmt.Println(u2.name)
	var u3 = user{name: "李四", id: 2}
	fmt.Printf("%v\n", u3)
	u3.say()
	say2(u3)
	u3.update_user()
	u3.say()
	fmt.Println(u3)

	// 返回的是指针
	var u4 = new(user)
	fmt.Println(u3)
	u4.name = "u4"
	u4.update_user()
	u3.real_update_user()
	fmt.Println(u3)

	u5 := u2
	fmt.Println(u5.user2.age)
}
