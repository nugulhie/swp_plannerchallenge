package com.example.swp_challenge.controller;

public class UserContorller {
    String userName;
    int cnt_key = 0;
    boolean[] has_achivement = new boolean[100];
    int planId;
    int challId;
    int boxRank;
    float getChance;

    void give_achivement(int i){
        has_achivement[i] = true;
    }
}
