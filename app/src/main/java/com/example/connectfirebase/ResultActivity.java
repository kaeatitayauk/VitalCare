package com.example.connectfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    ImageView ic_heart;
    Button back;
    String age,gender;
    Float bpm;
    private String strShowDate;
    TextView txt_date,age_push,gender_push,BPM_push,result;
    DatabaseHelperUser myDb;
    Button btn_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        myDb = new DatabaseHelperUser(this);


        ic_heart = (ImageView)findViewById(R.id.imageView);
        int imageResource = getResources().getIdentifier("@drawable/heart_icon",null,this.getPackageName());
        ic_heart.setImageResource(imageResource);

        age_push = (TextView) findViewById(R.id.Age_result_show);
        gender_push = (TextView)findViewById(R.id.Gender_result_show);
        BPM_push = (TextView)findViewById(R.id.BPM_result_show);

        Intent intent = getIntent();
        age = intent.getStringExtra("value_age");
        age_push.setText(age);



        Intent intent2 = getIntent();
        gender = intent2.getStringExtra("gender_value");
        gender_push.setText(gender);

        Intent intent3 = getIntent();
        bpm = intent3.getFloatExtra("BPM_value", 0.0f);
        BPM_push.setText(String.valueOf(bpm));

        //int bpm_compare = Integer.parseInt(String.valueOf(bpm));

        result = (TextView)findViewById(R.id.Result_message_show);
        txt_date = (TextView) findViewById(R.id.txt_date);
        btn_save = (Button) findViewById(R.id.btn_save);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
       if(gender.equals("Male")){

           if(bpm>=56 && bpm<=68 && Integer.parseInt(age)>=18){
               result.setText("You have an excellent heart rate");
           }

           else if(bpm>60 || bpm<100){
               result.setText("You have a normal heart rate");
           }

           if(bpm<60 || bpm>100){
               result.setText("You have an abnormal heart rate!");
               sendSMS("07737215305","Patient have got an abnormal heart rate !");
           }

        }
        if(gender.equals("Female")){

            if(bpm>=60 && bpm<=70 && Integer.parseInt(age)>=18){
                result.setText("You have an excellent heart rate");
            }

            else if(bpm>60 || bpm<100){
                result.setText("You have a normal heart rate");
            }

             if(bpm<60 || bpm>100){
                result.setText("You have an abnormal heart rate!");
                 sendSMS("07737215305","Patient have got an abnormal heart rate !");
            }




        }


        back = (Button)findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent its = new Intent(ResultActivity.this,Home_page.class);
                startActivity(its);

            }
        });

        getTimeFromDevice();
        //get Time from Device
        AddData();


    }
    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private void getTimeFromDevice() {
        DateFormat objDateFormat = new SimpleDateFormat("d/M/yyyy");
        Date objDate = new Date();
        strShowDate = objDateFormat.format(objDate);
        txt_date.setText(strShowDate);
    }//getTimeFromDevice

    public void AddData(){
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sdate = txt_date.getText().toString();
                String SAge = age_push.getText().toString();
                String SGender = gender_push.getText().toString();
                String SBPM= BPM_push.getText().toString();
                if(SAge.equals("")&&SGender.equals("")&&SBPM.equals("")){
                    Toast.makeText(getApplicationContext(),"no Data Inserted",Toast.LENGTH_SHORT).show();
                }else{
                    boolean isInserted = myDb.insertData(Sdate,SAge,SGender,SBPM);
                    if(isInserted == true)
                        Toast.makeText(ResultActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}