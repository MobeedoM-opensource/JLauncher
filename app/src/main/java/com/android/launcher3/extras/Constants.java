package com.android.launcher3.extras;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.launcher3.BuildConfig;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by massy on 12/02/2017.
 */

public class Constants {
    protected final static String REMOTE_CFG_URL = "https://";

    public final static String DRAWER_PROVIDER_INTENT = BuildConfig.APPLICATION_ID + ".extras.aidl.EXT_DRAWER_PROVIDER";
    public final static String DRAWER_PROVIDER_SERVICE = BuildConfig.APPLICATION_ID + ".extras.aidl.DRAWER_PROVIDER";
    public final static String REMOTE_EXEC_INTENT = BuildConfig.APPLICATION_ID + ".extras.aidl.REMOTE_EXEC";
    public static String DEFDRAWER_ID = "com.mobeedom.android.jinaFS";
    public static int  DEFDRAWER_MIN_V = 759;
    public static boolean isDefDrawerAvailable = false;
    public static boolean isDefDrawerOutdated = false;
    private static final String TAG = "Constants";

    public static void readRemoteCfg(final Context context) {
        final Properties remoteCfg = new Properties();
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Log.d(TAG, "Check remote cfg start");
                    URL url = new URL(REMOTE_CFG_URL);
                    InputStream in = url.openStream();
                    remoteCfg.clear();
                    remoteCfg.load(in);
                    Log.d(TAG, "Remote cfg = " + remoteCfg.values().toString());
                } catch (Exception e) {
                    Log.e(TAG, "Unable to fetch remote cfg " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {
                DEFDRAWER_ID = remoteCfg.getProperty("defdrawer_id", DEFDRAWER_ID);
                isDefDrawerAvailable = Utils.isAppInstalled(context, DEFDRAWER_ID, DEFDRAWER_MIN_V);
                if(!isDefDrawerAvailable && Utils.isAppInstalled(context, DEFDRAWER_ID))
                    isDefDrawerOutdated = true;
            }
        };
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
