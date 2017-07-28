package com.monkeyshen.appframe.appframelibrary.widget;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;

import com.blankj.utilcode.utils.SizeUtils;
import com.monkeyshen.appframe.appframelibrary.R;
import com.monkeyshen.appframe.appframelibrary.widget.ptr.PtrFrameLayout;
import com.monkeyshen.appframe.appframelibrary.widget.ptr.header.StoreHouseHeader;


/**
 * Description:下拉刷新
 * Created by MonkeyShen on 2017/1/18.
 * Mail:shenminjie92@sina.com
 */

public class CustomRefreshLayout extends PtrFrameLayout {


    public CustomRefreshLayout(Context context) {
        super(context);
        initView();
    }

    public CustomRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }


    /**
     * 初始化view
     */
    private void initView() {
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("BAIBEI");
        header.setPadding(0, SizeUtils.dp2px(15), 0, SizeUtils.dp2px(15));
        header.setTextColor(ResourcesCompat.getColor(getResources(), R.color.primary_text, null));
        setHeaderView(header);
        addPtrUIHandler(header);
    }
}
