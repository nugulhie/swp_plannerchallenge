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
    private int plan_id = 0;
    private int category;
    private Date date;

    public void setPlan(String content, int category){
        this.planContents = content;
        this.category = category;
        this.date = Calendar.getInstance().getTime();
        this.plan_id++;
    }

    public String getPlanContents(){
        return this.planContents;
    }

    public int getPlan_id(){
        return this.plan_id;
    }

    public int getCategory(){
        return this.category;
    }

    public Date getDate(){
        return this.date;
    }

}
