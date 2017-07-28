package com.monkeyshen.appframe.appframelibrary.base.paging;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.monkeyshen.appframe.appframelibrary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:空界面适配器，通过设置状态进行设置空数据显示
 * Created by MonkeyShen on 2017/3/1.
 * Mail:shenminjie92@sina.com
 */

public abstract class EmptyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 空数据状态
     * 有数据状态
     */
    public static final int STATUS_EMPTY_DATA = 1;
    public static final int STATUS_HAS_DATA = 2;

    /**
     * 有数据与空数据的viewType
     */
    public static final int VIEW_TYPE_EMPTY = 1;
    public static final int VIEW_TYPE_DATA = 2;
    public static final int VIEW_TYPE_HEADEVIEW = 3;

    /**
     * 数据状态
     */
    private int mStatus = STATUS_HAS_DATA;

    /**
     * 设置状态
     *
     * @param status status
     */
    public void setStatus(int status) {
        mStatus = status;
    }

    /**
     * 获取当前状态
     *
     * @return
     */
    public int getStatus() {
        return mStatus;
    }

    @Override
    public int getItemViewType(int position) {
        if (getHeaderView() != null) {
            if (position == 0) {
                return VIEW_TYPE_HEADEVIEW;
            }
        }
        if (getStatus() == STATUS_EMPTY_DATA) {
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_DATA;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false);
            return new EmptyViewHolder(view);
        }
        if (viewType == VIEW_TYPE_HEADEVIEW) {
            return getHeaderViewHolder(parent, viewType);
        }
        return getCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {
            EmptyViewHolder viewHolder = (EmptyViewHolder) holder;
            //默认操作
            viewHolder.tvEmptyView.setText(getEmptyStatusText());
            onEmptyViewHolder(viewHolder);
        }
        onHandlerViewHolder(holder, position);
    }

    /**
     * 处理viewHolder
     *
     * @param holder   holder
     * @param position position
     */
    protected abstract void onHandlerViewHolder(RecyclerView.ViewHolder holder, int position);


    /**
     * 获取空数据显示描述
     *
     * @return return
     */
    protected abstract CharSequence getEmptyStatusText();

    /**
     * 获取自己创建的viewHolder
     *
     * @param parent   parent
     * @param viewType viewType
     * @return return
     */
    public abstract RecyclerView.ViewHolder getCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 获取数据count
     *
     * @return return
     */
    public abstract int getDataItemCount();


    /**
     * 获取headerView
     *
     * @return
     */
    public abstract View getHeaderView();

    /**
     * 获取headerView viewHolder
     *
     * @param parent   parent
     * @param viewType viewType
     * @return return  return
     */
    protected abstract RecyclerView.ViewHolder getHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * 空视图处理
     *
     * @param viewHolder viewHolder
     */
    protected void onEmptyViewHolder(EmptyViewHolder viewHolder) {
    }


    @Override
    public int getItemCount() {
        if (getHeaderView() == null) {
            if (getStatus() == STATUS_HAS_DATA) {
                return getDataItemCount();
            } else {
                return 1;
            }
        } else {
            //在headerView之后显示emptyView
            if (getStatus() == STATUS_HAS_DATA) {
                return getDataItemCount() + 1;
            } else {
                return 2;
            }
        }
    }


    /**
     * 空emptyViewHolder
     */
    protected class EmptyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_empty)
        public TextView tvEmptyView;
        @BindView(R.id.layout_btn)
        public FrameLayout layoutBtn;
        @BindView(R.id.btn_create)
        public TextView btn;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
