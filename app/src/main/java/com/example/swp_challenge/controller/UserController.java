package com.example.swp_challenge.controller;


import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.swp_challenge.MainActivity;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;
import android.*;
import com.example.swp_challenge.controller.PlannerController;

public class UserController{
    private static final UserController user = new UserController();
    public static UserController getInstance(){
        return user;
    }
    PlannerController plan = PlannerController.getInstance();
    ChallengeController challenge =ChallengeController.getInstance();
    private String userName;
    private int cnt_key = 0;
    private int boxRank;
    private float getChance = 0.6f;
    private int boxOpen=0;
    private boolean[] hasAchivement = new boolean[100];

    public int getCnt_key() {
        return cnt_key;
    }

    public int getBoxRank() {
        return boxRank;
    }

    public float getChance() {
        return getChance;
    }

    public boolean getAchivement(int i) {
        return hasAchivement[i];
    }

    public String getUserName() {
        return userName;
    }

    public int getBoxOpen(){return boxOpen;}

    //여기서 부터 값 넣는 함수
    public void setCnt_key(int value) {
        this.cnt_key = value;
    }

    public void setBoxRank(int value) {
        this.boxRank = value;
    }

    public void setChance(float chance) {
        this.getChance = chance;
    }

    public void sethasachivement(int i, boolean flag) {
        this.hasAchivement[i] = flag;
    }

    public void setBoxOpen(int value){this.boxOpen = value;}

    public void giveAchivement(int i) {
        this.hasAchivement[i] = true;
    }

    float checkRank() { // 계급을 확인후 확률 부여
        switch (boxRank) {
            case 1:
                return 0.6f;
            case 2:
                return 0.5f;
            case 3:
                return 0.5f;
            case 4:
                return 0.5f;
            case 5:
                return 0.4f;
            case 6:
                return 0.4f;
            case 7:
                return 0.3f;
            case 8:
                return 0.3f;
            default:
                return 0.6f;
        }

    } //사용자의 상자의 계급을 확인하는 함수


}
