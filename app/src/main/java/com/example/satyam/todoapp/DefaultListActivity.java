package com.example.satyam.todoapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.satyam.todoapp.data.TaskContract;
import com.example.satyam.todoapp.data.TaskDbHelper;
import com.orhanobut.logger.Logger;

public class DefaultListActivity extends AppCompatActivity implements TaskAdapter.TaskClickListener , LoaderManager.LoaderCallbacks<Cursor> {


    private TaskDbHelper mDbHelper;
    private String[] mSelectionArgs;
    TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DefaultListActivity.this, EditorActivity.class);
                i.putExtra("listName", getIntent().getStringExtra("listName"));
                startActivity(i);
            }
        });

        mDbHelper = new TaskDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TODO, "Job");
        values.put(TaskContract.TaskEntry.COLUMN_PRIORITY, 1);
        values.put(TaskContract.TaskEntry.COLUMN_DUE_DATE, "today");
        values.put(TaskContract.TaskEntry.COLUMN_DUE_TIME, "RightNow");

//        for(int i =0;i<5;i++){
//
//            db.insert(TaskContract.TaskEntry.TABLE_NAME,null,values);
//
//        }

        mSelectionArgs = new String[]{getIntent().getStringExtra("listName")};
        Logger.d(mSelectionArgs);

        //  Log.i("DefaulActivity.java", mSelectionArgs[0]);

//        Cursor cursor = getContentResolver().query(TaskContract.TaskEntry.CONTENT_URI_TASK, null, TaskContract.TaskEntry.COLUMN_LIST_NAME + "=?", selectionArgs, null);

        Cursor cursor2 = getContentResolver().query(ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI_TASK, 5), null, null, null, null, null);

        cursor2.moveToNext();


//        if (cursor.getCount() != 0)
//            while (cursor.moveToNext()) {
//                Log.i("****", "*************");
//                Log.i("Values", cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TODO)));
//            }

        RecyclerView taskRecyclerView = (RecyclerView) findViewById(R.id.task_recyclerView);
        mTaskAdapter = new TaskAdapter(this, this);

        ItemTouchHelper.Callback callback = new TaskItemTouchHelperCallback(mTaskAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(taskRecyclerView);

        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRecyclerView.setAdapter(mTaskAdapter);
        getSupportLoaderManager().initLoader(1,null,this);
        cursor2.close();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnTaskClick(View view) {
       long tag = (long)view.getTag();
        Toast.makeText(this, "id = "+ tag, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Logger.d("here");

        if (getIntent().getStringExtra("listName") == null) {

            return new CursorLoader(this, TaskContract.TaskEntry.CONTENT_URI_TASK,null,null,null,null);

        }
        else
        {
            Logger.d("inside not null");
            return new CursorLoader(this,TaskContract.TaskEntry.CONTENT_URI_TASK, null, TaskContract.TaskEntry.COLUMN_LIST_NAME + "=?", mSelectionArgs, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mTaskAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTaskAdapter.swapCursor(null);

    }
}
