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

public class recyclerprecipitationchanceadaptor extends RecyclerView.Adapter<recyclerprecipitationchanceadaptor.viewholder> {
    Context context;
    ArrayList<model_recycler_precipitation> arrprecipitationchance;
    recyclerprecipitationchanceadaptor(Context context, ArrayList<model_recycler_precipitation> arrprecipitationchance){
        this.context = context;
        this.arrprecipitationchance = arrprecipitationchance;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.precipitation_chance_recycler_view, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        model_recycler_precipitation item = arrprecipitationchance.get(position);

        holder.rainvolume.setText(String.valueOf(arrprecipitationchance.get(position).Rain_Volume));
        holder.precipitationpercentage.setText(String.valueOf(arrprecipitationchance.get(position).Precipitation_Chance));
        holder.time.setText(String.valueOf(arrprecipitationchance.get(position).Time));
        holder.rainimage.setImageResource(R.drawable.raindrop);
        //String imageUrl = item.getImageUrl();
        //Picasso.get().load(imageUrl).into(holder.rainimage);
    }

    @Override
    public int getItemCount() {
        return arrprecipitationchance.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{

TextView precipitationpercentage, rainvolume, time;
ImageView rainimage;
        public viewholder(View itemView) {
            super(itemView);

            precipitationpercentage = itemView.findViewById(R.id.precipitationpercentage);
            rainvolume = itemView.findViewById(R.id.rainvolume);
            time = itemView.findViewById(R.id.time);
            rainimage = itemView.findViewById(R.id.rainimage);
        }
    }

}
