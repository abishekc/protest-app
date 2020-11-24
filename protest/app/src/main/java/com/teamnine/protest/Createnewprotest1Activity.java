package com.teamnine.protest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.language.v1beta2.CloudNaturalLanguage;
import com.google.api.services.language.v1beta2.CloudNaturalLanguageRequestInitializer;
import com.google.api.services.language.v1beta2.model.AnnotateTextRequest;
import com.google.api.services.language.v1beta2.model.AnnotateTextResponse;
import com.google.api.services.language.v1beta2.model.Document;
import com.google.api.services.language.v1beta2.model.Entity;
import com.google.api.services.language.v1beta2.model.Features;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;

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
        String location = locationBox.getText().toString();
        String description = descriptionBox.getText().toString();
        String startDate = startDateBox.getText().toString();
        String endDate = endDateBox.getText().toString();

        String owner = "empty_ownerE1";
        String id = "empty_idE1";
        String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (userID != "" && userID != null) {
            owner = userID;
        }

        ProtestEvent newEvent = new ProtestEvent(id, name, location, startDate, endDate, description, owner);



        completeSentimentAnalysis(description, newEvent, intent);


//       TODO: Sentiment score
//        newEvent.setSentiment(score);
    }

    private void completeSentimentAnalysis(String transcript, final ProtestEvent passable, final Intent next) {
        final CloudNaturalLanguage naturalLanguageService =
                new CloudNaturalLanguage.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(),
                        null
                ).setCloudNaturalLanguageRequestInitializer(
                        new CloudNaturalLanguageRequestInitializer("AIzaSyCa5SOrP4lGuotvRWYW9GPD_ZSn9lYQd-A")
                ).build();

        Document document = new Document();
        document.setType("PLAIN_TEXT");
        document.setLanguage("en-US");
        document.setContent(transcript);

        Features features = new Features();
        features.setExtractEntities(true);
        features.setExtractDocumentSentiment(true);

        final AnnotateTextRequest request = new AnnotateTextRequest();
        request.setDocument(document);
        request.setFeatures(features);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    AnnotateTextResponse response =
                            naturalLanguageService.documents()
                                    .annotateText(request).execute();
                    // More code here
                    final List<Entity> entityList = response.getEntities();
                    final float sentiment = response.getDocumentSentiment().getScore();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String entities = "";
                            for(Entity entity:entityList) {
                                entities += "\n" + entity.getName().toUpperCase();
                            }
                            Log.i("SUCCESS", "Sentiment is: " + sentiment);

                            int sentimentValue = (int) Math.round(100.0 * sentiment);
                            Log.i("SUCCESS", "Rounded value is: " + String.valueOf(sentimentValue));
                            passable.setSentiment(sentimentValue);

                            next.putExtra("Event", passable);
                            startActivity(next);
                        }
                    });
                } catch (IOException e) {
                    Log.e("CLOUD", e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}