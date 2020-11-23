package com.teamnine.protest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }
}