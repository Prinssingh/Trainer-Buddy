package com.example.findcurrentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private int REQUEST_CODE = 111;
    private ConnectivityManager manager;
    private NetworkInfo networkInfo;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private double selectedLat,selectedLng;
    private List<Address> addresses;
    private String selectedAddress;
    private Button btnRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);

        client = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        btnRoute = findViewById(R.id.btnRoute);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();

        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        }


        btnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "google.navigation:q="+selectedLat+", "+selectedLng+"&mode=d";
                Intent mapIntent = new Intent("android.intent.action,VIEW", Uri.parse(url));
                mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getPackageManager())!=null){
                    startActivity(mapIntent);
                }

                Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

            }
        });


    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>(){
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {

                            mMap = googleMap;

                            LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
                            //for marker option in google maps
                            MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("You are here");

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));

                            googleMap.addMarker(markerOptions).showInfoWindow();

                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(@NonNull LatLng latLng) {
                                    checkConnection();

                                    if(networkInfo.isConnected() && networkInfo.isAvailable()){

                                        selectedLat = latlng.latitude;
                                        selectedLng = latlng.longitude;

                                        GetAddress(selectedLat, selectedLng);

                                        btnRoute.setVisibility(View.VISIBLE);


                                    }else{
                                        Toast.makeText(MainActivity.this,"Please check Internet Connection",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                }
            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
        else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void checkConnection(){
        manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = manager.getActiveNetworkInfo();

    }

    private void GetAddress(double mLat,double mLng){
        geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

        if(mLat !=0){
            try {
                addresses = geocoder.getFromLocation(mLat,mLng,1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(addresses != null){
                //Directly get Address
                String mAddress = addresses.get(0).getAddressLine(0);

                //Custom Address creation
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                String dis = addresses.get(0).getSubAdminArea();

                Toast.makeText(getApplicationContext(),city+","+state+","+country+","+postalCode+","+knownName+","+dis,Toast.LENGTH_LONG).show();

                selectedAddress = mAddress;

                if(mAddress != null){
                    MarkerOptions markerOptions = new MarkerOptions();

                    LatLng latLng = new LatLng(mLat,mLng);

                    markerOptions.position(latLng).title(selectedAddress);

                    //mMap.addMarker(markerOptions).showInfoWindow();
                    Objects.requireNonNull(mMap.addMarker(markerOptions)).showInfoWindow();
                }
                else{
                    Toast.makeText(MainActivity.this,"Somthing went wrong",Toast.LENGTH_LONG).show();
                }

            }
            else{
                Toast.makeText(MainActivity.this,"Somthing went wrong",Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(MainActivity.this,"Latlng null",Toast.LENGTH_LONG).show();
        }

    }


}

