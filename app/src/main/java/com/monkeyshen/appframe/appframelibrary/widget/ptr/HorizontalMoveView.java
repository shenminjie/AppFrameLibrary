package com.monkeyshen.appframe.appframelibrary.widget.ptr;

/**
 * Description:水平滚动view
 * Created by MonkeyShen on 2017/3/7.
 * Mail:shenminjie92@sina.com
 */

public interface HorizontalMoveView {

    /**
     * 获取当前事件
     * MotionEvent.ACTION_DOWN
     * MotionEvent.ACTION_MOVE
     * 用于阻断手势
     */
    int getCurrentTouchEvent();
}
