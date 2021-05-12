package com.example.radiobtnandgroup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup rgBtnYesNo,rgBtnAgra;
    RadioButton radioButton1,radioButton2;
    Button button;

    String y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgBtnAgra = findViewById(R.id.rgBtnAgra);
        rgBtnYesNo = findViewById(R.id.rgBtn);
        button = findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getReasonEatableAns();


            }
        });

    }

    private void getReasonEatableAns() {

        int id1 = rgBtnAgra.getCheckedRadioButtonId();
        radioButton1 = findViewById(id1);

        int id2 = rgBtnYesNo.getCheckedRadioButtonId();
        radioButton2 = findViewById(id2);

        y1 = radioButton1.getText().toString();
        y2 = radioButton2.getText().toString();

        Toast.makeText(getApplicationContext(),"Your button1 is: "+radioButton1.getText().toString() +
                "\nYour button2 is: "+radioButton2.getText().toString(),Toast.LENGTH_SHORT).show();

    }
}