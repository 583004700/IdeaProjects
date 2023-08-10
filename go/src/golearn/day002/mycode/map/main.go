package main

import (
	"fmt"
	"reflect"
)

func update_map(m map[interface{}]interface{}) {
	m["a"] = "哈哈"
}

type obj struct {
	id   int
	name string
}

func main() {
	var m1 = make(map[string]string, 10)
	m1["a"] = "张三"
	m1["b"] = "李四"
	for key, value := range m1 {
		fmt.Printf("%s,%s\n", key, value)
	}

	var m2 = make(map[interface{}]interface{})
	m2[5] = 9
	m2["im"] = 8
	m2[nil] = 888

	m3 := m2
	p := &m3
	(*p)["k"] = 77777
	fmt.Printf("%t\n", reflect.DeepEqual(m2, m3))
	update_map(m2)
	fmt.Printf("%v\n", m2)

	var m4 = make(map[interface{}]interface{})
	var key obj = obj{
		5, "张三",
	}
	var key2 obj = obj{
		5, "张三",
	}
	m4[key] = 888
	// 只要两个对象中的值相同就可以取出来，并不要求是同一个对象
	fmt.Println(m4[key2])
}
