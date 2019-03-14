package com.tashfaelapp.konrad.safereturnhome;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DataBaseAdapter extends RecyclerView.Adapter<DataBaseAdapter.DataBaseViewHolder> {

    private Context context;
    private Cursor cursor;

    private Adapter.OnItemClickListener listener;

    public DataBaseAdapter(Context context, Cursor cursor) {

        this.context = context;
        this.cursor = cursor;
    }

    void setOnItemClickListener(Adapter.OnItemClickListener listener) {

        this.listener = listener;
    }

    class DataBaseViewHolder extends  RecyclerView.ViewHolder {

        private TextView baseLatLon;
        private TextView address;
        private TextView temperature;
        private TextView speed;
        private TextView altitude;
        private TextView accuracy;
        private TextView humidity;
        private TextView visibility;
        private TextView wind;
        private TextView pressure;


        private ImageView downBase;
        private HorizontalScrollView horizontalScrollViewBase;

        private ImageButton imageButtonBase;
        private ImageView imageViewMessengerIconBase;

        private DataBaseViewHolder(View itemView, final Adapter.OnItemClickListener listener) {
            super(itemView);

            baseLatLon = itemView.findViewById(R.id.textViewBaseLatLon);
            address = itemView.findViewById(R.id.textViewBase);
            temperature = itemView.findViewById(R.id.textViewTemperatureBase);
            speed = itemView.findViewById(R.id.textViewSpeedBase);
            altitude = itemView.findViewById(R.id.textViewAltitudeBase);
            accuracy = itemView.findViewById(R.id.textViewAccuracyBase);
            humidity = itemView.findViewById(R.id.textViewHumidityBase);
            visibility = itemView.findViewById(R.id.textViewVisibilityBase);
            wind = itemView.findViewById(R.id.textViewWindBase);
            pressure = itemView.findViewById(R.id.textViewPressureBase);

            downBase = itemView.findViewById(R.id.downBase);

            horizontalScrollViewBase = itemView.findViewById(R.id.horizontalScrollViewBase);

            imageButtonBase = itemView.findViewById(R.id.imageButtonBase);
            imageViewMessengerIconBase = itemView.findViewById(R.id.imageViewMessengerIconBase);

            itemView.setOnClickListener(new View.OnClickListener() {
                //RecyclerView + CardView Part 4 coding in flow
                @Override
                public void onClick(View v) {

                    if (listener != null) {

                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {

                            listener.onItemClick(position);
                        }
                    }
                }
            });

            imageViewMessengerIconBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {

                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {

                            listener.onMessengerClick(position);
                        }
                    }
                }
            });

            imageButtonBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {

                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {

                            listener.onSaveDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public DataBaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_base_item, viewGroup, false);
        DataBaseViewHolder dbvh = new DataBaseViewHolder(view, listener);
        return dbvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DataBaseViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        final DataBaseViewHolder viewHolder = holder;

        int id = (int) cursor.getLong(cursor.getColumnIndex(SQLiteHelper.COL_ID));

        String baseLatLon = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_LAT_LON));
        String address = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_ADDRESS_TIME));
        String temperature = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_TEMPERATURE));
        int speed = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COL_SPEED));
        int altitude = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COL_ALTITUDE));
        int accuracy = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COL_ACCURACY));
        String humidity = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_HUMIDITY));
        String visibility = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_VISIBILITY));
        String wind = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_WIND));
        String pressure = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_PRESSURE));

        holder.itemView.setTag(id);

        holder.baseLatLon.setText(baseLatLon);
        holder.address.setText(address);
        holder.temperature.setText(temperature + "\nTemperature");
        holder.speed.setText(speed  + " km/h\nSpeed");
        holder.altitude.setText(altitude + " m\nAltitude");
        holder.accuracy.setText(accuracy + " m\nAccuracy");
        holder.humidity.setText(humidity);
        holder.visibility.setText(visibility);
        holder.wind.setText(wind);
        holder.pressure.setText(pressure);

        holder.downBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.horizontalScrollViewBase.getVisibility() == View.GONE) {

                    viewHolder.horizontalScrollViewBase.setVisibility(View.VISIBLE);
                    viewHolder.downBase.animate().rotation(180).setDuration(200);
                }

                else if (viewHolder.horizontalScrollViewBase.getVisibility() == View.VISIBLE){

                    viewHolder.horizontalScrollViewBase.setVisibility(View.GONE);
                    viewHolder.downBase.animate().rotation(360).setDuration(200);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {

        if (cursor != null) {
            cursor.close();
        }

        cursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
