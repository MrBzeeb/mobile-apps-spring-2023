package com.example.assessment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SetGenderActivity extends AppCompatActivity {

    public static final String KEY_GENDER = "Gender";

    RadioGroup radioGroupGender;
    RadioButton checkedRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_gender);
        setTitle("Set Gender");

        radioGroupGender = findViewById(R.id.radioGroupGender);

        findViewById(R.id.buttonSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                checkedRadio = (RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId());
                intent.putExtra(KEY_GENDER, checkedRadio.getText());
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