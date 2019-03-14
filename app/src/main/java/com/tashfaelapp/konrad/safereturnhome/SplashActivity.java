package com.tashfaelapp.konrad.safereturnhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = findViewById(R.id.textView);

        textView.setY(-1000);

        textView.animate().translationYBy(400).alpha(1).setDuration(400);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                textView.animate().alpha(0).setDuration(400);
            }
        }, 1000);

        nextActivity();
    }
    private void nextActivity(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),OpenChoiceActivity.class));
                //without animation when second activity started
                overridePendingTransition(0, 0);
            }
        }, 1500);
    }
}
