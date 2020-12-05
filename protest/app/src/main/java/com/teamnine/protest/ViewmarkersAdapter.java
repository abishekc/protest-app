package com.teamnine.protest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewmarkersAdapter extends RecyclerView.Adapter<ViewmarkersAdapter.ViewmarkersHolder>{

    public static ArrayList<MapPin> pins;

    public static class ViewmarkersHolder extends RecyclerView.ViewHolder {

        private final TextView pinType;
        private final TextView pinLoc;
        private final TextView arrayLocation;

        public ViewmarkersHolder (View pin_list_item) {
            super(pin_list_item);
            pinType = pin_list_item.findViewById(R.id.pinType);
            pinLoc = pin_list_item.findViewById(R.id.pinLoc);
            arrayLocation = pin_list_item.findViewById(R.id.pinIndex);
        }
    }

    public ViewmarkersAdapter (ArrayList<MapPin> inputPins) {
         pins = inputPins;
     }

    @NonNull
    @Override
    public ViewmarkersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pin_list_item, parent, false);
        return new ViewmarkersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewmarkersHolder holder, int position) {
        MapPin currPin = pins.get(position);
        holder.pinType.setText(currPin.type);
        holder.pinLoc.setText(currPin.addr);
        holder.arrayLocation.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return pins.size();
    }
}
