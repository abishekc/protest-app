package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if user exists - if not, intent to login page.
        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mCurrentUser == null) {
            Intent i = new Intent(this, login.class);
            startActivity(i);
            finish();
        } else {
            Log.i("SUCCESS", "User is already logged in, UUID is: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
    }
}