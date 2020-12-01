package com.demo.mydemo.ascii;

public class AsciiDemo {
    public static void main(String[] args) {
//        char a = 'a';
//        int b = a;
        //打印b，在ascii当中十进制的数字对应是多少
//        System.out.println(b);

        String a = "AaZ";
        char[] chars = a.toCharArray();
        for (char aChar : chars) {
            int asciiCode = aChar;
            System.out.println(asciiCode);
        }
    }
}
