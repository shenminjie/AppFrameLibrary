package com.monkeyshen.appframe.appframelibrary.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.blankj.utilcode.utils.ToastUtils;
import com.monkeyshen.appframe.appframelibrary.base.mvp.BaseView;
import com.monkeyshen.appframe.appframelibrary.dialog.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:使用 butterknife，重写抽象方法  基础类fragment
 * Created by MonkeyShen on 2017/1/17.
 * Mail:shenminjie92@sina.com
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    /**
     * 解绑
     */
    private Unbinder mUnbinder;

    /**
     * 加载中
     */
    private LoadingDialog mLoadingDialog;


    /**
     * mContainer
     */
    private View mContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingDialog = new LoadingDialog(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //不要随便改动，实现onCreateContianerView即可
        mContainer = onCreateFragmentView(inflater, container, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, mContainer);

        return mContainer;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onDestroyView() {
        //处理加载中操作
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        mUnbinder.unbind();
        super.onDestroyView();
    }

    /**
     * 获取view
     * 在其中封装butterknife代码
     * 使用者只需要实现此方法，即可，使用butterknife会处理绑定
     *
     * @param inflater           inflater
     * @param container          container
     * @param savedInstanceState savedInstanceState
     * @return
     */
    public abstract View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


    @Override
    public abstract void initView();

    @Override
    public void showLoading() {
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(CharSequence text) {
        ToastUtils.showShortToast(text);
    }

}
