package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

public class ViewmarkersActivity extends AppCompatActivity {

    ProtestEvent currEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmarkers);

        Intent intent = getIntent();
        ProtestEvent event = intent.getParcelableExtra("Event");
        currEvent = event;

        System.out.println("SDFLKJSDFLKSJDF");
        System.out.println(event);
        System.out.println(event.getPinList());

        ArrayList<MapPin> pinList = currEvent.getPinList();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.otherMarkers);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new ViewmarkersAdapter(pinList);

        recyclerView.setAdapter(mAdapter);
    }

    public void switchToCreateMarker(View view) {
        Intent intent = new Intent(this, createNewMarker.class);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }

    public void switchToPin(View view) {
        TextView tappedEvent = view.findViewById(R.id.pinIndex);
        int ind = Integer.parseInt(tappedEvent.getText().toString());
        MapPin selectedPin = currEvent.getPinList().get(ind);

        TextView typeBox = (TextView) findViewById(R.id.markerType);
        TextView locationBox = (TextView) findViewById(R.id.markerLocation);
        TextView descriptionBox = (TextView) findViewById(R.id.markerDescription);

        typeBox.setText(selectedPin.getType());
        locationBox.setText(selectedPin.getAddr());
        descriptionBox.setText(selectedPin.getDescription());

    }
}