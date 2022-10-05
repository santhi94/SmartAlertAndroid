package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    EditText editext;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        editext=findViewById(R.id.editTextPhone2);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    }



    public void add_close(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("contacts",editext.getText().toString());
        editor.apply();
        Toast.makeText(this,"contact is sent",Toast.LENGTH_LONG).show();
    }


    public void next(View view){
        Intent intent =new Intent(this,MainActivity5.class);
        startActivity(intent);
    }
}