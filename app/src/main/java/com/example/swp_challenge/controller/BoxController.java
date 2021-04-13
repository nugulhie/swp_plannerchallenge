 package com.example.swp_challenge.controller;

import com.example.swp_challenge.controller.UserContorller;
import com.example.swp_challenge.controller.KeyController;
import com.example.swp_challenge.controller.UserAchivementController;

 public class BoxController {
    UserContorller user = new UserContorller();
    KeyController key = new KeyController();
    UserAchivementController AC = new UserAchivementController();

    float checkRank() { // 계급을 확인후 확률 부여
            switch (user.boxRank) {
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

    void getReward() {
            user.getChance = checkRank();
            int a = (int) Math.random() * 100 + 1;
            if (1 <= a && a <= user.getChance * 100) {
                key.getKey(1);
            } else if (user.getChance * 100 < a && a <= user.getChance * 100 + 30) {
                AC.giveAchivements();
            } else if (user.getChance * 100 + 30 < a && a <= user.getChance * 100 + 30 + ((100 - (user.getChance * 100 + 30)) / 2)) {
                key.getKey(2);
            } else {
                key.getKey(3);
            }
        } // 보상을 얻는 함수

    void boxOpen() {
        if (key.checkKey()) {
            getReward();
            user.cnt_key--;
        }
    } // 상자 여는 함수
}

