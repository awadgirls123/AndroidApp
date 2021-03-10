package com.example.clv4;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.gms.maps.SupportMapFragment;

import org.w3c.dom.Text;

import java.util.List;

public class ContactMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    final int PERMISSION_REQUEST_LOCATION = 101;
    GoogleMap gMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
//    LocationManager locationManager;
//    LocationListener gpsListener;
//    LocationListener networkListener;
    Location currentBestLocation;
//    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        createLocationRequest();
        createLocationCallback();
        startLocationUpdates();
        stopLocationUpdates();
        initListButton();
        initMapButton();
        initSettingsButton();
        initGetLocationButton();
        isBetterLocation(location);
    }


    private void createLocationRequest(){
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback(){
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                if(locationResult == null){
                    return;
                }
                for(Location location : locationResult.getLocations()){
                    Toast.makeText(getBaseContext(), "Lat:" + location.getLatitude() + " Long: " + location.getLongitude() + " Accuracy: " + location.getAccuracy(), Toast.LENGTH_LONG).show();
                }
            };
        };
    }



    private void startLocationUpdates() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            {
            return ;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            gMap.setMyLocationEnabled(true);
    }



    private void initListButton(){
        ImageButton ibList = (ImageButton) findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(view -> {
            Intent intent = new Intent(ContactMapActivity.this, ContactListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }



        //When map button is clicked, this method is activated
    private void initMapButton(){
        ImageButton ibMap = (ImageButton) findViewById(R.id.imageButtonMap);
        ibMap.setOnClickListener(view -> {
            Intent intent = new Intent(ContactMapActivity.this, ContactMapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    //When setting button is clicked, this method is activated
    private void initSettingsButton(){
        ImageButton ibSettings = (ImageButton) findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener((View view) -> {
            Intent intent = new Intent(ContactMapActivity.this, ContactSettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private void initGetLocationButton() {
        Button location = (Button) findViewById(R.id.buttonGetLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    locationManager = (LocationManager)getBaseContext().getSystemService(Context.LOCATION_SERVICE);
                    gpsListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {

                        }
                        public void onStatusChanged(String provider, int status, Bundle extra){

                        }
                        public void onProviderEnabled(String provider){

                        }
                        public void onProviderDisabled(String provider){

                        }
                    };
                    networkListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {

                        }
                        public void onStatusChanged(String provider, int status, Bundle extra){

                        }
                        public void onProviderEnabled(String provider){

                        }
                        public void onProviderDisabled(String provider){

                        }
                    };
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, gpsListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0, networkListener);


                }
                catch(Exception e){
                    Toast.makeText(getBaseContext(),"Error, Location not available",Toast.LENGTH_LONG).show();
                }



                try {
                    if(Build.VERSION.SDK_INT >= 23){
                        if (ContextCompat.checkSelfPermission(ContactMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(ContactMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                            Snackbar.make(findViewById(R.id.activity_contact_map),"MyContactList reuires this permission to locate" + "your contacts", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(ContactMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                                        }
                                    })
                                    .show();
                                 }else {
                                ActivityCompat.requestPermissions(ContactMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
                            }
                            } else{
                            startLocationUpdates();}
                        }else{
                        startLocationUpdates();
                    }


                }catch(Exception e){
                    Toast.makeText(getBaseContext(), "Error requesting permission", Toast.LENGTH_LONG).show();
                }

                }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        try{
            locationManager.removeUpdates(gpsListener);
            locationManager.removeUpdates(networkListener);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case PERMISSION_REQUEST_LOCATION:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startLocationUpdates();
                }
                else{
                    Toast.makeText(ContactMapActivity.this, "MyContactList will not locate your contacts.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean isBetterLocation(Location location){
        boolean isBetter = false;
        if(currentBestLocation == null){
            isBetter = true;
        }
        else if(location.getAccuracy() <= currentBestLocation.getAccuracy()){
            isBetter = true;
        }
        else if(location.getTime() - currentBestLocation.getTime() > 5*60*1000){
            isBetter = true;
        }
        return isBetter;
    }


}
