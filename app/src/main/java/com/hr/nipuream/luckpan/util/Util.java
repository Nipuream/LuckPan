package com.hr.nipuream.luckpan.util;

import android.content.Context;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-08-15 19:36
 * 邮箱：nipuream@163.com
 */
public class Util {


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static double change(double a){
        return a * Math.PI  / 180;
    }

    public static double changeAngle(double a){
        return a * 180 / Math.PI;
    }

}
