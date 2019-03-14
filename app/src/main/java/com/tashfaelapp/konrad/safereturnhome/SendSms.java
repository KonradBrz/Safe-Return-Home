package com.tashfaelapp.konrad.safereturnhome;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


class SendSms {

    public static class MyTask extends AsyncTask<Void, Void, Void>{

        private final WeakReference<MainActivity> weakReference;

        private String numberPhone;
        private String message;
        private int accuracy;
        private int altitude;

        static String messageLink;

        private int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

        private String SENT = "SMS_SENT";
        private String DELIVERED = "SMS_DELIVERED";

        private PendingIntent sentPI;
        private PendingIntent deliveredPI;

        private BroadcastReceiver smsDeliveredReceiver;

        static String address = "Could not find address from gps location";

        MyTask(MainActivity activity, final String numberPhone, final String message, final int accuracy, final int altitude){

            weakReference = new WeakReference<>(activity);

            this.message = message;
            this.numberPhone = numberPhone;
            this.accuracy = accuracy;
            this.altitude = altitude;
//            sentPI = PendingIntent.getBroadcast(activity, 0, new Intent(SENT), 0);
//            deliveredPI = PendingIntent.getBroadcast(activity, 0, new Intent(DELIVERED), 0);

            //activity.registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            sentPI = PendingIntent.getBroadcast(weakReference.get(), 0, new Intent(SENT), 0);
            deliveredPI = PendingIntent.getBroadcast(weakReference.get(), 0, new Intent(DELIVERED), 0);

            //weakReference.get().registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
        }

        @Override
        protected Void doInBackground(Void... voids) {

            address();
            Log.i("INFO", "doInBackground: "+address);

            MainActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()){

                return null;
            }

            //we need check permission for old device?
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
            else{

                messageLink = "https://www.google.com/maps?q=" + message;

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(numberPhone, null,messageLink +"\n"+"accuracy: "+ accuracy +" meters, \naltitude: "+altitude+" meters" , sentPI, deliveredPI);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            final MainActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()) {

                return;
            }

            smsDeliveredReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    //I have to put everywhere weakReference.get instead activity?

                    switch (getResultCode())
                    {
                        case RESULT_OK:
                            //Toast.makeText(weakReference.get(),"Sms delivered", Toast.LENGTH_SHORT).show();
                            StyleableToast.makeText(weakReference.get(), "Sms delivered", R.style.exampleToast).show();

                            addItem();
                            weakReference.get().unregisterReceiver(smsDeliveredReceiver);

                            MainActivity.showIconss = true;

                            break;
                        case RESULT_CANCELED:

                            Toast.makeText(weakReference.get(),"Sms not delivered", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };
            weakReference.get().registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
        }
        private void addItem () {

            Date currentTime = Calendar.getInstance().getTime();
            String str = (String) currentTime.toString().subSequence(0,19);

            String locationAndDate = "Sms delivered at " + str + "\n" + address;

            weakReference.get().exampleItems.add(0, new ExampleItem.Builder()
                    .imageResource(R.drawable.ic_checkicon)
                    .location(locationAndDate)
                    .text(message)
                    .altitude(DataLocationAndWeather.getAltitude())
                    .accuracy(DataLocationAndWeather.getAccuracy())
                    .speed(DataLocationAndWeather.getSpeed())
                    .humidity(DataLocationAndWeather.getHumidity())
                    .pressure(DataLocationAndWeather.getPressure())
                    .wind(DataLocationAndWeather.getWind())
                    .visibility(DataLocationAndWeather.getVisibility())
                    .temperature(DataLocationAndWeather.getTemperature())
                    .build());

            Log.i("INFO", "addItem: Sms delivered at "+str+message);

//            weakReference.get().imageButtonCheck.animate().translationYBy(-200);
//            weakReference.get().imageButtonWeather.animate().translationYBy(-200);
//
//            //give some if statement?
//            weakReference.get().imageButtonCheck.animate().alpha(1).setDuration(1000);
//            //Can I put weakreference fot this??
//            Animation pulseWithoutDuration = AnimationUtils.loadAnimation(weakReference.get(), R.anim.pulse_without_duration);
//            weakReference.get().imageButtonCheck.startAnimation(pulseWithoutDuration);
//
//            weakReference.get().imageButtonWeather.animate().alpha(1).setDuration(1000);
//
//            Animation pulseWithoutDuration1 = AnimationUtils.loadAnimation(weakReference.get(), R.anim.pulse_without_duration);
//            weakReference.get().imageButtonWeather.startAnimation(pulseWithoutDuration1);

            weakReference.get().showIcons();
        }

        private void address() {

            Geocoder geocoder = new Geocoder(weakReference.get(), Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocation(DataLocationAndWeather.getLatitude(), DataLocationAndWeather.getLongitude(), 1);

                if (addressList != null && addressList.size() > 0 ) {

                    address = "";

                    if (addressList.get(0).getThoroughfare() != null) {

                        address += addressList.get(0).getThoroughfare() + "\n";
                    }

                    if (addressList.get(0).getLocality() != null) {

                        address += addressList.get(0).getLocality() + " ";
                    }

                    if (addressList.get(0).getAdminArea() != null) {

                        address += addressList.get(0).getAdminArea();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
