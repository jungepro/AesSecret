package com.hui.util;

import cn.hutool.core.util.HexUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
/**
 * @author: 公众号：程序员辉哥
 */

/**
 * java使用AES加密解密 AES-128-ECB加密
 * 与mysql数据库aes加密算法通用
 * 数据库aes加密解密
 * -- 加密
 * SELECT to_base64(AES_ENCRYPT('www.gowhere.so','jkl;POIU1234++=='));
 * -- 解密
 * SELECT AES_DECRYPT(from_base64('Oa1NPBSarXrPH8wqSRhh3g=='),'jkl;POIU1234++==');
 */
@Slf4j
public class CdAesUtil {

    public static String cKey = "aes@secret";

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            log.error("加密===Key为空null");
            return null;
        }
        if (sKey.length() != 16) {
            log.error("加密===Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
        //  return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return HexUtil.encodeHexStr(encrypted).toUpperCase();
    }


    // 解密
    public static String Decrypt(String sSrc, String sKey) {
        try {
            if (sKey == null) {
                log.error("解密===Key为空null");
                return null;
            }
            if (sKey.length() != 16) {
                log.error("解密===Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //   byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            byte[] encrypted1 = HexUtil.decodeHex(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, StandardCharsets.UTF_8);
            return originalString;

        } catch (Exception ex) {
            log.error("解密异常===：{}", ex.getMessage());
            return sSrc;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         *//*
        String cKey = "jkl;POIU1234++==";
        // 需要加密的字串
        String cSrc = "";
        System.out.println(cSrc);
        // 加密
        String enString = CdAesUtil.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);
        enString = "y5nz0fgYRV+jUERBXPNSZg==)";
        // 解密
        String DeString = CdAesUtil.Decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);*/

        System.out.println(CdAesUtil.Encrypt("642123197312260534", "cdhy@Qgfsn-dmbsP"));
        System.out.println(CdAesUtil.Decrypt("Yhv/wzFvb0LD32sDLyO7YuRQCdM1l0ysGTf9FkfffPA=", "cdhy@Qgfsn-dmbsP"));

    }
}
