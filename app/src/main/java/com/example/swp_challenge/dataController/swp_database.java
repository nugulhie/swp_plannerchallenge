package com.example.swp_challenge.dataController;

import android.provider.BaseColumns;

public final class swp_database {

    public static final class UserDB implements BaseColumns { //사용자 데이터베이스
        public static final String user_name = "name";
        public static final String cnt_key = "cnt_key";
        public static final String plan_id = "plan_id";
        public static final String chall_id = "chall_id";
        public static final String box_rank = "box_rank";
        public static final String has_achivement = "has_achivement";
        public static final String birth = "birth";
        public static final String _TABLENAME0 = "User";
        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0+"("
                +user_name+" text, "
                +birth+" timestamp, "
                +cnt_key+" integer, "
                +has_achivement+" array integer"
                +plan_id+" integer, "
                +chall_id+" integer, "
                +box_rank+" integer not null );";

    }
    public static final class PlanDB implements BaseColumns{ // 일정 데이터베이스
        public static final String plan_id = "plan_id";
        public static final String contents = "contents";
        public static final String plan_category = "plan_category";
        public static final String plan_date = "plan_date";
        public static final String _TABLENAME1 = "Plan";
        public static final String _CREATE1 = "create table if not exist " + _TABLENAME1+"("
                +plan_id+" integer, "
                +plan_category+ " integer"
                +contents+" text"
                +plan_date+" timestamp);";
    }
    public static final class challengeDB implements BaseColumns{ // 도전과제 데이터베이스
        public static final String chall_id = "chall_id";
        public static final String chall_rating = "chall_rating";
        public static final String chall_contents = "chall_contents";
        public static final String chall_pass = "chall_pass";
        public static final String chall_date = "chall_date";
        public static final String _TABLENAME2 = "Challenge";
        public static final String _CREATE2 = "create table if not exist "+_TABLENAME2+"("
                +chall_id+" integer"
                +chall_rating+" double"
                +chall_contents+" text"
                +chall_pass+" boolean"
                +chall_date+" timestamp);";
    }
    public static final class achivementDB implements  BaseColumns { // 칭호 데이터베이스
        public static final String achive_id = "achive_id";
        public static final String achive_name = "achive_name";
        public static final String _TABLENAME3 = "Achivement";
        public static final String _CREATE3 = "create table if not exist " + _TABLENAME3 + "("
                + achive_id + " integer"
                + achive_name + " text);";
    }
}

