package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DepartmentActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton checkedRadio;
    Button buttonDeptCancel, buttonDeptSelect;

    public static final String KEY_DEPT = "DEPT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        radioGroup = findViewById(R.id.radioGroup);
        buttonDeptCancel = findViewById(R.id.buttonDeptCancel);
        buttonDeptSelect = findViewById(R.id.buttonDeptSelect);

        buttonDeptCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonDeptSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                checkedRadio = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                intent.putExtra(KEY_DEPT, checkedRadio.getText());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}