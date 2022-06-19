package edu.neu.madscourse.numad22su_haowenxue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationRequest;
import android.util.Log;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class LocationConfirmed extends AppCompatActivity implements LocationListener {
    Button btnResetDist;
    TextView textLatitude, textLongtitude, textTotalDistance;
    LocationManager locationManager;
    Switch switchApproximate;
    Boolean switchBolean = false;
    ArrayList<ArrayList<Double>> locHistory = new ArrayList<ArrayList<Double>>(); //store location history {{Longitude, Latitude}, {}....}
    Double totalDistance = 0.0;
    DecimalFormat df = new DecimalFormat("0.000");
    DecimalFormat df8 = new DecimalFormat("0.00000000");


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_confirmed);
        ActionBar actionBar = getSupportActionBar(); // calling the bar
        actionBar.setDisplayHomeAsUpEnabled(true); // showing the bar

        // Starting code here:
        textLatitude   = findViewById(R.id.textLatitude);
        textLongtitude = findViewById(R.id.textLongitude);
        textTotalDistance = findViewById(R.id.textTotalDistance);
        btnResetDist   = findViewById(R.id.btnResetDist);
        switchApproximate = findViewById(R.id.btnApproximate);
        btnResetDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDistance();
            }
        });
        switchApproximate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Log.d("switch:", "Enable approximate");
                    switchBolean = true;
                    totalDistance = 0.0;
                    textTotalDistance.setText("Please disable approximate \nfor accurate distance display!");
                    getLocation();
                }else{
                    switchBolean = false;
                    Log.d("switch:", "Enable precise location");
                    getLocation();
                }
            }
        });

        //get permission
        if(ContextCompat.checkSelfPermission(LocationConfirmed.this, Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(LocationConfirmed.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
        getLocation();

    }
    //if the reset button is clicked distance is reset & location is saved with current locaiton only.
    public void resetDistance(){
        locHistory = new ArrayList<ArrayList<Double>>();
        Double curLatitude= Double.parseDouble((String) textLatitude.getText());
        Double curLongtitude= Double.parseDouble((String) textLongtitude.getText());
        ArrayList<Double> curLatLong = new ArrayList<Double>();
        curLatLong.add(curLatitude);
        curLatLong.add(curLongtitude);
        locHistory.add(curLatLong);
        totalDistance = 0.0;
        if(switchBolean == true) {
            // turn on approximate
            textTotalDistance.setText("Please disable approximate \nfor accurate distance display!");
        } else{
            textTotalDistance.setText("0.0 km");
        }
        printLocationHistory(locHistory);
        if(locHistory.size() ==1){
            Log.d("Clicked: ", "locHistory reseted & total distance cleared!");
        }
    }


    //function for update the location manager(latitude, longtitude)
    @SuppressLint({"MissingPermission", "ServiceCast"})
    private void getLocation(){
        if (ContextCompat.checkSelfPermission(LocationConfirmed.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            this.finish();
            Toast.makeText(this, "Precision location Denied. Please Grant precision location in Setting.", Toast.LENGTH_SHORT).show();
        }
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
//            Criteria criteria = new Criteria();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,3,LocationConfirmed.this);
            Log.d("Try provider list ", ""+ locationManager.getAllProviders());
        } catch (Exception e){
            Log.d("LocationConfirmed ERROR: ", "getLocation Exception ERROR!!!");
            this.finish();
            Toast.makeText(this, "Precision location Denied. Please Grant precision location.", Toast.LENGTH_SHORT).show();
//            Log.d("Exception All provider list ", ""+ locationManager.getAllProviders());
//            LocationRequest locationRequest = new LocationRequest();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//            locationRequest.setInterval(500);
//            Log.d("LocationConfirmed ERROR: ", ""+LocationManager.getAllProviders());
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500,3,LocationConfirmed.this);

        }
    }

    // back button Function
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
//        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        Log.d("Location String:", ""+location.getLatitude()+","+location.getLongitude());
        if(switchBolean == true){
            // turn on approximate location
            textLatitude.setText(""+df8.format(location.getLatitude()*0.999998));
            textLongtitude.setText(""+df8.format(location.getLongitude()*0.999997));
        }else{
            // turn on precise location
            textLatitude.setText(""+df8.format(location.getLatitude()));
            textLongtitude.setText(""+df8.format(location.getLongitude()));
            ArrayList<Double> curLocation = new ArrayList<Double>();
            curLocation.add((Double) location.getLatitude());
            curLocation.add((Double) location.getLongitude());
            Log.d("Location Double:", ""+curLocation.get(0) + ","+ curLocation.get(1));
            locHistory.add(curLocation);
            printLocationHistory(locHistory);
            if (locHistory.size() >= 2) {
                totalDistance = calculateDistance(totalDistance, locHistory.get(locHistory.size() - 1), locHistory.get(locHistory.size() - 2)); //get the last two newly added location points
            } else {
                totalDistance = 0.0;
            }
            textTotalDistance.setText(""+df.format(totalDistance)+" km");
        }

    }

    //use following function to print out the location History points
    public void printLocationHistory(ArrayList<ArrayList<Double>> locHistory){
        for (int i=0; i < locHistory.size(); i++){
            Log.d("locHistory Array List "+i +" :", ""+ locHistory.get(i).get(0) +","+locHistory.get(i).get(1));
        }
    }

    public Double calculateDistance(Double initDistance, ArrayList<Double> pointA, ArrayList<Double> pointB){
        Double curDistance = distanceHelper(pointA.get(0), pointA.get(1), pointB.get(0), pointB.get(1));
        Log.d("Temp Distance: ", ""+curDistance);
        return(curDistance + initDistance);
    }
    //following Distance Calculation Using Latitude And Longitude
    // return with distance in km
    private double distanceHelper(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    /*::  This function converts decimal degrees to radians             :*/
    /*::  This function converts radians to decimal degrees             :*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}