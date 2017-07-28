package com.monkeyshen.appframe.appframelibrary.network_engine;

import android.os.Handler;

import com.monkeyshen.appframe.appframelibrary.network_engine.network_callback.UpLoadFileCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Description:进度条
 * Created by MonkeyShen on 2017/2/10.
 * Mail:shenminjie92@sina.com
 */
public class ProgressRequestBody extends RequestBody {

    /**
     * 文件
     */
    private File mFile;

    /**
     * 上传回调监听
     */
    private UpLoadFileCallBack mListener;

    /**
     * handler
     */
    private Handler mHandler;

    /**
     * 缓冲size
     */
    private static final int DEFAULT_BUFFER_SIZE = 2048;


    /**
     * 构造器
     *
     * @param file
     * @param listener
     */
    public ProgressRequestBody(final File file, final UpLoadFileCallBack listener, Handler handler) {
        mFile = file;
        mListener = listener;
        mHandler = handler;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("multipart/form-data");
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                mHandler.post(new ProgressUpdater(uploaded, fileLength));
                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }
    }

    /**
     * 回调更新任务
     */
    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            if (mUploaded != 0 && mTotal != 0) {
                mListener.onProgressUpdate((int) (100 * mUploaded / mTotal), mUploaded, mTotal);
            }
        }
    }
}
