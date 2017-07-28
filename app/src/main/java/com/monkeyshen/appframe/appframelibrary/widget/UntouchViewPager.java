package com.monkeyshen.appframe.appframelibrary.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Description:适用于禁止滑动的viewpager 用于首页帮助我们管理碎片
 * Created by MonkeyShen on 2017/1/18.
 * Mail:shenminjie92@sina.com
 */

public class UntouchViewPager extends ViewPager {

    /**
     * 默认禁止滑动
     */
    private boolean isPagingEnabled = false;

    public UntouchViewPager(Context context) {
        super(context);
    }

    public UntouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean canScroll) {
        this.isPagingEnabled = canScroll;
    }


}
