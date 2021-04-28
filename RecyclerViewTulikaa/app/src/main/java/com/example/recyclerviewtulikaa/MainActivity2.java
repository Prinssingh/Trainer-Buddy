package com.example.recyclerviewtulikaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rcv = findViewById(R.id.recyclerViewTulika);

        rcv.setLayoutManager(new LinearLayoutManager(this));

        String arr[] = {"shubham","tulika","ankit","sayyam","anurag","tulika","shubham","tulika"};
        rcv.setAdapter(new myAdapter(arr));

    }
}