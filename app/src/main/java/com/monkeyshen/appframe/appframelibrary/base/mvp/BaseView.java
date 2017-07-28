package com.monkeyshen.appframe.appframelibrary.base.mvp;

/**
 * Description:mvp中的视图接口，activity/fragment中实现此接口，presenter与view进行通信交互
 * Created by MonkeyShen on 2017/1/16.
 * Mail:shenminjie92@sina.com
 */
public interface BaseView {

    /**
     * 初始化view
     */
    void initView();

    /**
     * 显示loading界面
     */
    void showLoading();

    /**
     * 关闭Loading
     */
    void hideLoading();

    /**
     * 显示吐司
     *
     * @param text
     */
    void showToast(CharSequence text);

}
