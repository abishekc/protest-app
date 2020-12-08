package com.teamnine.protest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
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
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
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
import java.util.Arrays;
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

        String apiKey = "AIzaSyCa5SOrP4lGuotvRWYW9GPD_ZSn9lYQd-A";

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Initialize the AutocompleteSupportFragment.
        final AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        //autocompleteFragment.getView().findViewById(R.id.places_autocomplete_clear_button).setVisibility(View.GONE);
        autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_button).setVisibility(View.GONE);
        EditText autocompleteEditText = (EditText) autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_input);

        Typeface poppinsMed = Typeface.createFromAsset(getAssets(), "fonts/poppins_med.ttf");
        autocompleteEditText.setTypeface(poppinsMed);

        final float scale = autocompleteFragment.getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (4.8 * scale + 0.5f);
        autocompleteEditText.setTextSize(pixels);
        autocompleteEditText.setTextColor(getResources().getColor(R.color.fill));
        autocompleteEditText.setBackground(getResources().getDrawable(R.drawable.text_box));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("LOCATION", "Place: " + place.getName() + ", " + place.getId());
                EditText locationBox = (EditText) findViewById(R.id.location_input);
                locationBox.setText(place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("LOCATION", "An error occurred: " + status);
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
                        box.setText((monthOfYear + 1) + "." +  dayOfMonth + "." + year);
                    }
                }, year, month, day);
        picker.show();
    }

    public void nextScreen() {
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

        boolean nameEmpty = TextUtils.isEmpty(name);
        boolean locationEmpty = TextUtils.isEmpty(location);
        boolean descriptionEmpty = TextUtils.isEmpty(description);
        boolean startEmpty = TextUtils.isEmpty(startDate);
        boolean endEmpty = TextUtils.isEmpty(endDate);

        if (nameEmpty) {
            nameBox.setError("Event name is required.");
            nameBox.setHintTextColor(Color.parseColor("#F94F63"));
        }else{
            nameBox.setError(null);
        }

        if (locationEmpty) {
            locationBox.setError("Location is required.");
            locationBox.setHintTextColor(Color.parseColor("#F94F63"));
        }else{
            locationBox.setError(null);
        }

        if (descriptionEmpty) {
            descriptionBox.setError("Description is required.");
            descriptionBox.setHintTextColor(Color.parseColor("#F94F63"));
        }else{
            descriptionBox.setError(null);
        }

        if (startEmpty) {
            startDateBox.setError("Start date is required.");
            startDateBox.setHintTextColor(Color.parseColor("#F94F63"));
        }else{
            startDateBox.setError(null);
        }

        if (endEmpty) {
            endDateBox.setError("End date is required.");
            endDateBox.setHintTextColor(Color.parseColor("#F94F63"));
        }else{
            endDateBox.setError(null);
        }

        if(nameEmpty || locationEmpty ||descriptionEmpty || startEmpty || endEmpty){
            return;
        }

        Intent intent = new Intent(this, CreateNewProtest2Activity.class);


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