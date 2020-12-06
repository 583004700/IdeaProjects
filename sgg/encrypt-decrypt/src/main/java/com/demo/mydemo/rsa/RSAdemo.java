package com.demo.mydemo.rsa;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 非对称加密：RSA
 */
public class RSAdemo {
    public static void main(String[] args) throws Exception {
        String input = "硅谷";
        // 创建密钥对
        String algorithm = "RSA";
        //generateKeyToFile(algorithm,"a.pub","a.pri");
        PrivateKey privateKey = getPrivateKey("a.pri", algorithm);
        PublicKey publicKey = getPublicKey("a.pub", algorithm);

        String encode = encryptRSA(algorithm,publicKey,input);
        System.out.println("密文："+encode);
        String decode = decryptRSA(algorithm,privateKey,encode);
        System.out.println("明文："+decode);
    }

    public static PublicKey getPublicKey(String path, String algorithm) throws Exception {
        String publicKeyString = FileUtils.readFileToString(new File(path), "UTF-8");
        //创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        //创建公钥规则
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey getPrivateKey(String path, String algorithm) throws Exception {
        String privateKeyString = FileUtils.readFileToString(new File(path), "UTF-8");
        //创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        //创建私钥规则
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        return keyFactory.generatePrivate(keySpec);
    }

    public static String decryptRSA(String algorithm, Key key, String encrypted) throws Exception {
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        //对加密进行初始化
        //第一个参数：加密的模式
        //第二个参数：公钥或私钥
        cipher.init(Cipher.DECRYPT_MODE, key);
        //使用私钥加密
        byte[] bytes = cipher.doFinal(Base64.decode(encrypted));
        return new String(bytes);
    }

    public static String encryptRSA(String algorithm, Key key, String input) throws Exception {
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        //对加密进行初始化
        //第一个参数：加密的模式
        //第二个参数：公钥或私钥
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //使用私钥加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        return Base64.encode(bytes);
    }

    /**
     * 保存公钥和私钥，把公钥和私钥保存到根目录
     */
    private static void generateKeyToFile(String algorithm, String publicPath, String privatePath) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //生成私钥
        PrivateKey privateKey = keyPair.getPrivate();
        //生成公钥
        PublicKey publicKey = keyPair.getPublic();
        // 获取私钥的字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();
        // 获取公钥字节数组
        byte[] publicKeyEncoded = publicKey.getEncoded();
        String publicEncodeString = Base64.encode(publicKeyEncoded);
        String privateEncodeString = Base64.encode(privateKeyEncoded);
        //把公钥和私钥保存到根目录
        FileUtils.writeStringToFile(new File(publicPath), publicEncodeString, "UTF-8");
        FileUtils.writeStringToFile(new File(privatePath), privateEncodeString, "UTF-8");
    }
}
