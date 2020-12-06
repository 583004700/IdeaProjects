package com.demo.mydemo.digest;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要算法，为了防止篡改
 */
public class DigestDemo1 {
    public static void main(String[] args) throws Exception {
        //原文
        //MD5: 4124bc0a9335c27f086f24ba207a4912
        //SHA1:e0c9035898dd52fc65c41454cec9c4d2611bfb37
        //SHA-256:961b6dd3ede3cb8ecbaacbd68de040cd78eb2ed5889130cceb4c49268ea4d506
        //SHA-512:f6c5600ed1dbdcfdf829081f5417dccbbd2b9288e0b427e65c8cf67e274b69009cd142475e15304f599f429f260a661b5df4de26746459a3cef7f32006e5d1c1
        //常见的加密算法：
        //  MD5,SHA1,SHA-256,SHA-512
        String input = "aa";
//        String algorithm = "MD5";
        String algorithm = "SHA-1";
//        String algorithm = "SHA-256";
//        String algorithm = "SHA-512";
        String digest = getDigest(input, algorithm);
        System.out.println(digest);
    }

    /**
     * 获取数字摘要
     * @param input 原文
     * @param algorithm 算法
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String getDigest(String input, String algorithm) throws NoSuchAlgorithmException {
        //创建消息摘要对象
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        //执行消息摘要算法
        return toHex(messageDigest.digest(input.getBytes()));
    }

    private static String toHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            String s = Integer.toHexString(b & 0xff);
            if (s.length() == 1) {
                //高位需要补0
                s = "0" + s;
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
