package com.example.michiel.soundmeter;

public class Player {

    private String mPlayerName;
    private int mPlayerScore;

    public Player(String mPlayerName, int mPlayerScore){
        this.mPlayerName = mPlayerName;
        this.mPlayerScore = mPlayerScore;
    }


    public String getName(){
        return this.mPlayerName;
    }

    public void setName(String mPlayerName){
        this.mPlayerName = mPlayerName;
    }

    public int getScore(){
        return this.mPlayerScore;
    }

    public void setmPlayerName(int mPlayerScore){
        this.mPlayerScore= mPlayerScore;
    }
}
