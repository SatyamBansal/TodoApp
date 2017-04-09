package com.example.satyam.todoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.satyam.todoapp.data.TaskContract;
import com.example.satyam.todoapp.data.TaskDbHelper;

public class ListActivity extends AppCompatActivity implements ListAdapter.ListClickListener, LoaderManager.LoaderCallbacks<Cursor> {


    public static final String INTENT_LIST_NAME = "listName";

    private TaskDbHelper mDBhelper;
    Cursor mCursor;
    ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mDBhelper = new TaskDbHelper(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText editText = (EditText) findViewById(R.id.add_list_name_edit_text);
        RecyclerView listRecyclerView = (RecyclerView) findViewById(R.id.list_recyclerView);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ListAdapter(this, this);
        listRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = mDBhelper.getWritableDatabase();

                String listName = editText.getText().toString().trim();

//              To avoid empty list names
                if(!listName.equals("")) {
                    ContentValues values = new ContentValues();
                    values.put(TaskContract.ListEntry.COLUMN_LISTS, listName);

                    Uri uri = getContentResolver().insert(TaskContract.ListEntry.CONTENT_URI_LIST, values);
//                db.insert(TaskContract.ListEntry.TABLE_NAME,null,values);

                    Log.i("ListActivity", uri.toString());
//                mAdapter.swapCursor(db.rawQuery("SELECT * FROM  " + TaskContract.ListEntry.TABLE_NAME,null));
                }
                else {
                    Toast.makeText(ListActivity.this, "Enter list name", Toast.LENGTH_SHORT).show();
                }
            }
        });


        getSupportLoaderManager().initLoader(0, null, this);


    }

    @Override
    public void onListClick(String listName) {

        Intent intent = new Intent(ListActivity.this, DefaultListActivity.class);
        Log.i("**********", listName);
        intent.putExtra(INTENT_LIST_NAME, listName);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getBaseContext(), TaskContract.ListEntry.CONTENT_URI_LIST, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        mAdapter.swapCursor(cursor);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }

//    class QueryTask extends AsyncTask<Void,Void,Cursor>{
//        @Override
//        protected Cursor doInBackground(Void... params) {
//            SQLiteDatabase db = mDBhelper.getReadableDatabase();
//            return db.rawQuery("SELECT * FROM  " + TaskContract.ListEntry.TABLE_NAME,null);
//
//        }
//
//        @Override
//        protected void onPostExecute(Cursor cursor) {
//            mAdapter.swapCursor(cursor);
//        }
//    }
}
