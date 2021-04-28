package com.example.firstuiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class homePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toast.makeText(getApplicationContext(),"Aur bhai aa gya swaad",Toast.LENGTH_LONG).show();


    }
}