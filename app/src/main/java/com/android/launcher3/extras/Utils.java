package com.android.launcher3.extras;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by massy on 13/02/2017.
 */

public class Utils {
    public static boolean equals(String s1, String s2) {
        if (s1 == null && s2 == null)
            return false;
        if (s1 != null && s2 == null || s1 == null && s2 != null)
            return false;
        return s1.compareTo(s2) == 0;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        return isAppInstalled(context, packageName, -1);
    }

    public static boolean isAppInstalled(Context context, String packageName, int minVersion) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            if(minVersion >= 0 && info.versionCode < minVersion)
                return false;
            else
                return true;
        } catch (Exception e) {
            return false;
        }
    }
}
