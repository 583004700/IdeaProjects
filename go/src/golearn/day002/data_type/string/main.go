package main

import (
	"fmt"
	"strconv"
	"strings"
)

func asign_string() {
	s1 := "My name is 张朝阳☻"                        //字符串里可以包含任意Unicode字条
	s2 := "He say:\"I'm fine.\" \n\\Thank\tyou.\\" //包含转义字符
	s3 := `here is first line.	

	there is third line.
	` //反引号里的转义字符无效。反引号里的原封不动地输出，包括空白符和换行符
	fmt.Println("s1")
	fmt.Println(s1)
	fmt.Println("s2")
	fmt.Println(s2)
	fmt.Println("s3")
	fmt.Println(s3)
}

func string_op() {
	s := "born to win, born to die."
	fmt.Printf("sentence length %d\n", len(s))
	fmt.Printf("\"s\" length %d\n", len("s"))  //英文字母的长度为1
	fmt.Printf("\"中\"  length %d\n", len("中")) //一个汉字占3个长度
	arr := strings.Split(s, " ")
	fmt.Printf("arr[3]=%s\n", arr[3])
	fmt.Printf("contain die %t\n", strings.Contains(s, "die"))          //包含子串
	fmt.Printf("contain wine %t\n", strings.Contains(s, "wine"))        //包含子串
	fmt.Printf("first index of born %d\n", strings.Index(s, "born"))    //寻找子串第一次出现的位置
	fmt.Printf("last index of born %d\n", strings.LastIndex(s, "born")) //寻找子串最后一次出现的位置
	fmt.Printf("begin with born %t\n", strings.HasPrefix(s, "born"))    //以xxx开头
	fmt.Printf("end with die. %t\n", strings.HasSuffix(s, "die."))      //以xxx结尾
}

// 字符串拼接
func string_join() {
	s1 := "Hello"
	s2 := "how"
	s3 := "are"
	s4 := "you"
	merged := s1 + " " + s2 + " " + s3 + " " + s4
	fmt.Println(merged)
	merged = fmt.Sprintf("%s %s %s %s", s1, s2, s3, s4)
	fmt.Println(merged)
	merged = strings.Join([]string{s1, s2, s3, s4}, " ")
	fmt.Println(merged)
	//当有大量的string需要拼接时，用strings.Builder效率最高
	sb := strings.Builder{}
	sb.WriteString(s1)
	sb.WriteString(" ")
	sb.WriteString(s2)
	sb.WriteString(" ")
	sb.WriteString(s3)
	sb.WriteString(" ")
	sb.WriteString(s4)
	sb.WriteString(" ")
	merged = sb.String()
	fmt.Println(merged)
}

func string_impl() {
	s1 := "My name is 张朝阳"
	arr := []byte(s1)
	brr := []rune(s1)
	fmt.Printf("last byte %d\n", arr[len(arr)-1]) //string可以转换为[]byte或[]rune类型
	fmt.Printf("last byte %c\n", arr[len(arr)-1]) //byte或rune可以转为string
	fmt.Printf("last rune %d\n", brr[len(brr)-1])
	fmt.Printf("last rune %c\n", brr[len(brr)-1])
	L := len(s1)
	fmt.Printf("string len %d byte array len %d rune array len %d\n", L, len(arr), len(brr))
	for _, ele := range s1 {
		fmt.Printf("%c ", ele) //string中的每个元素是字符
	}
	fmt.Println()
	for i := 0; i < L; i++ {
		fmt.Printf("%c ", s1[i]) //[i]前面应该出现数组或切片，这里自动把string转成了[]byte（而不是[]rune）
	}
}

func string_other_convert() {
	var err error
	var i int = 8
	var i64 int64 = int64(i)
	//int转string
	var s string = strconv.Itoa(i) //内部调用FormatInt
	s = strconv.FormatInt(i64, 10)
	//string转int
	i, err = strconv.Atoi(s)
	//string转int64
	i64, err = strconv.ParseInt(s, 10, 64)

	//float转string
	var f float64 = 8.123456789
	s = strconv.FormatFloat(f, 'f', 2, 64) //保留2位小数
	fmt.Println(s)
	//string转float
	f, err = strconv.ParseFloat(s, 64)

	//string<-->[]byte
	var arr []byte = []byte(s)
	s = string(arr)

	//string<-->[]rune
	var brr []rune = []rune(s)
	s = string(brr)

	fmt.Printf("err %v\n", err)
}

func main() {
	// asign_string()
	// fmt.Println()
	// string_op()
	// fmt.Println()
	// string_join()
	// fmt.Println()
	string_impl()
	// string_other_convert()
}

//go run data_type/string/main.go
