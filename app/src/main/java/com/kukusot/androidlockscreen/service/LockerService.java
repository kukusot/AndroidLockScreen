package com.kukusot.androidlockscreen.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.kukusot.androidlockscreen.app.App;
import com.kukusot.androidlockscreen.view.LockScreenView;

/**
 * Created by Ivan on 12/23/2014.
 */
public class LockerService extends Service {

    Binder binder = new ViewServiceBinder();
    private boolean isViewAttached;
    private WindowManager windowManager;
    private BroadcastReceiver callReceiver;

    private WindowManager.LayoutParams playerParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
            WindowManager.LayoutParams.FLAG_FULLSCREEN, PixelFormat.TRANSLUCENT);
    private LockScreenView lockScreenView;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public class ViewServiceBinder extends Binder {
        public LockerService getService() {
            return LockerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        lockScreenView = new LockScreenView(this);
        callReceiver = new CallsReceiver();
        IntentFilter callFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callReceiver, callFilter);
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(lockScreenReceiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(callReceiver);
        unregisterReceiver(lockScreenReceiver);
        super.onDestroy();
    }

    public void showView() {

        if (windowManager != null && lockScreenView != null && !isViewAttached) {
            windowManager.addView(lockScreenView, playerParams);
            isViewAttached = true;
        }
    }

    public void removeView() {
        if (windowManager != null && lockScreenView != null && isViewAttached) {
            windowManager.removeView(lockScreenView);
            isViewAttached = false;
        }
    }

    BroadcastReceiver lockScreenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                App.lock();
            }

        }
    };

    class CallsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                removeView();
            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                showView();
            }
        }
    }


}
