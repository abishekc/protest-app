package com.teamnine.protest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class EditEventActivity extends AppCompatActivity {

    ProtestEvent currEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmarkers);

        Intent intent = getIntent();
        ProtestEvent event = intent.getParcelableExtra("Event");
        currEvent = event;

        System.out.println("DFODIFSDKNSDFOSDF");
        System.out.println(currEvent.getName());

        EditText nameBox = (EditText) findViewById(R.id.name_input);
        EditText locationBox = (EditText) findViewById(R.id.location_input);
        EditText startDateBox = (EditText) findViewById(R.id.enterDate1);
        EditText endDateBox = (EditText) findViewById(R.id.enterDate2);
        EditText descriptionBox = (EditText) findViewById(R.id.enterDescription);

        nameBox.setText(currEvent.getName());
        locationBox.setText(currEvent.getLocation());
        startDateBox.setText(currEvent.getStartDate());
        endDateBox.setText(currEvent.getEndDate());
        descriptionBox.setText(currEvent.getDescription());

    }

    public void updateEvent (View view) {
        EditText nameBox = (EditText) findViewById(R.id.name_input);
        EditText locationBox = (EditText) findViewById(R.id.location_input);
        EditText startDateBox = (EditText) findViewById(R.id.enterDate1);
        EditText endDateBox = (EditText) findViewById(R.id.enterDate2);
        EditText descriptionBox = (EditText) findViewById(R.id.enterDescription);

        String name = nameBox.getText().toString();
        String location = locationBox.getText().toString();
        String description = descriptionBox.getText().toString();
        String startDate = startDateBox.getText().toString();
        String endDate = endDateBox.getText().toString();

        currEvent.setName(name);
        currEvent.setLocation(location);
        currEvent.setDescription(description);
        currEvent.setStartDate(startDate);
        currEvent.setEndDate(endDate);

        String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();
//        TODO: UPDATE DATABASE

        Intent intent = new Intent(this, EventItem.class);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }

    public void cancelUpdate (View view) {
        Intent intent = new Intent(this, EventItem.class);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }
}
