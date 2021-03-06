package com.teamnine.protest;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class EventItem extends AppCompatActivity {

    private ProtestEvent currEvent;

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
        TextView editTitle = findViewById(R.id.edit_title);
        ImageButton sentimentIcon = findViewById(R.id.sentiment_icon);
        final TextView updateText = findViewById(R.id.update_text);
        final TextView updateTimeText = findViewById(R.id.update_time_text);

        ImageButton editButton = (ImageButton) findViewById(R.id.edit_button);

        eventName.setText(passedEvent.getName());
        eventLocation.setText(passedEvent.getLocation());
        eventStartDate.setText(passedEvent.getStartDate());
        eventEndDate.setText(passedEvent.getEndDate());
        eventDescription.setText(passedEvent.getDescription());

        if(passedEvent.getSentiment() <= -50) {
            sentimentScore.setText("Negative.");
            sentimentIcon.setBackground(getResources().getDrawable(R.drawable.round_sentiment_very_dissatisfied_white_48dp));
        } else if (passedEvent.getSentiment() > -50 && passedEvent.getSentiment() <= 0) {
            sentimentScore.setText("Somewhat Negative.");
            sentimentIcon.setBackground(getResources().getDrawable(R.drawable.round_sentiment_dissatisfied_white_48dp));
        } else if (passedEvent.getSentiment() > 0 && passedEvent.getSentiment() <= 50) {
            sentimentScore.setText("Somewhat Positive.");
            sentimentIcon.setBackground(getResources().getDrawable(R.drawable.round_sentiment_satisfied_white_48dp));
        } else if (passedEvent.getSentiment() > 50) {
            sentimentScore.setText("Positive!");
            sentimentIcon.setBackground(getResources().getDrawable(R.drawable.round_emoji_emotions_white_48dp));
        }

        updateText.setText("No updates to show.");
        updateTimeText.setVisibility(View.GONE);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("events").child(passedEvent.getId()).child("latest_update").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    mDatabase.child("updates").child(snapshot.getValue().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                ProtestFeed latestUpdate = snapshot.getValue(ProtestFeed.class);
                                if (latestUpdate.getDescription() != null) {
                                    updateText.setText(latestUpdate.getDescription());
                                    updateTimeText.setVisibility(View.VISIBLE);
                                    updateTimeText.setText(latestUpdate.getTime());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button feed_button = findViewById(R.id.feed_button);
        feed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedInterface(passedEvent, false);
            }
        });

        Button add_update_button = findViewById(R.id.add_new_update_button);
        add_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedInterface(passedEvent, true);
            }
        });

        String currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (currentID != null) {
            if (currentID.equals(passedEvent.getOwner())) {
                editTitle.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.VISIBLE);
                add_update_button.setVisibility(View.VISIBLE);
            }
        }

        editTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditEventActivity.class);
                intent.putExtra("Event", passedEvent);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditEventActivity.class);
                intent.putExtra("Event", passedEvent);
                startActivity(intent);
            }
        });

    }

    public void feedInterface(ProtestEvent event, Boolean add_flag) {
        Intent intent = new Intent(this, EventFeed.class);
        intent.putExtra("Event", event);
        intent.putExtra("add_flag", add_flag);
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

    public void updateEvent (View view) {
        Intent intent = new Intent(this, EditEventActivity.class);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }

    public void switchToEventList(View view) {
        Intent intent = new Intent(this, MyprotestsActivity.class);
        intent.putExtra("Event", currEvent);
        startActivity(intent);
    }
}
