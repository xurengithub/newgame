package com.xuren.game.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DecodeUtils {

    public static String md5(String str){

        byte[] md5pwd;
        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5pwd = md5.digest(str.getBytes());
//            str = new String(md5pwd);

            str = Base64.getEncoder().encodeToString(md5pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }

}
