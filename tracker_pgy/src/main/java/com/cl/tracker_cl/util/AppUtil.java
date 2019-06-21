package com.cl.tracker_cl.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.Locale;
import java.util.UUID;

/**
 * 获取APP的相关数据
 */
public class AppUtil {


    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }
    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static String getPhoneBrand(Context context) {
        return Build.DEVICE;
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        PackageManager pm = context.getPackageManager();
        if (pm != null) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
                if (packageInfo != null) {
                    versionName = packageInfo.versionName;
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }

        return versionName;
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getChannel(Context context) {
        String channel = "";
        PackageManager pm = context.getPackageManager();
        if (pm != null) {
            try {
                ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (appInfo != null && appInfo.metaData != null) {
                    channel = appInfo.metaData.getString("TRACKER_CHANNEL");
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }

        return channel;
    }

    public static String getDeviceId(Context context) {
        String deviceId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = UUID.randomUUID().toString();
        }

        return deviceId;
    }
}
