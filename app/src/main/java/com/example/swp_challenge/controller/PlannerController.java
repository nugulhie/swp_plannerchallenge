package com.example.swp_challenge.controller;


import android.app.Application;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class PlannerController extends Application {
    private String planContents = new String();
    private int plan_id = 0;
    private int category;
    private Date date;

    public void setPlan(String content, int category){
        this.planContents = content;
        this.category = category;
        this.date = Calendar.getInstance().getTime();
        this.plan_id++;
    }

    String getPlanContents(){
        return this.planContents;
    }

    int getPlan_id(){
        return this.plan_id;
    }

    int getCategory(){
        return this.category;
    }

    Date getDate(){
        return this.date;
    }

}
