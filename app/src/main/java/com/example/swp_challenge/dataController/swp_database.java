package com.example.swp_challenge.dataController;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

//
public final class swp_database {
    private swp_database(){}
    public static final class UserDB implements BaseColumns { //사용자 데이터베이스
        public static final String TABLE_NAME = "UserDataBase";
        public static final String USER_KEY = "USER_KEY";
        public static final String USER_NAME = "USER_NAME";
        public static final String BIRTH = "BIRTH";
        public static final String BOX_RANK = "BOX_RANK";
        public static final String BOX_OPEN_CNT = "BOX_OPEN_CNT";
        public static final String SQL_CREATE =
                "CREATE TABLE "+ UserDB.TABLE_NAME+ " ("
                +UserDB.USER_NAME+ " TEXT,"
                +UserDB._ID+ " INTEGER PRIMARY KEY,"
                +UserDB.USER_KEY+" INTEGER DEFAULT 0,"
                +UserDB.BIRTH+" TEXT,"
                +UserDB.BOX_OPEN_CNT+" INTEGER DEFAULT 0,"
                +UserDB.BOX_RANK+" INTEGER)";
        public static final String SQL_DELETE =
                "DROP TABLE IF EXISTS " + UserDB.TABLE_NAME;
    }

    public static final class PlanDB implements BaseColumns { // 일정 데이터베이스
        public static final String TABLE_NAME = "PlanDataBase";
        public static final String PLAN_ID = "PLAN_ID";
        public static final String PLAN_CONTENTS = "PLAN_CONTENTS";
        public static final String PLAN_CATEGORY = "PLAN_CATEGORY";
        public static final String PLAN_DATE = "PLAN_DATE";
        public static final String PLAN_YEAR = "PLAN_YEAR";
        public static final String PLAN_MONTH = "PLAN_MONTH";
        public static final String PLAN_DAY = "PLAN_DAY";
        public static final String SQL_CREATE =
                "CREATE TABLE "+ PlanDB.TABLE_NAME+ " ("
                +PlanDB.PLAN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +PlanDB.PLAN_CONTENTS+" TEXT,"
                +PlanDB.PLAN_CATEGORY+" TEXT,"
                +PlanDB.PLAN_YEAR+" INTEGER,"
                +PlanDB.PLAN_MONTH+" INTEGER,"
                +PlanDB.PLAN_DAY+" INTEGER,"
                +PlanDB.PLAN_DATE+" TEXT)";
        public static final String SQL_DELETE =
                "DROP TABLE IF EXISTS " + PlanDB.TABLE_NAME;
    }

    public static final class ChallengeDB implements BaseColumns { // 도전과제 데이터베이스
        public static final String TABLE_NAME =" ChallengeDataBase";
        public static final String CHALLENGE_ID = "CHALLENGE_ID";
        public static final String CHALLENGE_RATING = "CHALLENGE_RATING";
        public static final String CHALLENGE_CONTENTS = "CHALLENGE_CONTENTS";
        public static final String CHALLENGE_PASS = "CHALLENGE_PASS";
        public static final String CHALLENGE_DATE = "CHALLENGE_DATE";
        public static final String SQL_CREATE =
                "CREATE TABLE "+ ChallengeDB.TABLE_NAME+ " ("
                +ChallengeDB.CHALLENGE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ChallengeDB.CHALLENGE_RATING+ " REAL,"
                +ChallengeDB.CHALLENGE_CONTENTS+" TEXT,"
                +ChallengeDB.CHALLENGE_PASS+" INTEGER,"
                +ChallengeDB.CHALLENGE_DATE+" TEXT)";
        public static final String SQL_DELETE =
                "DROP TABLE IF EXISTS " + ChallengeDB.TABLE_NAME;
    }

}
//