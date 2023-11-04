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

public class adaptorweathercondition extends RecyclerView.Adapter<adaptorweathercondition.viewholder> {
    ArrayList<model_recycler_tendays> arrTenDays;
    Context context;

    public adaptorweathercondition(Context context, ArrayList<model_recycler_tendays> arrTenDays) {
        this.context = context;
        this.arrTenDays = arrTenDays;
    }

    @NonNull
    @Override
    public adaptorweathercondition.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ten_day_recycler_view, parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptorweathercondition.viewholder holder, int position) {
            model_recycler_tendays item = arrTenDays.get(position);
            holder.day.setText(String.valueOf(arrTenDays.get(position).day));
            holder.weather_condition.setText(String.valueOf(arrTenDays.get(position).Weather_Condition));
            holder.tempmax.setText(String.valueOf(arrTenDays.get(position).tempmax));
            holder.tempmin.setText(String.valueOf(arrTenDays.get(position).tempmin));
            String imageurl = item.getImageUrl();
            //holder.condition_image.setText(String.valueOf(arrTenDays.get(position).Condition_image));
            Picasso.get().load(imageurl).into(holder.condition_image);
    }

    @Override
    public int getItemCount() {
        return arrTenDays.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView day, weather_condition,tempmax, tempmin;
        ImageView condition_image;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.day);
            weather_condition = itemView.findViewById(R.id.weather_condition);
            condition_image = itemView.findViewById(R.id.condition_image);
            tempmax = itemView.findViewById(R.id.tempmax);
            tempmin = itemView.findViewById(R.id.tempmin);
        }
    }
}
