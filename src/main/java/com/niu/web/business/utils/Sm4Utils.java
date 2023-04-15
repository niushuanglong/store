package com.niu.web.business.utils;


import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class Sm4Utils {
    static{
        Security.addProvider(new BouncyCastleProvider());
    }
    //key必须是16字节，即128位
    final static String key = "niushuanglong000";

    //指明加密算法和秘钥
    static SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", key.getBytes());

    //加密为16进制，也可以加密成base64/字节数组
    public static String encryptSm4(String plaintext) {
        return sm4.encryptHex(plaintext);
    }

    //解密
    public static String decryptSm4(String ciphertext) {
        return sm4.decryptStr(ciphertext);
    }

    public static void main(String[] args) {
        String content = "hello sm4";
        String plain = encryptSm4(content);
        String cipher = decryptSm4(plain);
        System.out.println(plain + "\n" + cipher);
    }
}
