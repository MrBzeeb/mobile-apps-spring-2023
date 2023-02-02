package com.example.assessment2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_PROFILE = "Profile";

    Button buttonSetWeight, buttonSetGender, buttonSubmit, buttonReset;
    TextView textViewWeight, textViewGender;

    Profile profile = new Profile();

    private ActivityResultLauncher<Intent> startGenderResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //handle dept activity return
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        profile.setGender(data.getStringExtra(SetGenderActivity.KEY_GENDER));
                        textViewGender.setText(profile.getGender());
                    } else {
                        //textViewGender.setText("N/A");
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> startWeightResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //handle dept activity return
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        profile.setWeight(data.getDoubleExtra(SetWeightActivity.KEY_WEIGHT, -1.0));
                        textViewWeight.setText(Double.toString(profile.getWeight()));
                    } else {
                        //textViewWeight.setText("N/A");
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main");

        buttonSetWeight = findViewById(R.id.buttonSetWeight);
        buttonSetGender = findViewById(R.id.buttonSetGender);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonReset = findViewById(R.id.buttonReset);

        textViewWeight = findViewById(R.id.textViewWeight);
        textViewGender = findViewById(R.id.textViewGender);

        buttonSetGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launch set gender activity
                Intent intent = new Intent(MainActivity.this, SetGenderActivity.class);
                startGenderResult.launch(intent);
            }
        });

        buttonSetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launch set weight activity
                Intent intent = new Intent(MainActivity.this, SetWeightActivity.class);
                startWeightResult.launch(intent);
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewGender.setText("N/A");
                textViewWeight.setText("N/A");
                profile.setGender(null);
                profile.setWeight(null);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profile.getGender() != null && profile.getWeight() != null) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra(KEY_PROFILE, profile);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Both Gender and Weight are required.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}