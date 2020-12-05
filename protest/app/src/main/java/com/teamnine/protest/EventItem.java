package com.teamnine.protest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EventItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventitem);

        Intent intent = getIntent();
        final ProtestEvent passedEvent = intent.getParcelableExtra("Event");

        TextView eventName = findViewById(R.id.event_string);
        TextView eventLocation = findViewById(R.id.location_input);
        TextView eventStartDate = findViewById(R.id.start_date_input);
        TextView eventEndDate = findViewById(R.id.end_date_input);
        TextView eventDescription = findViewById(R.id.description_input);
        TextView sentimentScore = findViewById(R.id.sentiment);
        TextView editTitle = findViewById(R.id.edit_title);

        ImageButton editButton = (ImageButton) findViewById(R.id.edit_button);

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

        String currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (currentID != null) {
            if (currentID.equals(passedEvent.getOwner())) {
                editTitle.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.VISIBLE);
            }
        }

    }

    public void feedInterface(ProtestEvent event) {
        Intent intent = new Intent(this, EventFeed.class);
        intent.putExtra("Event", event);
        startActivity(intent);
    }
}
