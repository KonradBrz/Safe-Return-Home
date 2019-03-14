package com.tashfaelapp.konrad.safereturnhome;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class CheckDeliveredMessagesActivity extends AppCompatActivity {

    ArrayList<ExampleItem> exampleList;

    RecyclerView recyclerView;
    Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ConstraintLayout constraintLayout;

    Context context;

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_delivered_messages);
        //Slidr.attach(this);
        context = this;

        Intent intent = getIntent();

        exampleList = intent.getParcelableArrayListExtra("myList");

        createExampleList();
        buildRecyclerView();

        adapter.notifyDataSetChanged();

        //insertItem();
    }

    public void createExampleList() {

//        exampleList = new ArrayList<>();
//        exampleList.add(new ExampleItem(R.drawable.ic_my_location_black_24dp, "My location delivered successfully"));
    }

    public void buildRecyclerView() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new Adapter(exampleList);

        recyclerView.setLayoutManager(layoutManager);
        runAnimation (recyclerView,0);

        constraintLayout = findViewById(R.id.constraintLayout1);

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {

                final String lat_lon = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.textViewww)).getText().toString();
                Toast.makeText(getApplicationContext(), lat_lon, Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        //intent.putExtra("location", exampleList.get(position));
                        intent.putExtra("LOCATION I NEED", String.valueOf(lat_lon));

                        startActivity(intent);
                    }
                },100);
            }

            @Override
            public void onMessengerClick(final int position) {

                if (Blank_Sms.returnSmsOrMailBool() && !Blank_Mail.returnSmsOrMailBool()) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, "Hi, check my location:" +
                                    "\naccuracy: " + exampleList.get(position).getAccuracy() + " meters, " +
                                    "\naltitude: " + exampleList.get(position).getAltitude() + " meters\n" +
                                    "https://www.google.com/maps?q=" + exampleList.get(position).getText());
                            intent.setType("text/plain");
                            intent.setPackage("com.facebook.orca");

                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException ex) {
                                Toast.makeText(getApplicationContext(),
                                        "Oups!Can't open Facebook messenger right now",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    },100);
                }

                if (Blank_Mail.returnSmsOrMailBool() && !Blank_Sms.returnSmsOrMailBool()) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, "Hi, check my location:" +
                                    "\naccuracy: " + exampleList.get(position).getAccuracy() + " meters, " +
                                    "\naltitude: " + exampleList.get(position).getAltitude() + " meters\n" +
                                    "https://www.google.com/maps?q=" + exampleList.get(position).getText());
                            intent.setType("text/plain");
                            intent.setPackage("com.facebook.orca");

                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException ex) {
                                Toast.makeText(getApplicationContext(),
                                        "Oups!Can't open Facebook messenger right now",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 100);
                }
            }

            @Override
            public void onSaveDeleteClick(int position) {

                sqLiteHelper = new SQLiteHelper(getApplicationContext());
                sqLiteHelper.insertConditions(
                        exampleList.get(position).getText(),
                        exampleList.get(position).getLocation(),
                        exampleList.get(position).getTemperature(),
                        exampleList.get(position).getSpeed(),
                        exampleList.get(position).getAltitude(),
                        exampleList.get(position).getAccuracy(),
                        exampleList.get(position).getHumidity(),
                        exampleList.get(position).getVisibility(),
                        exampleList.get(position).getWind(),
                        exampleList.get(position).getPressure());

                Toast.makeText(getApplicationContext(), "Location saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void runAnimation(RecyclerView recyclerView, int type) {

        Context context = recyclerView.getContext();
        LayoutAnimationController controller = null;

        if (type == 0) {// fall down animation

            controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down);

            recyclerView.setAdapter(adapter);

            recyclerView.setLayoutAnimation(controller);
            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
