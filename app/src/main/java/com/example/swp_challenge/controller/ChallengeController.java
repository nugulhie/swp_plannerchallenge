package com.example.swp_challenge.controller;

import android.app.Application;
import java.util.Calendar;
import java.util.Date;

public class ChallengeController {
    private static final ChallengeController challenge = new ChallengeController();
    public static ChallengeController getInstance(){
        return challenge;
    }
    private int chall_id = 0;
    private float rating;
    private String contents;
    private boolean chall_pass;
    private Date date;

    public void setChallenge(float rating, String contents, boolean chall_pass){
        this.chall_id++;
        this.rating = rating;
        this.contents = contents;
        this.chall_pass = false;
        this.date = Calendar.getInstance().getTime();
    }
    public int getChall_id(){
        return this.chall_id;
    }
    public float getRating(){
        return this.rating;
    }
    public String getContents(){
        return this.contents;
    }
    public boolean getChall_pass(){
        return this.chall_pass;
    }
    public Date getDate(){
        return this.date;
    }

}
//    public static final class challengeDB implements BaseColumns{ // 도전과제 데이터베이스
//        public static final String chall_id = "chall_id";
//        public static final String chall_rating = "chall_rating";
//        public static final String chall_contents = "chall_contents";
//        public static final String chall_pass = "chall_pass";
//        public static final String chall_date = "chall_date";
//        public static final String _TABLENAME2 = "Challenge";
//        public static final String _CREATE2 = "create table if not exist "+_TABLENAME2+"("
//                +chall_id+" integer"
//                +chall_rating+" double"
//                +chall_contents+" text"
//                +chall_pass+" boolean"
//                +chall_date+" timestamp);";
//    }