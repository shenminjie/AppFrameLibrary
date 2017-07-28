package com.monkeyshen.appframe.appframelibrary.listener;

import android.view.View;

/**
 * Description:item选中监听事件
 * Created by MonkeyShen on 2017/2/13.
 * Mail:shenminjie92@sina.com
 */

public interface OnItemSelectListener<T> {

    /**
     * view选中监听，适用于recyclerView
     *
     * @param view     view
     * @param t        对象
     * @param position 在列表中的位置
     */
    void onItemSelectListener(View view, T t, int position);
}
