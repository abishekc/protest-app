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

import org.json.JSONException;
import org.json.JSONObject;

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

        ArrayList<MapPin> pinList = currEvent.getPinList();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.otherMarkers);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new ViewmarkersAdapter(pinList);

        recyclerView.setAdapter(mAdapter);
    }


    public void mapStuff(){
        String addr_url = "?address=" + location;
        String url = "https://maps.googleapis.com/maps/api/geocode/json" + addr_url + "&key=AIzaSyAHg81pUARe10Jif6txnxuso745wcJAi6Q";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject coords = response.optJSONArray("results").optJSONObject(0).optJSONObject("geometry").optJSONObject("bounds").optJSONObject("northeast");
                        double lat = coords.optDouble("lat");
                        double lon = coords.optDouble("lon");
                        System.out.println("GHJDFSDFJSD");
                        System.out.println(lat);
                        newPin.setLat(lat);
                        newPin.setLat(lon);

                        System.out.println(newPin.getDescription());


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        newPin.setLon(-99999.0);
                        newPin.setLat(-99999.0);
                        currEvent.addPin(newPin);

                        mDatabase.child("events").child(currEvent.getId()).setValue(currEvent);
                        intent.putExtra("Event", currEvent);
                        startActivity(intent);
                    }
                });
        queue.add(jsonObjectRequest);
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

        System.out.println("COORDS");
        System.out.println(selectedPin.getLat());
        System.out.println(selectedPin.getLon());
    }

    public void switchToEvent(View view) {
        Intent intent = new Intent(this, EventItem.class);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }
}