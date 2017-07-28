package com.monkeyshen.appframe.appframelibrary.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Description:基础loading view
 * Created by MonkeyShen on 2017/1/16.
 * Mail:shenminjie92@sina.com
 */

public class LoadingDialog extends ProgressDialog {


    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }
}
