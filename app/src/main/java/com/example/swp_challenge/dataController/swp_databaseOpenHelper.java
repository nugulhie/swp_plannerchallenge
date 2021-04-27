package com.example.swp_challenge.dataController;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    }
