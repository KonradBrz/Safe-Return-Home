package com.tashfaelapp.konrad.safereturnhome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "WEATHER_LOCATION_CONDITIONS";
    static final int DB_VERSION = 1;
    static final String TABLE_NAME = "WEALOC_TABLE";
    static final String COL_ID = "ID";
    static final String COL_LAT_LON = "LATITUDE_LONGITUDE";
    static final String COL_ADDRESS_TIME = "ADDRESS_AND_TIME";
    static final String COL_ALTITUDE = "ALTITUDE";
    static final String COL_TEMPERATURE = "TEMPERATURE";
    static final String COL_SPEED = "SPEED";
    static final String COL_ACCURACY = "ACCURACY";
    static final String COL_HUMIDITY = "HUMIDITY";
    static final String COL_VISIBILITY = "VISIBILITY";
    static final String COL_WIND = "WIND";
    static final String COL_PRESSURE = "PRESSURE";
    //static final String COLUMN_TIMESTAMP = "timestamp";

    SQLiteDatabase sqLiteDatabase;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY,"
                + COL_LAT_LON + " TEXT,"
                + COL_ADDRESS_TIME + " TEXT,"
                + COL_TEMPERATURE + " TEXT,"
                + COL_SPEED + " TEXT,"
                + COL_ALTITUDE + " TEXT,"
                + COL_ACCURACY + " TEXT,"
                + COL_HUMIDITY + " TEXT,"
                + COL_VISIBILITY + " TEXT,"
                + COL_WIND + " TEXT,"
                + COL_PRESSURE + " TEXT);";

        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void insertConditions(String latlon, String name, String temperature, int speed, int altitude, int accuracy, String humidity, String visibility, String wind, String pressure) {

        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_LAT_LON, latlon);
        values.put(COL_ADDRESS_TIME, name);
        values.put(COL_TEMPERATURE, temperature);
        values.put(COL_SPEED, speed);
        values.put(COL_ALTITUDE, altitude);
        values.put(COL_ACCURACY, accuracy);
        values.put(COL_HUMIDITY, humidity);
        values.put(COL_VISIBILITY, visibility);
        values.put(COL_WIND, wind);
        values.put(COL_PRESSURE, pressure);

        sqLiteDatabase.insert(TABLE_NAME, null, values);

        //DataBaseAdapter dataBaseAdapter = new DataBaseAdapter()

        sqLiteDatabase.close();
    }

    public ArrayList<Conditions> getAllConditions() {

        ArrayList<Conditions> conditions = new ArrayList<>();

        sqLiteDatabase = this.getReadableDatabase();

        String select_all = "Select * from " + TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(select_all, null);

        if (cursor.moveToFirst()) {

            do {
                Conditions conditions1 = new Conditions(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9));

                conditions.add(conditions1);

            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return conditions;
    }
}

class Conditions {

    private String latlon;
    private String name;
    private String temperature;
    private int speed;
    private int altitude;
    private int accuracy;
    private String humidity;
    private String visibility;
    private String wind;
    private String pressure;

    public Conditions(String latlon, String name, String temperature, int speed, int altitude, int accuracy, String humidity, String visibility, String wind, String pressure) {
        this.latlon = latlon;
        this.name = name;
        this.temperature = temperature;
        this.speed = speed;
        this.altitude = altitude;
        this.accuracy = accuracy;
        this.humidity = humidity;
        this.visibility = visibility;
        this.wind = wind;
        this.pressure = pressure;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getTemperature() {
//        return temperature;
//    }
//
//    public void setTemperature(String temperature) {
//        this.temperature = temperature;
//    }
//
//    public int getSpeed() {
//        return speed;
//    }
//
//    public void setSpeed(int speed) {
//        this.speed = speed;
//    }
//
//    public int getAltitude() {
//        return altitude;
//    }
//
//    public void setAltitude(int altitude) {
//        this.altitude = altitude;
//    }
//
//    public int getAccuracy() {
//        return accuracy;
//    }
//
//    public void setAccuracy(int accuracy) {
//        this.accuracy = accuracy;
//    }
//
//    public String getHumidity() {
//        return humidity;
//    }
//
//    public void setHumidity(String humidity) {
//        this.humidity = humidity;
//    }
//
//    public String getVisibility() {
//        return visibility;
//    }
//
//    public void setVisibility(String visibility) {
//        this.visibility = visibility;
//    }
//
//    public String getWind() {
//        return wind;
//    }
//
//    public void setWind(String wind) {
//        this.wind = wind;
//    }
//
//    public String getPressure() {
//        return pressure;
//    }
//
//    public void setPressure(String pressure) {
//        this.pressure = pressure;
//    }

    @NonNull
    @Override
    public String toString() {
        //return super.toString();
        return "\n" + name + "  " +
                temperature + " Temperature" + "  " +
                speed + " km/h Speed" + "  " +
                altitude + " m altitude" + "  " +
                accuracy + " m accuracy" + "  " +
                humidity + "  " +
                visibility + "  " +
                wind + "  " +
                pressure + "\n==================================";
    }
}