package com.example.satyam.todoapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by satyam on 17/3/17.
 */

public final class TaskContract {

    private TaskContract() {
    }

    public static final String AUTHORITY = "com.example.satyam.todoapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_TASKS = "tasks";

    public static final String PATH_LISTS = "lists";

    public static class TaskEntry implements BaseColumns {


        public static final Uri CONTENT_URI_TASK = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TODO = "todo";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_DUE_DATE = "dueDate";
        public static final String COLUMN_DUE_TIME = "dueTime";

        public static final String COLUMN_LIST_NAME = "listName";
        public static final String COLUMN_REMINDER = "reminder";

        public static final String COLUMN_DUE = "due";
        public static final int PRIORITY_LOW = 0;
        public static final int PRIORITY_MEDIUM = 1;
        public static final int PRIORITY_HIGH = 2;

        public static final int REMINDER_ON = 1;
        public static final int REMINDER_OFF = 0;


    }

    public static final class ListEntry implements BaseColumns {

        public static final Uri CONTENT_URI_LIST = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_LISTS).build();
        public static final String TABLE_NAME = "lists";
        public static final String COLUMN_LISTS = "listName";
        public static final String COLUMN_SORT_ORDER = "sortOrder";

    }
}
