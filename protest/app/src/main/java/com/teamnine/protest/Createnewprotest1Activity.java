package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Createnewprotest1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnewprotest1);

        /* UI COMPONENTS */
        /* BUTTONS */
        ImageButton next = (ImageButton) findViewById(R.id.next_screen_two);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen();
            }
        });
    }

    public void nextScreen() {
        Intent intent = new Intent(this, CreateNewProtest2Activity.class);

        EditText nameBox = (EditText) findViewById(R.id.name_input);
        EditText locationBox = (EditText) findViewById(R.id.location_input);
        EditText startDateBox = (EditText) findViewById(R.id.enterDate1);
        EditText endDateBox = (EditText) findViewById(R.id.enterDate2);
        EditText descriptionBox = (EditText) findViewById(R.id.enterDescription);

        String name = nameBox.getText().toString();
        String location = nameBox.getText().toString();
        String description = nameBox.getText().toString();
        String startDate = nameBox.getText().toString();
        String endDate = nameBox.getText().toString();

        String owner = "empty_ownerE1";
        String id = "empty_idE1";
        String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (userID != "" && userID != null) {
            owner = userID;
        }

        ProtestEvent newEvent = new ProtestEvent(id, name, location, startDate, endDate, description, owner);


//       TODO: Sentiment score
//        newEvent.setSentiment(score);

        intent.putExtra("Event", newEvent);
        startActivity(intent);
    }
}