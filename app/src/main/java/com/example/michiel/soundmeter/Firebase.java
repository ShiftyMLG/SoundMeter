package com.example.michiel.soundmeter;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = database.getReference();

    public Firebase(){

    }


    public void set(){
        dataRef.setValue("hello world");
    }
}
