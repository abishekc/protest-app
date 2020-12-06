package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

public class EventFeed extends AppCompatActivity {
    private ArrayList<ProtestFeed> feedList = new ArrayList<ProtestFeed>();
    private ProtestEvent feedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_feed);

        TextView addTitle = findViewById(R.id.add_update_title);
        ImageButton addButton = (ImageButton) findViewById(R.id.add_update_button);

        Intent intent = getIntent();
        final ProtestEvent passedEvent = intent.getParcelableExtra("Event");

        feedEvent = passedEvent;

        TextView EventName = findViewById(R.id.event_string);
        EventName.setText(passedEvent.name);

        findFeedsByEvent();

        String currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (currentID != null) {
            if (currentID.equals(passedEvent.getOwner())) {
                addTitle.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.VISIBLE);
            }
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBottomSheet bottomSheet = new AddBottomSheet();
                bottomSheet.setPassedEventId(passedEvent.getId());
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");
                findFeedsByEvent();
            }
        });
    }

    //This function should return a list of ProtestFeeds that are found in the database corresponding to the event
    public void findFeedsByEvent(){
        //TODO: Implement the actual database access and return the actual list
        final ArrayList<ProtestFeed> ret = new ArrayList<ProtestFeed>();

        final DatabaseReference updates = FirebaseDatabase.getInstance().getReference().child("updates");
        updates.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ret.clear();
                feedList.clear();

                for (DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    ProtestFeed protestFeed = eventSnapshot.getValue(ProtestFeed.class);
                    if (protestFeed.getEvent_id().equals(feedEvent.getId())) {
                        ret.add(protestFeed);
                    }
                }
                Collections.reverse(ret);
                feedList = ret;
                loadRecycler();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    };

    public void loadRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feedList);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = null;
        try {
            adapter = new FeedAdapter(feedList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(adapter);
    }
}