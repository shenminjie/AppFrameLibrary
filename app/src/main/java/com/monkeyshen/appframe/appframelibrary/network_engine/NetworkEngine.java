package com.monkeyshen.appframe.appframelibrary.network_engine;


import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.CallBack;
import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.UpLoadFileCallBack;

import java.io.File;
import java.util.Map;

/**
 * Description:网络引擎
 * Created by MonkeyShen on 2017/2/20.
 * Mail:shenminjie92@sina.com
 */

public interface NetworkEngine {

    /**
     * @param url      url
     * @param callback callback
     */
    void doGet(String url, CallBack callback);

    /**
     * 发送请求
     *
     * @param url      url
     * @param params   params
     * @param callback callback
     */
    void doPost(String url, Map<String, String> params, CallBack callback);

    /**
     * 上传文件
     *
     * @param url      url
     * @param file     file
     * @param params   params
     * @param callback callback
     */
    void doPost(String url, File file, Map<String, String> params, UpLoadFileCallBack callback);
}
