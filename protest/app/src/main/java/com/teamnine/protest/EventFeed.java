package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class EventFeed extends AppCompatActivity {
    private ArrayList<ProtestFeed> feedList = new ArrayList<ProtestFeed>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_feed);

        Intent intent = getIntent();
        ProtestEvent passedEvent = intent.getParcelableExtra("Event");

        TextView EventName = findViewById(R.id.event_string);
        EventName.setText(passedEvent.name);

        feedList = findFeedsByEvent(passedEvent);

        loadRecycler();

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