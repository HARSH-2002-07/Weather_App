package com.example.weather_project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adaptorhourlytemperaturerecyclerview extends RecyclerView.Adapter<adaptorhourlytemperaturerecyclerview.viewholder> {

    Context context;
    String photoUrl = "https://api.unsplash.com/";
    ArrayList<model_recycler_hourlytemperature> arrhourlytemperature;

    adaptorhourlytemperaturerecyclerview(Context context, ArrayList<model_recycler_hourlytemperature> arrhourlytemperature){
        this.context = context;
        this.arrhourlytemperature = arrhourlytemperature;
    }
    @NonNull
    @Override
    public adaptorhourlytemperaturerecyclerview.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hourly_temperature_recycler_view, parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptorhourlytemperaturerecyclerview.viewholder holder, int position) {

        model_recycler_hourlytemperature item = arrhourlytemperature.get(position);
        String imageurl = item.getImageUrl();
        holder.hourlytime.setText(String.valueOf(arrhourlytemperature.get(position).time));
    holder.hourlytemperaturetextview.setText(String.valueOf(arrhourlytemperature.get(position).hourlytemperature));

    //holder.hourlytemperatureimageview.setImageResource(Integer.parseInt(arrhourlytemperature.get(position).houlyimage));
    Picasso.get().load(imageurl).into(holder.hourlytemperatureimageview);
    }

    @Override
    public int getItemCount() {
        return arrhourlytemperature.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView hourlytemperaturetextview,hourlytime;
        ImageView hourlytemperatureimageview;

        public viewholder(View itemView) {
            super(itemView);

            hourlytemperaturetextview = itemView.findViewById(R.id.hourlytemperaturetextview);
            hourlytime = itemView.findViewById(R.id.hourlytime);
            hourlytemperatureimageview = itemView.findViewById(R.id.hourlytemperatureimageview);
        }
        public void setImage(String imageUrl) {
            // Load and set the image from the URL using Picasso
            Picasso.get().load(imageUrl).into(hourlytemperatureimageview);
        }
    }
}
