package com.hackathon.hackmsit.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hackathon.hackmsit.utilities.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    /*String CREATE_TABLE_NOTE = "CREATE TABLE "
            + Constants.NOTES_TABLE
            + "("
            + Constants.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Constants.COLUMN_TITLE + " TEXT Default 'Unknown', "
            + Constants.COLUMN_CONTENT + " TEXT Default 'Unknown', "
            + Constants.COLUMN_MODIFIED_TIME + " INTEGER, "
            + Constants.COLUMN_CREATED_TIME + " INTEGER )";*/

    private static final String CREATE_TABLE_NOTE = "create table "
            + Constants.NOTES_TABLE
            + "("
            + Constants.COLUMN_ID + " integer primary key autoincrement, "
            + Constants.COLUMN_TITLE + " text not null, "
            + Constants.COLUMN_CONTENT + " text not null, "
            + Constants.COLUMN_MODIFIED_TIME + " integer not null, "
            + Constants.COLUMN_CREATED_TIME + " integer not null " + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.NOTES_TABLE);
        onCreate(db);
    }
}
