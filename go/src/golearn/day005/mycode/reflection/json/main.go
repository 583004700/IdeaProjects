package main

import (
	"encoding/json"
	"fmt"
)

type User struct {
	Name string
	Age  int
	Sex  byte `json:"gender"`
}

func main() {
	u := User{"张三", 18, 1}
	jsonStr, err := json.Marshal(u)
	if err == nil {
		fmt.Println(string(jsonStr))
	}
}
