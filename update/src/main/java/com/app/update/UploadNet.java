package com.app.update;

import android.text.TextUtils;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by jb on 2017/7/24.
 */

public class UploadNet {
    private final static int READ_TIMEOUT = 3000;
    private final static int CONNECT_TIMEOUT = 3000;
    private static boolean DEBUG = true;

    public static String get(String urlStr) {
        if (TextUtils.isEmpty(urlStr)) return null;

        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setRequestMethod("GET");
            int responseCode = urlConnection.getResponseCode();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            if (responseCode == 200 || responseCode == 206) {
                byte[] buffer = new byte[1024];
                InputStream inputStream = urlConnection.getInputStream();

                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.close();

            }
            String s = new String(bos.toByteArray());
            if (DEBUG) {
                Log.i("net", s);
            }


            return s;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void downFile(String downUrl, String savePath, String filweName, DownFileListenter listenter) {

        if (TextUtils.isEmpty(downUrl) || TextUtils.isEmpty(savePath) || TextUtils.isEmpty(filweName))
            return;

        try {
            URL url = new URL(downUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setRequestMethod("GET");
            int responseCode = urlConnection.getResponseCode();


            if (responseCode == 200 || responseCode == 206) {

                File dirFile = new File(savePath);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                File saveFile = new File(savePath, filweName);
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }

                byte[] buffer = new byte[1024];
                InputStream inputStream = urlConnection.getInputStream();
                int contentLength = urlConnection.getContentLength();


                FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
                long size = 0;
                int len = 0;
                if (listenter != null) {
                    listenter.onStart();
                }
                while ((len = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    size += len;
                    if (listenter != null) {
                        listenter.onProgress(contentLength, size);
                    }
                }
                fileOutputStream.close();
                if (listenter != null) {
                    listenter.onFinish();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (listenter != null) {
                listenter.onDownError();
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
            if (listenter != null) {
                listenter.onDownError();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            if (listenter != null) {
                listenter.onDownError();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (listenter != null) {
                listenter.onDownError();
            }
        }
    }

    public interface DownFileListenter {
        void onStart();

        void onProgress(long total, long progress);

        void onFinish();

        void onDownError();
    }
}
