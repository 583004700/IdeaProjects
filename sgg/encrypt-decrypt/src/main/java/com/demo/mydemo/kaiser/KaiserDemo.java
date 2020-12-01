package com.demo.mydemo.kaiser;

/**
 * 凯撒加密
 */
public class KaiserDemo {
    public static void main(String[] args) {
        //定义原文
        String input = "Hello World";
        int key = 3;
        //把原文右移动3位
        String s = encryptKaiser(input,key);
        System.out.println("加密==："+s);
        String s1 = decryptKaiser(s,key);
        System.out.println("解密==："+s1);
    }

    private static String decryptKaiser(String s,int k){
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            int b = aChar;
            b -= k;
            char c = (char) b;
            sb.append(c);
        }
        return sb.toString();
    }

    private static String encryptKaiser(String input,int key) {
        char[] chars = input.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            int b = aChar;
            b += key;
            char newB = (char)b;
            sb.append(newB);
        }
        return sb.toString();
    }
}
