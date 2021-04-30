package com.example.swp_challenge.controller;
public class UserAchivementController {
    private static final UserAchivementController achive = new UserAchivementController();
    public static UserAchivementController getInstance(){
        return achive;
    }

    void initAchivement(){
        String[] achs = new String[10];
        achs[0] = "뉴비";
        achs[1] = "드디어 첫 상자!";
        achs[2] = "이제 적응했음!";
        achs[3] = "난 게을러";
        achs[4] = "뭐하는거지?";
        achs[5] = "최고의 계획가";
        achs[6] = "얼마나 더 가야할까";
        achs[7] = "조금만 더";
        achs[8] = "첫 승급!";
        achs[9] = "여기까지는 기본!";
        //1~ 30
    }

    void giveAchivements(UserController user, int i){
        user.giveAchivement(i);
    } //사용자에게 칭호를 주는 함수

    void getUserAchivements(int userSelect_Achive_){ //사용자가 선택한 칭호의 number로 칭호를 선택한 후에 인텐트로 보내준다.


    } // 사용자가 가지고 있는 칭호를 가져오는 함수

    void setUserAchivements(){

    } // 시스템에 칭호 데이터를 주는 함수

    void checkAchivements(UserController user, int achiveNumber){
        for(int i =100;i<100;i++){
            if(user.getAchivement(i)){ //boolean값을 이용해서 if문 진입
                if(i == achiveNumber){ //가지고있음

                }
            }
        }
    } // 사용자가 가지고 있는 칭호를 확인하는 함수
}//시스템에 호출할때 user -> 정수값 / 정수값을