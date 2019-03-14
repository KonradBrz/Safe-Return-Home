package com.tashfaelapp.konrad.safereturnhome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DestroyFromNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        onBackPressed();
    }

    private void onBackPressed() {

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}