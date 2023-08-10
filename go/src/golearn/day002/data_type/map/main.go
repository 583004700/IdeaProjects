package main

import (
	"fmt"
)

func main() {
	var m map[string]int                                      //声明
	m = make(map[string]int)                                  //初始化，容量为0
	m = make(map[string]int, 5)                               //初始化，容量为5。强烈建议初始化时给一个合适的容量，减少扩容的概率
	m = map[string]int{"语文": 0, "数学": 39, "物理": 57, "历史": 49} //初始化时直接赋值
	m["英语"] = 59                                              //往map里添加key-value对
	fmt.Println(m["数学"])                                      //读取key对应的value，如果key不存在，则返回value类型的默认值
	delete(m, "数学")                                           //从map里删除key-value对
	fmt.Println(m["数学"])
	//取key对应的value建议使用这种方法，先判断key是否存在
	if value, exists := m["语文"]; exists {
		fmt.Println(value)
	} else {
		fmt.Println("map里不存在[语文]这个key")
	}
	//获取map的长度，无法获取map的cap
	fmt.Printf("map里有%d对KV\n", len(m))
	//遍历map
	for key, value := range m {
		fmt.Printf("%s=%d\n", key, value)
	}
	fmt.Println("-----------")
	//多次遍历map返回的顺序是不一样的，但相对顺序是一样的，因为每次随机选择一个开始位置，然后顺序遍历
	for key, value := range m {
		fmt.Printf("%s=%d\n", key, value)
	}
	fmt.Println("-----------")

	//一边遍历一边修改
	for key, value := range m {
		m[key] = value + 1
	}
	for key, value := range m {
		fmt.Printf("%s=%d\n", key, value)
	}
	fmt.Println("-----------")

	//for range取得的是值拷贝
	for _, value := range m {
		value = value + 1
	}
	for key, value := range m {
		fmt.Printf("%s=%d\n", key, value)
	}
}

//go run data_type/map/main.go
