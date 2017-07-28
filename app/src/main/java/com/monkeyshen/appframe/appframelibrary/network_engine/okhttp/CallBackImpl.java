package com.monkeyshen.appframe.appframelibrary.network_engine.okhttp;

import android.os.Handler;
import android.text.TextUtils;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.CallBack;

import org.json.JSONObject;

import java.io.IOException;


/**
 * Description:接口回调数据
 * Created by MonkeyShen on 2017/1/17.
 * Mail:shenminjie92@sina.com
 */
public class CallBackImpl implements okhttp3.Callback {

    /**
     * 回调返回
     */
    private CallBack mCallBack;

    /**
     * handler
     */
    private Handler mHandler;

    /**
     * gson
     */
    private Gson mGson;


    /**
     * 成功返回1
     */
    public static final String RESULT_TYPE_SUCCESS = "OK";

    /**
     * 本地出错
     * 未知错误
     */
    public static final String RESULT_TYPE_UNDEFINED = "ERROR_UNDEFINED";

    /**
     * 提示语，网络出错
     */
    public static final String NETWORK_FAILED = "网络出错";
    public static final String NETWORK_BUYS = "服务器正忙，请稍后重试";

    /**
     * 三大数据接收
     */
    public static final String KEY_MSG = "message";
    public static final String KEY_DATA = "data";
    public static final String KEY_STATUS = "status";

    /**
     * sskey 失效
     */
    public static final String CODE_SSKEY = "777";
    public static final String CODE_SSKET_S = "666";
    /**
     * 构造器
     *
     * @param callBack callBack
     * @param mGson
     */
    public CallBackImpl(CallBack callBack, Handler mHandler, Gson mGson) {
        mCallBack = callBack;
        this.mHandler = mHandler;
        this.mGson = mGson;
    }


    @Override
    public void onFailure(okhttp3.Call call, IOException e) {
        e.printStackTrace();
        LogUtils.e("网络输出------>onFailure", call.request().url() + "");
        if (mCallBack == null) {
            return;
        }
        onFailed(RESULT_TYPE_UNDEFINED, null, NETWORK_FAILED);
    }

    @Override
    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
        try {
            LogUtils.e("网络输出地址----->onResponse", call.request().url() + " ");
            if (mCallBack == null) {
                return;
            }
            if (response.isSuccessful()) {
                String responseString = response.body().string();
                LogUtils.e("网络输出字符串---->onResponse", responseString + "");
                if (TextUtils.isEmpty(responseString)) {
                    onFailed(RESULT_TYPE_UNDEFINED, null, NETWORK_BUYS);
                    LogUtils.e("tag", "输出字符串为空:" + responseString);
                    return;
                }
                JSONObject jsonObject = new JSONObject(responseString);
                String msg = jsonObject.getString(KEY_MSG);
                String status = jsonObject.getString(KEY_STATUS);
                if(CODE_SSKEY.equals(status) || CODE_SSKET_S.equals(status)){
                    /**
                     * do sth
                     */
                }

                Object t = null;
                if (jsonObject.has(KEY_DATA)) {
                    String data = jsonObject.getString(KEY_DATA);
                    if(mCallBack.mType != null){
                       t = mGson.fromJson(data, mCallBack.mType);
                    }
                    LogUtils.e("网络输出对象----->onResponse:", t + "");
                } else {
                    LogUtils.e("网络输出对象----->onResponse:", "不存在data字段");
                }
                if (RESULT_TYPE_SUCCESS.equals(status)) {
                    onSuccess(t, responseString, msg);
                } else {
                    onFailed(status, responseString, msg);
                }
            } else {
                onFailed(RESULT_TYPE_UNDEFINED, null, NETWORK_BUYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("网络输出有误---->onResponse", e.getMessage() + "' "+Thread.currentThread());
            onFailed(RESULT_TYPE_UNDEFINED, null, NETWORK_BUYS);
        }
    }

    /**
     * 主线回调
     *
     * @param status
     * @param response
     * @param msg
     */
    private void onFailed(final String status, final String response, final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallBack.onFailed(status, response, msg);
            }
        });
    }

    /**
     * 在主线程中执行的回调
     *
     * @param t
     * @param response
     * @param msg
     */
    private void onSuccess(final Object t, final String response, final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallBack.onSuccess(t, response, msg);
            }
        });
    }
}
