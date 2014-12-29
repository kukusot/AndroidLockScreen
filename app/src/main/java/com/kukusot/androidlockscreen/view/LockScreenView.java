package com.kukusot.androidlockscreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kukusot.androidlockscreen.R;
import com.kukusot.androidlockscreen.app.App;


/**
 * Created by Ivan on 12/23/2014.
 */
public class LockScreenView extends FrameLayout {
    private GestureDetector mGestureDetector;

    public LockScreenView(Context context) {
        super(context);
        init();
    }

    public LockScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LockScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY | SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        inflate(getContext(), R.layout.view_lock, this);
        Log.e("tag", "context is"+getContext().getClass().getCanonicalName());
        mGestureDetector = new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (velocityX < -10.0f || velocityX > 10.0f){
                    App.unlock();
                }
                return true;

            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}
