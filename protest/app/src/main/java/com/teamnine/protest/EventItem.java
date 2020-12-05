package com.teamnine.protest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventItem extends AppCompatActivity {

    ProtestEvent currEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventitem);

        Intent intent = getIntent();
        final ProtestEvent passedEvent = intent.getParcelableExtra("Event");
        currEvent = passedEvent;

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

        Button feed_button = findViewById(R.id.feed_button);
        feed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedInterface(passedEvent);
            }
        });

    }

    public void feedInterface(ProtestEvent event) {
        Intent intent = new Intent(this, EventFeed.class);
        intent.putExtra("Event", event);
        startActivity(intent);
    }

    public void switchToPins (View view) {
        Intent intent = new Intent(this, ViewmarkersActivity.class);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }

    public void switchToCreatePin (View view) {
        Intent intent = new Intent(this, createNewMarker.class);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }
}
