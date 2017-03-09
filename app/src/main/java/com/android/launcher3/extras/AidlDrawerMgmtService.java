package com.android.launcher3.extras;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.launcher3.Launcher;
import com.android.launcher3.settings.SettingsProvider;

public class AidlDrawerMgmtService extends Service {
    private static final String TAG = "AidlDrawerMgmtService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IAidlDrawerMgmtIntf.Stub mBinder = new IAidlDrawerMgmtIntf.Stub() {
        @Override
        public String getCurrentDrawerUrl() throws RemoteException {
            return Launcher.getExternalDrawerUri(getApplicationContext());
        }

        @Override
        public boolean setCurrentDrawerUrl(String uri) throws RemoteException {
            SettingsProvider.putString(getApplicationContext(), SettingsProvider.SETTINGS_EXTERNAL_DRAWER_URI, uri);
            return true;
        }

        @Override
        public void notifyDrawerInstalled(String uri) throws RemoteException {
            if(uri != null) {
                try {
                    Intent i = Intent.parseUri(uri, 0);
                    if(Utils.equals(i.getComponent().getPackageName(), Constants.DEFDRAWER_ID)
                            && Utils.isAppInstalled(getApplicationContext(), Constants.DEFDRAWER_ID, Constants.DEFDRAWER_MIN_V)) {
                        SettingsProvider.putString(getApplicationContext(), SettingsProvider.SETTINGS_EXTERNAL_DRAWER_URI, uri);
                        SettingsProvider.putBoolean(getApplicationContext(), SettingsProvider.SETTINGS_UI_USE_EXTERNAL_DRAWER, true);
                        Constants.isDefDrawerAvailable = true;
                    }
                }catch (Throwable e) {
                    Log.e(TAG, "Error in notifyDrawerInstalled", e);
                }
            }
        }
    };
}
