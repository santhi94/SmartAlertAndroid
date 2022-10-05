package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity implements SensorEventListener{
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
                return;
            }else {
                send_sms(preferences.getString("contacts", "no value yet"), message1);
            }
        }
    }

    SensorManager sensorManager;
    Timestamp timestamp;
    Sensor sensor;
    TextView textView,textView2,textView5,textView6;
    MediaPlayer player;
    Boolean timer_running;
    Boolean flag=false;
    Boolean flag2=false;
    private static final int SMS_REQ_CODE=123;
    EditText editext2;
    SharedPreferences preferences;
    String longi,lat;
    String message1;
    String message2="False alarm!!I am ok ";
    CountDownTimer countDownTimer;
    FirebaseDatabase database;
    DatabaseReference myref1,myref2;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView=findViewById(R.id.textView2);
        textView2=findViewById(R.id.textView3);
        textView5= findViewById(R.id.textView5);
        textView5.setText(R.string.safe);
        textView6= findViewById(R.id.textView6);
        textView6.setText(R.string.safe2);
        player=MediaPlayer.create(this,R.raw.count);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor,0);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        database = FirebaseDatabase.getInstance("https://my-application2-63e09-default-rtdb.firebaseio.com/");
        myref1 = database.getReference("messages");
        myref2 = database.getReference("messages");



        Intent intent = getIntent();
        longi = intent.getStringExtra("longi");
        lat = intent.getStringExtra("lat");



        message1="I am at longtitude" +longi + " " +"and latitude " +lat +" " +"and i have fell";






    }


    @Override
    public void onSensorChanged(SensorEvent sensor) {
        if(sensor.values[0]==0 && sensor.values[1]==0 &&sensor.values[2]==0) {
            timer();
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void timer() {
        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            int count = 30;

            @Override
            public void onTick(long millisUntilFinished) {
                count--;

                textView.setText(String.valueOf(count));
                timer_running = true;

            }

            @Override
            public void onFinish() {
                timer_running = false;
                if (!flag) {
                    checking();


                }


            }

        };
        textView.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        countDownTimer.start();
        player.start();
        if(flag2 && flag){
            player.stop();
            countDownTimer.cancel();
            textView.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);
        }



    }
    public void checking() {


            send_sms(preferences.getString("contacts", "no value yet"), message1);
            myref1.push().setValue(message1);
            flag = true;

        }


    public void abort(View view){
        if(!timer_running) {
            send_sms(preferences.getString("contacts", "no value yet"), message2);
            Toast.makeText(this, "SMS message is sent", Toast.LENGTH_LONG).show();
            myref2.push().setValue(message2);
        }else{
            flag2=true;
            flag=true;
            timer();



        }

    }

   /* private void send_sms(String recipient, String message){
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(recipient,null,message,null,null);
        Toast.makeText(this,"SMS message is sent",Toast.LENGTH_LONG).show();
    } */

   public void send_sms(String recipient,String message){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_REQ_CODE);
        }else{

            SmsManager manager=SmsManager.getDefault();
            manager.sendTextMessage(recipient,null,message,null,null);

            Toast.makeText(this,"SMS message is sent",Toast.LENGTH_LONG).show();
        }
    }

    public void next(View view){
        Intent intent =new Intent(this,MainActivity4.class);
        intent.putExtra("longi",longi);
        intent.putExtra("lat",lat);
        startActivity(intent);
    }









}