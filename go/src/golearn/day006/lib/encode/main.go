package main

import (
	"compress/zlib" //compress下还有其他压缩算法，如bzip、gip、lzw
	"encoding/base64"
	"encoding/json"
	"fmt"
	"golearn/day006/lib/encode/data"
	"io"
	"os"

	"github.com/bytedance/sonic"
	"github.com/mailru/easyjson"
)

var user = data.User{Name: "张三", Gender: 1, Age: 18}

func use_json() {
	if bs, err := json.Marshal(user); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(string(bs))
		var u data.User
		if err = json.Unmarshal(bs, &u); err != nil {
			fmt.Println(err)
		} else {
			fmt.Printf("name %s gender %d age %d\n", u.Name, u.Gender, u.Age)
		}
	}
}

func easy_json() {
	if bs, err := easyjson.Marshal(user); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(string(bs))
		var u data.User
		if err = easyjson.Unmarshal(bs, &u); err != nil {
			fmt.Println(err)
		} else {
			fmt.Printf("name %s gender %d age %d\n", u.Name, u.Gender, u.Age)
		}
	}
}

func use_sonic() {
	if bs, err := sonic.Marshal(user); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(string(bs))
		var u data.User
		if err = sonic.Unmarshal(bs, &u); err != nil {
			fmt.Println(err)
		} else {
			fmt.Printf("name %s gender %d age %d\n", u.Name, u.Gender, u.Age)
		}
	}
}

func base_64() {
	bs := []byte{1, 2, 3, 4, 5, 6, 7, 8, 9}
	str := base64.StdEncoding.EncodeToString(bs)
	fmt.Println(str)
	if cont, err := base64.StdEncoding.DecodeString(str); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(cont)
	}

	//经常把小图片序列化成base64编码
	if fin, err := os.Open("data/gopher.png"); err != nil {
		fmt.Println(err)
	} else {
		defer fin.Close()
		bs := make([]byte, 10*1024)
		n, _ := fin.Read(bs)
		str := base64.StdEncoding.EncodeToString(bs[:n])
		fmt.Println(str)
	}
}

func compress() {
	fin, err := os.Open("data/readme.md")
	if err != nil {
		fmt.Println(err)
		return
	}
	stat, _ := fin.Stat()
	fmt.Printf("压缩前文件大小 %dB\n", stat.Size())

	fout, err := os.OpenFile("data/readme.zlib", os.O_CREATE|os.O_TRUNC|os.O_WRONLY, 0666)
	if err != nil {
		fmt.Println(err)
		return
	}

	bs := make([]byte, 1024)
	writer := zlib.NewWriter(fout) //压缩写入
	for {
		n, err := fin.Read(bs)
		if err != nil {
			if err == io.EOF {
				break
			} else {
				fmt.Println(err)
			}
		} else {
			writer.Write(bs[:n])
		}
	}
	writer.Close()
	fout.Close()
	fin.Close()

	fin, err = os.Open("data/readme.zlib")
	if err != nil {
		fmt.Println(err)
		return
	}
	stat, _ = fin.Stat()
	fmt.Printf("压缩后文件大小 %dB\n", stat.Size())

	reader, err := zlib.NewReader(fin) //解压
	io.Copy(os.Stdout, reader)         //把一个流拷贝到另外一个流
	reader.Close()
	fin.Close()
}

func main() {
	use_json()
	fmt.Println()
	easy_json()
	fmt.Println()
	use_sonic()
	fmt.Println()
	base_64()
	fmt.Println()
	compress()
}

//go run lib/encode/main.go
