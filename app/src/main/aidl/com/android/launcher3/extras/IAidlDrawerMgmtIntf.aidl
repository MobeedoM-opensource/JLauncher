package com.android.launcher3.extras;

interface IAidlDrawerMgmtIntf {
    String getCurrentDrawerUrl();
    boolean setCurrentDrawerUrl(String uri);
    void notifyDrawerInstalled(String uri);
}
