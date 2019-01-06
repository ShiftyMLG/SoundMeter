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

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> players_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        Intent intent = getIntent();
        players_list = intent.getStringArrayListExtra("players");


        final ListView lv = (ListView) findViewById(R.id.leaderboardList);

         arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, players_list);

        lv.setAdapter(arrayAdapter);
    }


    public void openMainActivity(View view) {
        Log.d("TESTING", "Back button clicked!");
        Intent backIntent = new Intent(this, MainActivity.class);
        backIntent.putStringArrayListExtra("players", (ArrayList<String>) players_list );
        startActivity(backIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("players", (ArrayList<String>) players_list );
    }
}


