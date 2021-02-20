package com.example.speedmonitor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Permission;

public class MainActivity extends AppCompatActivity implements LocationListener {
LocationManager locManager;
LocationListener li;
int speedvalue;
int MY_PERMISSION=99;
TextView speed, monitor;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speed=findViewById(R.id.myspeed);
        monitor=findViewById(R.id.status);
        speed.setText("00 m/s");monitor.setText("");
        locationPermissions();
        locManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try{
           locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        }catch(SecurityException k){
            Toast.makeText(this,""+k.getMessage(),Toast.LENGTH_LONG).show();
        }
        this.onLocationChanged(null);
    }

    @Override
    public void onLocationChanged(Location location) {
       if(location==null){
        speed.setText("0 m/s");
        }else{
         speedvalue=(int)location.getSpeed();
           if(speedvalue>0&&speedvalue<40){
               speed.setText(speedvalue);
               monitor.setText("This speed is friendly");
           }else if(speedvalue>39&&speedvalue<80){
               speed.setText(speedvalue);
               monitor.setText("This speed is dangerous slow down");
           }else{
               speed.setText(speedvalue);
               monitor.setText("Start singing death hymn and rest in peace");
           }
       }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
       //To do auto generated method
    }

    @Override
    public void onProviderEnabled(String provider) {
// to do auto generated method
    }

    @Override
    public void onProviderDisabled(String provider) {
     //To do auto generated method
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void locationPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PermissionChecker.PERMISSION_GRANTED&&
        (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PermissionChecker.PERMISSION_GRANTED)){
           this.requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},99);
        }
    }
}