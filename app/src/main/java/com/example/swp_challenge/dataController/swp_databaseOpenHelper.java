package com.example.swp_challenge.dataController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.swp_challenge.R;

import java.text.SimpleDateFormat;
import java.util.Date;
////
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
        db.execSQL(swp_database.UserDB.SQL_DELETE);
        db.execSQL(swp_database.PlanDB.SQL_DELETE);
        db.execSQL(swp_database.ChallengeDB.SQL_DELETE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //----------------------------PLANDB-----------------------------
    public void insertPlan(String contents, String category, int year, int month, int day) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String fullday =year+"-"+month+"-"+day;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        values.put(swp_database.PlanDB.PLAN_CONTENTS, contents);
        values.put(swp_database.PlanDB.PLAN_CATEGORY, category);
        values.put(swp_database.PlanDB.PLAN_YEAR, year);
        values.put(swp_database.PlanDB.PLAN_MONTH, month);
        values.put(swp_database.PlanDB.PLAN_DAY, day);
        values.put(swp_database.PlanDB.PLAN_DATE, fullday);
        long newRowId = db.insert(swp_database.PlanDB.TABLE_NAME, null, values);
    }
    public void updatePlan(int id, String newcontents, String category){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat();
        values.put(swp_database.PlanDB.PLAN_CONTENTS, newcontents);
        values.put(swp_database.PlanDB.PLAN_CATEGORY, category);

        String selection = swp_database.PlanDB.PLAN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        int count = db.update(
                swp_database.PlanDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
    public void plandelete(int id){
        SQLiteDatabase db =getWritableDatabase();
        String selection = swp_database.PlanDB.PLAN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        int deletedRows = db.delete(swp_database.PlanDB.TABLE_NAME, selection, selectionArgs);
    }


    //-----------------------------CHALENGEDB----------------------------
    public void insertChallenge(String contents, Date date, float rating, int selectDay1, int selectDay2, int selectMonth1, int selectMonth2, int selectYear1, int selectYear2){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String select1, select2, select3;
        select1 = selectYear1+"년"+selectMonth1+"월"+selectDay1+"일";
        select2 = " ~ "+selectYear2+"년"+selectMonth2+"월"+selectDay2+"일";
        select3 = select1 + select2;
        if(selectDay1 == selectDay2){
            if(selectMonth1 == selectMonth2){
                if(selectYear1 == selectYear2){
                   select3 = selectYear1 + "년" + selectMonth1 + "월" + selectDay1+"일";
                }
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        SimpleDateFormat dayChanger = new SimpleDateFormat("dd");
        values.put(swp_database.ChallengeDB.CHALLENGE_CONTENTS,contents);
        values.put(swp_database.ChallengeDB.CHALLENGE_DUE, select3);
        values.put(swp_database.ChallengeDB.CHALLENGE_PASS,0);
        values.put(swp_database.ChallengeDB.CHALLENGE_YEAR1, selectYear1);
        values.put(swp_database.ChallengeDB.CHALLENGE_MONTH1, selectMonth1);
        values.put(swp_database.ChallengeDB.CHALLENGE_DAY1, selectDay1);
        values.put(swp_database.ChallengeDB.CHALLENGE_YEAR2, selectYear2);
        values.put(swp_database.ChallengeDB.CHALLENGE_MONTH2, selectMonth2);
        values.put(swp_database.ChallengeDB.CHALLENGE_DAY2, selectDay2);
        values.put(swp_database.ChallengeDB.CHALLENGE_RATING, rating);
        values.put(swp_database.ChallengeDB.CHALLENGE_CHECK, 0);
        values.put(swp_database.ChallengeDB.CHALLENGE_DAY, Integer.parseInt(dayChanger.format(date)));
        long newRowId = db.insert(swp_database.ChallengeDB.TABLE_NAME, null, values);
    }

    public void updateChallenge(int id, String newcontents,float newrating){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        values.put(swp_database.ChallengeDB.CHALLENGE_CONTENTS,newcontents);
        values.put(swp_database.ChallengeDB.CHALLENGE_RATING, newrating);
        String selection = swp_database.ChallengeDB.CHALLENGE_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        int count = db.update(
                swp_database.ChallengeDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void updatePass(int id ,int pass){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        values.put(swp_database.ChallengeDB.CHALLENGE_PASS, pass);
        String selection = swp_database.ChallengeDB.CHALLENGE_ID+ " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        int count = db.update(
                swp_database.ChallengeDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);

    }
    public void challengedelete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String selection = swp_database.ChallengeDB.CHALLENGE_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        int deleteRows = db.delete(swp_database.ChallengeDB.TABLE_NAME,selection,selectionArgs);
    }
    public void updateCheckValue(int id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String selection = swp_database.ChallengeDB.CHALLENGE_ID+ " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        values.put(swp_database.ChallengeDB.CHALLENGE_CHECK, 1);

        int count = db.update(
                swp_database.ChallengeDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
    //----------------------------------USERDB------------------------------------
    public void insertUsername(String username, String birth){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(swp_database.UserDB.USER_NAME, username);
        values.put(swp_database.UserDB.USER_KEY, 0);
        values.put(swp_database.UserDB.BIRTH, birth);
        values.put(swp_database.UserDB.BOX_OPEN_CNT, 0);
        values.put(swp_database.UserDB.BOX_RANK, 1);
        values.put(swp_database.UserDB.USER_ACHIVE, 0);
        values.put(swp_database.UserDB.CURRENT_ACHIVE, "뉴비");
        values.put(swp_database.UserDB.CURRENT_IMG, R.drawable.newbi);
        long newRowId = db.insert(swp_database.UserDB.TABLE_NAME, null, values);
    }


    public void updateUserBoxRank(String user_name,int box_rank){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(swp_database.UserDB.BOX_RANK, box_rank);
        String selection = swp_database.UserDB.USER_NAME+ " LIKE ?";
        String[] selectionArgs = {user_name};

        int count = db.update(
                swp_database.UserDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
    public void updateUserBoxOpenCnt(String user_name, int box_cnt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(swp_database.UserDB.BOX_OPEN_CNT, box_cnt);
        String selection = swp_database.UserDB.USER_NAME+ " LIKE ?";
        String[] selectionArgs = {user_name};

        int count = db.update(
                swp_database.UserDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
    public void updateUserKeyCount(String user_name, int key_cnt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(swp_database.UserDB.USER_KEY, key_cnt);
        String selection = swp_database.UserDB.USER_NAME+ " LIKE ?";
        String[] selectionArgs = {user_name};

        int count = db.update(
                swp_database.UserDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
    public void updateUserAchive(String username, int achivenumber, String currentAchive) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String temps = currentAchive + "-" + Integer.toString(achivenumber);
        values.put(swp_database.UserDB.USER_ACHIVE, temps);
        String selection = swp_database.UserDB.USER_NAME + " LIKE ?";
        String[] selectionArgs = {username};
        int count = db.update(
                swp_database.UserDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
    public void updateCurrentAchive(String username, String achive, int currnetImg){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(swp_database.UserDB.CURRENT_ACHIVE, achive);
        values.put(swp_database.UserDB.CURRENT_IMG, currnetImg);
        String selection = swp_database.UserDB.USER_NAME+" LIKE ?";
        String[] selectionArgs = {username};
        int count = db.update(
                swp_database.UserDB.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }
    public void updateUsername(String username, String newname){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(swp_database.UserDB.USER_NAME, newname);
        String selection = swp_database.UserDB.USER_NAME+" LIKE ?";
        String[] selectionArgs = {username};
        int count = db.update(
                swp_database.UserDB.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }
}
