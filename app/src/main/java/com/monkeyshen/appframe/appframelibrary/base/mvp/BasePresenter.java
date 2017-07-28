package com.monkeyshen.appframe.appframelibrary.base.mvp;


import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Description:mvp中的presenter接口，与view进行交互
 * Created by MonkeyShen on 2017/1/16.
 * Mail:shenminjie92@sina.com
 */
public abstract class BasePresenter<T extends BaseView> {

    /**
     * 若引用
     */
    private Reference<T> mReference;


    /**
     * 绑定view
     *
     * @param view
     */
    public void attachView(T view) {
        mReference = new WeakReference<T>(view);
    }

    /**
     * 取消绑定
     */
    public void detachView() {
        if (mReference != null) {
            mReference.clear();
            mReference = null;
        }
    }

    /**
     * 是否绑定了界面
     *
     * @return
     */
    public boolean isAttachView() {
        return mReference != null && mReference.get() != null;
    }

    /**
     * 获取view
     *
     * @return
     */
    public T getView() {
        return mReference.get();
    }


}
