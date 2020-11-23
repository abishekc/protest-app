package com.teamnine.protest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        /* UI COMPONENTS */
        /* BUTTONS */
        Button changeLoginButton = (Button) findViewById(R.id.change_login_button);
        Button signupButton = (Button) findViewById(R.id.signup_button);

        /* EDIT TEXT*/
        final EditText signupEmailInput = (EditText) findViewById(R.id.signup_email_input);
        final EditText signupPasswordInput = (EditText) findViewById(R.id.signup_password_input);

        changeLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receivedEmail = signupEmailInput.getText().toString();
                String receivedPassword = signupPasswordInput.getText().toString();

                completeSignup(receivedEmail, receivedPassword);
            }
        });
    }

    private void completeSignup(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("SUCCESS", "Signup has been completed succesfully, intent to Main Activity.");

                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Log.e("SIGNUP", task.getException().getMessage());
                }
            }
        });
    }
}