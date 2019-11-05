package com.bw.zhaozhipeng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Copyright (C)
 * <p>
 * FileName: NetUtil
 * <p>
 * Author: zhaozhipeng
 * <p>
 * Date: 2019/11/5 8:44
 * <p>
 * Description:
 */
public class NetUtil {
    private NetUtil() {

    }

    private static class NetUtils {
        private static NetUtil netUtil = new NetUtil();
    }

    public static NetUtil getInstance() {
        return NetUtils.netUtil;
    }

    public interface Shared {
        void doGetSuccess(String json);

        void doGetFiled(String error);

        void doPhotoSuccess(Bitmap bitmap);

        void doPhotoFiled(String error);
    }

    private String io2String(InputStream inputStream) throws IOException {
        StringBuffer buffer = new StringBuffer();
        byte[] bytes = new byte[1024];
        int p = 0;
        while ((p = inputStream.read(bytes)) != -1) {
            String json = new String(bytes, 0, p);
            buffer.append(json);
        }
        return buffer.toString();
    }

    private Bitmap io2Bitmap(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }

    @SuppressLint("StaticFieldLeak")
    public void doGet(final String http, final Shared shared) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                HttpURLConnection connection = null;
                InputStream inputStream = null;
                try {
                    URL url = new URL(http);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    if (connection.getResponseCode() == 200) {
                        inputStream = connection.getInputStream();
                        return io2String(inputStream);
                    } else {
                        Log.d("TAG", "请求失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (TextUtils.isEmpty(s)) {
                    shared.doGetFiled("请检查doInBackground代码");
                } else {
                    shared.doGetSuccess(s);
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void doGetPhoto(final String http, final Shared shared) {
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                HttpURLConnection connection = null;
                InputStream inputStream = null;
                try {
                    URL url = new URL(http);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    if (connection.getResponseCode() == 200) {
                        inputStream = connection.getInputStream();
                        return io2Bitmap(inputStream);
                    } else {
                        Log.d("TAG", "请求图片失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {
                    shared.doPhotoSuccess(bitmap);
                } else {
                    shared.doPhotoFiled("请检查doInBackground代码");
                }
            }
        }.execute();
    }


    public boolean iswang(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean iswifi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isyidong(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        } else {
            return false;
        }
    }
}
