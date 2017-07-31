package com.monkeyshen.appframe.appframelibrary.base.application;

import android.app.Application;
import android.graphics.Bitmap;

import com.blankj.utilcode.utils.Utils;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

/**
 * Description:配合框架使用，初始化工具类以及fresco配置
 * Created by MonkeyShen on 2017/7/31.
 * Mail:shenminjie92@sina.com
 */

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initFresco();
    }

    /**
     * 初始化Fresco
     */
    private void initFresco() {
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setMaxCacheSize(200 * 1024 * 1024)//最大缓存 --200M
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return getCacheDir();
                    }
                })
                .build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setDownsampleEnabled(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this, config);
    }
}
