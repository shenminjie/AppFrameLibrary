package com.monkeyshen.appframe.appframelibrary.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.monkeyshen.appframe.appframelibrary.R;


/**
 * Description:title toolbar
 * Created by MonkeyShen on 2017/1/16.
 * Mail:shenminjie92@sina.com
 */

public class CustomToolbar extends android.support.v7.widget.Toolbar implements View.OnClickListener {

    /**
     * 设置title
     */
    private TextView mTvMainTitle;

    /**
     * 左icon
     */
    private TextView mTvMainTitleLeft;

    /**
     * 右icon
     */
    private TextView mTvMainTitleRight;

    /**
     * 回调操作
     */
    private OnToolbarClickListener mListener;


    private View mView;

    public CustomToolbar(Context context) {
        super(context);
        initView();
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 设置监听事件
     * 监听三个view的点击事件
     *
     * @param onListener onListener
     */
    public void setListener(OnToolbarClickListener onListener) {
        mListener = onListener;
    }

    /**
     * 初始化view
     */
    private void initView() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.common_titleview_layout, this, false);
        mTvMainTitleLeft = (TextView) mView.findViewById(R.id.tv_left_icon);
        mTvMainTitleRight = (TextView) mView.findViewById(R.id.tv_right_icon);
        mTvMainTitle = (TextView) mView.findViewById(R.id.tv_title);
        addView(mView);

        //设置监听事件
        mTvMainTitleLeft.setOnClickListener(this);
        mTvMainTitleRight.setOnClickListener(this);
        mTvMainTitle.setOnClickListener(this);
    }

    /**
     * 设置主title的内容
     */
    public void setMainTitle(String text) {
        this.setTitle(" ");
        mTvMainTitle.setVisibility(View.VISIBLE);
        mTvMainTitle.setText(text);
    }


    /**
     * 设置主title的内容文字的颜色
     *
     * @param color 颜色
     */
    public void setMainTitleColor(int color) {
        mTvMainTitle.setTextColor(color);
    }

    /**
     * 设置title左边文字
     */
    public void setMainTitleLeftText(String text) {
        mTvMainTitleLeft.setVisibility(View.VISIBLE);
        mTvMainTitleLeft.setText(text);
    }

    /**
     * 设置title左边文字颜色
     */
    public void setMainTitleLeftColor(int color) {
        mTvMainTitleLeft.setTextColor(color);
    }

    /**
     * 设置title左边图标
     */
    public void setMainTitleLeftDrawable(int res) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        mTvMainTitleLeft.setCompoundDrawables(dwLeft, null, null, null);
    }


    /**
     * 设置title右边文字
     *
     * @param text text
     */
    public void setMainTitleRightText(String text) {
        mTvMainTitleRight.setVisibility(View.VISIBLE);
        mTvMainTitleRight.setText(text);
    }


    /**
     * 设置title右边文字颜色
     *
     * @param color color
     */
    public void setMainTitleRightColor(int color) {
        mTvMainTitleRight.setTextColor(color);
    }


    /**
     * 设置title右边图标
     *
     * @param res res
     */
    public void setMainTitleRightDrawable(int res) {
        mTvMainTitleRight.setVisibility(View.VISIBLE);
        Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        mTvMainTitleRight.setCompoundDrawables(null, null, dwRight, null);
    }



    //隐藏Toolbar
    public void isShowToolbar() {
        mView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) {
            return;
        }
        int id = v.getId();
        if (id == R.id.tv_left_icon) {
            mListener.onLeftIconClickListener(v);
        } else if (id == R.id.tv_right_icon) {
            mListener.onRightIconClickListener(v);

        } else if (id == R.id.tv_title) {
            mListener.onMainTitleClickListener(v);

        }
    }

    /**
     * 是否显示leftIcon
     *
     * @param isShow isShow
     */
    public void isShowLeftIcon(boolean isShow) {
        if (isShow) {
            mTvMainTitleLeft.setVisibility(View.VISIBLE);
        } else {
            mTvMainTitleLeft.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示右icon
     *
     * @param isShow isShow
     */
    public void isShowRightIcon(boolean isShow) {
        if (isShow) {
            mTvMainTitleRight.setVisibility(View.VISIBLE);
        } else {
            mTvMainTitleRight.setVisibility(View.GONE);
        }
    }

    /**
     * 获取标题
     *
     * @return return
     */
    public TextView getMainTitle() {
        return mTvMainTitle;
    }


    /**
     * toolbar点击事件处理
     */
    public interface OnToolbarClickListener {

        /**
         * 左icon点击事件
         *
         * @param leftView leftView
         */
        void onLeftIconClickListener(View leftView);

        /**
         * 右icon点击事件
         *
         * @param rightView rightView
         */
        void onRightIconClickListener(View rightView);

        /**
         * 主title点击事件处理
         *
         * @param titleView titleView
         */
        void onMainTitleClickListener(View titleView);

    }

}
