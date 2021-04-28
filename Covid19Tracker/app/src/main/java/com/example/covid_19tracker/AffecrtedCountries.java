package com.example.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffecrtedCountries extends AppCompatActivity {

    EditText edtSearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;

    public static List<CountryModel> countryModelList = new ArrayList<>();
    CountryModel countryModel;
    myCustomAdapter myCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affecrted_countries);

        edtSearch = findViewById(R.id.edtSearch);
        listView = findViewById(R.id.listView);
        simpleArcLoader = findViewById(R.id.loader);

        getSupportActionBar().setTitle("Affected Countries"); //for Back Icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //for Back Icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //for Back Icon


        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("position",i);
                startActivity(intent);
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                myCustomAdapter.getFilter().filter(charSequence);
                myCustomAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //for Back Icon
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

        String Url = "https://disease.sh/v3/covid-19/countries/";

        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i =0;i<jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String countryName = jsonObject.getString("country");
                        String cases = jsonObject.getString("cases");
                        String todayCases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todayDeaths = jsonObject.getString("todayDeaths");
                        String recovered = jsonObject.getString("recovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");
                        String population = jsonObject.getString("population");

                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flagUrl = object.getString("flag");

                        countryModel = new CountryModel(flagUrl,countryName,cases,todayCases,deaths,todayDeaths,recovered,active,critical,population);
                        countryModelList.add(countryModel);
                        Log.d("shubham",countryModel.toString());

                    }

                    myCustomAdapter = new myCustomAdapter(AffecrtedCountries.this,countryModelList);
                    listView.setAdapter(myCustomAdapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}