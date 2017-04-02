package com.example.satyam.todoapp.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.satyam.todoapp.data.TaskContract;

/**
 * Created by satyam on 20/3/17.
 */

public final class DatabaseUtils {


    public static Cursor queryTask(Context context, int id) {

        Cursor returnCursor;
        Uri qUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI_TASK, id);
        returnCursor = context.getContentResolver().query(qUri, null, null, null, null, null);
        returnCursor.moveToNext();

        return returnCursor;

    }
}
