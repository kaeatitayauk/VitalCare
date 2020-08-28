package com.example.connectfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //Declare variables
    Button btn;
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText age;
    Spinner mySpinner;
    String gender,BPM;
    LineChart Signal_linechart;
    List<RetrieveData> list= new ArrayList <>();
    LineDataSet lineDataSet = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    float BPM_New = 0;
    float Array_y[] = new float[100];
    float Array_x[] = new float[100];
    double M = 5;
    double N = 30;
    double HP_CONSTANT  = ((float)1/(float)M);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toast.makeText(this, "Firebase connection Success", Toast.LENGTH_LONG).show();

        //Call Variables with Layout,declare intent for connecting with other activity and also declare firebase connection.//////
        btn = (Button) findViewById(R.id.button);
        age = (EditText) findViewById(R.id.input_age);
        Signal_linechart = (LineChart)findViewById(R.id.line_chart);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ECG_data");
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

     ///////////////////////////////////////For Gender Dropdown list//////////////////////////////////////////////////////
        mySpinner = (Spinner) findViewById(R.id.gender_spin_pick);

        final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Gender));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


        ///For listen an event that item is selected.
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        gender = mySpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Please Select Your Gender!", Toast.LENGTH_SHORT).show();
            }
        });
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        // Retrieving and showing an ECG signal from firebase.
        fetch_data();


        //When you clicked an analyse button.
        PassingDataToResultActivity();


    }
////////////////////////////////////////////////////////////////////////////////End OnCreate......////////////////////////////////////////////////////////////

    ////// Method For Passing the data to another activity. /////////
    private void PassingDataToResultActivity(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enter Age
                String value = age.getText().toString();


                Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                if (value.matches("")) {
                    Toast.makeText(MainActivity.this, "Please Enter Your Age!", Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    intent.putExtra("value_age", value);
                    intent.putExtra("gender_value",gender);
                    intent.putExtra("BPM_value",BPM_New);
                    startActivity(intent);

                }
            }

        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///// Retrieve the ECG data from firebase and store it in Array[].////
    private void fetch_data(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int differ =0;
                ArrayList<Entry> dataVals = new ArrayList<Entry>();
                float sample=0;
                float []RR_interval = new float[100];
                float PEAK = 0,PEAK_QRS =0;
                float RR_time=0;
                float[] highpass;
                float[] lowpass = new float[0];
                float[] QRS ;
                float[] highPass = new float[(int) sample];
                int M = 5; // M is recommended to be 5 or 7 according to the paper
                float constant = (float) 1/M;

                for (DataSnapshot ds : snapshot.getChildren()) {
                    RetrieveData signal = ds.getValue(RetrieveData.class); //Declare RetrieveData to MainActivity.class for using methods
                                                                        // which includes in RetrieveData.class

                    list.add(signal); //Add all data from firebase in List / Array list.
                }
                for (int i=0;i<list.size();i++)
                {
                    //Log.e("x_Value", String.valueOf(list.get(i).getx_Value())); //Test printing data in Logcat on android studio.
                   // Log.e("Reading_val", String.valueOf(list.get(i).getReading_val()));//Test printing data in Logcat on android studio.


                    Array_y[i] = list.get(i).getReading_val(); //Voltage value from firebase ( Reading_val ).
                    Array_x[i] = list.get(i).getx_Value(); //Data record from firebase ( x_value ).

                    sample = Array_y.length;// number of samples


                    for (int j=0;j<Array_y.length;j++){

                        if(Array_y[j]>PEAK) {
                            PEAK = Array_y[j];
                        }


                    }


                    if(Array_y.length==50){
                      //  Toast.makeText(MainActivity.this, "BPM:  " + Array_y[i]/50 + "-"+Array_x.length+"-"+sample+"="+PEAK, Toast.LENGTH_LONG).show();
                        //float[] element = highPass(new int[]{Array_y[i]},Integer.parseInt(String.valueOf(Array_x[i])));
                        //Toast.makeText(MainActivity.this, "BPM:  " + element[i], Toast.LENGTH_LONG).show();

                        BPM = String.valueOf(Array_y[i]/50);////// BPM data....Prepare to send to result activity.



                    }


                    dataVals.add(new Entry(Array_x[i],Array_y[i])); // Put the data x_value and Reading_val from firebase into
                                                                    // ArrayList<> for the line graph.

                    //signal_analysis(Array_x[i],Array_y[i]);

                    
                }
                showchart(dataVals); // Calling method for showing the line graph.
                
                
                ECGAnalyser analyser = new ECGAnalyser();
                for (int i =0;i<Array_y.length-1;i++) {
                    highpass = analyser.highPass(Array_y, (int) sample);
                    Log.e("HIGH_PASS", String.valueOf(highpass[i]));
                    lowpass = analyser.lowPass(Array_y, (int) sample);
                    Log.e("LOW_PASS", String.valueOf(lowpass[i]));
                    QRS = analyser.QRS(lowpass,lowpass.length);
                    Log.e("QRS", String.valueOf(QRS[i]));
                    if(QRS[i]==1){

                        RR_interval[i] = Array_x[i];
                       // Log.e("RR_interval", String.valueOf(RR_interval[i]));
                        Log.e("RR_interval", String.valueOf(RR_interval[i]));
                        float [] result = findRR(RR_interval);
                        differ = (int) Math.abs(result[0]-result[1]);

                       // Log.e("Differ", String.valueOf(differ));

                    }
                   
                }
                BPM_New = Array_x[99]/differ;
                if(Array_x[99]>70000){
                    BPM_New = (Array_x[99]-Array_x[0])/differ;
                }
                Log.e("BPM", String.valueOf(BPM_New));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Cannot fetch any data from Firebase!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private float[] findRR(float[] RR_interval) {
        float[]result = new float[2];
        int c =0;
        for (float i : RR_interval){
            if(i>0) {
                result[c] = i;
                c+=1;
            }
            if(c>1){
                break;
            }
        }
        return result;
    }


    //////// Show an ECG signal on line graph. //////////
    private void showchart(ArrayList<Entry> dataVals) {
        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel("ECG Amplitude");
        lineDataSet.setLineWidth(2);
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueTextSize(0);
        lineDataSet.setColor(Color.RED);
        Signal_linechart.clear();
        Signal_linechart.setDrawBorders(true);
        Signal_linechart.setBorderWidth(3);
        Signal_linechart.setBorderColor(Color.BLUE);
        Signal_linechart.setData(lineData);
        Signal_linechart.invalidate();
    }
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}// End Class.





