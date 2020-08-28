package com.example.connectfirebase;

public class ECGAnalyser{

    public float[] highPass(float[] Array_y, int nsample) {

        float[] highPass = new float[nsample];
        int M = 5; // M is recommended to be 5 or 7 according to the paper
        int constant = (int) 1/M;

        for(int i=0; i<Array_y.length; i++) {
            float y1 = 0;
            float y2 = 0;

            int y2_index = i-((M+1)/2);
            if(y2_index < 0) {
                y2_index = nsample + y2_index;
            }
            y2 = Array_y[y2_index];

            int y1_sum = 0;
            for(int j=i; j>i-M; j--) {
                int x_index = i - (i-j);
                if(x_index < 0) {
                    x_index = nsample + x_index;
                }
                y1_sum += Array_y[x_index];
            }

            y1 = constant * y1_sum;
            highPass[i] = y2 - y1;

        }

        return highPass;
    }

    public static float[] lowPass(float[] Array_y, int nsample) {
        float[] lowPass = new float[nsample];
        for(int i=0; i<Array_y.length; i++) {
            float sum = 0;
            if(i+30 < Array_y.length) {
                for(int j=i; j<i+30; j++) {
                    float current = Array_y[j] * Array_y[j];
                    sum += current;
                }
            }
            else if(i+30 >= Array_y.length) {
                int over = i+30 - Array_y.length;
                for(int j=i; j<Array_y.length; j++) {
                    float current = Array_y[j] * Array_y[j];
                    sum += current;
                }
                for(int j=0; j<over; j++) {
                    float current = Array_y[j] * Array_y[j];
                    sum += current;
                }
            }

            lowPass[i] = sum;
        }

        return lowPass;

    }

    public static float[] QRS(float[] lowPass, int nsample) {

        float[] QRS = new float[nsample];

        double threshold = 0;

        for(int i=0; i<2; i++) {
            if(lowPass[i] > threshold) {
                threshold = lowPass[i];
            }
        }

        int frame = 4;

        for(int i=0; i<lowPass.length; i+=frame) {
            float max = 0;
            int index = 0;

            if(i + frame > lowPass.length) {
                index = lowPass.length;
            }else {
                index = i + frame;
            }

            for(int j=i; j<index; j++) {
                if(lowPass[j] > max) {
                    max = lowPass[j];
                }
            } // Finding the PEAK

            boolean added = false;
            for(int j=i; j<index; j++) {
                if(lowPass[j] > threshold && !added) {
                    QRS[j] = 1;
                    added = true;
                }
                else {
                    QRS[j] = 0;
                }
            }


            threshold = 0.15 * 0.2 * max + (1 - 0.15) * threshold;

        }

        return QRS;
    }


}
