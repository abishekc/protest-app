package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyprotestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprotests);

        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println("HEEEEEEE");
        System.out.println(userID);
        final ArrayList<ProtestEvent> eventList = new ArrayList<>();
        final DatabaseReference events = FirebaseDatabase.getInstance().getReference().child("events");
        events.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    ProtestEvent protestEvent = eventSnapshot.getValue(ProtestEvent.class);
                    if (protestEvent.getOwner() != null && protestEvent.getOwner().equals(userID)) {
                        System.out.println("INNER");
                        eventList.add(protestEvent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        System.out.println(eventList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.protestList);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = null;
        try {
            mAdapter = new EventAdapter(eventList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(mAdapter);
    }

    public void switchToEvent(View view) {
        TextView tappedEvent = view.findViewById(R.id.eventID);
        final String requestedEvent = tappedEvent.getText().toString();
        final Intent intent = new Intent(this, EventItem.class);

        DatabaseReference events = FirebaseDatabase.getInstance().getReference().child("events");
        events.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProtestEvent protestEvent = dataSnapshot.getValue(ProtestEvent.class);
                if (protestEvent.getId() == requestedEvent) {
                    intent.putExtra("Event", protestEvent);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}