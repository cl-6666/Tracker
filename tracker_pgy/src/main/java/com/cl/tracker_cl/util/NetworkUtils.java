package com.cl.tracker_cl.util;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


public class NetworkUtils {

    /**
     * 获取网络类型
     *
     * @param context Context
     * @return 网络类型
     */
    public static String networkType(Context context) {
        try {
            // 检测权限
            if (!SensorsDataUtils.checkHasPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)) {
                return "NULL";
            }

            // Wifi
            ConnectivityManager manager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Network network = manager.getActiveNetwork();
                    if (network != null) {
                        NetworkCapabilities capabilities = manager.getNetworkCapabilities(network);
                        if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            return "WIFI";
                        }
                    }
                } else {
                    NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                        return "WIFI";
                    }
                }
            }

            // Mobile network
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                    .TELEPHONY_SERVICE);

            if (telephonyManager == null) {
                return "NULL";
            }

            int networkType = telephonyManager.getNetworkType();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return "4G";
//                case TelephonyManager.NETWORK_TYPE_NR:
//                    return "5G";
                default:
                    return "NULL";
            }
        } catch (Exception e) {
            return "NULL";
        }
    }

    /**
     * 是否有可用网络
     *
     * @param context Context
     * @return true：网络可用，false：网络不可用
     */
    public static boolean isNetworkAvailable(Context context) {
        // 检测权限
        if (!SensorsDataUtils.checkHasPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)) {
            return false;
        }
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Network network = cm.getActiveNetwork();
                    if (network != null) {
                        NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
                        if (capabilities != null) {
                            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
                        }
                    }
                } else {
                    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                    return networkInfo != null && networkInfo.isConnected();
                }
            }
            return false;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return false;
        }
    }
    /**
     * 获取当前连接WIFI的SSID
     */
    public static String getSSID(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wm != null) {
            WifiInfo winfo = wm.getConnectionInfo();
            if (winfo != null) {
                String s = winfo.getSSID();
                if (s.length() > 2 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
                    return s.substring(1, s.length() - 1);
                }
            }
        }
        // 解决 8.0 9.0 系统中获取 wifiName 为 "unknown ssid" 问题
        // 9.0 系统，需要GPS权限才可以正确获取到WIFI名称
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            String s = wifiInfo.getExtraInfo();
            if (s!=null){
                if (s.length() > 2 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
                    return s.substring(1, s.length() - 1);
                }
            }
        }
        return "UNKNOWN";
    }
}
