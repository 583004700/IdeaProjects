package main

import (
	"fmt"
	"golearn/day006/mycode/a"
)

func main() {
	fmt.Println("hello world")

	u := a.User{Name: "张三"}
	t := a.Teacher{Age: 18, U: &u}
	fmt.Printf(t.U.Name)

	var x string
	fmt.Scanf("%s", x)
}
