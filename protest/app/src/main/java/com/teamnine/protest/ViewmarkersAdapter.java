package com.teamnine.protest;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewmarkersAdapter extends RecyclerView.Adapter<ViewmarkersAdapter.ViewmarkersHolder>{

    public static class ViewmarkersHolder extends RecyclerView.ViewHolder {
        public ViewmarkersHolder (View Viewmarker_item) {
            super(Viewmarker_item);
        }
    }

    public ViewmarkersAdapter () {
         return;
     }

    @NonNull
    @Override
    public ViewmarkersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewmarkersHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
