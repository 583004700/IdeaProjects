package database

import (
	"fmt"
	"os"
	"regexp"
	"strings"
)

var (
	sqlInjectRegx *regexp.Regexp
)

func init() {
	str := `(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\b(select|update|and|or|delete|insert|trancate|char|chr|into|substr|ascii|declare|exec|count|master|into|drop|execute)\b)`
	var err error
	sqlInjectRegx, err = regexp.Compile(str)
	if err != nil {
		panic(err)
	}
}

// 对用户输入执行严格的校验，不能包含特殊符号和mysql保留字
func FilteredSQLInject(input string) bool {
	return sqlInjectRegx.MatchString(strings.ToLower(input))
}

func CheckError(err error) {
	if err != nil {
		fmt.Fprintf(os.Stderr, "fatal error: %s\n", err.Error()) //stdout是行缓冲的，他的输出会放在一个buffer里面，只有到换行的时候，才会输出到屏幕。而stderr是无缓冲的，会直接输出
		os.Exit(1)
	}
}

func test() {
	str := `^(?:[0-9]+)([a-z]+)([0-9]+)` //?:捕获非匹配，即开头需要有若干个数字，但不会计入match的组里
	var err error
	sqlInjectRegx, err := regexp.Compile(str)
	if err != nil {
		panic(err)
	}
	text := "123abc456ww"
	indexes := sqlInjectRegx.FindAllStringSubmatch(text, -1)
	for _, row := range indexes {
		fmt.Println(row) //[123abc456 abc 456]
		//如果把?:去掉，结果则是[123abc456 123 abc 456]
	}
}
