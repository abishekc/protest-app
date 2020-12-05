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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class EventFeed extends AppCompatActivity {
    private ArrayList<ProtestFeed> feedList = new ArrayList<ProtestFeed>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_feed);

        TextView addTitle = findViewById(R.id.add_update_title);
        ImageButton addButton = (ImageButton) findViewById(R.id.add_update_button);

        Intent intent = getIntent();
        ProtestEvent passedEvent = intent.getParcelableExtra("Event");

        TextView EventName = findViewById(R.id.event_string);
        EventName.setText(passedEvent.name);

        feedList = findFeedsByEvent(passedEvent);

        loadRecycler();

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
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");
            }
        });
    }

    //This function should return a list of ProtestFeeds that are found in the database corresponding to the event
    public ArrayList<ProtestFeed> findFeedsByEvent(ProtestEvent event){
        //TODO: Implement the actual database access and return the actual list
        ArrayList<ProtestFeed> ret = new ArrayList<ProtestFeed>();
        ret.add(new ProtestFeed("1","1","This is a fake feed 1","1"));
        ret.add(new ProtestFeed("2","1","Another fake feed","2"));
        ret.add(new ProtestFeed("3","1","One more!","3"));
        return ret;
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