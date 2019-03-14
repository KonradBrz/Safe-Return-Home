package com.tashfaelapp.konrad.safereturnhome;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gigamole.library.PulseView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textViewTime;
    TextView textViewMail;
    TextView textViewNumber;
    TextView textViewMarguee;
    TextView textViewInfo;

    String email;
    String numberPhone;

    ImageButton imageButton;
    ImageButton imageButtonCheck;
    ImageButton imageButtonWeather;

    AnimationDrawable animationDrawable;

    LocationGPS locationGPS;
    LocationNETWORK locationNETWORK;

    String newPhoneNumber;
    String newMailAddress;
    String newTime;

    Boolean mainButtonIsClicked = false;

    ArrayList<ExampleItem> exampleItems;

    PulseView pulseView;

    public static boolean showIconss = false;

    //but if I want only this for finish OpenChoiceActivity in other Activity?
    //public static Activity activityMain;
    //public static boolean isActiveMainActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusCheck();

        OpenChoiceActivity.activityOpenChoice.finish();

        textViewTime = findViewById(R.id.textViewTime);
        textViewMail = findViewById(R.id.textViewMail);
        textViewNumber = findViewById(R.id.textViewNumber);
        textViewInfo = findViewById(R.id.textViewInfo);

        imageButton = findViewById(R.id.imageButton);
        imageButtonCheck = findViewById(R.id.buttonnn);
        imageButtonWeather = findViewById(R.id.imageButtonWeather);

       // imageButtonCheck.animate().translationYBy(-300);
      //  imageButtonWeather.animate().translationYBy(-300);

        //textViewTime.animate().alpha(1).setDuration(2000);
        //textViewMail.animate().alpha(1).setDuration(2000);
        //textViewNumber.animate().alpha(1).setDuration(2000);

        textViewInfo.animate().alpha(1).setDuration(2000);

        textViewMarguee = findViewById(R.id.textViewMarquee);
        textViewMarguee.setSelected(true);

        pulseView = findViewById(R.id.pulse);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newPhoneNumber= null;
                newMailAddress= null;
                newTime= null;
            } else {
                newPhoneNumber= extras.getString("PHONE NUMBER I NEED");
                newMailAddress= extras.getString("EMAIL ADDRESS I NEED");
                newTime= extras.getString("TIME I NEED");
            }
        } else {
            newPhoneNumber= (String) savedInstanceState.getSerializable("PHONE NUMBER I NEED");
            newMailAddress= (String) savedInstanceState.getSerializable("EMAIL ADDRESS I NEED");
            newTime= (String) savedInstanceState.getSerializable("TIME I NEED");
        }

        textViewNumber.setText(newPhoneNumber);
        textViewMail.setText(newMailAddress);
        textViewTime.setText(newTime);
        textViewInfo.setText(String.valueOf(" number phone: " + newPhoneNumber + ", e-mail address: " + newMailAddress + ", time: " + newTime + " minutes."));

        exampleItems = new ArrayList<>();
    }

    public void start(View view){

        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        String stopText = "                        Click button to stop";
        textViewMarguee.setText(stopText);

        Log.i("INFO", "start MAIL : "+Blank_Mail.returnSmsOrMailBool());
        Log.i("INFO", "start SMS : "+Blank_Sms.returnSmsOrMailBool());


        if (mainButtonIsClicked) {

            if (Blank_Mail.returnSmsOrMailBool() && !Blank_Sms.returnSmsOrMailBool()) {

                imageButton.startAnimation(pulse);

                pulseView.finishPulse();

                Blank_Mail.smsOrMail = false;

                textViewNumber.setText(null);

                Intent serviceIntentNETWORK = new Intent(this, ServiceNETWORK.class);

                stopService(serviceIntentNETWORK);

                LocationNETWORK.mLocationManager.removeUpdates(LocationNETWORK.mLocationListenerNETWORK);

                LocationNETWORK.locationNETWORK = false;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        finish();
                    }
                },150);
            }

            if (Blank_Sms.returnSmsOrMailBool() && !Blank_Mail.returnSmsOrMailBool()) {

                imageButton.startAnimation(pulse);

                textViewMail.setText(null);

                Blank_Sms.smsOrMail = false;

                Intent serviceIntentGPS = new Intent(this, ServiceGPS.class);

                stopService(serviceIntentGPS);

                LocationGPS.mLocationManager.removeUpdates(LocationGPS.mLocationListenerGPS);

                LocationGPS.locationGPS = false;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        finish();
                    }
                },150);
            }
        }

        if (!mainButtonIsClicked) {

            mainButtonIsClicked = true;

            animationDrawable = (AnimationDrawable)imageButton.getDrawable();
            animationDrawable.start();

            imageButton.startAnimation(pulse);

            pulseView.startPulse();

            email = textViewMail.getText().toString();
            numberPhone = textViewNumber.getText().toString();

            int time = Integer.valueOf(newTime);
            int timeLocation = time*60000;

            if (Blank_Mail.returnSmsOrMailBool() && !Blank_Sms.returnSmsOrMailBool()) {

                Log.i("INFO", "getData: "+email);

                locationNETWORK = new LocationNETWORK(this, email, timeLocation);

                Intent serviceIntentNetwork = new Intent(this, ServiceNETWORK.class);
                //serviceIntentNetwork.putExtra("inputExtra", "Application running in the background");

                ContextCompat.startForegroundService(this, serviceIntentNetwork);
            }

            if (Blank_Sms.returnSmsOrMailBool() && !Blank_Mail.returnSmsOrMailBool()) {

                Log.i("INFO", "getData: "+numberPhone);

                locationGPS = new LocationGPS(this, numberPhone, timeLocation);

                Intent serviceIntentGps = new Intent(this, ServiceGPS.class);
                //serviceIntentGps.putExtra("inputExtra", "Application running in the background");

                ContextCompat.startForegroundService(this, serviceIntentGps);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();

        Intent intent = new Intent(getApplicationContext(), OpenChoiceActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);
    }

    public void buttonnn (View view) {

        Animation pulseWithoutDuration = AnimationUtils.loadAnimation(this, R.anim.pulse);
        imageButtonCheck.startAnimation(pulseWithoutDuration);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, CheckDeliveredMessagesActivity.class);
                intent.putParcelableArrayListExtra("myList", exampleItems);
                //intent.putExtra("myList", exampleItems);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }, 150);
    }

    public void imageButtonWeather (View view) {

        Animation pulseWithoutDuration = AnimationUtils.loadAnimation(this, R.anim.pulse);
        imageButtonWeather.startAnimation(pulseWithoutDuration);

        if (showIconss) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }, 150);
        }
    }

    @Override
    public void onBackPressed() {
        if (mainButtonIsClicked) {
            StyleableToast.makeText(this, "To go back, press the pulse button", R.style.exampleToastTwo).show();
        } else {
            super.onBackPressed();
        }
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, you have to enable it.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();

                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    void showIcons() {

//        imageButtonCheck.animate().translationYBy(300);
//        imageButtonWeather.animate().translationYBy(300);
//
//        //give some if statement?
//        imageButtonCheck.animate().alpha(1).setDuration(1000);
//        //Can I put weakreference fot this??
//        Animation pulseWithoutDuration = AnimationUtils.loadAnimation(this, R.anim.pulse_without_duration);
//        imageButtonCheck.startAnimation(pulseWithoutDuration);
//
//        imageButtonWeather.animate().alpha(1).setDuration(1000);
//
//        Animation pulseWithoutDuration1 = AnimationUtils.loadAnimation(this, R.anim.pulse_without_duration);
//        imageButtonWeather.startAnimation(pulseWithoutDuration1);
    }
}




