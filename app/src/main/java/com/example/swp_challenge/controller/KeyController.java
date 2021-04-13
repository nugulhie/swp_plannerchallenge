package com.example.swp_challenge.controller;
import com.example.swp_challenge.controller.UserContorller;
public class KeyController {
    UserContorller user = new UserContorller();
    boolean checkKey() {
        if (user.cnt_key > 0) {
            return true;
        } else {
            return false;
        }
    } // 사용자가 열쇠를 가지고 있는지 확인하는 함수

    void getKey(int hint) {
        switch (hint) {
            case 1:
                user.cnt_key++;
                break;
            case 2:
                user.cnt_key = user.cnt_key + 2;
                break;
            case 3:
                user.cnt_key = user.cnt_key + 3;
        }
    } // 열쇠를 얻는 함수
}
