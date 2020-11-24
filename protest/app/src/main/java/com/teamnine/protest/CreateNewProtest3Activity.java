package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class CreateNewProtest3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.create_new_protest3);

        Intent intent = getIntent();
        final ProtestEvent passedEvent = intent.getParcelableExtra("Event");

        final ShareDialog shareDialog = new ShareDialog(this);

        Button fb_btn = findViewById(R.id.fb_share_btn);
        fb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //The link is currently set to google, but will later change to the link to download our app.
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setQuote(String.format("Come and join me in the protest \"%s\"", passedEvent.name))
                        .setContentUrl(Uri.parse("www.google.com"))
                        .build();
                if(ShareDialog.canShow(ShareLinkContent.class)){
                    shareDialog.show(content);
                }

            }
        });

        Button twitter_btn = findViewById(R.id.twitter_share_btn);
        twitter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = String.format("Come and join me in the protest \"%s\"", passedEvent.name);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, msg);
                intent.setType("text/plain");
                intent.setPackage("com.twitter.android");
                startActivity(intent);
            }
        });

        ImageButton next = findViewById(R.id.next_screen_main);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen();
            }
        });
    }

    public void nextScreen() {
        Intent intent = new Intent(this, MyprotestsActivity.class);
        startActivity(intent);
    }
}
