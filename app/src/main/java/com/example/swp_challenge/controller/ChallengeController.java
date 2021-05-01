package com.example.swp_challenge.controller;

import android.app.Application;
import java.util.Calendar;
import java.util.Date;

public class ChallengeController {
    private static final ChallengeController challenge = new ChallengeController();
    public static ChallengeController getInstance(){
        return challenge;
    }
    private float rating;
    private String contents;
    private int chall_pass;
    private Date date;

    public void setChallenge(float rating, String contents){
        this.rating = rating;
        this.contents = contents;
        this.chall_pass = 0;
        this.date = Calendar.getInstance().getTime();
    }
    public float getRating(){
        return this.rating;
    }
    public String getContents(){
        return this.contents;
    }
    public int getChall_pass(){
        return this.chall_pass;
    }
    public Date getDate(){
        return this.date;
    }

}
