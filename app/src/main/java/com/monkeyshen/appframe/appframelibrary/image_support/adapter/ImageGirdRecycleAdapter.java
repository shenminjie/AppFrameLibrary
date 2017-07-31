package com.monkeyshen.appframe.appframelibrary.image_support.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.monkeyshen.appframe.appframelibrary.R;
import com.monkeyshen.appframe.appframelibrary.image_support.bean.Image;
import com.monkeyshen.appframe.appframelibrary.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Description:
 * Created by MonkeyShen on 2017/1/21.
 * Mail:shenminjie92@sina.com
 */

public class ImageGirdRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_NORMAL = 1;

    private Context mContext;

    private LayoutInflater mInflater;
    private boolean showCamera = true;
    private boolean showSelectIndicator = true;

    private List<Image> mImages = new ArrayList<>();
    private List<Image> mSelectedImages = new ArrayList<>();


    public ImageGirdRecycleAdapter(Context context, boolean showCamera, int width) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showCamera = showCamera;
    }


    /**
     * 显示选择指示器
     *
     * @param b
     */
    public void showSelectIndicator(boolean b) {
        showSelectIndicator = b;
    }

    public void setShowCamera(boolean b) {
        if (showCamera == b) return;

        showCamera = b;
        notifyDataSetChanged();
    }

    public boolean isShowCamera() {
        return showCamera;
    }

    /**
     * 选择某个图片，改变选择状态
     *
     * @param image
     */
    public void select(Image image) {
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);
        } else {
            mSelectedImages.add(image);
        }
        int position = mImages.indexOf(image);
        if (isShowCamera()) {
            notifyItemChanged(position + 1);
        } else {
            notifyItemChanged(position);
        }
    }

    /**
     * 通过图片路径设置默认选择
     *
     * @param resultList
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        for (String path : resultList) {
            Image image = getImageByPath(path);
            if (image != null) {
                mSelectedImages.add(image);
            }
        }
        if (mSelectedImages.size() > 0) {
            notifyDataSetChanged();
        }
    }

    private Image getImageByPath(String path) {
        if (mImages != null && mImages.size() > 0) {
            for (Image image : mImages) {
                if (image.path.equalsIgnoreCase(path)) {
                    return image;
                }
            }
        }
        return null;
    }

    /**
     * 设置数据集
     *
     * @param images
     */
    public void setData(List<Image> images) {
        mSelectedImages.clear();

        if (images != null && images.size() > 0) {
            mImages = images;
        } else {
            mImages.clear();
        }
        notifyDataSetChanged();
    }


    public Image getItem(int i) {
        if (showCamera) {
            if (i == 0) {
                return null;
            }
            return mImages.get(i - 1);
        } else {
            return mImages.get(i);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (showCamera) {
            return position == 0 ? TYPE_CAMERA : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CAMERA) {
            View view = mInflater.inflate(R.layout.mis_list_item_camera, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) {
                        return;
                    }
                    mListener.onCameraSelectListener(v);
                }
            });
            return new RecyclerView.ViewHolder(view) {
            };
        } else {
            View view = mInflater.inflate(R.layout.mis_list_item_image, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.bindData(getItem(position));
            viewHolder.indicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) {
                        return;
                    }
                    mListener.onImageSelectListener(v, getItem(position));
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) {
                        return;
                    }
                    mListener.onImageClickListener(v, getItem(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return showCamera ? mImages.size() + 1 : mImages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView image;
        ImageView indicator;
        View mask;
        View itemView;

        ViewHolder(View view) {
            super(view);
            image = (SimpleDraweeView) view.findViewById(R.id.image);
            indicator = (ImageView) view.findViewById(R.id.checkmark);
            mask = view.findViewById(R.id.mask);
            itemView = view;
            view.setTag(this);
        }

        void bindData(final Image data) {
            if (data == null) return;
            // 处理单选和多选状态
            if (showSelectIndicator) {
                indicator.setVisibility(View.VISIBLE);
                if (mSelectedImages.contains(data)) {
                    // 设置选中状态
                    indicator.setImageResource(R.mipmap.mis_btn_selected);
                    mask.setVisibility(View.VISIBLE);
                } else {
                    // 未选择
                    indicator.setImageResource(R.mipmap.mis_btn_unselected);
                    mask.setVisibility(View.GONE);
                }
            } else {
                indicator.setVisibility(View.GONE);
            }
            //显示图片
            int size = ScreenUtils.getScreenWidth() / 6;
            ImageLoader.setImage("file://" + data.path, image, size, size);
        }
    }

    /**
     * 监听事件
     */
    private OnItemSelectListener mListener;

    /**
     * 设置设置回调
     *
     * @param listener
     */
    public void setListener(OnItemSelectListener listener) {
        this.mListener = listener;
    }

    /**
     * 回调
     */
    public interface OnItemSelectListener {
        /**
         * 图片选
         *
         * @param v
         * @param image
         */
        void onImageSelectListener(View v, Image image);

        /**
         * 相机
         *
         * @param v
         */
        void onCameraSelectListener(View v);


        /**
         * 点击图片事件
         *
         * @param v
         * @param image
         */
        void onImageClickListener(View v, Image image);
    }

}
