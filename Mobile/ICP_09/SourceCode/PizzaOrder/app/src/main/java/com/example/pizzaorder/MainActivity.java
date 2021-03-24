package com.example.pizzaorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText customerName, customerEmail;
    private Spinner spinnerPizzaSize;
    private CheckBox Cheese, Olives;
    private TextView quantityTextView;

    private String pizzaSize;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerPizzaSize = findViewById(R.id.sp_pizzaSize);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pizzaSize, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPizzaSize.setAdapter(adapter);
        spinnerPizzaSize.setOnItemSelectedListener(this);


        customerName = findViewById(R.id.et_customerName);
        customerEmail = findViewById(R.id.et_customerEmail);
        quantityTextView = findViewById(R.id.tv_quantity);
        Cheese = findViewById(R.id.cb_toppingsCheese);
        Olives = findViewById(R.id.cb_toppingsOlives);

    } // on create


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         pizzaSize = parent.getItemAtPosition(position).toString();
        // Toast.makeText(this, pizzaSize, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    public void gotoOrder(View view){
        String message = createOrderMessage();
        sendEmail(message);
    }

    private double calculatePrice(){

        double price = 10;

        switch (this.pizzaSize){
            case "Small":
                price *= 0.8;
                break;
            case "Large":
                price *=1.1;
                break;
            case "X-Large":
                price *=1.2;
                break;
        }

        if (this.Cheese.isChecked())
            price += 1;

        if (this.Olives.isChecked())
            price += 2;

        return price;
    }


    public void sendEmail(String message){

        String toSender = this.customerEmail.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {this.customerEmail.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT,"Your Pizza Order");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(intent, "Sending mail"));
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email client installed", Toast.LENGTH_SHORT).show();
        }

    }


    private String createOrderMessage()
    {
        String message = "Dear "+ this.customerName.getText().toString() + ",\n\n" +
                         "Please find below your order summary \n" +
                         "\n\nPizza size :" + pizzaSize +
                         "\nQuantity :" + this.quantity +

                          "\n\nToppings" +
                         "\nCheese :" + (this.Cheese.isChecked() ? "Yes":"No") +
                         "\nOlives :" + (this.Olives.isChecked() ? "Yes":"No") +
                         "\n\nPrice   : $" + calculatePrice() * quantity;

        return message;
    }



    public void gotoSummary(View view){
        String message = createOrderMessage();
        Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
        intent.putExtra("toSender", this.customerEmail.getText().toString());
        intent.putExtra("summaryMessage", message);
        startActivity(intent);
    }


    public void decrement(View view){
        if (quantity > 1) {
            quantity--;
            displayQuantity(quantity);
        }
        else {
            Log.i("MainActivity", "Please select at least one pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_few_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }

    public void increment (View view){
        if (quantity < 10){
            quantity++;
            displayQuantity(quantity);
        }
        else {
            Log.i("MainActivity", "Please select less than ten pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_many_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    public void displayQuantity(int count){
        quantityTextView = findViewById(R.id.tv_quantity);
        quantityTextView.setText("" + count);
    }





} // class