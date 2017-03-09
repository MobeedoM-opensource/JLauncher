/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.launcher3;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.android.launcher3.stats.LauncherStats;
import com.android.launcher3.stats.internal.service.AggregationIntentService;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

public class LauncherApplication extends Application {

    private static LauncherStats sLauncherStats = null;
    private static LauncherApplication mInstance = null;

    /**
     * Get the reference handle for LauncherStats commands
     *
     * @return {@link LauncherStats}
     */
    public static LauncherStats getLauncherStats() {
        return sLauncherStats;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sLauncherStats = LauncherStats.getInstance(this);
        AggregationIntentService.scheduleService(this);
        LeakCanary.install(this);
    }

    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : infos) {
                if (processInfo.pid == pid) {
                    return processInfo.processName;
                }
            }
        }
        return null;
    }

    public static LauncherApplication getInstance() {
        return mInstance;
    }
}
