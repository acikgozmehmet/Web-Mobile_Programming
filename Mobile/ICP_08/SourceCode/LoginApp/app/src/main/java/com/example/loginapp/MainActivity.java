package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText eUsername;
    private EditText ePassword;
    private Button eLogin;
    private TextView eAttemptsInfo;

    // credentials
    private final String username = "Mehmet";
    private final String password = "12345678";

    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eUsername = findViewById(R.id.etUsername);
        ePassword = findViewById(R.id.etPassword);
        eLogin = findViewById(R.id.btnLogin);
        eAttemptsInfo = findViewById(R.id.tvAttemptsInfo);

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputUsername = eUsername.getText().toString().trim();
                String inputPassword = ePassword.getText().toString().trim();

                if (inputUsername.length() == 0 || inputPassword.length() == 0 ) // check if fields are empty
                {
                    Toast.makeText(MainActivity.this, "Please enter the username or password", Toast.LENGTH_SHORT).show();
                }
                else {
                    // check if credentials are correct
                    boolean isValid = validateCredentials(inputUsername, inputPassword);

                    if (!isValid) { // if credentials are not correct
                        counter--;
                        Toast.makeText(MainActivity.this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
                        eAttemptsInfo.setText("Number of attempts remaining: " + counter);

                        // if it is the last attempt, disable the button
                        if (counter == 0) {
                            eLogin.setEnabled(false);
                        }

                    } else { // if the credentials are correct
                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                        //switching to the new activity
                        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private boolean validateCredentials(String username, String password)
    {
        return (this.username.equals(username) && this.password.equals(password));
    }
}