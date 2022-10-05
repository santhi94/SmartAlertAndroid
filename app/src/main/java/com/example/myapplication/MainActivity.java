package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText editext,editextpass;
    FirebaseAuth auth;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editext=findViewById(R.id.editTextTextPersonName2);
        editextpass=findViewById(R.id.editTextTextPassword);
        database = FirebaseDatabase.getInstance("https://my-application2-63e09-default-rtdb.firebaseio.com/");
        auth=FirebaseAuth.getInstance();
        myRef = database.getReference("users");
    }


    public void signup(View view) {
        String email = editext.getText().toString();
        String password = editextpass.getText().toString();
        myRef.setValue(email);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    showmessage("success", "User created successfully");
                    openactivity();
                }else{
                    showmessage("error!",task.getException().getLocalizedMessage());

                }
            }
        });
    }

    public void login(View view){
        String email = editext.getText().toString();
        String password = editextpass.getText().toString();
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    showmessage("Success","User signed in successfully");
                    openactivity();

                }else{
                    showmessage("Error!",task.getException().getLocalizedMessage());
                }

            }
        });


    }

    void showmessage(String title,String message){
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(true).show();
        }

    public void openactivity(){
        Intent intent =new Intent(this,MainActivity5.class);
        startActivity(intent);
    }
}