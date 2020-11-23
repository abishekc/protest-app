package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateNewProtest2Activity extends AppCompatActivity {

    ProtestEvent passedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_protest2);

        Intent intent = getIntent();
        passedEvent = intent.getParcelableExtra("Event");
    }

    public void nextScreen (View view) {
        Intent intent = new Intent(this, CreateNewProtest3Activity.class);

        EditText routeDescriptionBox = (EditText) findViewById(R.id.description_box);
        String routeDescription = routeDescriptionBox.getText().toString();

        if (!routeDescription.equals("")) {
            passedEvent.setRouteDescription(routeDescription);
        }

        // TODO: passedEvent to firebase

        intent.putExtra("Event", passedEvent);
        startActivity(intent);
    }
}
