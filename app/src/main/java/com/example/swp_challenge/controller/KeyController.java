package com.example.swp_challenge.controller;

public class KeyController {
    private static final KeyController key = new KeyController();
    public static KeyController getInstance(){
        return key;
    }
    boolean checkKey(UserController user) {
        if (user.getCnt_key() > 0) {
            return true;
        } else {
            return false;
        }
    } // 사용자가 열쇠를 가지고 있는지 확인하는 함수


    void getKey(UserController user, int hint) {
        user.setCnt_key(user.getCnt_key()+hint);
    } // 열쇠를 얻는 함수
}
