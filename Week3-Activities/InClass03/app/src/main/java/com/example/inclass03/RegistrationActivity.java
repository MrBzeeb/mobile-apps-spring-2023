package com.example.inclass03;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    public static final String KEY_REG = "Reg";

    Button buttonSelect, buttonSubmit;
    EditText editTextName, editTextEmail, editTextID;
    TextView textViewDept;

    private ActivityResultLauncher<Intent> startDeptForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //handle dept activity return
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        textViewDept.setText(data.getStringExtra(DepartmentActivity.KEY_DEPT));
                    } else {
                        textViewDept.setText(null);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        buttonSelect = findViewById(R.id.buttonSelect);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextID = findViewById(R.id.editTextID);
        textViewDept = findViewById(R.id.textViewDept);

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switch to department activity
                Intent intent = new Intent(RegistrationActivity.this, DepartmentActivity.class);
                startDeptForResult.launch(intent);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //grab data and move to profile activity
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                int ID = Integer.parseInt(editTextID.getText().toString());
                String department = textViewDept.getText().toString();

                User data = new User(name, email, ID, department);

                //switch to profile activity
                Intent intent = new Intent(RegistrationActivity.this, ProfileActivity.class);
                intent.putExtra(KEY_REG, data);
                startDeptForResult.launch(intent);
            }
        });
    }
}