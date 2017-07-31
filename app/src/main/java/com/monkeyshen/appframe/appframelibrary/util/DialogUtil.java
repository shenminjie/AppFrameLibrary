package com.monkeyshen.appframe.appframelibrary.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Description:统一管理确定，警告弹窗
 * Created by MonkeyShen on 2017/2/6.
 * Mail:shenminjie92@sina.com
 */

public class DialogUtil {

    /**
     * 显示alert
     *
     * @param context   context
     * @param alertText alertText
     */
    public static Dialog showAlert(Context context, CharSequence alertText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(alertText);
        builder.setPositiveButton("确定", null);
        AlertDialog dialog = builder.show();
        return dialog;
    }

    public static Dialog showAlert(Context context, CharSequence alertText, CharSequence btnText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(alertText);
        builder.setPositiveButton(btnText, null);
        AlertDialog dialog = builder.show();
        return dialog;
    }

    /**
     * 显示dialog
     *
     * @param context          activity
     * @param title            标题
     * @param message          内容
     * @param positiveText     按钮1文本
     * @param positiveListener 按钮1点击事件
     * @param negativeText     按钮2文本
     * @param negativeListener 按钮2，点击事件
     */
    public static Dialog showConfirm(Context context, CharSequence title, CharSequence message,
                                     CharSequence positiveText, DialogInterface.OnClickListener positiveListener,
                                     CharSequence negativeText, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, positiveListener);
        builder.setNegativeButton(negativeText, negativeListener);
        AlertDialog dialog = builder.show();
        return dialog;
    }

    /**
     * @param title       标题
     * @param message     内容
     * @param btnText     文本1
     * @param btnListener 按钮
     * @param context     activity
     */
    public static Dialog showAlert(Context context, CharSequence title, CharSequence message,
                                   CharSequence btnText, DialogInterface.OnClickListener btnListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(btnText, btnListener);
        AlertDialog dialog = builder.show();
        return dialog;
    }

}
