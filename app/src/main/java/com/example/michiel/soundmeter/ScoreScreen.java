package com.example.michiel.soundmeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ScoreScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
    }


    public void openMainActivity(View view) {
        Log.d("TESTING", "Back button clicked!");
        Intent backIntent = new Intent(this, MainActivity.class);
        startActivity(backIntent);
    }
}
