package com.monkeyshen.appframe.appframelibrary.network_engine;



import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.CallBack;
import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.UpLoadFileCallBack;
import com.monkeyshen.appframe.appframelibrary.network_engine.okhttp.OkHttpHelper;

import java.io.File;
import java.util.Map;

/**
 * Description:
 * Created by MonkeyShen on 2017/2/20.
 * Mail:shenminjie92@sina.com
 */

public class NetworkEngineImpl implements NetworkEngine {


    @Override
    public void doGet(String url, CallBack callback) {
        OkHttpHelper.getinstance().get(url, callback);
    }

    @Override
    public void doPost(String url, Map<String, String> params, CallBack callback) {
        OkHttpHelper.getinstance().post(url, params, callback);
    }

    @Override
    public void doPost(String url, File file, Map<String, String> params, UpLoadFileCallBack callback) {
        OkHttpHelper.getinstance().postFile(url, file, params, callback);
    }


}
