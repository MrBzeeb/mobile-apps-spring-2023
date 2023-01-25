package com.example.inclass02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText ticketPrice;
    TextView percentDisplay, priceDisplay;
    Button button5, button10, button15, button20, button50, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ticketPrice = findViewById(R.id.ticketPrice);
        percentDisplay = findViewById(R.id.percentDisplay);
        priceDisplay = findViewById(R.id.priceDisplay);
        button5 = findViewById(R.id.button5);
        button10 = findViewById(R.id.button10);
        button15 = findViewById(R.id.button15);
        button20 = findViewById(R.id.button20);
        button50 = findViewById(R.id.button50);
        buttonClear = findViewById(R.id.buttonClear);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketPrice.setText("");
                percentDisplay.setText("");
                priceDisplay.setText("");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyDiscount(5.0);
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyDiscount(10.0);
            }
        });

        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyDiscount(15.0);
            }
        });

        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyDiscount(20.0);
            }
        });

        button50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyDiscount(50.0);
            }
        });


    }

    private void applyDiscount(double discount) {
        try {
            Double priceOfTicket = Double.valueOf(ticketPrice.getText().toString());
            double discountedTicketPrice = priceOfTicket * ((100.0 - discount) / 100.0);
            percentDisplay.setText(Double.toString(discount));
            priceDisplay.setText(Double.toString(discountedTicketPrice));
        } catch (NumberFormatException exception) {
            Toast.makeText(MainActivity.this, "Number is not valid format", Toast.LENGTH_SHORT).show();
        }

    }
}