package com.example.evaluation1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText numberA, numberB;
    TextView operatorDisplay, answerDisplay;
    Button buttonCalculate, buttonReset;
    RadioButton operatorAdd, operatorSubtract, operatorMultiply, operatorDivide;
    RadioGroup operatorSelection;

    int operatorType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberA = findViewById(R.id.numberA);
        numberB = findViewById(R.id.numberB);
        operatorDisplay = findViewById(R.id.operatorDisplay);
        answerDisplay = findViewById(R.id.answerDisplay);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonReset = findViewById(R.id.buttonReset);
        operatorAdd = findViewById(R.id.operatorAdd);
        operatorSubtract = findViewById(R.id.operatorSubtract);
        operatorMultiply = findViewById(R.id.operatorMultiply);
        operatorDivide = findViewById(R.id.operatorDivide);
        operatorSelection = findViewById(R.id.operatorSelection);

        //buttonCalculate

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberA.setText("");
                numberB.setText("");
                answerDisplay.setText("N/A");
                operatorDisplay.setText("+");
                operatorAdd.setChecked(true);
            }
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateAndDisplay();
            }
        });

        operatorSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton checkedButton = radioGroup.findViewById(id);
                if(checkedButton == operatorAdd) {
                    operatorType = 0;
                } else if (checkedButton == operatorSubtract) {
                    operatorType = 1;
                } else if (checkedButton == operatorMultiply) {
                    operatorType = 2;
                } else if (checkedButton == operatorDivide) {
                    operatorType = 3;
                } else {
                    Toast.makeText(MainActivity.this, "Error selecting operator", Toast.LENGTH_SHORT).show();
                    return;
                }

                switch(operatorType) {
                    case 0:
                        operatorDisplay.setText("+");
                        break;
                    case 1:
                        operatorDisplay.setText("-");
                        break;
                    case 2:
                        operatorDisplay.setText("*");
                        break;
                    case 3:
                        operatorDisplay.setText("/");
                        break;
                }

            }
        });
    }

    private void calculateAndDisplay() {
        String operandA = numberA.getText().toString();
        String operandB = numberB.getText().toString();
        Double doubleA, doubleB;

        // Converting number A
        try {
            doubleA = Double.valueOf(operandA);
        } catch (NumberFormatException exception) {
            Toast.makeText(MainActivity.this, "Operand A is not in correct format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Converting number B
        try {
            doubleB = Double.valueOf(operandB);
        } catch (NumberFormatException exception) {
            Toast.makeText(MainActivity.this, "Operand B is not in correct format", Toast.LENGTH_SHORT).show();
            return;
        }

        double answer = 0.0;

        switch(operatorType) {
            case 0:
                answer = doubleA + doubleB;
                break;
            case 1:
                answer = doubleA - doubleB;
                break;
            case 2:
                answer = doubleA * doubleB;
                break;
            case 3:
                if(doubleB == 0.0) {
                    Toast.makeText(MainActivity.this, "Operand B is zero which would cause divide by zero error", Toast.LENGTH_SHORT).show();
                    answerDisplay.setText("N/A");
                    return;
                }

                answer = doubleA / doubleB;
                break;
        }

        answerDisplay.setText(String.format("%.2f", answer));
    }
}