package com.tashfaelapp.konrad.safereturnhome;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

class SendMail {

    public static class MyTask extends AsyncTask<Void, Void, Void> {

        private final WeakReference<MainActivity> weakReference;

        private Session session;

        private String email;
        private String subject;
        private String message;
        private int accuracy;
        private int altitude;

        private ProgressDialog progressDialog;

        public static String messageLink;

        public static String address = "Could not find address from gps location";

        MyTask(MainActivity activity, String email, String subject, String message, int accuracy, int altitude) {

            weakReference = new WeakReference<>(activity);

            this.email = email;
            this.subject = subject;
            this.message = message;
            this.accuracy = accuracy;
            this.altitude = altitude;

            messageLink = "https://www.google.com/maps?q=" + message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MainActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()) {

                return;
            }
            //Showing progress dialog while sending email
            progressDialog = ProgressDialog.show(activity, "Sending message", "Please wait...", false, false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            address();
            Log.i("INFO", "doInBackground: " + address);

            Properties props = new Properties();

            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                        }
                    });
            try {
                //Creating MimeMessage object
                MimeMessage mm = new MimeMessage(session);
                //Setting sender address
                mm.setFrom(new InternetAddress(Config.EMAIL));
                //Adding receiver
                mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                //Adding subject
                mm.setSubject(subject);
                //Adding message
                mm.setText(messageLink + "\n" + "accuracy: " + accuracy + " meters, \naltitude: " + altitude + " meters", "UTF-8", "html");
                //Sending email
                Transport.send(mm);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            MainActivity activity = weakReference.get();
            if (activity == null || activity.isFinishing()) {

                return;
            }

            //Dismissing the progress dialog
            progressDialog.dismiss();
            //Showing a success message
            //Toast.makeText(activity,"Message sent",Toast.LENGTH_LONG).show();
            StyleableToast.makeText(activity, "Message sent", R.style.exampleToast).show();

            addItem();
        }

        private void addItem() {

            Date currentTime = Calendar.getInstance().getTime();
            String str = (String) currentTime.toString().subSequence(0, 19);

            String locationAndDate = "Mail delivered at " + str + "\n" + address;

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

            Log.i("INFO", "addItem: Mail sent at " + str + message);

            MainActivity.showIconss = true;

//            weakReference.get().imageButtonCheck.animate().translationYBy(-200);
//            weakReference.get().imageButtonWeather.animate().translationYBy(-200);
//
//            //give some if statement??
//            weakReference.get().imageButtonCheck.animate().alpha(1).setDuration(1000);
//            //Can I put weak reference fot this??
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
                List<Address> addressList = geocoder.getFromLocation(LocationNETWORK.getLatitude(), LocationNETWORK.getLongitude(), 1);

                if (addressList != null && addressList.size() > 0) {

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
