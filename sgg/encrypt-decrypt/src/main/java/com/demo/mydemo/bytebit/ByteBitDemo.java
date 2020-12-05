package com.demo.mydemo.bytebit;

public class ByteBitDemo {
    public static void main(String[] args) {
        String a = "尚";
        byte[] bytes = a.getBytes();
        for (byte aByte : bytes) {
            int c = aByte;
            System.out.println(c);
            String s = Integer.toBinaryString(c);
            System.out.println(s);
        }
    }
}
