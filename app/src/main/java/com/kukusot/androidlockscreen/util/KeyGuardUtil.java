package com.kukusot.androidlockscreen.util;

import android.app.KeyguardManager;
import android.content.Context;

/**
 * Created by Ivan on 12/26/2014.
 */
public class KeyGuardUtil {

    private Context context;
    private static KeyGuardUtil instance;
    private boolean isKeyGuardActive = true;

    private KeyguardManager keyguardManager;
    private KeyguardManager.KeyguardLock keyguardLock;

    private KeyGuardUtil(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        instance = new KeyGuardUtil(context.getApplicationContext());
    }

    public static KeyGuardUtil getInstance() {
        return instance;
    }

    public void disableKeyGuard() {
        if (keyguardManager == null) {
            keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            keyguardLock = keyguardManager.newKeyguardLock(context.getPackageName());
        }
        if (isKeyGuardActive) {
            isKeyGuardActive = false;
            keyguardLock.disableKeyguard();
        }
    }

}
