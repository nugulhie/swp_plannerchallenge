 package com.example.swp_challenge.controller;

 import android.util.Log;
 import android.widget.Toast;
////
 public class BoxController {
     private static final BoxController box = new BoxController();
     public static BoxController getInstance(){
         return box;
     } //싱글톤 작업

     KeyController key =  KeyController.getInstance();


     public int getReward(UserController user) {
         user.setCnt_key(user.getCnt_key() - 1);
         user.setBoxOpen(user.getBoxOpen() + 1);
         user.setChance(user.checkRank());
         int hint = 0;
         int a = (int)(Math.random() * 100 + 1);
         if (1 <= a && a <= user.getChance() * 100) {
             hint = 0;
             user.setCnt_key(user.getCnt_key()+hint+1);
             Log.d("box123123123", "getReward: 1");
             return hint;
         } else if (user.getChance() * 100 < a && a <= user.getChance() * 100 + 30) {
             hint = 1;
             Log.d("box123123123", "getReward: achive");
             user.setCnt_key(user.getCnt_key());
             return hint;
         } else if (user.getChance() * 100 + 30 < a && a <= user.getChance() * 100 + 30 + ((100 - (user.getChance() * 100 + 30)) / 2)) {
             hint = 2;
             user.setCnt_key(user.getCnt_key()+ hint);
             Log.d("box123123123", "getReward: 2");
             return hint;
         } else {
             hint = 3;
             user.setCnt_key(user.getCnt_key()+hint);
             Log.d("box123123123", "getReward: 3");
             return hint;
         }


     } // 보상을 얻는 함수

     public void boxOpenCount(UserController user){
         int rank = 1;
         int openCount = user.getBoxOpen();
         Log.d("box", "boxOpen: "+user.getBoxOpen());
         Log.d("box", "rank: "+user.getBoxRank());
        if (openCount<10){
            rank = 1;
            user.setBoxRank(rank);
            Log.d("box", "rank: "+user.getBoxRank());
        }
        else if (10<=openCount&& openCount<20){
            rank = 2;
            user.setBoxRank(rank);
            Log.d("box", "rank: "+user.getBoxRank());
        }
        else if(20<=openCount&&openCount<30){
            rank = 3;
            user.setBoxRank(rank);
            Log.d("box", "rank: "+user.getBoxRank());
        }
        else if(30<=openCount&&openCount<40) {
            rank = 4;
            user.setBoxRank(rank);
            Log.d("box", "rank: " + user.getBoxRank());
        }
        else if(40<=openCount&&openCount<50) {
            rank = 5;
            user.setBoxRank(rank);
            Log.d("box", "rank: " + user.getBoxRank());
        }
        else if(60<=openCount&&openCount<70) {
            rank = 6;
            user.setBoxRank(rank);
            Log.d("box", "rank: " + user.getBoxRank());
        }
        else if(70<=openCount&&openCount<80) {
            rank = 7;
            user.setBoxRank(rank);
            Log.d("box", "rank: " + user.getBoxRank());
        }
        else if(80<=openCount) {
            rank = 8;
            user.setBoxRank(rank);
            Log.d("box", "rank: " + user.getBoxRank());
        }
     }
 }

//
