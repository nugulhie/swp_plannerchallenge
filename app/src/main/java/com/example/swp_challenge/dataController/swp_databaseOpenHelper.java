package com.example.swp_challenge.dataController;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ContextThemeWrapper;

import com.example.swp_challenge.controller.UserController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class swp_databaseOpenHelper {
    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(swp_database.UserDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+swp_database.UserDB._TABLENAME0);
            onCreate(db);
        }
    }

    public swp_databaseOpenHelper (Context context){
        this.mCtx = context;
    }

    public swp_databaseOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    public long insertColumn(String user_name, int cnt_key, int box_rank){
        UserController user = new UserController();
        ContentValues values = new ContentValues();
        values.put(swp_database.UserDB.cnt_key, user.getCnt_key());
        values.put(swp_database.UserDB.box_rank, user.getBoxRank());
        //values.put(swp_database.CreateDB.birth, );

        return mDB.insert(swp_database.UserDB._TABLENAME0, null, values);
    }
    public long plan_insertColumn(int plan_id, String plan_contents,  int plan_category, Date plan_date){
        ContentValues values = new ContentValues();
        values.put(swp_database.PlanDB.plan_id, plan_id);
        //values.put(swp_database.PlanDB.pl, plan_id);
        return mDB.insert(swp_database.PlanDB._TABLENAME1, null, values);
    }
    public long challenge_insertColumn(int chall_id, float chall_rating, String chall_contents, boolean chall_pass, Date chall_date){
        ContentValues values = new ContentValues();
        return mDB.insert(swp_database.challengeDB._TABLENAME2, null, values);
    }
 /*   public long insertColumn(int achive_id, String achive_name){
        ContentValues values = new ContentValues();
        values.put(swp_database.achivementDB.achive_id, 1);
        return mDB.insert(swp_database.achivementDB._TABLENAME3, null, values);
    }*/

}
//일정 저장, 도전과제 저장