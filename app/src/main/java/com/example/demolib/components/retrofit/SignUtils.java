package com.example.demolib.components.retrofit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by john on 2017/6/22.
 */

public class SignUtils {

    public static String getPackageSignInfo(Context context, String packageName) {
        try {
            PackageInfo pis = context.getPackageManager().
                    getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Signature[] sigs = pis.signatures;
            String sig = new String(sigs[0].toChars());
            return sig;
        } catch (Exception e) {
            return "";
        }
    }

    public static String MD5Encode(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String toHexString = Integer.toHexString(b & 255);
                if (toHexString.length() == 1) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(toHexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


}
