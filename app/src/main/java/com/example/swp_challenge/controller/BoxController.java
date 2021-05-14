 package com.example.swp_challenge.controller;

 import android.util.Log;
 import android.widget.Toast;
//
 public class BoxController {
     private static final BoxController box = new BoxController();
     public static BoxController getInstance(){
         return box;
     } //싱글톤 작업

     KeyController key =  KeyController.getInstance();
     UserAchivementController AC =  UserAchivementController.getInstance();


     public void getReward(UserController user) {

         user.setChance(user.checkRank());
         int a = (int)(Math.random() * 100 + 1);
         int randomAchive = (int)(Math.random()*100);
         if (1 <= a && a <= user.getChance() * 100) {
             key.getKey(user, 1);
             Log.d("box123123123", "getReward: 1");
         } else if (user.getChance() * 100 < a && a <= user.getChance() * 100 + 30) {
             AC.giveAchivements(user,randomAchive);
             Log.d("box123123123", "getReward: achive");
         } else if (user.getChance() * 100 + 30 < a && a <= user.getChance() * 100 + 30 + ((100 - (user.getChance() * 100 + 30)) / 2)) {
             key.getKey(user,2);
             Log.d("box123123123", "getReward: 2");
         } else {
             key.getKey(user,3);
             Log.d("box123123123", "getReward: 3");
         }

     } // 보상을 얻는 함수

     public void boxOpen(UserController user) {
             getReward(user);
             user.setCnt_key(user.getCnt_key() - 1);
             user.setBoxOpen(user.getBoxOpen()+1);
             Log.d("159753", "boxOpen: "+user.getCnt_key());
     } // 상자 여는 함수

     public void boxOpenCount(UserController user){
        if (user.getBoxOpen()<10){
            user.setBoxRank(1);
        }
        else if (10<=user.getBoxOpen()&& user.getBoxOpen()<20){
            user.setBoxOpen(2);
        }
        else if(20<=user.getBoxOpen()&&user.getBoxOpen()<30){
            user.setBoxOpen(3);
        }
     }
 }

//
