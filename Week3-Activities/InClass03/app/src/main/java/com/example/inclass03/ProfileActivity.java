package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView textProfileName, textProfileEmail, textProfileID, textProfileDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textProfileName = findViewById(R.id.textProfileName);
        textProfileEmail = findViewById(R.id.textProfileEmail);
        textProfileID = findViewById(R.id.textProfileID);
        textProfileDept = findViewById(R.id.textProfileDept);

        if(getIntent() != null && getIntent().hasExtra(RegistrationActivity.KEY_REG)) {
            User user = (User) getIntent().getSerializableExtra(RegistrationActivity.KEY_REG);

            textProfileName.setText(user.getName());
            textProfileEmail.setText(user.getEmail());
            textProfileID.setText(String.valueOf(user.getID()));
            textProfileDept.setText(user.getDepartment());
        }
    }
}