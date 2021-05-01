package com.example.swp_challenge.dataController;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class swp_databaseOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "swp_challenge.db";

    public swp_databaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(swp_database.UserDB.SQL_CREATE);
        db.execSQL(swp_database.ChallengeDB.SQL_CREATE);
        db.execSQL(swp_database.PlanDB.SQL_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(swp_database.UserDB.SQL_DELETE);
        db.execSQL(swp_database.PlanDB.SQL_DELETE);
        db.execSQL(swp_database.ChallengeDB.SQL_DELETE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {    //DB의 자체 버전 다운그레이드. 신경 안써도 됨.
        onUpgrade(db, oldVersion, newVersion);
    }
    public void insertPlan(String contents, int category,  Date date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        values.put(swp_database.PlanDB.PLAN_CONTENTS, contents);
        values.put(swp_database.PlanDB.PLAN_CATEGORY, category);
        values.put(swp_database.PlanDB.PLAN_DATE, dateFormat.format(date));
        long newRowId = db.insert(swp_database.PlanDB.TABLE_NAME, null, values);
    }


    public void insertChallenge(String contents, Date date, float rating){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        values.put(swp_database.ChallengeDB.CHALLENGE_CONTENTS,contents);
        values.put(swp_database.ChallengeDB.CHALLENGE_DATE,dateFormat.format(date));
        values.put(swp_database.ChallengeDB.CHALLENGE_PASS,0);
        values.put(swp_database.ChallengeDB.CHALLENGE_RATING, rating);
        long newRowId = db.insert(swp_database.ChallengeDB.TABLE_NAME, null, values);
    }

}
//