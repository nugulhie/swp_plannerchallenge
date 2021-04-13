package com.example.swp_challenge.controller;
public class UserAchivementController {

    UserController user = new UserController();

    void giveAchivements(){} //사용자에게 칭호를 주는 함수

    void getUserAchivements(int userSelect_Achive_){ //사용자가 선택한 칭호의 number로 칭호를 선택한 후에 인텐트로 보내준다.


    } // 사용자가 가지고 있는 칭호를 가져오는 함수

    void setUserAchivements(){

    } // 시스템에 칭호 데이터를 주는 함수

    void checkAchivements(int achiveNumber){
        for(int i =100;i<100;i++){
            if(user.getAchivement(i)){ //boolean값을 이용해서 if문 진입
                if(i == achiveNumber){ //가지고있음

                }
            }
        }
    } // 사용자가 가지고 있는 칭호를 확인하는 함수
}
//test
