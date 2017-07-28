package com.monkeyshen.appframe.appframelibrary.network_engine.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.CallBack;
import com.monkeyshen.appframe.appframelibrary.network_engine.ProgressRequestBody;
import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.UpLoadFileCallBack;

import java.io.File;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Description:
 * Created by MonkeyShen on 2017/2/20.
 * Mail:shenminjie92@sina.com
 */

public class OkHttpHelper {


    public static final String tag = "CustomTrust";
    private static final String CLIENT_KET_PASSWORD = "213679301700631";

    private final int TIMEOUT = 30;
    /**
     * 采用单例模式使用OkHttpClient
     */
    private static OkHttpHelper mOkHttpHelperInstance;
    private static OkHttpClient mClientInstance;
    private Handler mHandler;
    private Gson mGson;

    /**
     * 单例模式，私有构造函数，构造函数里面进行一些初始化
     */
    private OkHttpHelper() {
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory=null;

        trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        try {
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,new X509TrustManager[]{trustManager},null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }


//        mClientInstance = new OkHttpClient();
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        mClientInstance = httpBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS).writeTimeout(TIMEOUT, TimeUnit.SECONDS) //设置超时
                .sslSocketFactory(sslSocketFactory).hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取实例
     *
     * @return return
     */
    public static OkHttpHelper getinstance() {
        if (mOkHttpHelperInstance == null) {
            synchronized (OkHttpHelper.class) {
                if (mOkHttpHelperInstance == null) {
                    mOkHttpHelperInstance = new OkHttpHelper();
                }
            }
        }
        return mOkHttpHelperInstance;
    }

    /**
     * 封装一个request方法，不管post或者get方法中都会用到
     */
    public void request(final Request request, final CallBack callback) {
        mClientInstance.newCall(request).enqueue(new CallBackImpl(callback, mHandler, mGson));
    }


    /**
     * 对外公开的get方法
     *
     * @param url      url
     * @param callback callback
     */
    public void get(String url, CallBack callback) {
        Request request = buildRequest(url, null, HttpMethodType.GET);
        request(request, callback);
    }

    /**
     * 对外公开的post方法
     *
     * @param url      url
     * @param params   params
     * @param callback callback
     */
    public void post(String url, Map<String, String> params, CallBack callback) {
        Request request = buildRequest(url, params, HttpMethodType.POST);
        request(request, callback);
    }

    /**
     * 构建请求对象
     *
     * @param url    url
     * @param params params
     * @param type   type
     * @return return
     */
    private Request buildRequest(String url, Map<String, String> params, HttpMethodType type) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (type == HttpMethodType.GET) {
            builder.get();
        } else if (type == HttpMethodType.POST) {
            builder.post(buildRequestBody(params));
        }
        return builder.build();
    }

    /**
     * 通过Map的键值对构建请求对象的body
     *
     * @param params params
     * @return return
     */
    private RequestBody buildRequestBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entity : params.entrySet()) {
                //判断是否为空
                if (entity.getValue() instanceof String) {
                    if (entity.getValue() != null) {
                        builder.add(entity.getKey(), entity.getValue() == null ? "" : entity.getValue());
                    }
                }

            }
        }
        return builder.build();
    }


    /**
     * 通过Map的键值对构建请求对象的body
     *
     * @param url      url
     * @param file     file
     * @param params   params
     * @param callback callback
     */
    public void postFile(String url, File file, Map<String, String> params, UpLoadFileCallBack callback) {
        ProgressRequestBody requestFile =    // 根据文件格式封装文件
                new ProgressRequestBody(file, callback, mHandler);
        RequestBody requestBody;

        String str = file.getName();
        if(str.endsWith(".tmp")){
            str = str.substring(0,str.length()-4);
        }


        MultipartBody.Builder mulBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)   // multipart/form-data
                .addFormDataPart("file",str, requestFile);
        for (Map.Entry<String, String> entity : params.entrySet()) {
            //判断是否为空
            if (entity.getValue() instanceof String) {
                if (entity.getValue() != null) {
                    mulBuilder.addFormDataPart(entity.getKey(),
                            entity.getValue() == null ? "" : entity.getValue());
                }
            }
        }
        requestBody = mulBuilder.build();
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.post(requestBody);
        request(builder.build(), callback);
    }

    /**
     * 这个枚举用于指明是哪一种提交方式
     */
    enum HttpMethodType {
        GET,
        POST
    }
}
