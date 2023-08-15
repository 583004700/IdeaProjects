package math

import (
	"fmt"
)

func init() {
	fmt.Println("enter package/common/math/basic")
}

func Sum(arr []int) int {
	rect := 0
	for _, ele := range arr {
		rect += ele
	}
	return rect
}
