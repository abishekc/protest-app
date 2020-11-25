package com.teamnine.protest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventitem);

        Intent intent = getIntent();
        ProtestEvent passedEvent = intent.getParcelableExtra("Event");

        TextView eventName = findViewById(R.id.event_string);
        TextView eventLocation = findViewById(R.id.location_input);
        TextView eventStartDate = findViewById(R.id.start_date_input);
        TextView eventEndDate = findViewById(R.id.end_date_input);
        TextView eventDescription = findViewById(R.id.description_input);
        TextView sentimentScore = findViewById(R.id.sentiment);

        eventName.setText(passedEvent.getName());
        eventLocation.setText(passedEvent.getLocation());
        eventStartDate.setText(passedEvent.getStartDate());
        eventEndDate.setText(passedEvent.getEndDate());
        eventDescription.setText(passedEvent.getDescription());
        sentimentScore.setText(String.valueOf(passedEvent.getSentiment()));
    }
}
