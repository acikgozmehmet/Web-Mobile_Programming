// Created by Vijaya Yeruva on 11/21/2020

package com.example.insertcalendarevent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    TextView textView, textView3, textView4;

    CalendarView calendarView;
    Button createEvent;
    Calendar beginCal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textView = findViewById(R.id.textView);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        calendarView = findViewById(R.id.calendarView);
        createEvent = (Button) findViewById(R.id.create_eventbut);

        textView.setText("Calendar View Example");
        textView3.setText("Today's Date");
        Format f = new SimpleDateFormat("MM/dd/yyyy");
        textView4.setText(f.format(new Date()));

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Auto-generated method stub
                insert();
            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // add code here
                String date_str= String.valueOf(month+1)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year);
                textView3.setText("Selected Date: ");
                textView4.setText(date_str);
            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insert() {

        String[] arr = textView4.getText().toString().trim().split("/");
        int month = Integer.parseInt(arr[0]);
        int day = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);

        beginCal = Calendar.getInstance();
        beginCal.set(year, month, day, 00, 00);

        Intent intent = new Intent(Intent.ACTION_INSERT,CalendarContract.Events.CONTENT_URI);
        // Add the calendar event details
        intent.putExtra(CalendarContract.Events.TITLE, "Launch!");
        intent.putExtra(CalendarContract.Events.DESCRIPTION,"Learn Java Android Coding");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"UMKC.com");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginCal.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        // Use the Calendar app to add the new event.
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}