package common

import "bytes"

func ZeroPadding(ciphertext []byte, blockSize int) []byte {
	padding := blockSize - len(ciphertext)%blockSize
	padtext := bytes.Repeat([]byte{0}, padding) //用0填充
	return append(ciphertext, padtext...)
}

// ZeroUnPadding 这种方法不严谨，末尾的0不一定全是padding出来的
func ZeroUnPadding(origData []byte) []byte {
	return bytes.TrimFunc(origData,
		func(r rune) bool {
			return r == rune(0) //截掉尾部连续的0
		})
}
