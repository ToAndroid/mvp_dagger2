package com.app.update;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by john on 2017/7/28.
 */

public class test {
    public static void main(String[] args) throws SQLException, IOException {
        File file = new File("C:/wamp64/www/1.apk");
        if (file.exists()){
            String fileMD5 = MD5Util.getFileMD5(file);
            Log.e(test.class.getSimpleName(),fileMD5);
        }
    }
}
