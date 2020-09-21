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

               if (Integer.parseInt(age) >= 18 && Integer.parseInt(age) <= 25) {
                   if (bpm >= 56 && bpm <= 61) {
                       result.setText("You have an excellent heart rate");
                   }
                   if (bpm >= 62 && bpm <= 65) {
                       result.setText("You have a good heart rate");
                   }
                   if (bpm >= 66 && bpm <= 69) {
                       result.setText("You have an above average heart rate");
                   }
                   if (bpm >= 70 && bpm <= 73) {
                       result.setText("You have an average heart rate");
                   }
                   if (bpm >= 74 && bpm <= 81) {
                       result.setText("You have a below average heart rate");
                   }
               }
               if (Integer.parseInt(age) >= 25 && Integer.parseInt(age) <= 35) {
                   if (bpm >= 55 && bpm <= 61) {
                       result.setText("You have an excellent heart rate");
                   }
                   if (bpm >= 62 && bpm <= 65) {
                       result.setText("You have a good heart rate");
                   }
                   if (bpm >= 66 && bpm <= 70) {
                       result.setText("You have an above average heart rate");
                   }
                   if (bpm >= 71 && bpm <= 74) {
                       result.setText("You have an average heart rate");
                   }
                   if (bpm >= 75 && bpm <= 81) {
                       result.setText("You have a below average heart rate");
                   }
               }

               if (Integer.parseInt(age) >= 36 && Integer.parseInt(age) <= 45) {
                   if (bpm >= 57 && bpm <= 62) {
                       result.setText("You have an excellent heart rate");
                   }
                   if (bpm >= 63 && bpm <= 66) {
                       result.setText("You have a good heart rate");
                   }
                   if (bpm >= 67 && bpm <= 70) {
                       result.setText("You have an above average heart rate");
                   }
                   if (bpm >= 71 && bpm <= 75) {
                       result.setText("You have an average heart rate");
                   }
                   if (bpm >= 76 && bpm <= 82) {
                       result.setText("You have a below average heart rate");
                   }
               }
               if (Integer.parseInt(age) >= 46 && Integer.parseInt(age) <= 55) {
                   if (bpm >= 58 && bpm <= 63) {
                       result.setText("You have an excellent heart rate");
                   }
                   if (bpm >= 64 && bpm <= 67) {
                       result.setText("You have a good heart rate");
                   }
                   if (bpm >= 68 && bpm <= 71) {
                       result.setText("You have an above average heart rate");
                   }
                   if (bpm >= 72 && bpm <= 76) {
                       result.setText("You have an average heart rate");
                   }
                   if (bpm >= 77 && bpm <= 83) {
                       result.setText("You have a below average heart rate");
                   }
               }
               if (Integer.parseInt(age) >= 55 && Integer.parseInt(age) <= 65) {
                   if (bpm >= 57 && bpm <= 61) {
                       result.setText("You have an excellent heart rate");
                   }
                   if (bpm >= 62 && bpm <= 67) {
                       result.setText("You have a good heart rate");
                   }
                   if (bpm >= 68 && bpm <= 71) {
                       result.setText("You have an above average heart rate");
                   }
                   if (bpm >= 72 && bpm <= 75) {
                       result.setText("You have an average heart rate");
                   }
                   if (bpm >= 76 && bpm <= 81) {
                       result.setText("You have a below average heart rate");
                   }
               }
               if (Integer.parseInt(age) >= 65) {
                   if (bpm >= 56 && bpm <= 61) {
                       result.setText("You have an excellent heart rate");
                   }
                   if (bpm >= 62 && bpm <= 65) {
                       result.setText("You have a good heart rate");
                   }
                   if (bpm >= 66 && bpm <= 69) {
                       result.setText("You have an above average heart rate");
                   }
                   if (bpm >= 70 && bpm <= 73) {
                       result.setText("You have an average heart rate");
                   }
                   if (bpm >= 74 && bpm <= 79) {
                       result.setText("You have a below average heart rate");
                   }
               }

               if (bpm < 60 || bpm > 100) {
                   result.setText("You have an abnormal heart rate!");
                   sendSMS("07737215305", "The patient has an abnormal heart rate!");
               }


        }
        if(gender.equals("Female")){

            if(Integer.parseInt(age)>=18 && Integer.parseInt(age)<=25 ){
                if(bpm>=61 && bpm<=65){
                    result.setText("You have an excellent heart rate");
                }
                if(bpm>=66 && bpm<=69){
                    result.setText("You have a good heart rate");
                }
                if(bpm>=70 && bpm<=73){
                    result.setText("You have an above average heart rate");
                }
                if(bpm>=74 && bpm<=78){
                    result.setText("You have an average heart rate");
                }
                if(bpm>=79 && bpm<=84){
                    result.setText("You have a below average heart rate");
                }
            }
            if(Integer.parseInt(age)>=25 && Integer.parseInt(age)<=35 ) {
                if(bpm>=60 && bpm<=64){
                    result.setText("You have an excellent heart rate");
                }
                if(bpm>=65 && bpm<=68){
                    result.setText("You have a good heart rate");
                }
                if(bpm>=69 && bpm<=72){
                    result.setText("You have an above average heart rate");
                }
                if(bpm>=73 && bpm<=76) {
                    result.setText("You have an average heart rate");
                }
                if(bpm>=77 && bpm<=82){
                    result.setText("You have a below average heart rate");
                }
            }

            if(Integer.parseInt(age)>=36 && Integer.parseInt(age)<=45 ) {
                if(bpm>=60 && bpm<=64){
                    result.setText("You have an excellent heart rate");
                }
                if(bpm>=65 && bpm<=69){
                    result.setText("You have a good heart rate");
                }
                if(bpm>=70 && bpm<=73){
                    result.setText("You have an above average heart rate");
                }
                if(bpm>=74 && bpm<=78) {
                    result.setText("You have an average heart rate");
                }
                if(bpm>=79 && bpm<=84){
                    result.setText("You have a below average heart rate");
                }
            }
            if(Integer.parseInt(age)>=46 && Integer.parseInt(age)<=55 ) {
                if(bpm>=61 && bpm<=65){
                    result.setText("You have an excellent heart rate");
                }
                if(bpm>=66 && bpm<=69){
                    result.setText("You have a good heart rate");
                }
                if(bpm>=70 && bpm<=73){
                    result.setText("You have an above average heart rate");
                }
                if(bpm>=74 && bpm<=77) {
                    result.setText("You have an average heart rate");
                }
                if(bpm>=78 && bpm<=83){
                    result.setText("You have a below average heart rate");
                }
            }
            if(Integer.parseInt(age)>=55 && Integer.parseInt(age)<=65 ) {
                if(bpm>=60 && bpm<=64){
                    result.setText("You have an excellent heart rate");
                }
                if(bpm>=65 && bpm<=68){
                    result.setText("You have a good heart rate");
                }
                if(bpm>=69 && bpm<=73){
                    result.setText("You have an above average heart rate");
                }
                if(bpm>=74 && bpm<=77) {
                    result.setText("You have an average heart rate");
                }
                if(bpm>=78 && bpm<=83){
                    result.setText("You have a below average heart rate");
                }
            }
            if(Integer.parseInt(age)>=65 ) {
                if(bpm>=60 && bpm<=64){
                    result.setText("You have an excellent heart rate");
                }
                if(bpm>=65 && bpm<=68){
                    result.setText("You have a good heart rate");
                }
                if(bpm>=69 && bpm<=72){
                    result.setText("You have an above average heart rate");
                }
                if(bpm>=73 && bpm<=76) {
                    result.setText("You have an average heart rate");
                }
                if(bpm>=77 && bpm<=84){
                    result.setText("You have a below average heart rate");
                }
            }

             if(bpm<60 || bpm>100){
                result.setText("You have an abnormal heart rate!");
                 sendSMS("07737215305","The patient has an abnormal heart rate!");
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