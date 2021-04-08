package com.example.forkifydemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class createUser extends AppCompatActivity {

    private RequestQueue requestQueue;
    private EditText first_name, last_name, job;
    private Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        first_name = (EditText)findViewById(R.id.first_name);
        last_name = (EditText)findViewById(R.id.last_name);
        job = (EditText)findViewById(R.id.job);
        createBtn = (Button) findViewById(R.id.createBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String Name = first_name.getText().toString()+" "+last_name.getText().toString();
                String Job = job.getText().toString();

                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("name", Name);
                    jsonObject.put("job", Job);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String URL="https://reqres.in/api/users";

                postData(URL,jsonObject);
            }
        });

    }

    public void postData(String url,JSONObject data){
        RequestQueue requstQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      Log.d("shubham", String.valueOf(response));
                      Toast.makeText(createUser.this, String.valueOf(response), Toast.LENGTH_LONG).show();
                      ClearData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Log.d("shubham", String.valueOf(error));
                    }
                });
        requstQueue.add(jsonobj);

    }

    void ClearData(){
        first_name.setText("");
        last_name.setText("");
        job.setText("");
    }

}