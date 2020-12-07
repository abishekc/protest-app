package com.teamnine.protest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class Createnewprotest1Activity extends AppCompatActivity {
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnewprotest1);

        /* UI COMPONENTS */
        /* BUTTONS */
        ImageButton next = (ImageButton) findViewById(R.id.next_screen_two);
        final EditText startDateBox = (EditText) findViewById(R.id.enterDate1);
        final EditText endDateBox = (EditText) findViewById(R.id.enterDate2);

        startDateBox.setInputType(InputType.TYPE_NULL);
        startDateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerStart(startDateBox);
            }
        });

        endDateBox.setInputType(InputType.TYPE_NULL);
        endDateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerStart(endDateBox);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen();
            }
        });
    }

    private void datePickerStart(final EditText box) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(Createnewprotest1Activity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        box.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                    }
                }, year, month, day);
        picker.show();
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

        setLatLon(newEvent);

        completeSentimentAnalysis(description, newEvent, intent);

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

    public void setLatLon(final ProtestEvent event) {
        String addr_url = "?address=" + event.getLocation();
        String url = "https://maps.googleapis.com/maps/api/geocode/json" + addr_url + "&key=AIzaSyAHg81pUARe10Jif6txnxuso745wcJAi6Q";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject coords = response.optJSONArray("results").optJSONObject(0).optJSONObject("geometry").optJSONObject("location");
                        double lat = coords.optDouble("lat");
                        double lon = coords.optDouble("lng");
                        event.setLat(lat);
                        event.setLon(lon);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        event.setLat(-9999.0);
                        event.setLon(-9999.0);
                    }
                });
        queue.add(jsonObjectRequest);
    }
}