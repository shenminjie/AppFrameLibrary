package com.monkeyshen.appframe.appframelibrary.image_support;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.BarUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.monkeyshen.appframe.appframelibrary.R;
import com.monkeyshen.appframe.appframelibrary.widget.MultiTouchViewPager;

import java.io.Serializable;
import java.util.List;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * 多图片查看
 * create by smj 2017-2-13
 */
public class ViewPagerActivity extends AppCompatActivity {

    /**
     * 数据源
     */
    private List<String> mUris;

    /**
     * 默认选中位置
     */
    private int mDefaultSelectPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        ((Toolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

        if (getIntent().hasExtra(PARAM_URIS)) {
            mUris = (List<String>) getIntent().getSerializableExtra(PARAM_URIS);
            mDefaultSelectPosition = getIntent().getIntExtra(PARAM_DEFAULT_SELECT, 0);
        }
        if (mUris == null || mUris.size() == 0) {
            LogUtils.e("smj-----没有数据！");
            return;
        }
//        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        MultiTouchViewPager viewPager = (MultiTouchViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new DraweePagerAdapter());
//        indicator.setViewPager(viewPager);

        BarUtils.setColor(this, ResourcesCompat.getColor(getResources(), android.R.color.black, getTheme()));

        //默认选中
        if (mDefaultSelectPosition < mUris.size()) {
            viewPager.setCurrentItem(mDefaultSelectPosition);
        }
    }

    /**
     * 入参
     */
    public static final String PARAM_URIS = "PARAM_URIS";
    public static final String PARAM_DEFAULT_SELECT = "PARAM_DEFAULT_SELECT";

    /**
     * 图片uri
     *
     * @param context context
     * @param images  images
     */
    public static void startActivity(Context context, List<String> images, int position) {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        intent.putExtra(PARAM_URIS, (Serializable) images);
        intent.putExtra(PARAM_DEFAULT_SELECT, position);
        context.startActivity(intent);
    }

    /**
     * 适配器
     */
    class DraweePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mUris.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, final int position) {
            final PhotoDraweeView photoDraweeView = (PhotoDraweeView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_viewpager, viewGroup, false);
            SizeUtils.forceGetViewSize(photoDraweeView, new SizeUtils.onGetSizeListener() {
                @Override
                public void onGetSize(View view) {
                    String uri = mUris.get(position);
                    //本地区别
                    if (!uri.startsWith("http")) {
                        uri = "file://" + uri;
                    }
                    ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                            .setResizeOptions(new ResizeOptions(view.getMeasuredWidth(), view.getMeasuredHeight()))
                            .build();
                    PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                            .setOldController(photoDraweeView.getController())
                            .setImageRequest(request)
                            .setControllerListener(new BaseControllerListener<ImageInfo>() {
                                @Override
                                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                                    super.onFinalImageSet(id, imageInfo, animatable);
                                    if (imageInfo == null) {
                                        return;
                                    }
                                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                                }
                            })
                            .build();
                    photoDraweeView.setController(controller);
                }
            });
            try {
                viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            photoDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    LogUtils.e("---->>>", "图片长按选择事件---保存本地操作:" + mUris.get(position));
                    return false;
                }
            });
            return photoDraweeView;
        }
    }

    @Override
    protected void onDestroy() {
        //清空图片内存
//        Fresco.getImagePipeline().clearMemoryCaches();
        super.onDestroy();
    }
}
