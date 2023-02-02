package com.example.assessment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetWeightActivity extends AppCompatActivity {

    public static final String KEY_WEIGHT = "Weight";

    EditText editTextNumberDecimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_weight);
        setTitle("Set Weight");

        editTextNumberDecimal = findViewById(R.id.editTextNumberDecimal);

        findViewById(R.id.buttonSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = editTextNumberDecimal.getText().toString();
                Double weight;

                try {
                    weight = Double.parseDouble(result);
                } catch (NumberFormatException exception) {
                    Toast.makeText(SetWeightActivity.this, "Weight is required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra(KEY_WEIGHT, weight);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}