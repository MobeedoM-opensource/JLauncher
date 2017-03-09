package com.android.launcher3.extras;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.launcher3.Launcher;

/**
 * Created by massy on 11/02/2017.
 */

public class RemoteExecReceiver extends BroadcastReceiver {
    private final static String TAG = "RemoteExecReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            // allowed only if using external drawer and same package
            if (Launcher.isExternalDrawerActive(context) && "EXEC_REMOTE".equals(intent.getStringExtra("COMMAND"))) {
                Intent i = Intent.parseUri(intent.getStringExtra("URI"), 0);
                Intent drwI = Intent.parseUri(Launcher.getExternalDrawerUri(context), 0);
                if (drwI.getComponent().getPackageName().compareTo(i.getComponent().getPackageName()) != 0)
                    Log.d(TAG, String.format("RemoteExecReceiver.onReceive: skipped %s/%s", i.getPackage(), drwI.getComponent().getPackageName()));
                else
                    context.startActivity(i);
            } else {
                Log.d(TAG, String.format("RemoteExecReceiver.onReceive: skipped external drawer is disabled %s", intent.getStringExtra("COMMAND")));
            }
        } catch (Throwable e) {
            Log.e(TAG, "Error in onReceive", e);
        }
    }
}
