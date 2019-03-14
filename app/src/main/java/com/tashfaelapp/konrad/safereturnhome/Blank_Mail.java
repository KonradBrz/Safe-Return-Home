package com.tashfaelapp.konrad.safereturnhome;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
// * {@link Blank_Mail.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Blank_Mail#newInstance} factory method to
// * create an instance of this fragment.
// */
public class Blank_Mail extends Fragment {
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

    public static boolean smsOrMail = false;
    private static boolean buttonTick = false;

    public Blank_Mail() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Blank_Mail newInstance() {
        Blank_Mail fragment = new Blank_Mail();
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
        View view = inflater.inflate(R.layout.fragment_blank__mail, container, false);

        imageButtonFragment = view.findViewById(R.id.imageButtonFragment);
        editTextFragment = view.findViewById(R.id.editTextFragment);

        imageButtonFragment.animate().translationYBy(400);

        imageButtonFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
                imageButtonFragment.startAnimation(pulse);

                if (editTextFragment.getText().toString().isEmpty()) {

                    Log.i("INFO", "onClick: EMAIL ADDRESS IS EMPTY");
                    Toast.makeText(getContext(), "Email address is empty", Toast.LENGTH_SHORT).show();

                } else {

                    final String emailAddress = editTextFragment.getText().toString();
                    final String time = textViewFragment.getText().toString();

                    smsOrMail = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("EMAIL ADDRESS I NEED", emailAddress);
                            intent.putExtra("TIME I NEED", time);
                            startActivity(intent);

                            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                        }
                    }, 80);
                }
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

        return view;
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

        Log.i("defefef", "onResume: IM BACK");

        ((OpenChoiceActivity)Objects.requireNonNull(getActivity())).returnLinearLayout();

        buttonTick = false;

        mListener = null;
    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static boolean returnSmsOrMailBool() {

        return smsOrMail;
    }
}
