package com.example.connectfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class calendarActivityUser extends AppCompatActivity{

    private static final String TAG = "calendarActivityUser";
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendaractivityuser_layout);
        mCalendarView =(CalendarView)findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date =  dayOfMonth + "/" + (month+1) + "/" + year;
                Log.d(TAG,"onSelectedDayChange: dd/MM/yyyy: "+date);

                Intent intent = new Intent(calendarActivityUser.this,calendarUser.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
    }
}
