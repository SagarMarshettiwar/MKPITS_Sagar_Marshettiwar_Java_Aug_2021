package com.example.orcodegenerator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Objects;

public class trustM {




    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEINumber(Context context) {
        try {
            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                if (!TextUtils.isEmpty(telephonyManager.getDeviceId())) {
                    return telephonyManager.getDeviceId();
                } else {
                    return "Device ID not found!";
                }
            } else {
                String imeiNo = null;
                List<SubscriptionInfo> subsList = null;
                SubscriptionManager subsManager = null;
                subsManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                subsList = Objects.requireNonNull(subsManager).getActiveSubscriptionInfoList();
                imeiNo = subsList.get(0).getIccId();
                if (!TextUtils.isEmpty(imeiNo)) {
                    return imeiNo;
                } else {
                    imeiNo = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    if (!TextUtils.isEmpty(imeiNo)) {
                        return imeiNo;
                    } else {
                        return "Device id not found";
                    }

                }
            }
        } catch (Exception e) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (!TextUtils.isEmpty(telephonyManager.getDeviceId())) {
                    return telephonyManager.getDeviceId();
                } else {
                    String imeiNo = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    if (!TextUtils.isEmpty(imeiNo)) {
                        return imeiNo;
                    } else {
                        return "Device id not found!!";
                    }
                }
            } catch (Exception e1) {
                try{
                    String imeiNo = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    if (!TextUtils.isEmpty(imeiNo)) {
                        return imeiNo;
                    }else {
                        return "Device id not found!!!";
                    }
                }catch (Exception e2){
                    return "Device id not found!!!!";
                }
            }

        }
    }
}
