package com.example.swp_challenge.controller;

import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import com.example.swp_challenge.R;

public class UserAchivementController {
    private static final UserAchivementController achive = new UserAchivementController();

    public static String[] achs = new String[10];
    public static int[] img = new int[10];
    public static int randoms = 0;
    public static UserAchivementController getInstance() {
        achs[0] = "뉴비";
        achs[1] = "드디어 첫 상자!";
        achs[2] = "이제 적응했음!";
        achs[3] = "난 게을러";
        achs[4] = "뭐하는거지?";
        achs[5] = "최고의 계획가";
        achs[6] = "얼마나 더 가야할까";
        achs[7] = "조금만 더";
        achs[8] = "첫 승급!";
        achs[9] = "여기까지는 기본!";
        img[0] = R.drawable.newbi;
        img[1] = R.drawable.firstbox1;
        img[2] = R.drawable.nowvet;
        img[3] = R.drawable.granpa;
        img[4] = R.drawable.whatru;
        img[5] = R.drawable.bestplanner;
        img[6] = R.drawable.howlong;
        img[7] = R.drawable.morego;
        img[8] = R.drawable.firstgrade;
        img[9] = R.drawable.thisismylevel;

        //1~ 30

        return achive;
    }
    public int getImg(int selectAchive){
        return img[selectAchive];
    }


    public int[] giveAchivements(String currentAchivements) {
        int temps[] = new int[currentAchivements.length() / 2 + 1];
        String temp[] = currentAchivements.split("-");
        for (int i = 0; i < temp.length; i++) {
            temps[i] = Integer.parseInt(temp[i]);
        }
        return temps;
    } //사용자에게 칭호를 주는 함수

    public String getAchivements(int userSelect_Achive_) { //사용자가 선택한 칭호의 number로 칭호를 선택한 후에 인텐트로 보내준다.

        return achs[userSelect_Achive_];
    } // 사용자가 가지고 있는 칭호를 가져오는 함수

    public void setrandoms(int i) {
        randoms = i;
        Log.d("achive", "setrandoms: " + i);
    }

    public String rewardAchive() {
        Log.d("achive", "rewardAchive: " + achs[randoms]);
        return achs[randoms];
    }

}////