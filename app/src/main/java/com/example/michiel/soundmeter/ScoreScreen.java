package com.example.michiel.soundmeter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreScreen extends AppCompatActivity {
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> players_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        players_list = new ArrayList<String>();
        readFile();


        final ListView lv = (ListView) findViewById(R.id.leaderboardList);

         arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.list_item, players_list);

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
    private String readFile(){
        File directory = this.getFilesDir();
        File file = new File(directory, MainActivity.filename);
        StringBuilder b = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while((line = br.readLine()) != null){
                int size = 35;
                int space = size - line.toCharArray().length - 1;
                String spacing = "";
                for(int i = 0; i < space; i++)
                    spacing += " ";
                String part1 = line.split(" ")[0];
                String part2 = line.split(" ")[1];
                String total = part1 + spacing+ part2;

                if(!players_list.contains(total)) {
                    players_list.add(total);
                    b.append(total);
                    b.append("\n");
                }
            }
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Log.i("ReadFile", b.toString());
        filterPlayerList();
        return b.toString();
    }

    private void filterPlayerList(){
        for(int i =0; i < players_list.size(); i++){
            for(int j =0; j < players_list.size(); j++){
                if(i != j){
                    String first = replaceMultipleSpaces(players_list.get(i));
                    String second = replaceMultipleSpaces(players_list.get(j));
                    if(first.split(" ")[0].equals(second.split(" ")[0])){
                        int size1 = Integer.parseInt(first.split(" ")[1]);
                        int size2 = Integer.parseInt(second.split(" ")[1]);
                        if(size1 > size2){
                            players_list.remove(j);
                        }else{
                            players_list.remove(i);
                        }
                    }
                }
            }
        }
    }

    private String replaceMultipleSpaces(String in){
        Pattern ptn = Pattern.compile("\\s+");
        Matcher match = ptn.matcher(in);
        return match.replaceAll(" ");

    }

    public void resetBoard(View view) {
        FileOutputStream outputStream;


        try {
            outputStream = openFileOutput(MainActivity.filename, Context.MODE_PRIVATE);
            String str = "";
            outputStream.write(str.getBytes());
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        players_list = new ArrayList<String>();
        final ListView lv = (ListView) findViewById(R.id.leaderboardList);

        arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.list_item, players_list);

        lv.setAdapter(arrayAdapter);
    }
}


