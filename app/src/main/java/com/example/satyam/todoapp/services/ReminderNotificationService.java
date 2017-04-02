package com.example.satyam.todoapp.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.satyam.todoapp.DefaultListActivity;
import com.example.satyam.todoapp.Utils.DatabaseUtils;
import com.example.satyam.todoapp.data.TaskContract;

/**
 * Created by satyam on 20/3/17.
 */

public class ReminderNotificationService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ReminderNotificationService() {
        super("ReminderNotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Context context = getApplicationContext();
        Toast.makeText(context, "Service started", Toast.LENGTH_SHORT).show();
        int test = intent.getIntExtra("id", -1);

        Log.i("*************", "Service Started");

        Cursor cursor = DatabaseUtils.queryTask(context, intent.getIntExtra("id", -1));
        String notificationHeading = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TODO));
        Log.i("****************", notificationHeading);
        createNotification(context, notificationHeading);

    }

    private void createNotification(Context context, String heading) {
        Intent intent = new Intent(context, DefaultListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(heading)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.sym_def_app_icon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
