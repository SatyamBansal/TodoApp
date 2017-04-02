package com.example.satyam.todoapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.satyam.todoapp.Utils.AlarmUtils;
import com.example.satyam.todoapp.data.TaskContract;
import com.example.satyam.todoapp.data.TaskContract.TaskEntry;

import java.util.Calendar;

public class EditorActivity extends AppCompatActivity {


    private Spinner mSpinner;
    private int mPriority;
    private Button mDueTimeButton;
    private Button mDueDateButton;
    private String mTask;
    static Calendar calendar;


    private static String mDueTime;
    private static String mDueDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        calendar = Calendar.getInstance();
        mSpinner = (Spinner) findViewById(R.id.spinner_priority);
        setUpSpinner();
        mDueTimeButton = (Button) findViewById(R.id.button_dueTime);

        mDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerFragment fragment = new TimePickerFragment();

                fragment.show(getSupportFragmentManager(), "timePicker");


            }
        });

        mDueDateButton = (Button) findViewById(R.id.button_dueDate);
        mDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        final EditText taskEdit = (EditText) findViewById(R.id.edit_todo);


        Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask = taskEdit.getText().toString().trim();

                ContentValues value = new ContentValues();
                value.put(TaskEntry.COLUMN_LIST_NAME, getIntent().getStringExtra("listName"));
                value.put(TaskEntry.COLUMN_DUE_TIME, calendar.getTimeInMillis());

                value.put(TaskEntry.COLUMN_TODO, mTask);
                value.put(TaskEntry.COLUMN_PRIORITY, mPriority);

                Uri uri = getContentResolver().insert(TaskEntry.CONTENT_URI_TASK, value);
                int id = Integer.parseInt(uri.getPathSegments().get(1));
                AlarmUtils.createAlarm(getBaseContext(), id, calendar.getTimeInMillis(), 0);
                Log.i("!!!!!!!!!!!", "" + id);

                finish();


            }
        });


    }

    private void setUpSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_priority_options, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.priority_low))) {
                        mPriority = TaskContract.TaskEntry.PRIORITY_LOW;
                    } else if (selection.equals(getString(R.string.priority_medium))) {
                        mPriority = TaskContract.TaskEntry.PRIORITY_MEDIUM;
                    } else {
                        mPriority = TaskContract.TaskEntry.PRIORITY_HIGH;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPriority = 0; // Unknown
            }
        });
    }

    // Class for creating TimePicker

    public static class TimePickerFragment extends android.support.v4.app.DialogFragment implements TimePickerDialog.OnTimeSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            Log.i("EditorActivity", calendar.getTimeInMillis() + "");

            mDueTime = Integer.toString(hourOfDay);
            String mint = Integer.toString(minute);
            if (minute < 10) {
                mDueTime += "0" + mint;
            } else {
                mDueTime += mint;
            }

            Toast.makeText(getContext(), " " + mDueTime, Toast.LENGTH_SHORT).show();


        }

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


            calendar.set(year, month, dayOfMonth);
            Log.i("EditorActivity", calendar.getTime().toString());


            Toast.makeText(getContext(), "" + dayOfMonth + "|" + month + "|" + year, Toast.LENGTH_SHORT).show();
            mDueDate = Long.toString(dayOfMonth * 1000000 + month * 10000 + year);

        }
    }
}
