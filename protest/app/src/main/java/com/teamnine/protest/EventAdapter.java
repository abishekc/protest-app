package com.teamnine.protest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>{

    ArrayList<ProtestEvent> eventList = new ArrayList<>();

    public static class EventHolder extends RecyclerView.ViewHolder {
        private final TextView eventName;
        private final TextView eventID;

        public EventHolder(View view) {
            super(view);
            eventName = view.findViewById(R.id.eventName);
            eventID = view.findViewById(R.id.eventID);
        }
    }

    public EventAdapter(ArrayList<ProtestEvent> events) throws JSONException {
        eventList = events;
    }

    @Override
    public EventAdapter.EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);
        return new EventHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventHolder holder, int position) {
        holder.eventName.setText(eventList.get(position).getName());
        holder.eventID.setText(eventList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
