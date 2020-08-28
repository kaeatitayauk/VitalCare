package com.example.connectfirebase;

public class RetrieveData {
    float x_value;
    float Reading_val;

    public RetrieveData(float x_value, float reading_val) {
        this.x_value = x_value;
        this.Reading_val = reading_val;
    }

    public RetrieveData() {}

    public float getx_Value() {

        return x_value;
    }

    public void setx_Value(float x_Value) {

        this.x_value = x_value;
    }

    public float getReading_val() {

        return Reading_val;
    }

    public void setReading_val(float reading_val) {

        Reading_val = reading_val;
    }
}
