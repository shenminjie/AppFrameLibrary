package com.monkeyshen.appframe.appframelibrary.image_support.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.monkeyshen.appframe.appframelibrary.R;


/**
 * Description:分割线
 * Created by MonkeyShen on 2017/1/21.
 * Mail:shenminjie92@sina.com
 */

public class ImageItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int width = (int) parent.getContext().getResources().getDimension(R.dimen.width_multi_image_margin);
        outRect.set(0, 0, width, width);
    }
}
