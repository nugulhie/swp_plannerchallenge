package com.example.swp_challenge.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.swp_challenge.Activity.MainActivity;

public class KeyController {
    private static final KeyController key = new KeyController();
    public static KeyController getInstance(){
        return key;
    }
    public boolean checkKey(UserController user) {
        if (user.getCnt_key() > 0) {
            return true;
        } else {
            return false;
        }
    } // 사용자가 열쇠를 가지고 있는지 확인하는 함수
//

    public boolean givekey(UserController user, int chall_pass){
        if(3<=chall_pass){
            user.setCnt_key(user.getCnt_key()+1);
            return true;
        }
        else return false;
    }
}
////