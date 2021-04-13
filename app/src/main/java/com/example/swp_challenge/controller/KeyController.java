package com.example.swp_challenge.controller;

public class KeyController {

    UserController user = new UserController();

    boolean checkKey() {
        if (user.getCnt_key() > 0) {
            return true;
        } else {
            return false;
        }
    } // 사용자가 열쇠를 가지고 있는지 확인하는 함수


    void getKey(int hint) {
        user.setCnt_key(user.getCnt_key()+hint);
    } // 열쇠를 얻는 함수
}
