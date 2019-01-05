package com.example.michiel.soundmeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
ToggleButton onOff;
TextView Tscore;
TextView TmaxScore;
ProgressBar progressBar;
int scoreValue;
int maxScoreValue;
MicReader micReader;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        micReader = new MicReader();
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Tscore = (TextView)findViewById(R.id.Tscore);
        TmaxScore = (TextView)findViewById(R.id.TmaxScore);
        onOff = (ToggleButton)findViewById(R.id.onOff);
        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    micReader.onResume();
                    scoreValue = (int)micReader.lastLevel;
                    Tscore.setText(Integer.toString(scoreValue));
                    Log.i("micReader",Integer.toString(scoreValue));
                    /*scoreValue = progressBar.getProgress();
                    Tscore.setText(Integer.toString(scoreValue));
                    setMaxScoreValue();*/
                }else{
                    micReader.onPause();
                }
            }
        });
    }

    private void setMaxScoreValue(){
        if(scoreValue > maxScoreValue){
            maxScoreValue = scoreValue;
            TmaxScore.setText(Integer.toString(maxScoreValue));
        }
    }
}
