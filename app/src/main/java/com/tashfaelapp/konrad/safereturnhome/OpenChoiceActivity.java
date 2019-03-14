package com.tashfaelapp.konrad.safereturnhome;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenChoiceActivity extends AppCompatActivity implements Blank_Sms.OnFragmentInteractionListener, Blank_Mail.OnFragmentInteractionListener {

    private ImageButton imageButtonEmail;
    private ImageButton imageButtonPhone;
    private LinearLayout linearLayout;
    private ImageView imageViewInfoIcon;

    private Toast toast;

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    //but if I want only this for finish OpenChoiceActivity in other Activity?
    public static Activity activityOpenChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_choice);

        checkAndRequestPermissions();

//        ConstraintLayout constraintLayout = findViewById(R.id.open_choice_layout);
//        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(3000);
//        animationDrawable.setExitFadeDuration(6000);
//        animationDrawable.start();

        activityOpenChoice = this;

        imageButtonEmail = findViewById(R.id.imageButtonEmail);
        imageButtonPhone = findViewById(R.id.imageButtonPhone);

        imageViewInfoIcon = findViewById(R.id.imageViewInfoIcon);

        linearLayout = findViewById(R.id.linearLayout);

        imageButtonEmail.animate().alpha(1).setDuration(800);
        imageButtonPhone.animate().alpha(1).setDuration(800);

        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBlankSms();
            }
        });

        imageButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBlankMail();
            }
        });

        imageViewInfoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showToast();
            }
        });
    }

    public void openBlankMail() {

        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        imageButtonEmail.startAnimation(pulse);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Blank_Mail blankMail = Blank_Mail.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.fragment_container_email_sms, blankMail, "BLANK_MAIL").commit();

                linearLayout.animate().translationYBy(450).setDuration(150);
            }
        }, 80);
    }

    public void openBlankSms() {

        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        imageButtonPhone.startAnimation(pulse);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Blank_Sms blankSms = Blank_Sms.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_left);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.fragment_container_email_sms, blankSms, "BLANK_SMS").commit();

                linearLayout.animate().translationYBy(450).setDuration(150);
            }
        }, 80);

//        new AlertDialog.Builder(this)
//                .setTitle("Option not available :(")
//                .setMessage("Unfortunately this option (automatically sending sms) is not available at this moment, because Google not accept \"sms permission\", so I can show this function during a job interview and in the future I'll buy a number phone for this app :)")
//
//                // Specifying a listener allows you to take an action before dismissing the dialog.
//                // The dialog is automatically dismissed when a dialog button is clicked.
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Continue with delete operation
//                    }
//                })
//
//                // A null listener allows the button to dismiss the dialog and take no further action.
//                .setNegativeButton(android.R.string.no, null)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
    }

    public void save(View view) {

        ArrayList<Conditions> conditions;

        SQLiteHelper sqLiteHelper;
        sqLiteHelper = new SQLiteHelper(this);
        conditions = sqLiteHelper.getAllConditions();

        Log.i("LIST SAVED", "save: " + conditions.toString());

        Intent intent = new Intent(OpenChoiceActivity.this, DataBaseForLocations.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

        onBackPressed();
    }

    public void returnLinearLayout() {

        linearLayout.animate().translationYBy(-450).setDuration(100);
    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int readContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int internetPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (readContactsPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (internetPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.d("INFO", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("INFO", "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("INFO", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                            showDialogOK(
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage("SMS, Read Contacts, Location Services and Internet Permission required for this app")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    public void showToast() {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void finish() {
        super.finish();
        if (toast != null) {
            toast.cancel();
        }
    }
}


