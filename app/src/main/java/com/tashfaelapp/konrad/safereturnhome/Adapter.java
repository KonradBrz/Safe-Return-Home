package com.tashfaelapp.konrad.safereturnhome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class ExampleViewHolder extends RecyclerView.ViewHolder {

    private ImageButton imageButtonSave;

    private ImageView imageView;
    private TextView textView;
    private TextView textView2;
    private ImageView imageViewMessengerIcon;
    //ImageView viewCol;
    private HorizontalScrollView horizontalScrollView;
    private ImageView down;

    private TextView textViewAltitudeScroll;
    private TextView textViewAccuracyScroll;
    private TextView textViewSpeedScroll;
    private TextView textViewHumidityScroll;
    private TextView textViewVisibilityScroll;
    private TextView textViewWindScroll;
    private TextView textViewPressureScroll;
    private TextView textViewTemperatureScroll;

    ExampleViewHolder(@NonNull View itemView, final Adapter.OnItemClickListener listener) {
        super(itemView);

        imageButtonSave = itemView.findViewById(R.id.imageButtonSave);

        imageView = itemView.findViewById(R.id.imageViewww);
        textView = itemView.findViewById(R.id.textViewww);
        textView2 = itemView.findViewById(R.id.textViewww2);
        imageViewMessengerIcon = itemView.findViewById(R.id.imageViewMessengerIcon);
        horizontalScrollView = itemView.findViewById(R.id.horizontalScrollView);
        down = itemView.findViewById(R.id.down);

        textViewAltitudeScroll = itemView.findViewById(R.id.textViewAltitudeScroll);
        textViewAccuracyScroll = itemView.findViewById(R.id.textViewAccuracyScroll);
        textViewSpeedScroll = itemView.findViewById(R.id.textViewSpeedScroll);
        textViewHumidityScroll = itemView.findViewById(R.id.textViewHumidityScroll);
        textViewVisibilityScroll = itemView.findViewById(R.id.textViewVisibilityScroll);
        textViewWindScroll = itemView.findViewById(R.id.textViewWindScroll);
        textViewPressureScroll = itemView.findViewById(R.id.textViewPressureScroll);
        textViewTemperatureScroll = itemView.findViewById(R.id.textViewTemperatureScroll);

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

        imageViewMessengerIcon.setOnClickListener(new View.OnClickListener() {
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

        imageButtonSave.setOnClickListener(new View.OnClickListener() {
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

    public ImageButton getImageButtonSave() {
        return imageButtonSave;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public TextView getTextView2() {
        return textView2;
    }

    public ImageView getImageViewMessengerIcon() {
        return imageViewMessengerIcon;
    }

    public HorizontalScrollView getHorizontalScrollView() {
        return horizontalScrollView;
    }

    public ImageView getDown() {
        return down;
    }

    public TextView getTextViewAltitudeScroll() {
        return textViewAltitudeScroll;
    }

    public TextView getTextViewAccuracyScroll() {
        return textViewAccuracyScroll;
    }

    public TextView getTextViewSpeedScroll() {
        return textViewSpeedScroll;
    }

    public TextView getTextViewHumidityScroll() {
        return textViewHumidityScroll;
    }

    public TextView getTextViewVisibilityScroll() {
        return textViewVisibilityScroll;
    }

    public TextView getTextViewWindScroll() {
        return textViewWindScroll;
    }

    public TextView getTextViewPressureScroll() {
        return textViewPressureScroll;
    }

    public TextView getTextViewTemperatureScroll() {
        return textViewTemperatureScroll;
    }
}

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ExampleItem> exampleList;
    ExampleItem currentItem;

    private OnItemClickListener listener;

    public interface OnItemClickListener {

        void onItemClick(int position);

        void onMessengerClick(int position);

        void onSaveDeleteClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }

    Adapter(ArrayList<ExampleItem> exampleList) {

        this.exampleList = exampleList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        currentItem = exampleList.get(position);

        final ExampleViewHolder viewHolder = (ExampleViewHolder) holder;

        viewHolder.getImageView().setImageResource(currentItem.getImageResource());
        viewHolder.getTextView().setText(currentItem.getText());
        viewHolder.getTextView2().setText(currentItem.getLocation());

        viewHolder.getTextViewAltitudeScroll().setText(String.valueOf(currentItem.getAltitude() + " m\nAltitude"));
        viewHolder.getTextViewAccuracyScroll().setText(String.valueOf(currentItem.getAccuracy() + " m\nAccuracy"));

        viewHolder.getTextViewSpeedScroll().setText(String.valueOf(currentItem.getSpeed() + " km/h\nSpeed"));

        viewHolder.getTextViewHumidityScroll().setText(String.valueOf(currentItem.getHumidity()));
        viewHolder.getTextViewVisibilityScroll().setText(String.valueOf(currentItem.getVisibility()));
        viewHolder.getTextViewWindScroll().setText(String.valueOf(currentItem.getWind()));
        viewHolder.getTextViewPressureScroll().setText(String.valueOf(currentItem.getPressure()));
        viewHolder.getTextViewTemperatureScroll().setText(String.valueOf(currentItem.getTemperature() + "\nTemperature"));

        viewHolder.getDown().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.getHorizontalScrollView().getVisibility() == View.GONE) {

                    viewHolder.getHorizontalScrollView().setVisibility(View.VISIBLE);
                    viewHolder.getDown().animate().rotation(180).setDuration(200);
                }

                else if (viewHolder.getHorizontalScrollView().getVisibility() == View.VISIBLE){

                    viewHolder.getHorizontalScrollView().setVisibility(View.GONE);
                    viewHolder.getDown().animate().rotation(360).setDuration(200);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }
}
