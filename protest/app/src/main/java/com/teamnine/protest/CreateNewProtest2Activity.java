package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewProtest2Activity extends AppCompatActivity {

    ProtestEvent passedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_protest2);

        Intent intent = getIntent();
        passedEvent = intent.getParcelableExtra("Event");

        if (passedEvent != null) {
            Log.i("SUCCESS", "Event has been succesfully passed. ID is (should be empty): " + passedEvent.getId() + "." + " Name is (should not be empty): " + passedEvent.getName());
        }

        /* UI COMPONENTS */
        /* BUTTONS */
        ImageButton next = (ImageButton) findViewById(R.id.next_screen_three);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen();
            }
        });
    }

    public void nextScreen() {
        Intent intent = new Intent(this, CreateNewProtest3Activity.class);

        EditText routeDescriptionBox = (EditText) findViewById(R.id.description_box);
        String routeDescription = routeDescriptionBox.getText().toString();

        if (!routeDescription.equals("")) {
            passedEvent.setRouteDescription(routeDescription);
        }

        completeFirebaseWrite(passedEvent);
        passedEvent.addPin(new MapPin("a", "b", "c"));

        intent.putExtra("Event", passedEvent);
        startActivity(intent);
    }

    private void completeFirebaseWrite(ProtestEvent event) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("events").push().getKey();
        event.setId(key);

        mDatabase.child("events").child(event.getId()).setValue(event);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(userID != null) {
            mDatabase.child("users").child(userID).child("owned_events").setValue(event.getId());
        }
    }
}
