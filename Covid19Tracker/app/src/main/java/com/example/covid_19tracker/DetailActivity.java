package com.example.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private int positionCountry;
    private TextView tvCountry;
    private TextView tvCases;
    private TextView tvTodayCases;
    private TextView tvCritical;
    private TextView tvActive;
    private TextView tvTotalDeaths;
    private TextView tvTodayDeaths;
    private  TextView tvRecovered;
    private TextView tvPopulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details of "+AffecrtedCountries.countryModelList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //for Back Icon
        getSupportActionBar().setDisplayShowHomeEnabled(true); //for Back Icon



        tvCountry = findViewById(R.id.tvCountry);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvPopulation = findViewById(R.id.tvPopulation);

        tvCountry.setText(AffecrtedCountries.countryModelList.get(positionCountry).getCountry());
        tvCases.setText(AffecrtedCountries.countryModelList.get(positionCountry).getCases());
        tvRecovered.setText(AffecrtedCountries.countryModelList.get(positionCountry).getRecovered());
        tvCritical.setText(AffecrtedCountries.countryModelList.get(positionCountry).getCriticalCases());
        tvActive.setText(AffecrtedCountries.countryModelList.get(positionCountry).getActiveCases());
        tvTodayCases.setText(AffecrtedCountries.countryModelList.get(positionCountry).getTodayCases());
        tvTotalDeaths.setText(AffecrtedCountries.countryModelList.get(positionCountry).getTotalDeaths());
        tvTodayDeaths.setText(AffecrtedCountries.countryModelList.get(positionCountry).getTodayDeaths());
        tvPopulation.setText(AffecrtedCountries.countryModelList.get(positionCountry).getPopulation());



    }

    //for Back Icon
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}