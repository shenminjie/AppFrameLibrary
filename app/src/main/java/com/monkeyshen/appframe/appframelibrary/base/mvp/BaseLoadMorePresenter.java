package com.monkeyshen.appframe.appframelibrary.base.mvp;

/**
 * Description:基础类获取更多数据
 * Created by MonkeyShen on 2017/2/18.
 * Mail:shenminjie92@sina.com
 */

public abstract class BaseLoadMorePresenter<T extends BaseLoadMoreView> extends BasePresenter<T> {

    /**
     * 当前页面
     */
    protected int mCurrentPage;

    /**
     * 是否正在加载中
     */
    protected boolean isLoadingData;


    /**
     * 每次加载10数据
     */
    public static final int PAGE_SIZE = 20;


}
