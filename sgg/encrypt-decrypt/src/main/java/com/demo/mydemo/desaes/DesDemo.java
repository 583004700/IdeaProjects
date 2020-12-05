package com.demo.mydemo.desaes;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DesDemo {
    public static void main(String[] args) throws Exception{
        //原文
        String input = "硅谷";
        //定义key
        //如果使用des进行加密，那么密钥必须是8个字节
        String key = "12345678";
        //算法
        String transformation = "DES";
        //加密类型
        String algorithm = "DES";
        //加密
        String encryptDES = encryptDES(input,key,transformation,algorithm);
        System.out.println("加密："+encryptDES);
        //解密
        String decryptDES = decryptDES(encryptDES,key,transformation,algorithm);
        System.out.println("解密："+decryptDES);
    }

    public static String decryptDES(String encryptDES,String key,String transformation, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),algorithm);
        //Cipher.DECRYPT_MODE:解密模式
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        byte[] bytes = cipher.doFinal(Base64.decode(encryptDES));
        return new String(bytes);
    }

    public static String encryptDES(String input,String key,String transformation, String algorithm) throws Exception{
        //创建加密对象
        Cipher cipher = Cipher.getInstance(transformation);

        //创建加密规则
        //第一个参数：表示key的字节
        //第二个参数：表示加密类型
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),algorithm);
        //进行加密初始化
        //第一个参数表示模式，加密模式，解密模式
        //第二个参数表示加密规则
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        //调用加密方法
        //参数表示原文的字节数组
        byte[] bytes = cipher.doFinal(input.getBytes());

//        for (byte aByte : bytes) {
//            System.out.println(aByte);
//        }
//        //打印密文
//        //如果直接打印密文会出现乱码
//        //是因为在编码表上面找不到对应的字符
//        System.out.println(new String(bytes));

        //创建base64对象
        String encode = Base64.encode(bytes);
        //System.out.println(encode);
        return encode;
    }
}
