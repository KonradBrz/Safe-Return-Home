package com.tashfaelapp.konrad.safereturnhome;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class DataLocationAndWeather {

    private static final String BASE_URL = "http://api.openweathermap.org/";
    private static final String APP_ID = "dbc00a2dd2222daec032a144a26d4d0a";

    private static int altitude;
    private static int accuracy;

    private static double latitude;
    private static double longitude;
    private static int speed;

    private static String temperature;
    private static String humidity;
    private static String visibility;
    private static String pressure;
    private static String wind;
    private static String sunrise;
    private static String sunset;
    private static String country;
    private static String name;

    private static String iconWeather;

    DataLocationAndWeather(double latitude, double longitude, int altitude, int accuracy, int speed) {

       DataLocationAndWeather.latitude = latitude;
       DataLocationAndWeather.longitude = longitude;
       DataLocationAndWeather.altitude = altitude;
       DataLocationAndWeather.accuracy = accuracy;
       DataLocationAndWeather.speed = speed;
    }

    void getCurrentData() {

        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        Call<WeatherResponse> call = service.getCurrentWeatherData(lat, lon, APP_ID);

        call.enqueue(new Callback<WeatherResponse>() {
            @SuppressLint("SimpleDateFormat")
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {

                if (response.code() == 200) {

                    //WTF HERE?
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    final float celsiusConverter = (float) 273.15;
                    float celsius = (Float.parseFloat(String.valueOf(weatherResponse.main.temp)) - celsiusConverter);
                    temperature = String.valueOf(new DecimalFormat("##.#").format(celsius)) + "°";

                    humidity = weatherResponse.main.humidity + "\n" + "Humidity";
                    visibility = weatherResponse.visibility + " m\n" + "Visibility";
                    pressure = weatherResponse.main.pressure + "\n" + "Pressure";
                    wind = weatherResponse.wind.speed + " m/s\n" + "Wind";//deg here?
                    country = weatherResponse.sys.country;
                    name = weatherResponse.name;

                    //String sunrise = new SimpleDateFormat("H:mm  dd MMM yy").format(new Date(weatherResponse.sys.sunrise*1000)) + " \n" + "Sunrise";
                    sunrise = new SimpleDateFormat("H:mm").format(new Date(weatherResponse.sys.sunrise * 1000)) + " \n" + "Sunrise";
                    sunset = new SimpleDateFormat("H:mm").format(new Date(weatherResponse.sys.sunset * 1000)) + " \n" + "Sunset";

                    iconWeather = weatherResponse.weather.get(0).icon;

                    String weatherData =
                                    "Country: " + weatherResponse.sys.country + "\n" +
                                    "Name: " + weatherResponse.name + "\n" +
                                    "Temperature: " + celsius + "\n" +
                                    "Temperature(Min): " + weatherResponse.main.temp_min + "\n" +
                                    "Temperature(Max): " + weatherResponse.main.temp_max + "\n" +
                                    "Humidity: " + weatherResponse.main.humidity + "\n" +
                                    "Pressure: " + weatherResponse.main.pressure + "\n" +
                                    "Visibility: " + weatherResponse.visibility + "\n" +
                                    "Altitude: " +altitude + "\n" +
                                    "Accuracy: " +accuracy + "\n" +
                                    "Latitude: " +latitude + "\n" +
                                    "Longitude: " +longitude + "\n" +
                                    "Temperature: " +temperature + "\n";

                    Log.i("POGODA", "onResponse: "+weatherData);

                    //textViewCountry.setText(weatherData);

                    //String ic = weatherResponse.weather.get(0).icon;
                    //String icUrl = "http://openweathermap.org/img/w/" + ic + ".png";
                    //Picasso.get().load(icUrl).into(imageViewIcon);
                    //String k = "https://cdn.onlinewebfonts.com/svg/img_31317.png";
                    //Picasso.get().load(k).into(imageViewIcon);

                    Log.i("INFO", "onResponse: " + SendSms.MyTask.address);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {

                Log.i("Weather response", "onFailure: "+t);
            }
        });
    }

    static int getAltitude() {
        return altitude;
    }

    static int getAccuracy() {
        return accuracy;
    }

    static int getSpeed() {return speed;}

    static double getLatitude() {
        return latitude;
    }

    static double getLongitude() {
        return longitude;
    }

    static String getTemperature() {
        return temperature;
    }

    static String getHumidity() {
        return humidity;
    }

    static String getVisibility() {
        return visibility;
    }

    static String getPressure() {
        return pressure;
    }

    static String getWind() {
        return wind;
    }

    static String getSunrise() {
        return sunrise;
    }

    static String getSunset() {
        return sunset;
    }

    static String getIconWeather() {
        return iconWeather;
    }

    static String getCountry() { return country;}

    static String getName() { return name;}
}
