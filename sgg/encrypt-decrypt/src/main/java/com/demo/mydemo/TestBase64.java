package com.demo.mydemo;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class TestBase64 {
    public static void main(String[] args) {
        // base64 每三个字节一组，重新划分成四组，每组六位，最高位补0    所以原来3个字节的，要用4个字节去表示了
        // 如果字节不够时，用 = 等补齐,例如
        System.out.println(Base64.encode("1".getBytes()));

        System.out.println(Base64.encode("123".getBytes()));
    }
}
