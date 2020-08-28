
package com.example.connectfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Home_page extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button btn1 = (Button)findViewById(R.id.btn_userxmain);
        Button btn2 = (Button)findViewById(R.id.btn_userxreport);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent its = new Intent(Home_page.this,MainActivity.class);
                startActivity(its);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Home_page.this, "Waiting for coding report", Toast.LENGTH_LONG).show();
                Intent it = new Intent(Home_page.this,calendarUser.class);
                startActivity(it);
            }
        });
    }



}