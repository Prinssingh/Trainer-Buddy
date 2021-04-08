package com.example.forkifydemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class showUser extends AppCompatActivity {

    private TextView textViewResult;
    private RequestQueue requestQueue;
    private Button clickBtn,dataWithImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        textViewResult = findViewById(R.id.text_view_result);
        clickBtn = findViewById(R.id.clickBtn);
        dataWithImg = findViewById(R.id.dataWithImg);

        requestQueue = Volley.newRequestQueue(this);

        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });

        dataWithImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showUser.this,dataWithImg.class);
                startActivity(intent);
            }
        });

    }

    private void jsonParse() {

        String url = "https://reqres.in/api/users?page=2";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i = 0 ; i<jsonArray.length();i++){
                        JSONObject emp = jsonArray.getJSONObject(i);

                        String firstName = emp.getString("first_name");
                        String lastName = emp.getString("last_name");
                        String email = emp.getString("email");
                        int id = emp.getInt("id");

                        textViewResult.append("Person Name - "+firstName + " "+lastName+"\nAge - "+ String.valueOf(id) +"\nEmail -  "+email + "\n\n");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);

    }


}