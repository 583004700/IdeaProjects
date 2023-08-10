package common

import "fmt"

type People interface {
	Think()
}

type User struct {
	Id     int
	Name   string `model:"pk" type:"string"` //``里定义一些Tag
	addr   string
	Weight float32 //体重，单位kg
	Height float32 //身高，单位米
}

func (user *User) BMI() float32 {
	return user.Weight / (user.Height * user.Height)
}

func (User) Think() { //可以传User的变量名，或者用_
	fmt.Println("God laughs")
}

type Book struct {
	ISBN   string
	Author User
}

type Student struct {
	User
	Score float32
}
