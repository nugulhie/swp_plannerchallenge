package com.example.swp_challenge.controller;


import android.app.Application;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class PlannerController{
    private static final PlannerController plan = new PlannerController();
    public static PlannerController getInstance(){
        return plan;
    }
    private String planContents = new String();
    private String category;
    private Date date;
    private int year;
    private int month;
    private int day;

    public void setPlan(String content, String category, int year, int month, int day){
        this.planContents = content;
        this.category = category;
        this.date = Calendar.getInstance().getTime();
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getPlanContents(){
        return this.planContents;
    }

    public String getCategory(){
        return this.category;
    }

    public Date getDate(){
        return this.date;
    }

}
////