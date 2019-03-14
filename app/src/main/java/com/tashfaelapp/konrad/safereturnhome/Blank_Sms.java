package com.tashfaelapp.konrad.safereturnhome;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Blank_Sms.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Blank_Sms#newInstance} factory method to
// * create an instance of this fragment.
// */
public class Blank_Sms extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText editTextFragment;
    private ImageButton imageButtonFragment;
    private SeekBar seekBarFragment;
    private TextView textViewFragment;
    private ImageButton contacts;

    public static boolean smsOrMail = false;
    private static boolean buttonTick = false;

    private static final int REQUEST_READ_CONTACTS = 79;
    private static final int PICK_CONTACT = 1;

    private String name;
    private String number;

    public Blank_Sms() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Blank_Sms newInstance() {
        Blank_Sms fragment = new Blank_Sms();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_blank__sms, container, false);

        imageButtonFragment = view.findViewById(R.id.imageButtonFragment);
        editTextFragment = view.findViewById(R.id.editTextFragment);

        imageButtonFragment.animate().translationYBy(400);

        imageButtonFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
                imageButtonFragment.startAnimation(pulse);

                if (editTextFragment.getText().toString().isEmpty()) {

                    Log.i("INFO", "onClick: PHONE NUMBER IS EMPTY");
                    Toast.makeText(getContext(), "Phone number is empty", Toast.LENGTH_SHORT).show();

                } else {

                    final String phoneNumber = editTextFragment.getText().toString();
                    final String time = textViewFragment.getText().toString();

                    smsOrMail = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("PHONE NUMBER I NEED", phoneNumber);
                            intent.putExtra("TIME I NEED", time);
                            startActivity(intent);

                            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                        }
                    },80);
                }
                //Objects.requireNonNull(getActivity()).finish();
            }
        });

        seekBarFragment = view.findViewById(R.id.seekBarFragment);
        textViewFragment = view.findViewById(R.id.textViewFragmnet);

        seekBarFragment.setMax(55);
        seekBarFragment.setProgress(0);

        seekBarFragment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (!buttonTick) {

                    imageButtonFragment.animate().translationYBy(-400).setDuration(1800);

                    imageButtonFragment.animate().alpha(1).setDuration(200);

                    buttonTick = true;
                }

                progress = seekBarFragment.getProgress() + 5;

                String str = String.valueOf(progress);

                textViewFragment.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Animation pulseWithoutDuration = AnimationUtils.loadAnimation(getContext(), R.anim.pulse_without_duration);
                imageButtonFragment.startAnimation(pulseWithoutDuration);
            }
        });


        contacts = view.findViewById(R.id.contacts);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                contacts.animate().alpha(1).setDuration(1000);
            }
        },300);

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permission();
            }
        });
        return view;
    }

    public void permission() {

        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            getContacts();
        } else {
            requestLocationPermission();
        }
    }

    public void getContacts() {

        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    protected void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                android.Manifest.permission.READ_CONTACTS)) {

            Toast.makeText(getContext(), "Contacts list is not available.", Toast.LENGTH_SHORT).show();


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{android.Manifest.permission.READ_CONTACTS},
                            REQUEST_READ_CONTACTS);
                }
            },600);
        } else {

            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {

        if (data != null) {
            Uri result = data.getData();

            Cursor c = Objects.requireNonNull(getActivity()).getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone._ID + "=?",
                    new String[]{Objects.requireNonNull(result).getLastPathSegment()}, null);

            if (Objects.requireNonNull(c).getCount() >= 1 && c.moveToFirst()) {

                number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            }
        }
        editTextFragment.setText(number);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        ((OpenChoiceActivity)Objects.requireNonNull(getActivity())).returnLinearLayout();

        buttonTick = false;

        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static boolean returnSmsOrMailBool () {

        return smsOrMail;
    }
}
