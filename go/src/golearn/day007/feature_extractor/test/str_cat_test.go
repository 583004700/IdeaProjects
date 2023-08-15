package test

import (
	"bytes"
	"fmt"
	"strconv"
	"strings"
	"testing"
)

/**
单元测试和基准测试必须放在以_test.go为后缀的文件里。
单元测试函数以Test开头，基准测试函数以Benchmark开头。
单元测试以*testing.T为参数，函数无返回值。
基准测试以*testing.B为参数，函数无返回值。
*/

const LOOP int = 100

func BenchmarkStrCatWithOperator(b *testing.B) {
	hello := "hello"
	golang := "golang"
	b.ResetTimer() //重置计时器，避免上面的初始化工作带来的干扰
	for i := 0; i < b.N; i++ {
		var str string
		for i := 0; i < LOOP; i++ { //使用“+”连接很多字符串
			str += hello + "," + golang
		}
	}
}

func BenchmarkStrCatWithJoin(b *testing.B) {
	hello := "hello"
	golang := "golang"
	arr := make([]string, LOOP*2)
	for i := 0; i < LOOP; i++ {
		arr = append(arr, hello)
		arr = append(arr, golang)
	}
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		_ = strings.Join(arr, ",")
	}
}

func BenchmarkStrCatWithBuffer(b *testing.B) {
	hello := "hello"
	golang := "golang"
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		var buffer bytes.Buffer
		buffer.Grow(LOOP * 12) //如果能预估将来需要buffer的大小，则通过Grow()来指定，以获得最佳性能。这一行不是必须的
		for i := 0; i < LOOP; i++ {
			buffer.WriteString(hello)
			buffer.WriteString(",")
			buffer.WriteString(golang)
		}
		_ = buffer.String()
	}
}

/**
在go_performance目录下执行：
go test -bench=StrCat -run=none -benchtime=2s -cpuprofile=data/cpu.prof -memprofile=data/mem.prof feature_extractor/test/str_cat_test.go
-bench=StrCat	运行匹配上StrCat的基准测试函数，-bench=.表示运行所有基准测试函数
-run=none	默认情况下go test会运行单元测试(代码里的TestBuffer函数)，为了防止单元测试的输出影响我们查看基准测试的结果，run后面加一个正则，只运行匹配上的单元测试函数
-benchtime=2s	默认情况下每个函数运行1秒
*/

/**
goos: darwin
goarch: amd64
pkg: go_search_engine/go_performance
BenchmarkStrCatWithOperator-4             200000             19946 ns/op
BenchmarkStrCatWithJoin-4                 500000              4804 ns/op
BenchmarkStrCatWithBuffer-4               500000              4389 ns/op
PASS
结论：当需要连接很多字符串，用加号连接是很慢的，strings.Join和bytes.Buffer性能差不多，如果通过buffer.Grow()能准确预估所需要内存，则bytes.Buffer性能会更好。
函数名后面有个-4，4就是运行时GOMAXPROCS的值，默认情况下GOMAXPROCS就等于机器的核数。
第2列是函数运行次数，第3列是每次运行的耗时。
*/

// 单元测试。在go_performance目录下执行：go test -v go_test.go -timeout=20m -count=2
func TestBuffer(t *testing.T) {
	hello := "hello"
	golang := "golang"
	var buffer bytes.Buffer
	buffer.WriteString(hello)
	buffer.WriteString(",")
	buffer.WriteString(golang)
	fmt.Println(buffer.String()) //hello,golang
}

func BenchmarkInt2StrWithItoA(b *testing.B) {
	var n int = 64
	for i := 0; i < b.N; i++ {
		_ = strconv.Itoa(n)
	}
}

func BenchmarkInt2StrWithFormat(b *testing.B) {
	var n int = 64
	for i := 0; i < b.N; i++ {
		_ = strconv.FormatInt(int64(n), 10)
	}
}

func BenchmarkInt2StrWithSprintf(b *testing.B) {
	var n int = 64
	for i := 0; i < b.N; i++ {
		_ = fmt.Sprintf("%d", n)
	}
}

/**
在go_performance目录下执行：
go test -bench=Int2Str -run=^$ -benchmem
-run=^$跟-run=none效果等同，不运行所有的单元测试函数。
-benchmem可以输出：每次操作分配几个字节；每次操作进行几次内存分配

goos: darwin
goarch: amd64
pkg: go_search_engine/go_performance
BenchmarkInt2StrWithItoA-4              500000000                3.95 ns/op            0 B/op          0 allocs/op
BenchmarkInt2StrWithFormat-4            300000000                4.06 ns/op            0 B/op          0 allocs/op
BenchmarkInt2StrWithSprintf-4           10000000               107 ns/op              16 B/op          2 allocs/op

结论：int转string，strconv.Itoa和strconv.FormatInt性能相当，fmt.Sprintf特别慢，因为它需要申请更多的内存。
*/
