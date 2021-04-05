package com.example.swp_challenge;


public class boxmethod {
    double chance;
    int rank;
    int cntbox;
    boolean checkOpen;
    boolean haskey;

    public boxmethod(boolean haskey, int rank, int cntbox){
        double chance = 0;
        this.rank = rank;
        this.cntbox = cntbox;
        boolean checkOpen = false;
        this.haskey = haskey;
    }
    double checkRank(int boxRank){ // 계급을 확인후 확률 부여
        switch(boxRank){
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

    }
    void getReward(){
        chance=checkRank(rank);
        int a =(int)Math.random()*100+1;
        if(1 <= a && a <= chance*100){
            //열쇠1개
        }
        else if(chance*100 < a && a <= chance*100+30){
            //칭호
        }
        else if(chance*100+30 < a && a <= chance*100 + 30 + ((100-(chance*100+30))/2)){
            //열쇠2개
        }
        else{
            //열쇠3개
        }
    }
    void boxOpen(boolean haskey){
        if(haskey){
            getReward();
        }
    }

}



