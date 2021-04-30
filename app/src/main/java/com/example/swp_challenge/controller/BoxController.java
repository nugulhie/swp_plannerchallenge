 package com.example.swp_challenge.controller;

 public class BoxController {
     private static final BoxController box = new BoxController();
     public static BoxController getInstance(){
         return box;
     } //싱글톤 작업

     KeyController key = new KeyController();
     UserAchivementController AC = new UserAchivementController();


     public void getReward(UserController user) {

         user.setChance(user.checkRank());
         int a = (int)(Math.random() * 100 + 1);
         int randomAchive = (int)(Math.random()*100);
         if (1 <= a && a <= user.getChance() * 100) {
             key.getKey(user, 1);
         } else if (user.getChance() * 100 < a && a <= user.getChance() * 100 + 30) {
             AC.giveAchivements(user,randomAchive);
         } else if (user.getChance() * 100 + 30 < a && a <= user.getChance() * 100 + 30 + ((100 - (user.getChance() * 100 + 30)) / 2)) {
             key.getKey(user,2);
         } else {
             key.getKey(user,3);
         }

     } // 보상을 얻는 함수

     public void boxOpen(UserController user) {
         if (key.checkKey(user)) {
             getReward(user);
             user.setCnt_key(user.getCnt_key() - 1);
             System.out.println(user.getCnt_key());
             System.out.println("키를 사용");
         }
     } // 상자 여는 함수
 }

//
