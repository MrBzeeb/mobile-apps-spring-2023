package com.example.assessment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewGender, textViewWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        Intent intent = getIntent();
        Profile profile = (Profile) intent.getSerializableExtra(MainActivity.KEY_PROFILE);

        textViewGender = findViewById(R.id.textViewGender);
        textViewWeight = findViewById(R.id.textViewWeight);

        textViewGender.setText(profile.getGender());
        textViewWeight.setText(profile.getWeight().toString());

        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}