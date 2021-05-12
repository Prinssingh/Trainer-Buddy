package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView ans;
    private EditText num1,num2;
    private Button ansBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ans = findViewById(R.id.tfAns);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        ansBtn = findViewById(R.id.ansBtn);

        ansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double a,b,c;
                try{
                    a = Double.parseDouble(num1.getText().toString());
                    b = Double.parseDouble(num2.getText().toString());
                    c=a+b;
                    ans.setText(Double.toString(c));
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Please input number only",Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}