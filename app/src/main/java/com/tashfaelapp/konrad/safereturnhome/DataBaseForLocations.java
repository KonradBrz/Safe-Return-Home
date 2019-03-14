package com.tashfaelapp.konrad.safereturnhome;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import java.util.Objects;

public class DataBaseForLocations extends AppCompatActivity {

    private SQLiteDatabase sqLiteDatabase;
    private DataBaseAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_for_locations);

        //Toast.makeText(this,"Database of saved locations", Toast.LENGTH_SHORT).show();
        StyleableToast.makeText(this, "Database of saved locations", R.style.exampleToastTwo).show();

        final SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        recyclerView = findViewById(R.id.recyclerViewDataBase);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataBaseAdapter(this, getAllItems());
        //recyclerView.setAdapter(adapter);

        runAnimation (recyclerView,0);

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                final String lat_lon = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.textViewBaseLatLon)).getText().toString();
                StyleableToast.makeText(getApplicationContext(), lat_lon, R.style.exampleToastTwo).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        intent.putExtra("LOCATION I NEED", String.valueOf(lat_lon));

                        startActivity(intent);
                    }
                }, 100);
            }

            @Override
            public void onMessengerClick(int position) {

                final String accuracy = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.textViewAccuracyBase)).getText().toString();
                //Toast.makeText(getApplicationContext(), accuracy, Toast.LENGTH_SHORT).show();

                final String altitude = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.textViewAltitudeBase)).getText().toString();
                //Toast.makeText(getApplicationContext(), altitude, Toast.LENGTH_SHORT).show();

                final String lat_lon = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.textViewBaseLatLon)).getText().toString();
                //Toast.makeText(getApplicationContext(), lat_lon, Toast.LENGTH_SHORT).show();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, "Hi, check my location: \n" +
                                accuracy + "\n" +
                                altitude + "\n" +
                                "https://www.google.com/maps?q="
                                + lat_lon);
                        intent.setType("text/plain");
                        intent.setPackage("com.facebook.orca");

                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            Toast.makeText(getApplicationContext(),
                                    "Can't open Facebook messenger right now",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 100);
            }

            @Override
            public void onSaveDeleteClick(final int position) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        removeItem((int) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.getTag());
                    }
                }, 200);
            }
        });
    }

    private void removeItem(int id) {

        sqLiteDatabase.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.COL_ID + "=" + id, null);
        adapter.swapCursor(getAllItems());
    }

    public Cursor getAllItems() {
        return sqLiteDatabase.query(
                SQLiteHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
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
}
