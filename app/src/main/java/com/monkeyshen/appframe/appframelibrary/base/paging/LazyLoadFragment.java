package com.monkeyshen.appframe.appframelibrary.base.paging;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.monkeyshen.appframe.appframelibrary.base.fragment.BaseMvpFragment;
import com.monkeyshen.appframe.appframelibrary.base.mvp.BasePresenter;
import com.monkeyshen.appframe.appframelibrary.base.mvp.BaseView;

/**
 * 基于mvp fragment ，实现懒加载机制
 * 懒加载 碎片
 * Created by shenminjie on 2016/12/17.
 */

public abstract class LazyLoadFragment<V extends BaseView, T extends BasePresenter<V>> extends BaseMvpFragment<V, T> {

    /**
     * 是否显示给用户
     */
    protected boolean isVisibleToUser;

    /**
     * 是否初始化view
     */
    protected boolean isViewInit;

    /**
     * 是否已经初始化过数据
     */
    protected boolean isInitData;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.isViewInit = true;
        prepareInit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareInit();
    }

    /**
     * 准备初始化
     */
    public void prepareInit() {
        if (isViewInit && isVisibleToUser && !isInitData) {
            lazyLoad();
            isInitData = true;
        }
    }

    /**
     * 懒加载
     */
    public abstract void lazyLoad();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInitData = false;
        isViewInit = false;
    }


}
