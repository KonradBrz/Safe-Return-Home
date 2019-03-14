package com.tashfaelapp.konrad.safereturnhome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OpenFromNotification extends BroadcastReceiver {

    ServiceGPS serviceGPS;

    @Override
    public void onReceive(Context context, Intent intent) {

        openCurrentActivity();
    }

    private void openCurrentActivity () {

//        final Intent intent = new Intent(serviceGPS.getContext(), MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//        serviceGPS.getContext().startActivity(intent);
    }
}
