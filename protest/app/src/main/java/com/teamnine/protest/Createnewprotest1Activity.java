package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Createnewprotest1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnewprotest1);
    }

    public void nextScreen(View view) {
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

        ProtestEvent newEvent = new ProtestEvent(name, location, startDate, endDate, description, "Put owner here");

//       TODO: Sentiment score
//        newEvent.setSentiment(score);

        intent.putExtra("Event", newEvent);
        startActivity(intent);
    }
}