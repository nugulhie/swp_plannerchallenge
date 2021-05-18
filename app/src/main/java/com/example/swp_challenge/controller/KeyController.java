package com.example.swp_challenge.controller;

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

    public void getKey(UserController user, int hint) {
        int cnt_key = user.getCnt_key() + hint;
        user.setCnt_key(cnt_key);
    } // 열쇠를 얻는 함수
    public void givekey(UserController user, int chall_pass){
        if(1==chall_pass){
            user.setCnt_key(user.getCnt_key()+1);
        }
        //Todo ChallengeDB에서 오늘의 도전과제들의 chall_pass를 가져와야함
        /*가져와서 chall_pass */
    }
}
////