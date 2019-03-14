package com.tashfaelapp.konrad.safereturnhome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a01d;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a01n;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a02d;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a02n;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a03d;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a03n;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a04d;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a04n;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a09d;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a09n;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a10d;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a10n;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a11d;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a11n;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a13d;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a13n;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a50d;
import static com.tashfaelapp.konrad.safereturnhome.R.drawable.ic_a50n;

public class WeatherActivity extends AppCompatActivity {

    private TextView textViewCountry;
    private ImageView imageViewIcon;
    private TextView textViewCelsius;
    private TextView textViewAltitude;
    private TextView textViewAccuracy;
    private TextView textViewHumidity;
    private TextView textViewVisibility;
    private TextView textViewPressure;
    private TextView textViewWind;
    private TextView textViewSunrise;
    private TextView textViewSunset;
    private TextView textViewAddress;

    private android.support.v7.widget.GridLayout gridLayout;
    private android.support.v7.widget.GridLayout gridLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        textViewCountry = findViewById(R.id.textView4);
        imageViewIcon = findViewById(R.id.imageViewIcon);
        textViewCelsius = findViewById(R.id.textViewCelsius);
        textViewAltitude = findViewById(R.id.textViewAltitude);
        textViewAccuracy = findViewById(R.id.textViewAccuracy);
        textViewHumidity = findViewById(R.id.textViewHumidity);
        textViewVisibility = findViewById(R.id.textViewVisibility);
        textViewPressure = findViewById(R.id.textViewPressure);
        textViewWind = findViewById(R.id.textViewWind);
        textViewSunrise = findViewById(R.id.textViewSunrise);
        textViewSunset = findViewById(R.id.textViewSunset);
        textViewAddress = findViewById(R.id.textViewAddress);

        gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setY(450);

        gridLayout2 = findViewById(R.id.gridLayout2);
        gridLayout2.setY(450);

        setCurrentData();
    }

    public void setCurrentData() {

        String altitude = String.valueOf(DataLocationAndWeather.getAltitude() + " m\n" + "Altitude");
        String accuracy = String.valueOf(DataLocationAndWeather.getAccuracy() + " m\n" + "Accuracy");

        Log.i("INFO", "getCurrentData: " + altitude);
        Log.i("INFO", "getCurrentData: " + accuracy);

        if (LocationGPS.getLocationGPS()) {

            if (!SendSms.MyTask.address.equals("Could not find address from gps location")) {

                textViewAddress.setText(SendSms.MyTask.address);
            } else {

                textViewAddress.setText(DataLocationAndWeather.getName());
            }
        }

        if (LocationNETWORK.getLocationNETWORK()) {

            if (!SendMail.MyTask.address.equals("Could not find address from gps location")) {

                textViewAddress.setText(SendMail.MyTask.address);
            } else {

                textViewAddress.setText(DataLocationAndWeather.getName());
            }
        }

        textViewAltitude.setText(altitude);
        textViewAccuracy.setText(accuracy);

        textViewCelsius.setText(DataLocationAndWeather.getTemperature());
        textViewHumidity.setText(DataLocationAndWeather.getHumidity());
        textViewVisibility.setText(DataLocationAndWeather.getVisibility());
        textViewPressure.setText(DataLocationAndWeather.getPressure());
        textViewWind.setText(DataLocationAndWeather.getWind());
        textViewSunrise.setText(DataLocationAndWeather.getSunrise());
        textViewSunset.setText(DataLocationAndWeather.getSunset());
        textViewCountry.setText(DataLocationAndWeather.getCountry());

        String icon = DataLocationAndWeather.getIconWeather();

        switch (icon) {

            case "01d":
                imageViewIcon.setImageResource(ic_a01d);
                break;
            case "01n":
                imageViewIcon.setImageResource(ic_a01n);
                break;
            case "02d":
                imageViewIcon.setImageResource(ic_a02d);
                break;
            case "02n":
                imageViewIcon.setImageResource(ic_a02n);
                break;
            case "03d":
                imageViewIcon.setImageResource(ic_a03d);
                break;
            case "03n":
                imageViewIcon.setImageResource(ic_a03n);
                break;
            case "04d":
                imageViewIcon.setImageResource(ic_a04d);
                break;
            case "04n":
                imageViewIcon.setImageResource(ic_a04n);
                break;
            case "09d":
                imageViewIcon.setImageResource(ic_a09d);
                break;
            case "09n":
                imageViewIcon.setImageResource(ic_a09n);
                break;
            case "10d":
                imageViewIcon.setImageResource(ic_a10d);
                break;
            case "10n":
                imageViewIcon.setImageResource(ic_a10n);
                break;
            case "11d":
                imageViewIcon.setImageResource(ic_a11d);
                break;
            case "11n":
                imageViewIcon.setImageResource(ic_a11n);
                break;
            case "13d":
                imageViewIcon.setImageResource(ic_a13d);
                break;
            case "13n":
                imageViewIcon.setImageResource(ic_a13n);
                break;
            case "50d":
                imageViewIcon.setImageResource(ic_a50d);
                break;
            case "50n":
                imageViewIcon.setImageResource(ic_a50n);
                break;
        }

        gridLayout.animate().alpha(1).translationYBy(-450).setDuration(250);
        gridLayout2.animate().alpha(1).translationYBy(-450).setDuration(350);
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
