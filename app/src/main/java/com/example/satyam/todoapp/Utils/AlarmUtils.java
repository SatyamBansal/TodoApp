package com.example.satyam.todoapp.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.satyam.todoapp.services.ReminderNotificationService;


// handle Redminders for todo tasks

public final class AlarmUtils {

    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;

    public static void createAlarm(Context context, int id, long timeInMilis, long minRepeat) {


        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderNotificationService.class);
        intent.putExtra("id", id);
        alarmIntent = PendingIntent.getService(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        int minute = Integer.parseInt(time.substring(2));
//        String h = time.substring(0,2);
//        int hourOfDay = Integer.parseInt(time.substring(0,2));
//        int dayOfMonth = Integer.parseInt(date.substring(0,2));
//        int month = Integer.parseInt(date.substring(2,4));
//        int year = Integer.parseInt(date.substring(4));
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        calendar.set(Calendar.MINUTE, minute);
////        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
////        calendar.set(Calendar.MONTH,month);
////        calendar.set(Calendar.YEAR,year);
        if(Build.VERSION.SDK_INT>19)
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, timeInMilis, alarmIntent);
        else
            alarmMgr.set(AlarmManager.RTC_WAKEUP, timeInMilis, alarmIntent);


    }

}
