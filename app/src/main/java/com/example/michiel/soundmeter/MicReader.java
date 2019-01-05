package com.example.michiel.soundmeter;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.ImageView;

public class MicReader extends Activity {

    private static final int sampleRate = 8000;
    private AudioRecord audio;
    private int bufferSize;
    public double lastLevel = 0;
    private Thread thread;
    private static final int SAMPLE_DELAY = 75;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            bufferSize = AudioRecord
                    .getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO,
                            AudioFormat.ENCODING_PCM_16BIT);
        } catch (Exception e) {
            android.util.Log.e("TrackingFlow", "Exception", e);
        }
    }

    protected void onResume() {
        super.onResume();
        audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize);

        audio.startRecording();
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
                            }else
                            if(lastLevel > 50 && lastLevel <= 100){
                                //toast message?
                            }else
                            if(lastLevel > 100 && lastLevel <= 170){
                                //toast message?
                            }
                            if(lastLevel > 170){
                                //toast message?
                            }
                        }
                    });
                }
            }
        });
        thread.start();
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

}
