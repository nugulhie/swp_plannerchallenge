package com.example.swp_challenge.dataController;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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





    //----------------------------PLANDB-----------------------------
    public void insertPlan(String contents, String category, int year, int month, int day) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String fullday =year+"-"+month+"-"+day;
        SimpleDateFormat dateFormat = new SimpleDateFormat(); //작동 이상무
        values.put(swp_database.PlanDB.PLAN_CONTENTS, contents);
        values.put(swp_database.PlanDB.PLAN_CATEGORY, category);
        values.put(swp_database.PlanDB.PLAN_YEAR, year);
        values.put(swp_database.PlanDB.PLAN_MONTH, month);
        values.put(swp_database.PlanDB.PLAN_DAY, day);
        values.put(swp_database.PlanDB.PLAN_DATE, fullday);
        long newRowId = db.insert(swp_database.PlanDB.TABLE_NAME, null, values);
    }
    public void updatePlan(String oldcontents, String newcontents, String category,  Date date){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat(); //작동이상무 ID값으로 가져오거나 해당 문자열로 검색은 문제가 있을 것같음
        values.put(swp_database.PlanDB.PLAN_CONTENTS, newcontents); //예전값을 들고올때 컨트롤러에 값을 세팅을 하던가 바로 검색한 변수값을 넣던가 선택해야함
        values.put(swp_database.PlanDB.PLAN_CATEGORY, category);
        values.put(swp_database.PlanDB.PLAN_DATE, dateFormat.format(date));


        String selection = swp_database.PlanDB.PLAN_CONTENTS + " LIKE ?"; //도전과제와 마찬가지 선택날짜에 해당하는 값을 DB에서 검색하여 넘겨야함
        String[] selectionArgs = {oldcontents};

        int count = db.update(
                swp_database.PlanDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    //-----------------------------CHALENGEDB----------------------------
    public void insertChallenge(String contents, Date date, float rating, int selectDay1, int selectDay2, int selectMonth1, int selectMonth2, int selectYear1, int selectYear2){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String select1, select2, select3;
        select1 = selectYear1+"년"+selectMonth1+"월"+selectDay1+"일"; // 문제 발생 도전과제는 하루를 기준으로 설정이 가능하다.
        select2 = " ~ "+selectYear2+"년"+selectMonth2+"월"+selectDay2+"일";
        select3 = select1 + select2;
        if(selectDay1 == selectDay2){
            if(selectMonth1 == selectMonth2){
                if(selectYear1 == selectYear2){
                   select3 = selectYear1 + "년" + selectMonth1 + "월" + selectDay1+"일";
                }
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(); //작동 이상무
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
        values.put(swp_database.ChallengeDB.CHALLENGE_DAY, Integer.parseInt(dayChanger.format(date)));
        long newRowId = db.insert(swp_database.ChallengeDB.TABLE_NAME, null, values);
    }

    public void updateChallenge(String oldcontents, String newcontents, Date newdate, float newrating){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues(); //작동 이상무 설계 해야할듯 ID값을 기준으로 가져오는게 편할것 같음
        SimpleDateFormat dateFormat = new SimpleDateFormat(); //예전값을 들고올때 컨트롤러에 값을 세팅을 해주고 넣던가 바로 검색한 변수값을 넣던가 해야할듯 설계 하자
        values.put(swp_database.ChallengeDB.CHALLENGE_CONTENTS,newcontents);
        values.put(swp_database.ChallengeDB.CHALLENGE_RATING, newrating);
        String selection = swp_database.ChallengeDB.CHALLENGE_CONTENTS + " LIKE ?"; //조건식에 오늘 날짜에 해당 혹은 선택 날짜에 해당하는 DB에서 값을 검색해서 넘겨야함 잘못되면 같은 문자열을 전부 업데이트함
        String[] selectionArgs = {oldcontents};

        int count = db.update(
                swp_database.ChallengeDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void updatePass(String contents ,int pass){
        SQLiteDatabase db = getWritableDatabase(); //작동이상무
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        values.put(swp_database.ChallengeDB.CHALLENGE_PASS, pass);
        String selection = swp_database.ChallengeDB.CHALLENGE_CONTENTS+ " LIKE ?";
        String[] selectionArgs = {contents};

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
        Log.d("159753", "updateUserKeyCount: "+count);
    }


//populate table


}
//