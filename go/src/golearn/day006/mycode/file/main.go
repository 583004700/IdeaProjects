package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strings"
)

func main() {
	if fin, err := os.Open("D:\\IdeaProjects\\go\\src\\golearn\\day006\\mycode\\file\\a.txt"); err != nil {
		fmt.Println(err)
		return
	} else {
		defer fin.Close()
		reader := bufio.NewReader(fin)
		for {
			if line, err := reader.ReadString('\n'); err == nil {
				line = strings.TrimRight(line, "\n")
				fmt.Println(line)
			} else {
				if err == io.EOF {
					if len(line) > 0 {
						fmt.Println(line)
					}
				} else {
					fmt.Println(err)
				}
				break
			}
		}
	}
}
