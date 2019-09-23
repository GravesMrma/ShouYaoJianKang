package com.wuhanzihai.rbk.ruibeikang.utils;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author baihe
 */
public class AES {
    private static final String IV_STRING = "B8jOOlPHcZE4chD0";

    public static String encryptAES(String content, String key) {
        try {
            byte[] byteContent = content.getBytes("UTF-8");
            // 注意，为了能与 iOS 统一
            // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
            byte[] enCodeFormat = key.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(byteContent);
            // 同样对加密后数据进行 base64 编码
            Log.e("bianma",Base64.encodeToString(encryptedBytes,Base64.NO_WRAP));
            return URLEncoder.encode(Base64.encodeToString(encryptedBytes,Base64.NO_WRAP));
        } catch (Exception e) {
        }
        return null;
    }
}
