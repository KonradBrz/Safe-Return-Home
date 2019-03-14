package com.tashfaelapp.konrad.safereturnhome;

import android.os.Parcel;
import android.os.Parcelable;

public class ExampleItem implements Parcelable {

    private int imageResource;
    private String text;
    private String location;
    private int altitude;
    private int accuracy;
    private int speed;
    private String humidity;
    private String visibility;
    private String wind;
    private String pressure;
    private String temperature;

    //    ExampleItem(int mImageResource, String mText, String mLocation, int mAltitude) {
//
//        this.mImageResource = mImageResource;
//        this.mText = mText;
//        this.mLocation = mLocation;
//        this.mAltitude = mAltitude;
//    }

    public static class Builder {

        private int imageResource;
        private String text;
        private String location;

        private int altitude;
        private int accuracy;
        private int speed;

        private String humidity;
        private String visibility;
        private String wind;
        private String pressure;
        private String temperature;

        public Builder imageResource(Integer imageResource) {
            this.imageResource = imageResource;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder altitude(Integer altitude) {
            this.altitude = altitude;
            return this;
        }

        public Builder accuracy(Integer accuracy) {
            this.accuracy = accuracy;
            return this;
        }

        public Builder speed(Integer speed) {
            this.speed = speed;
            return this;
        }

        public Builder humidity(String humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder visibility(String visibility) {
            this.visibility = visibility;
            return this;
        }

        public Builder wind(String wind) {
            this.wind = wind;
            return this;
        }

        public Builder pressure(String pressure) {
            this.pressure = pressure;
            return this;
        }

        public Builder temperature(String temperature) {
            this.temperature = temperature;
            return this;
        }

        public ExampleItem build() {
            return new ExampleItem(this);
        }
    }

    private ExampleItem(Builder builder) {
        this.imageResource = builder.imageResource;
        this.text = builder.text;
        this.location = builder.location;
        this.altitude = builder.altitude;
        this.accuracy = builder.accuracy;
        this.speed = builder.speed;
        this.humidity = builder.humidity;
        this.visibility = builder.visibility;
        this.wind = builder.wind;
        this.pressure = builder.pressure;
        this.temperature = builder.temperature;
    }

    int getImageResource() {
        return imageResource;
    }

    String getText() {
        return text;
    }

    String getLocation() {
        return location;
    }

    int getAltitude() {
        return altitude;
    }

    int getAccuracy() {
        return accuracy;
    }

    int getSpeed() {
        return speed;
    }

    String getHumidity() {
        return humidity;
    }

    String getVisibility() {return visibility;}

    String getWind() {return wind;}

    String getPressure() {return pressure;}

    String getTemperature() {return temperature;}

    protected ExampleItem(Parcel in) {

        imageResource = in.readInt();
        text = in.readString();
        location = in.readString();
        altitude = in.readInt();
        accuracy = in.readInt();
        speed = in.readInt();
        humidity = in.readString();
        visibility = in.readString();
        wind = in.readString();
        pressure = in.readString();
        temperature = in.readString();
    }

    public static final Creator<ExampleItem> CREATOR = new Creator<ExampleItem>() {
        @Override
        public ExampleItem createFromParcel(Parcel in) {
            return new ExampleItem(in);
        }

        @Override
        public ExampleItem[] newArray(int size) {
            return new ExampleItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(imageResource);
        dest.writeString(text);
        dest.writeString(location);
        dest.writeInt(altitude);
        dest.writeInt(accuracy);
        dest.writeInt(speed);
        dest.writeString(humidity);
        dest.writeString(visibility);
        dest.writeString(wind);
        dest.writeString(pressure);
        dest.writeString(temperature);
    }
}
