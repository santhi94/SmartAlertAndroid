package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity5 extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    Double longitude;
    Double latitude;
    String lat,longi;
    EditText editext;
    SharedPreferences preferences;
    TextView textView;
    String contact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        editext=findViewById(R.id.editTextPhone3);
        textView=findViewById(R.id.textView9);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }



    }

    public void getLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,this);
            Toast.makeText(this,"Congrats!We have locate you ",Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        longitude=Double.parseDouble(new DecimalFormat("##.####").format(longitude));
        latitude=Double.parseDouble(new DecimalFormat("##.####").format(latitude));


        longi=longitude.toString();
        lat=latitude.toString();




    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public void add_close(View view) {

        contact = editext.getText().toString();
        if (contact.matches("")) {
            Toast.makeText(this, "You did not enter a contact", Toast.LENGTH_SHORT).show();
            return;
        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("contacts", editext.getText().toString());
            editor.apply();
            Toast.makeText(this, "Contact added successfulyy", Toast.LENGTH_SHORT).show();
        }
    }

    public void next(View view){
        Intent intent =new Intent(this,MainActivity2.class);
        intent.putExtra("longi",longi);
        intent.putExtra("lat",lat);

        startActivity(intent);
    }
}