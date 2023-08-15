package data //注意：package不能是main

type User struct {
	Name   string
	Gender byte
	Age    int
}

//easyjson -all user.go
