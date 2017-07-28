package com.monkeyshen.appframe.appframelibrary.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * Created by MonkeyShen on 2017/3/8.
 * Mail:shenminjie92@sina.com
 */

public class TimeUtils {

    /**
     * 根据工具字典进行修改
     * 获取友好型与当前时间的差
     *
     * @param millis 毫秒时间戳
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于1秒钟内，显示刚刚</li>
     * <li>如果在1分钟内，显示XXX秒前</li>
     * <li>如果在1小时内，显示XXX分钟前</li>
     * <li>如果在1小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    @SuppressLint("DefaultLocale")
    public static String getFriendlyTimeSpanByNow(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 1000) {
            return "刚刚";
        } else if (span < MIN) {
            return String.format("%d秒前", span / SEC);
        } else if (span < HOUR) {
            return String.format("%d分钟前", span / MIN);
        }
        // 获取当天00:00
        Date date = new Date();
        String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
//        long wee = (now / DAY) * DAY - 8 * HOUR;
        long wee = 0;
        try {
            wee = new SimpleDateFormat("yyyy-MM-dd").parse(str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     * 秒与毫秒的倍数
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 86400000;

}
