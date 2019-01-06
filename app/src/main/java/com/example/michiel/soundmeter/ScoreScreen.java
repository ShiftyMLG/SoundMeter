package com.example.michiel.soundmeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class ScoreScreen extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        final ListView lv = (ListView) findViewById(R.id.leaderboardList);

        // Initializing a new String Array
        String[] fruits = new String[] {
                "Jonas van Hoof",
                "Tom Meyers"
        };

        final List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, fruits_list);

        lv.setAdapter(arrayAdapter);

        fruits_list.add("Michiel Meurs");
    }

    public void openMainActivity(View view) {
        Log.d("TESTING", "Back button clicked!");
        Intent backIntent = new Intent(this, MainActivity.class);
        startActivity(backIntent);
    }
}


