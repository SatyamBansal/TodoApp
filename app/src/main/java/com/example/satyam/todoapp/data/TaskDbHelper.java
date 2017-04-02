package com.example.satyam.todoapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.satyam.todoapp.data.TaskContract.TaskEntry;

/**
 * Created by satyam on 17/3/17.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    public static final int DATABASAE_VERSION = 1;
    public static final String DATABASE_NAME = "task.db";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASAE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_TASKS_TABLE = "CREATE TABLE " + TaskEntry.TABLE_NAME + " ( "
                + TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskEntry.COLUMN_TODO + " TEXT NOT NULL, "
                + TaskEntry.COLUMN_PRIORITY + " INTEGER NOT NULL, "

                + TaskEntry.COLUMN_LIST_NAME + " TEXT DEFAULT 'default', "
                + TaskEntry.COLUMN_REMINDER + " INTEGER, "
                + TaskEntry.COLUMN_DUE_TIME + " INTEGER );";

        String SQL_CREATE_LISTS_TABLE = "CREATE TABLE " + TaskContract.ListEntry.TABLE_NAME + " ( "
                + TaskContract.ListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskContract.ListEntry.COLUMN_SORT_ORDER + " INTEGER AUTO_INCREMENT  , "
                + TaskContract.ListEntry.COLUMN_LISTS + " TEXT NOT NULL );";

        db.execSQL(SQL_CREATE_TASKS_TABLE);
        db.execSQL(SQL_CREATE_LISTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
