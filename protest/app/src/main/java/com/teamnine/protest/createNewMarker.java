package com.teamnine.protest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

public class createNewMarker extends AppCompatActivity {

    ProtestEvent currEvent;
    private FusedLocationProviderClient fusedLocationClient;
    protected Location lastLocation;
    private double lastLat;
    private double lastLong;
    private int LOCATIONREQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_marker);

        Intent intent = getIntent();
        currEvent = intent.getParcelableExtra("Event");

        Spinner spinner = (Spinner) findViewById(R.id.typeofmarker);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.markerTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    public void getCurrLoc (View view) {
        final RequestQueue queue = Volley.newRequestQueue(this);
        if (ContextCompat.checkSelfPermission(createNewMarker.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(createNewMarker.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Required Location Permission")
                        .setMessage("Location permissions are required for this feature!")
                        .setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(createNewMarker.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATIONREQUEST);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(createNewMarker.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATIONREQUEST);
            }
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                lastLocation = task.getResult();
                                lastLat = lastLocation.getLatitude();
                                lastLong = lastLocation.getLongitude();
                                String gps_url = "?latlng=" + lastLat + "," + lastLong;
                                String url = "https://maps.googleapis.com/maps/api/geocode/json" + gps_url + "&key=AIzaSyAHg81pUARe10Jif6txnxuso745wcJAi6Q";
                                System.out.println(url);
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                String address = response.optJSONArray("results").optJSONObject(0).optString("formatted_address");
                                                EditText locationBox = (EditText) findViewById(R.id.locationInput);
                                                locationBox.setText(address);
                                            }
                                        }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println("Random error");
                                            }
                                        });
                                queue.add(jsonObjectRequest);
                            } else {
                                System.out.println("No location detected");
                            }
                        }
                    });
        }
    }

    public void createNewMarker(View view) {
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeofmarker);
        EditText locationBox = (EditText) findViewById(R.id.locationInput);
        EditText descriptionBox = (EditText) findViewById(R.id.descriptionInput);
        String type = typeSpinner.getSelectedItem().toString();
        String location = locationBox.getText().toString();
        String description = descriptionBox.getText().toString();

        MapPin newPin = new MapPin(type, location, description);
        Intent intent = new Intent(this, ViewmarkersActivity.class);

        //TODO Add to feed?
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        currEvent.addPin(newPin);
        mDatabase.child("events").child(currEvent.getId()).setValue(currEvent);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }

    public void cancelCreate(View view) {
        finish();
    }
}