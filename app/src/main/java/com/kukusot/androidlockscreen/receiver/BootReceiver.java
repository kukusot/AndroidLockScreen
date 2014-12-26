package com.kukusot.androidlockscreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kukusot.androidlockscreen.app.App;

/**
 * Created by Ivan on 12/26/2014.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        App.lock();
    }
}
