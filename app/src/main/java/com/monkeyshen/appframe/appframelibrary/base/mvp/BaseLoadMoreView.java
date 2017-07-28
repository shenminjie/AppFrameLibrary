package com.monkeyshen.appframe.appframelibrary.base.mvp;



/**
 * Description:加载更多试图
 * Created by MonkeyShen on 2017/2/18.
 * Mail:shenminjie92@sina.com
 */

public interface BaseLoadMoreView extends BaseView {
    /**
     * 关闭下拉刷新
     */
    void closeRefresh();

    /**
     * 显示加载中
     */
    void showLoadMore();

    /**
     * 显示记载失败
     */
    void showLoadFailed();

    /**
     * 显示加载完成
     */
    void showLoadCompete();

    /**
     * 隐藏底部
     */
    void hideLoadFooter();

}
