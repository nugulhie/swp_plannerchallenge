 package com.example.swp_challenge.controller;

 public class BoxController {

    UserController user = new UserController();
    KeyController key = new KeyController();
    UserAchivementController AC = new UserAchivementController();


    void getReward() {
            user.setChance(user.checkRank());
            int a = (int) Math.random() * 100 + 1;
            if (1 <= a && a <= user.getChance() * 100) {
                key.getKey(1);
            } else if (user.getChance() * 100 < a && a <= user.getChance() * 100 + 30) {
                AC.giveAchivements();
            } else if (user.getChance() * 100 + 30 < a && a <= user.getChance() * 100 + 30 + ((100 - (user.getChance() * 100 + 30)) / 2)) {
                key.getKey(2);
            } else {
                key.getKey(3);
            }
        } // 보상을 얻는 함수

    void boxOpen() {
        if (key.checkKey()) {
            getReward();
            user.setCnt_key(user.getCnt_key()-1);
        }
    } // 상자 여는 함수
}

