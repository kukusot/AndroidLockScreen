package com.kukusot.androidlockscreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kukusot.androidlockscreen.R;
import com.kukusot.androidlockscreen.app.App;


/**
 * Created by Ivan on 12/23/2014.
 */
public class LockScreenView extends FrameLayout {

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
        TextView textView = (TextView) findViewById(R.id.unlock);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                App.unlock();
            }
        });

    }
}
