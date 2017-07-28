package com.monkeyshen.appframe.appframelibrary.network_engine.network_callback;


import java.lang.reflect.Type;

/**
 * Description:指定class 进行回调数据
 * Created by MonkeyShen on 2017/2/20.
 * Mail:shenminjie92@sina.com
 */
public abstract class ClzCallBack extends CallBack<Object> {

    /**
     * 回调
     *
     * @param type
     */
    public ClzCallBack(Type type) {
        mType = type;
    }

    public ClzCallBack() {
    }
}
