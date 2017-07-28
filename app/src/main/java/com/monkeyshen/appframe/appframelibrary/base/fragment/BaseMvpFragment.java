package com.monkeyshen.appframe.appframelibrary.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monkeyshen.appframe.appframelibrary.base.mvp.BasePresenter;
import com.monkeyshen.appframe.appframelibrary.base.mvp.BaseView;

/**
 * Description:mvp Fragment处理
 * Created by MonkeyShen on 2017/1/17.
 * Mail:shenminjie92@sina.com
 */

public abstract class BaseMvpFragment<V extends BaseView, T extends BasePresenter<V>> extends BaseFragment {

    /**
     * 表现层
     */
    protected T mPresenter;

    /**
     * 创建表现层
     *
     * @return
     */
    public abstract T createPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //绑定view
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        //解绑
        if (mPresenter != null && mPresenter.isAttachView()) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }
}
