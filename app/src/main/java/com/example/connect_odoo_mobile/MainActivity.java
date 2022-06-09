package com.example.connect_odoo_mobile;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mapping view
        mapping();
        //get uid
        getUid();
    }

    private void getUid() {
        intent = getIntent();
        uid = intent.getIntExtra("uid",-1);
        Log.d(TAG, "getUid: " +uid);
    }

    private void mapping() {
    }
}