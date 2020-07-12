package com.atguigu;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BitTest {
    public static void main(String[] args) throws IOException {
        //JVM使用的是大端模式
        System.out.println(1 >> 24);
        System.out.println(1);
        System.out.println(1 << 2);
        System.out.println(1 << 3);
        System.out.println(1 << 4);
        System.out.println((1<<3) & (1<<4));
        System.out.println("-----------------");
        int a = 0x12345678;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(a);
        byte[] b = baos.toByteArray();
        for(int i = 0;i<4;i++){
            System.out.println(Integer.toString(b[i])+"---"+Integer.toHexString(b[i])+"---"+Integer.toBinaryString(b[i]));
        }
    }
}
