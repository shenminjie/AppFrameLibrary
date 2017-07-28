package com.monkeyshen.appframe.appframelibrary.network_engine.network_callback;


/**
 * Description:在此基础上进行数据封装
 * Created by MonkeyShen on 2017/1/17.
 * Mail:shenminjie92@sina.com
 */
public interface BaseCallBack<T> {

    /**
     * 返回对象
     * 网络连接成功，并且业务上成功
     *
     * @param t
     * @param response
     */
    void onSuccess(T t, String response, String msg);

    /**
     * 网络返回失败--连不上，业务失败
     *
     * @param status 状态码
     * @param msg    错误信息
     */
    void onFailed(String status, String response, String msg);

}