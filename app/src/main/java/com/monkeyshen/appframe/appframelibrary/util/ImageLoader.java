package com.monkeyshen.appframe.appframelibrary.util;

import android.net.Uri;
import android.view.View;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Description:
 * Created by MonkeyShen on 2017/3/10.
 * Mail:shenminjie92@sina.com
 */

public class ImageLoader {

    /**
     * 加载图片
     *
     * @param url        url
     * @param draweeView draweeView
     */
    public static void setImage(final String url, final SimpleDraweeView draweeView) {
        //如果知道尺寸，直接设置
        if (draweeView.getWidth() != 0 && draweeView.getHeight() != 0) {
            setImage(url, draweeView, (draweeView.getWidth()), (draweeView.getHeight()));
            return;
        }
        //强制获取尺寸在设置size
        SizeUtils.forceGetViewSize(draweeView, new SizeUtils.onGetSizeListener() {
            @Override
            public void onGetSize(View view) {
                setImage(url, draweeView, (view.getWidth()), (view.getHeight()));
            }
        });
    }

    /**
     * 设置图片，根据
     *
     * @param url        url
     * @param draweeView draweeView
     * @param width      长度
     * @param height     宽度
     */
    public synchronized static void setImage(final String url, final SimpleDraweeView draweeView, int width, int height) {
        if (width != 0 && height != 0) {
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url + ""))
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(request)
                    .build();
            draweeView.setController(controller);
        } else {
            LogUtils.e("tag", "width:" + width + " height:" + height);
            //暂时直接处理
            draweeView.setImageURI(Uri.parse(url + ""));
        }
    }

    /**
     * 加载图片
     * @param imgUrl
     * @param view
     */
    public static void downImage(String imgUrl, SimpleDraweeView view) {
       view.setImageURI(imgUrl);
    }
}
