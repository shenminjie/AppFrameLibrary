package com.monkeyshen.appframe.appframelibrary.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;


/**
 * Created by SeenFar on 16/1/11.
 */
public class ViewUtil {


    static float scale = 1;

    public static void init(Context context) {
        scale = context.getResources().getDisplayMetrics().density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return (int) (dpValue * scale + 0.5f);
    }


    public static void setViewSize(View v, int width, int height) {

        ViewGroup.LayoutParams params = v.getLayoutParams();

        if (width >= 0)
            params.width = width;
        if (height >= 0)
            params.height = height;
        v.setLayoutParams(params);
    }

    public static void setViewHeight(View v, int height) {

        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = height;
        v.setLayoutParams(params);
    }

    public static void setViewWidth(View v, int width) {

        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = width;
        v.setLayoutParams(params);
    }

    public static int getViewHeight(View v) {

        ViewGroup.LayoutParams params = v.getLayoutParams();
        return params.height;
    }

    public static int getViewWidth(View v) {

        ViewGroup.LayoutParams params = v.getLayoutParams();
        return params.width;
    }


    public static void setViewMarginWithDP(View v, int left, int top, int right, int bottom) {
        setViewMargin(v,
                dip2px(left),
                dip2px(top),
                dip2px(right),
                dip2px(bottom)
        );
    }


    public static void setViewMargin(View v, int left, int top, int right, int bottom) {

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        if (left >= 0)
            params.leftMargin = left;
        if (right >= 0)
            params.rightMargin = right;
        if (top >= 0)
            params.topMargin = top;
        if (bottom >= 0)
            params.bottomMargin = bottom;

        v.setLayoutParams(params);
    }



    public static Rect getLocation(View view) {

        if (view == null) {
            return new Rect(0, 0, 0, 0);
        }

        int[] l = new int[2];
        view.getLocationOnScreen(l);
        return new Rect(l[0], l[1], view.getWidth() + l[0], view.getHeight() + l[1]);

    }


    public static void removeFormSuper(View view) {

        if (view != null) {
            ViewGroup vg = (ViewGroup) view.getParent();
            if (vg != null)
                vg.removeView(view);
        }

    }

    public static void addToView(ViewGroup vg, View view) {

        if (view == null || vg == null || view.getParent() == vg)
            return;

        removeFormSuper(view);
        vg.addView(view);

    }

    public static void setRound(View v) {

        int roundRadius = ViewUtil.getViewWidth(v);
        setCorner(v, 0, roundRadius, null, null);

    }


    public static void setCorner(View v, int roundRadius) {

        setCorner(v, 0, roundRadius, null, null);
    }

    public static void setRound(View v, String fillColor) {

        setCorner(v, 0, 1000, null, fillColor);
    }


    public static void setCorner(View v, int Corner, String fillColor) {

        setCorner(v, 0, Corner, null, fillColor);
    }

    public static void setRoundBound(View v, int width, String strokeColor) {

        setCorner(v, width, 1000, strokeColor, null);
    }

    public static void setRoundBoundWithdp(View v, int width, String strokeColor) {

        setRoundBound(v, dip2px(width), strokeColor);
    }

    public static void setRoundBound(View v, int width, String strokeColor, String fillColor) {

        setCorner(v, width, 1000, strokeColor, fillColor);
    }


    public static void setCorner(View v, int strokeWidth, int roundRadius, String strokeColor, String fillColor) {

        GradientDrawable gd = getFradientDrawable(strokeWidth, roundRadius, strokeColor, fillColor);
        if (v != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(gd);
        }
    }

    public static GradientDrawable getFradientDrawable(int strokeWidth, int roundRadius, String strokeColor, String fillColor) {

        int istrokeColor = 0;
        int ifillColor = 0;
        if (strokeColor != null)
            istrokeColor = Color.parseColor(strokeColor);//边框颜色

        if (fillColor != null)
            ifillColor = Color.parseColor(fillColor);//内部填充颜色


        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(ifillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, istrokeColor);
        return gd;
    }


    public static void setLCorner(View v, int strokeWidth, float lRadius, String strokeColor, String fillColor) {

        GradientDrawable gd = getFradientDrawable(strokeWidth, 0, strokeColor, fillColor);

        gd.setCornerRadii(new float[]{lRadius, lRadius, 0, 0, 0, 0, lRadius, lRadius});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(gd);
        }
    }


    public static void setKeyboardState(View v, boolean state) {

        if (state == true) {

            v.requestFocus();
            InputMethodManager inputManager =
                    (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(v, 0);

            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);

        } else {


            InputMethodManager inputManager =
                    (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(v, 0);
            inputManager.hideSoftInputFromWindow(
                    v.getApplicationWindowToken(), 0);

        }


    }

    public static void setImage(ImageView view, Context context, int id) {
        Drawable drawable = context.getResources().getDrawable(id);
        view.setImageDrawable(drawable);
    }


    public interface AniEnd {
        public void end();
    }

    public static void translateBottom(View view, boolean show, final AniEnd listener) {

        Animation translateIn =
                new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, show ? 1 : 0
                        , Animation.RELATIVE_TO_SELF, show ? 0 : 1);
        translateIn.setDuration(500);
        translateIn.setFillAfter(true);
        view.startAnimation(translateIn);

        if (listener != null)
            translateIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    listener.end();

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

    }

    public static void translateRight(View view, boolean show) {

        Animation translateIn =
                new TranslateAnimation(Animation.RELATIVE_TO_SELF, show ? 1 : 0,
                        Animation.RELATIVE_TO_SELF, show ? 1 : 0,
                        Animation.RELATIVE_TO_SELF, 0
                        , Animation.RELATIVE_TO_SELF, 0);
        translateIn.setDuration(400);
        translateIn.setFillAfter(true);
        view.startAnimation(translateIn);
    }

    /**
     * 是否显示view
     *
     * @param view      view
     * @param isVisible 显示或者隐藏
     */
    public static void setViewVisible(View view, boolean isVisible) {
        if (isVisible) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        } else {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }
        }
    }

}
