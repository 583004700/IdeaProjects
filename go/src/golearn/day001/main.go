package main

import (
	"fmt"
	"golearn/day001/util"
	"runtime"
	"strconv"
)

var (
	a int    = 3
	b string = "hello"
)

func AddSub(a int, b int) (int, int) {
	return a + b, a - b
}

func fo() {
	a := 8
	b := 9
	_ = a + b
}

func op() {
	var a int
	var b int
	a = 7
	b = 3
	c := a * b
	d := a / b
	e := a % b
	fmt.Printf("c=%d, d=%d, e=%d\n", c, d, e)
}

func logic() {
	var a int
	var b int
	a = 7
	b = 3
	c := a == b
	d := a > b
	e := !(a > b)
	f := c && d
	g := c || d
	fmt.Printf("c=%t, d=%t, e=%t\n", c, d, e)
	fmt.Printf("f=%t, g=%t\n", f, g)
}

func bitop() {
	a := 7
	b := 3
	c := a & b
	d := a | b
	e := a ^ b
	f := ^a

	g := a << 4
	h := a >> 5
	fmt.Printf("a=%b,b=%b,c=%b,d=%b,e=%b,f=%b,g=%b,h=%b\n", a, b, c, d, e, f, g, h)
}

func asign() {
	a := 7
	b := 3

	a = a + b
	fmt.Println(a)
	a += b
	fmt.Println(a)
	a = a / b
	fmt.Println(a)
	a /= b
	fmt.Println(a)
	a &= b
	fmt.Println(a)
	a |= b
	fmt.Println(a)
	a++
	fmt.Println(a)
}

func bit_demo() {
	var a int32 = 260
	fmt.Printf("os arch %s, int size %d\n", runtime.GOARCH, strconv.IntSize)
	fmt.Printf("260 %s\n", util.BinaryFormat(a))
	fmt.Printf("-260 %s\n", util.BinaryFormat(-a))
	fmt.Printf("260&4 %s\n", util.BinaryFormat(a&4))
	fmt.Printf("260|3 %s\n", util.BinaryFormat(a|3))
	fmt.Printf("260^7 %s\n", util.BinaryFormat(a^7))
	fmt.Printf("^260 %s\n", util.BinaryFormat(^a))
	fmt.Printf("^-260 %s\n", util.BinaryFormat(^-a))

	fmt.Printf("-260>>10 %s\n", util.BinaryFormat(-a>>10))
	fmt.Printf("-260<<3 %s\n", util.BinaryFormat(-a<<3))
}

func format() {
	var s string = "hello"
	var p *string = &s
	fmt.Printf("%p\n", p)
	fmt.Printf("%s\n", *p)
}

func ch() {
	var s string = "你好hello"
	for _, ele := range s {
		fmt.Printf("%c %d %T\n", ele, ele, ele)
	}
}

/*
多行注释
*/
func ff() {
	var f float64 = 123.4567890000000
	fmt.Printf("%f\n", f)
	fmt.Printf("%.2f\n", f)
	fmt.Printf("%e\n", f)
	fmt.Printf("%.2e\n", f)
	fmt.Printf("%g\n", f)
	fmt.Printf("%.2g\n", f)
}

// Deprecated
func fd() {
	var a int = 123
	fmt.Printf("%08d\n", a)
}

// varinit 这个函数的功能是
func varinit() {
	var a int
	var f float32
	var b byte
	var s string //TODO:这个地方
	var p *byte
	var d bool
	var A int
	fmt.Printf("a=%d f=%f b=%d s=%s p=%v d=%t A%d\n", a, f, b, s, p, d, A)
}

var i int = 9

func main() {
	fmt.Printf("%p", &i)
	i = 10
	fmt.Printf("%p", &i)
	//op()
	//logic()
	//bitop()
	//asign()
	//bit_demo()
	//format()
	//ch()
	//ff()
	//fd()
	c, _ := AddSub(5, 6)
	fmt.Println(c)
	varinit()
}
