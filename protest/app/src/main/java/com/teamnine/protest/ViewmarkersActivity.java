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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewmarkersActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProtestEvent currEvent;
    String location;
    ArrayList<MapPin> pinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmarkers);

        Intent intent = getIntent();
        ProtestEvent event = intent.getParcelableExtra("Event");
        currEvent = event;
        location = currEvent.getLocation();

        pinList = currEvent.getPinList();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.otherMarkers);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new ViewmarkersAdapter(pinList);

        recyclerView.setAdapter(mAdapter);

        MapView map = findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        map.getMapAsync(this);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        for (MapPin pin: pinList) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(pin.getLat(), pin.getLon()))
                    .title(pin.getType()));
        }
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

        typeBox.setText(selectedPin.getType());
        locationBox.setText(selectedPin.getAddr());

    }

    public void switchToEvent(View view) {
        Intent intent = new Intent(this, EventItem.class);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }
}