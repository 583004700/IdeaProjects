package main

import (
	"errors"
	"fmt"
	"math"
	"runtime"
	"strconv"
	"unsafe"
)

// 自定义类型
type signal uint8
type ms map[string]string
type add func(a, b int) int
type user struct {
	name string
	age  int
}

// 类型别名
type semaphore = uint8

func default_value() {
	var a int
	var b byte
	var f float32
	var t bool
	var s string
	var r rune
	var arr [3]int
	var slc []int

	fmt.Printf("default value of int %d\n", a)
	fmt.Printf("default value of byte %d\n", b)
	fmt.Printf("default value of float %.2f\n", f)
	fmt.Printf("default value of bool %t\n", t)
	fmt.Printf("default value of string [%s]\n", s)
	fmt.Printf("default value of rune %d, [%c]\n", r, r)
	fmt.Printf("default int array is %v\n", arr) //取每个元素对应类型的默认值
	fmt.Printf("default slice is nil %t\n", slc == nil)
}

// 基本数据类型
func variable() {
	fmt.Printf("os arch %s, int size %d\n", runtime.GOARCH, strconv.IntSize) //int是4字节还是8字节，取决于操作系统是32位还是64位
	var a int = 5
	var b int8 = 5
	var c int16 = 5
	var d int32 = 5
	var e int64 = 5
	var f uint = 5
	var g uint8 = 5
	var h uint16 = 5
	var i uint32 = 5
	var j uint64 = 5
	fmt.Printf("a=%d, b=%d, c=%d, d=%d, e=%d, f=%d, g=%d, h=%d, i=%d, j=%d\n", a, b, c, d, e, f, g, h, i, j)
	var k float32 = 5
	var l float64 = 5
	fmt.Printf("k=%f, l=%.2f\n", k, l) //%.2f保留2位小数
	var m complex128 = complex(4, 7)
	var n complex64 = complex(4, 7)
	fmt.Printf("type of m is %T, type of n is %T\n", m, n) //%T输出变量类型
	fmt.Printf("m=%v, n=%v\n", m, n)                       //按值的本来值输出
	fmt.Printf("m=%+v, n=%+v\n", m, n)                     //在 %v 基础上，对结构体字段名和值进行展开
	fmt.Printf("m=%#v, n=%#v\n", m, n)                     //输出 Go 语言语法格式的值
	fmt.Printf("m的实部%f, m的虚部%f\n", real(m), imag(m))
	fmt.Printf("m的实部%e, m的虚部%g\n", real(m), imag(m)) //%e科学计数法，%g根据实际情况采用%e或%f格式（以获得更简洁、准确的输出）
	o := true                                        //等价于var o bool = true
	fmt.Printf("o=%t\n", o)                          //%t布尔变量
	var pointer unsafe.Pointer = unsafe.Pointer(&a)
	var p uintptr = uintptr(pointer)
	var ptr *int = &a
	fmt.Printf("p=%x pointer=%p ptr=%p\n", p, pointer, ptr) //%p输出地址，%x十六进制
	var q byte = 100                                        //byte是uint，取值范围[0,255]
	fmt.Printf("q=%d, binary of q is %b\n", q, q)           //%b输出二进制
	var r rune = '☻'                                        //rune实际上是int32，即可以表示2147483647种字符，包括所有汉字和各种特殊符号
	fmt.Printf("r=%d, r=%U\n", r, r)                        //%U Unicode 字符
	var s string = "I'm 张朝阳"
	fmt.Printf("s=%s\n", s)
	var t error = errors.New("my error")
	fmt.Printf("error is %v\n", t)
	fmt.Printf("error is %+v\n", t) //在 %v 基础上，对结构体字段名和值进行展开
	fmt.Printf("error is %#v\n", t) //输出 Go 语言语法格式的值
}

// 强制类型转换
func type_cast() {
	//高精度向低精度转换，数字很小时这种转换没问题
	var ua uint64 = 1
	i8 := int8(ua)
	fmt.Printf("i8=%d\n", i8)

	//最高位的1变成了符号位
	ua = uint64(math.MaxUint64)
	i64 := int64(ua)
	fmt.Printf("i64=%d\n", i64)

	//位数丢失
	ui32 := uint32(ua)
	fmt.Printf("ui32=%d\n", ui32)

	//单个字符可以转为int
	var i int = int('a')
	fmt.Printf("i=%d\n", i)

	//bool和int不能相互转换

	//byte和int可以互相转换
	var by byte = byte(i)
	i = int(by)
	fmt.Printf("i=%d\n", i)

	//float和int可以互相转换，小数位会丢失
	var ft float32 = float32(i)
	i = int(ft)
	fmt.Printf("i=%d\n", i)
}

func main() {
	fmt.Printf("os arch %s, int size %d\n", runtime.GOARCH, strconv.IntSize) //int是4字节还是8字节，取决于操作系统是32位还是64位

	var b byte = 100
	var r rune = '汉' //rune可以表示2147483647种字符，包括所有汉字和各种特殊符号
	fmt.Printf("b=%d, r=%d\n", b, r)

	fmt.Printf("max int64 %d, min int64 %d\n", math.MaxInt64, math.MinInt64)
	fmt.Printf("max int32 %d, min int32 %d\n", math.MaxInt32, math.MinInt32)
	fmt.Printf("max int16 %d, min int16 %d\n", math.MaxInt16, math.MinInt16)
	//强制类型转换，转成uint64
	fmt.Printf("max uint64 %d\n", uint64(math.MaxUint64))
	fmt.Println()

	default_value()
	fmt.Println()
	variable()
	fmt.Println()
	type_cast()
	fmt.Println()
	var sig signal
	var sem semaphore
	fmt.Printf("type of sig %T\n", sig) //自定义类型
	fmt.Printf("type of sem %T\n", sem) //类型别名
}

//go run data_type/basic/main.go
