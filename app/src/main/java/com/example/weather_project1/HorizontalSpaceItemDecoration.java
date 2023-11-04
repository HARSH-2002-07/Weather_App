package com.example.weather_project1;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int space; // Define the space between items

    public HorizontalSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override

    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        if (position != RecyclerView.NO_POSITION) {
            outRect.left = 20; // Add left spacing
            outRect.right = 20; // Add right spacing
            outRect.top = 0; // Add top spacing
            outRect.bottom = 0; // Add bottom spacing
        }}
}
