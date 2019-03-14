package com.tashfaelapp.konrad.safereturnhome;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class WidgetService extends Service {

    private WindowManager mWindowManager;
    private View mFloatingHeadView;

    WindowManager.LayoutParams params;

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    public WidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {
        super.onCreate();

        mFloatingHeadView = LayoutInflater.from(this).inflate(R.layout.app_widget, null);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }else{
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (mWindowManager != null) {
            Log.d("Bubble", "added bubble to view.");
            mWindowManager.addView(mFloatingHeadView, params);
        } else {
            Log.d("Bubble", "windowManager is null");
        }

        ImageView closeButton = mFloatingHeadView.findViewById(R.id.chat_head_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopSelf();
            }
        });

        final ImageView chatHeadImage = mFloatingHeadView.findViewById(R.id.chat_head);
        chatHeadImage.setOnTouchListener(new View.OnTouchListener() {

            private int lastAction;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        initialX = params.x;
                        initialY = params.y;

                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        lastAction = event.getAction();
                        return true;
                    case MotionEvent.ACTION_UP:

                        if (lastAction == MotionEvent.ACTION_DOWN) {

                            Intent intent = new Intent(WidgetService.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);


                            stopSelf();
                        }

                        lastAction = event.getAction();
                        return true;

                    case MotionEvent.ACTION_MOVE:

                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        mWindowManager.updateViewLayout(mFloatingHeadView, params);
                        lastAction = event.getAction();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingHeadView != null) mWindowManager.removeView(mFloatingHeadView);
    }
}
