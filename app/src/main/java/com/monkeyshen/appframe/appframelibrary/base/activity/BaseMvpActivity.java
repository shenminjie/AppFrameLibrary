package com.monkeyshen.appframe.appframelibrary.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.monkeyshen.appframe.appframelibrary.base.mvp.BasePresenter;
import com.monkeyshen.appframe.appframelibrary.base.mvp.BaseView;

/**
 * Description: 适用于mvp模型的activity
 * V 视图，T 表现层，继承此类需实现createPresenter与实现V接口，否则报错
 * Created by MonkeyShen on 2017/1/16.
 * Mail:shenminjie92@sina.com
 */

public abstract class BaseMvpActivity<V extends BaseView, T extends BasePresenter<V>> extends BaseActivity {

    /**
     * 表现层presenter
     */
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    /**
     * 创建表现层
     *
     * @return Presenter
     */
    public abstract T createPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null && mPresenter.isAttachView()) {
            mPresenter.detachView();
        }
    }


}
