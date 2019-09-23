package com.wuhanzihai.rbk.ruibeikang.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



/**
 * author : Snow651
 * date   : 2018/12/14 14:00
 * desc   :
 */
public class MD5Util {
    /**
     * 字符串加密
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String shaEncrypt(String str) {
        String strSrc = "";
        try {
            strSrc = URLEncoder.encode(str,"UTF-8");
            MessageDigest md = null;
            String strDes = null;
            byte[] bt = strSrc.getBytes();
            try {
                md = MessageDigest.getInstance("SHA-256");// 将此换成SHA-1、SHA-512、SHA-384等参数
                md.update(bt);
                strDes = bytes2Hex(md.digest()); // to HexString
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
            return strDes;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strSrc;
    }

    /**
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
