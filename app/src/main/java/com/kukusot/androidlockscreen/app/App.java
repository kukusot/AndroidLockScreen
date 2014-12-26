package com.kukusot.androidlockscreen.app;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.kukusot.androidlockscreen.service.LockerService;
import com.kukusot.androidlockscreen.util.KeyGuardUtil;

/**
 * Created by Ivan on 12/24/2014.
 */
public class App extends Application {

    static LockerService.ViewServiceBinder binder;


    @Override
    public void onCreate() {
        super.onCreate();
        bindLockerService();
        KeyGuardUtil.init(this);

    }

    private void bindLockerService() {
        Intent intent = new Intent(this, LockerService.class);
        bindService(intent, new LockServiceConnection(), Context.BIND_AUTO_CREATE);
    }


    private class LockServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (LockerService.ViewServiceBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    public static void lock() {
        KeyGuardUtil.getInstance().disableKeyGuard();
        binder.getService().showView();
    }

    public static void unlock() {
        binder.getService().removeView();
    }
}
