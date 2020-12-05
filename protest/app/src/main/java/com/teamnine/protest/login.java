package com.teamnine.protest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /* UI COMPONENTS */
        /* BUTTONS */
        Button changeSignupButton = (Button) findViewById(R.id.change_signup_button);
        Button loginButton = (Button) findViewById(R.id.login_button);

        /* EDIT TEXT*/
        final EditText loginEmailInput = (EditText) findViewById(R.id.login_email_input);
        final EditText loginPasswordInput = (EditText) findViewById(R.id.login_password_input);

        changeSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), signup.class);
                startActivity(i);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receivedEmail = loginEmailInput.getText().toString();
                String receivedPassword = loginPasswordInput.getText().toString();
                boolean emailEmpty = TextUtils.isEmpty(receivedEmail);
                boolean passEmpty = TextUtils.isEmpty(receivedPassword);

                if (emailEmpty) {
                    loginEmailInput.setError("Username is required.");
                    loginEmailInput.setHintTextColor(Color.parseColor("#F94F63"));
                }

                if (passEmpty) {
                    loginPasswordInput.setError("Password is required.");
                    loginPasswordInput.setHintTextColor(Color.parseColor("#F94F63"));
                }

                if(!emailEmpty && !passEmpty) {
                    completeLogin(receivedEmail, receivedPassword);
                }
            }
        });
    }

    private void completeLogin(String email, String password) {
        if(email != "" && password != "") {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.i("SUCCESS", "Login has been completed succesfully, intent to MainActivity.");

                        Intent i = new Intent(getBaseContext(), MyprotestsActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Log.e("LOGIN", task.getException().getLocalizedMessage());
                    }
                }
            });
        }
    }

}