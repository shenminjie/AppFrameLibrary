package com.monkeyshen.appframe.appframelibrary.network_engine.network_callback;


import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.CallBack;

/**
 * Description:上传回调监听
 * Created by MonkeyShen on 2017/2/10.
 * Mail:shenminjie92@sina.com
 */

public abstract class UpLoadFileCallBack<T> extends CallBack<T> {

    /**
     * 成功进度
     *
     * @param percentage
     */
    public abstract void onProgressUpdate(int percentage, long uploaded, long total);

    /**
     * 上传过程中失败
     */
    public abstract void onError();

    /**
     * 上传过程中完成
     */
    public abstract void onFinish();
}
