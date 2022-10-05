package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

public class MainActivity4 extends AppCompatActivity {
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                return;
            } else {
                send_sms("47893574", "hello");
            }
        }
    }

    SharedPreferences preferences;
    FirebaseStorage storage;
    StorageReference myRef;
    int counter;
    String photo_name=String.valueOf(counter);
    private static final int SMS_REQ_CODE=123;
    String logi,lat;
    FirebaseDatabase database;
    DatabaseReference ref4,ref5,ref6;
    String message3,message4,message5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        storage = FirebaseStorage.getInstance();
        myRef = storage.getReference();
        Intent intent = getIntent();
        logi = intent.getStringExtra("longi");
        lat = intent.getStringExtra("lat");
        database = FirebaseDatabase.getInstance("https://my-application2-63e09-default-rtdb.firebaseio.com/");
        ref4 = database.getReference("messages");
        ref5 = database.getReference("messages");
        ref6=database.getReference("messages");


        Random random=new Random();
        counter= random.nextInt();
        photo_name=String.valueOf(counter);
        message3="I am at longitude " + logi +""+"and latitude"+lat+ ""+"and there is a fire";
        message4="I am at longitude " + logi+" "+"and latitude"+lat+""+""+ "and there is a flood";
        message5="I am at longitude " + logi+" "+"and latitude"+lat+""+""+ "and there is a snowfall";


    }

    public void send_sms(String recipient,String message){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_REQ_CODE);
        }else{
            SmsManager manager=SmsManager.getDefault();
            manager.sendTextMessage(recipient,null,message,null,null);
            Toast.makeText(this,"SMS message is sent",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            Bitmap bitmap = (Bitmap) extra.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] bytes = baos.toByteArray();
            myRef.child(photo_name).putBytes(bytes).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Image uploaded",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }




    }


    public void camera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,123);
    }


    public void fire_alarm(View view){
        camera();
        send_sms(preferences.getString("contacts","no value yet"),"I am at longitude " + logi +""+"and latitude"+lat+ ""+"and there is a fire");
        ref4.push().setValue(message3);

    }


    public void flood_alarm(View view){
        camera();
        send_sms(preferences.getString("contacts","no value yet"),"I am at longitude " + logi+" "+"and latitude"+lat+""+""+ "and there is a flood");
        ref5.push().setValue(message4);
    }


    public void snowfall_alarm(View view){
        camera();
        send_sms(preferences.getString("contacts","no value yet"),"I am at longitude " + logi+" "+"and latitude"+lat+""+""+ "and there is a snowfall");
        ref6.push().setValue(message5);
    }


}