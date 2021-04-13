 package com.example.swp_challenge;

public class BoxKey_Controller {
    static int cnt_key;
    double chance;
    int rank;

    double checkRank(int boxRank) { // 계급을 확인후 확률 부여
            switch (boxRank) {
                case 1:
                    return 0.6;
                case 2:
                    return 0.5;
                case 3:
                    return 0.5;
                case 4:
                    return 0.5;
                case 5:
                    return 0.4;
                case 6:
                    return 0.4;
                case 7:
                    return 0.3;
                case 8:
                    return 0.3;
                default:
                    return 0.6;
            }

        } //사용자의 상자의 계급을 확인하는 함수

    void getReward() {
            chance = checkRank(rank);
            int a = (int) Math.random() * 100 + 1;
            if (1 <= a && a <= chance * 100) {
                getKey(1);
            } else if (chance * 100 < a && a <= chance * 100 + 30) {
                UserAchivementControll.giveAchivements();
            } else if (chance * 100 + 30 < a && a <= chance * 100 + 30 + ((100 - (chance * 100 + 30)) / 2)) {
                getKey(2);
            } else {
                getKey(3);
            }
        } // 보상을 얻는 함수

    void boxOpen() {
        if (checkKey(cnt_key)) {
            getReward();
            cnt_key--;
        }
    } // 상자 여는 함수

    boolean checkKey(int cnt_key) {
        if (cnt_key > 0) {
            return true;
        } else {
            return false;
        }
    } // 사용자가 열쇠를 가지고 있는지 확인하는 함수

    void getKey(int hint) {
        switch (hint) {
            case 1:
                cnt_key++;
                break;
            case 2:
                cnt_key = cnt_key + 2;
                break;
            case 3:
                cnt_key = cnt_key + 3;
        }
    } // 열쇠를 얻는 함수


}

class UserAchivementControll { //사용자 칭호 메소드 개발중
    static String[] achive = new String[100];

    static void giveAchivements(){} //사용자에게 칭호를 주는 함수

    void getUserAchivements(int userSelect_Achive_){ //사용자가 선택한 칭호의 number로 칭호를 선택한 후에 인텐트로 보내준다.
    } // 사용자가 가지고 있는 칭호를 가져오는 함수

    void setUserAchivements(String s){
        //Intent intent = new Intent(getApplicationContext(), MainActivity.class); //intent로 칭호값을 넘기는 메소드
    } // 시스템에 칭호 데이터를 주는 함수

    void checkAchivements(String achiveName){
        for(int i =100;i<100;i++){
            if(achive[i] == achiveName){
                //가지고있다.
            }
        }
    } // 사용자가 가지고 있는 칭호를 확인하는 함수
}
//test
