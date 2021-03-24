package com.example.pizzaorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SummaryActivity extends AppCompatActivity {

    private TextView orderSummary;
    private String toSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        orderSummary = findViewById(R.id.tv_OrderSummary);

        Intent intent = getIntent();
        String message = intent.getStringExtra("summaryMessage");
        toSender = intent.getStringExtra("toSender");
        orderSummary.setText(message);
    }


    public void gotoOrder(View view){

        String message = orderSummary.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {toSender});
        intent.putExtra(Intent.EXTRA_SUBJECT,"Your Pizza Order");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(intent, "Sending mail"));
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SummaryActivity.this, "There are no email client installed", Toast.LENGTH_SHORT).show();
        }

    }


} // class
