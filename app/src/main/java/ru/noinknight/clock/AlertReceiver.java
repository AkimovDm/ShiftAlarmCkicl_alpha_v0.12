package ru.noinknight.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AlertReceiver extends BroadcastReceiver {

    SharedPreferences sPref;

    @Override
    public void onReceive(Context context, Intent intent) {
        Date inputDate = new Date();
        sPref= context.getSharedPreferences("datepref", Context.MODE_PRIVATE);
        inputDate.setTime(sPref.getLong("date",0));
        Log.d("TAG_LOG",inputDate.toString());

        Date currentDate = new Date(System.currentTimeMillis());
        // Проверить, если дата позже
        long days = ShiftHandler.getDays(inputDate);
        boolean fireAlarm = ShiftHandler.daysChecker(days);


        Intent i = new Intent();
        i.setClassName("ru.noinknight.clock", "ru.noinknight.clock.theAlarm");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("FireAlarm", fireAlarm);
        i.putExtra("Days",days);
        context.startActivity(i);


    }
}
