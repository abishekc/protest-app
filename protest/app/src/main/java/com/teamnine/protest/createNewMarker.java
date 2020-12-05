package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createNewMarker extends AppCompatActivity {

    ProtestEvent currEvent;

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

    public void createNewMarker(View view) {
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeofmarker);
        EditText locationBox = (EditText) findViewById(R.id.locationInput);
        EditText descriptionBox = (EditText) findViewById(R.id.descriptionInput);
        String type = typeSpinner.getSelectedItem().toString();
        String location = locationBox.getText().toString();
        String description = descriptionBox.getText().toString();

        MapPin newPin = new MapPin(type, location, description);
        currEvent.addPin(newPin);

        //TODO Add to feed?
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("events").child(currEvent.getId()).setValue(currEvent);

        Intent intent = new Intent(this, ViewmarkersActivity.class);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }
}