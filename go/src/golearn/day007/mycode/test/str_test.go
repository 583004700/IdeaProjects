package test

import (
	"bytes"
	"fmt"
	"testing"
)

// go test -v day007/mycode/test/str_test.go  -run=Buffer -count=2
func TestBuffer(b *testing.T) {
	hello := "hello"
	golang := "golang"
	var buffer bytes.Buffer
	buffer.WriteString(hello)
	buffer.WriteString(",")
	buffer.WriteString(golang)
	fmt.Printf(buffer.String())
}

const LOOP int = 100

// go test -v day007/mycode/test/str_test.go  -run=^$ -bench=Buffer -count=10
func BenchmarkBuffer(b *testing.B) {
	hello := "hello"
	golang := "golang"
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		var buffer bytes.Buffer
		buffer.Grow(LOOP * 12)
		for i := 0; i < LOOP; i++ {
			buffer.WriteString(hello)
			buffer.WriteString(",")
			buffer.WriteString(golang)
		}
		_ = buffer.String()
	}
}
