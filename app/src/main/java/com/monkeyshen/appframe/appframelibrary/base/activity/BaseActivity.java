package com.monkeyshen.appframe.appframelibrary.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.monkeyshen.appframe.appframelibrary.R;
import com.monkeyshen.appframe.appframelibrary.base.mvp.BaseView;
import com.monkeyshen.appframe.appframelibrary.dialog.LoadingDialog;
import com.monkeyshen.appframe.appframelibrary.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Description:基类activity
 * 使用CustomToolbar (id 默认为R.id.toolbar),ButterKnife,
 * Created by MonkeyShen on 2017/1/16.
 * Mail:shenminjie92@sina.com
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView, CustomToolbar.OnToolbarClickListener {

    /**
     * 加载框view
     */
    private LoadingDialog mLoadingDialog;

    CustomToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //避免过度绘制，统一背景白色
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        mLoadingDialog = new LoadingDialog(this);
        setContentView(getContentViewResource());

        ButterKnife.bind(this);
        mToolbar = (CustomToolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            //设置toolbar基础，监听事件、按钮显示
            mToolbar.setListener(this);
            setSupportActionBar(mToolbar);
            mToolbar.isShowLeftIcon(true);
        }
        //直接初始化视图
        initView();

    }

    @Override
    public abstract void initView();


    /**
     * 获取 资源文件
     */
    public abstract
    @LayoutRes
    int getContentViewResource();


    /**
     * 获取toolbar
     *
     * @return toolbar
     */
    public CustomToolbar getToolbar() {
        return mToolbar;
    }

    public void hideToolbar() {
        getToolbar().setVisibility(View.GONE);
    }

    /**
     * 设置title
     *
     * @param res 字符串资源
     */
    public void setToolbarTitle(int res) {
        String title = getString(res);
        setToolbarTitle(title);
    }

    /**
     * 设置title
     *
     * @param title 文本
     */
    public void setToolbarTitle(String title) {
        mToolbar.setMainTitle(title);
    }


    @Override
    public void showLoading() {
        if (!mLoadingDialog.isShowing() && !isFinishing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * dialog  点击其他地方不消失
     *
     * @param isCance false
     */
    public void showLoading(boolean isCance) {
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
            mLoadingDialog.setCanceledOnTouchOutside(isCance);
        }
    }

    public boolean isLoading() {
        return mLoadingDialog != null && mLoadingDialog.isShowing();
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

    @Override
    protected void onDestroy() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        super.onDestroy();
    }


    /***************
     * toolbar点击回调事件
     **************/
    @Override
    public void onLeftIconClickListener(View leftView) {
        //结束activity
        finish();
        LogUtils.e("tag", "左按钮点击事件");
    }

    @Override
    public void onRightIconClickListener(View rightView) {
        LogUtils.e("tag", "右按钮点击事件");
    }

    @Override
    public void onMainTitleClickListener(View titleView) {
        LogUtils.e("tag", "标题点击事件处理");
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
//            event.getX() > left && event.getX() < right &&
            if (event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }


}
