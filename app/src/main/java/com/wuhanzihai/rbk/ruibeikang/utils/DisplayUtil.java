package com.wuhanzihai.rbk.ruibeikang.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import android.view.WindowManager;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DisplayUtil {

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context
                .CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        if (!TextUtils.isEmpty(content)) {

        }
    }

    public static String formatToseara(Double data) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(data);
    }

    public static String formatTwoPoint(double value) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(value);
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0);
            return pi.versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0);
            return pi.versionName;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return 屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 计算中文字符
     *
     * @param sequence
     * @return
     */
    public static int countChineseChar(CharSequence sequence) {

        if (TextUtils.isEmpty(sequence)) {
            return 0;
        }
        int charNum = 0;
        for (int i = 0; i < sequence.length(); i++) {
            char word = sequence.charAt(i);
            if (isChineseChar(word)) {//中文
                charNum++;
            }
        }
        return charNum;
    }

    /**
     * 判断是否是中文
     *
     * @param c
     * @return
     */
    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前的时间
     *
     * @return 格式化之后的时间字符串
     */
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//得到当前的时间
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getHourTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 判断时间字符串是否是今天
     *
     * @throws ParseException
     */
    public static boolean isToday(String day) {
        try {
            Calendar pre = Calendar.getInstance();
            Date predate = new Date(System.currentTimeMillis());
            pre.setTime(predate);

            Calendar cal = Calendar.getInstance();
            Date date = getDateFormat().parse(day);
            cal.setTime(date);

            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                        - pre.get(Calendar.DAY_OF_YEAR);

                if (diffDay == 0) {
                    return true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 获取时间格式
     */
    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    /**
     * string转double
     */
    public static double stringToDouble(String data) {
        double value = 0;
        if (TextUtils.isEmpty(data)) {
            return value;
        }
        try {
            value = Double.valueOf(data);
        } catch (NumberFormatException e) {
            value = 0;
        }
        return value;
    }

    /**
     * string转int
     */
    public static int stringToInt(String data) {
        int value = 0;
        if (TextUtils.isEmpty(data)) {
            return value;
        }
        try {
            value = Integer.valueOf(data);
        } catch (NumberFormatException e) {
            value = 0;
        }
        return value;
    }

    /**
     * 获取手机的型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取时间间隔的天数
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static long getDistanceDays(long beforeDate, long afterDate) {
        return (afterDate - beforeDate) / (1000 * 60 * 60 * 24);
    }

    /**
     * 设置图片的宽度(高度和宽度保持一致)
     *
     * @param context      上下文
     * @param showImageNum 显示图片的数量
     */
    public static int getImageWidth(Context context, int showImageNum) {
        int screenWidth = DisplayUtil.getScreenWidth(context);
        int spaceWidth = DisplayUtil.dip2px(context, 20 + (showImageNum - 1) * 10);
        int actualWidth = (screenWidth - spaceWidth) / showImageNum;
        return actualWidth;
    }
}
