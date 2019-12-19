package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Button mybtn;
    String hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView=(TextView)findViewById(R.id.textView);
        final Button button1 =  (Button)findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                textView.setText("");
                textView.setText(getCPUDetails());
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                textView.setText("");
                textView.setText(getRAM());
            }
        });

    }

    public String getCPUDetails() {
        ProcessBuilder pb;
        String cpuDetails = "";
        String [] Data = {"/system/bin/cat", "/proc/cpuinfo"};
        InputStream is;
        Process process;
        byte[] bArray;
        bArray = new byte[1024];
        for(int i=0; i<Data.length; i++) {
            System.out.print("Data is " + Data[i]);
        }
        try {
            pb = new ProcessBuilder(Data);
            process = pb.start();
            is = process.getInputStream();
            while(is.read(bArray) != -1) {
                cpuDetails = cpuDetails + new String(bArray);
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(cpuDetails);
        return cpuDetails;
    }

    public String getRAM() {
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        long totalMemory = mi.totalMem;
        long availMemory = mi.availMem;
        double inMB = availMemory / 1048576;

        System.out.println("availableMegs"+ totalMemory);
        return String.valueOf(inMB);
    }
}
