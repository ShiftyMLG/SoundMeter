package com.example.michiel.soundmeter;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private static final int sampleRate = 8000;
    private AudioRecord audio;
    private int bufferSize;
    private double lastLevel = 0;
    private Thread thread;
    private static final int SAMPLE_DELAY = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Tscore = (TextView)findViewById(R.id.Tscore);
        TmaxScore = (TextView)findViewById(R.id.TmaxScore);
        onOff = (ToggleButton)findViewById(R.id.onOff);
        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    onResume();
                }else{
                    onPause();
                    Tscore.setText("score");
                }
            }
        });
        try {
            bufferSize = AudioRecord
                    .getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO,
                            AudioFormat.ENCODING_PCM_16BIT);
        } catch (Exception e) {
            android.util.Log.e("TrackingFlow", "Exception", e);
        }
    }

    private void setMaxScoreValue(){
        if(scoreValue > maxScoreValue){
            maxScoreValue = scoreValue;
            TmaxScore.setText(Integer.toString(maxScoreValue));
        }
    }

    protected void onResume() {
        super.onResume();
        audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        Log.i("micReader", "in onResume()");
        audio.startRecording();
        Log.i("micReader", "audio.startRecording()");
        thread = new Thread(new Runnable() {
            public void run() {
                while(thread != null && !thread.isInterrupted()){
                    //Let's make the thread sleep for a the approximate sampling time
                    try{Thread.sleep(SAMPLE_DELAY);}catch(InterruptedException ie){ie.printStackTrace();}
                    readAudioBuffer();//After this call we can get the last value assigned to the lastLevel variable

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if(lastLevel > 0 && lastLevel <= 50){
                                //toast message?
                                //Log.i("micReader","low value");
                            }else
                            if(lastLevel > 50 && lastLevel <= 100){
                                //toast message?
                                //Log.i("micReader","medium value");
                            }else
                            if(lastLevel > 100 && lastLevel <= 170){
                                //toast message?
                                //Log.i("micReader","high value");
                            }
                            if(lastLevel > 170){
                                //toast message?
                               // Log.i("micReader","insane value");
                            }
                        }
                    });
                }
            }
        });
        thread.start();
        Log.i("micReader","thread.start");
    }

    /**
     * Functionality that gets the sound level out of the sample
     */
    private void readAudioBuffer() {

        try {
            short[] buffer = new short[bufferSize];

            int bufferReadResult = 1;

            if (audio != null) {

                // Sense the voice...
                bufferReadResult = audio.read(buffer, 0, bufferSize);
                double sumLevel = 0;
                for (int i = 0; i < bufferReadResult; i++) {
                    sumLevel += buffer[i];
                }
                lastLevel = Math.abs((sumLevel / bufferReadResult));
                scoreValue = (int)lastLevel;
                Tscore.setText(Integer.toString(scoreValue));
                progressBar.setProgress(scoreValue);

               // Log.i("micReader",Integer.toString(scoreValue));
                setMaxScoreValue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        thread.interrupt();
        thread = null;
        try {
            if (audio != null) {
                audio.stop();
                audio.release();
                audio = null;
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public void OpenLeaderboard(View view) {
        Log.d("TESTING", "Leaderboard button clicked!");
        Intent intent = new Intent(this, ScoreScreen.class);
        startActivity(intent);
    }
}
