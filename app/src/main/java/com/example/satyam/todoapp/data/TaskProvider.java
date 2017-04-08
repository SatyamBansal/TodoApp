package com.example.satyam.todoapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.satyam.todoapp.data.TaskContract.TaskEntry;

/**
 * Created by satyam on 17/3/17.
 */

public class TaskProvider extends ContentProvider {

    public static final int TASKS = 100;
    public static final int TASK_WITH_ID = 101;
    public static final int LISTS = 200;
    public static final int LIST_WITH_ID = 201;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    TaskDbHelper mDbHelper;

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_WITH_ID);

        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_LISTS, LISTS);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_LISTS + "/#", LIST_WITH_ID);


        return uriMatcher;


    }

    @Override
    public boolean onCreate() {
        mDbHelper = new TaskDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int code = sUriMatcher.match(uri);
        Cursor returnCursor;

        switch (code) {
            case TASKS:
                returnCursor = db.query(TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TASK_WITH_ID:
                Log.i("**TAsk with id ", uri.toString());
                selection = TaskEntry._ID + "=?";

                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                returnCursor = db.query(TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case LISTS:
                returnCursor = db.query(TaskContract.ListEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);


        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int code = sUriMatcher.match(uri);
        long id = -1;

        switch (code) {
            case TASKS:
                id = database.insert(TaskEntry.TABLE_NAME, null, values);
                break;
            case LISTS:
                id = database.insert(TaskContract.ListEntry.TABLE_NAME, null, values);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);


        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsDeleted = -1;

        switch (match){
            case TASK_WITH_ID:
                selection = TaskEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = db.delete(TaskEntry.TABLE_NAME,selection,selectionArgs);
                break;

            default:throw new IllegalArgumentException("Deletion is not supported for " + uri);

        }

        getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
