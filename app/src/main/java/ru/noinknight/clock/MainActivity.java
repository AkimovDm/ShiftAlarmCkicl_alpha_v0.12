package ru.noinknight.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Button btnStart;
    Button btnCancel;
    TextView alarmText;
    EditText enteredDate;

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart=findViewById(R.id.btn_start);
        btnCancel=findViewById(R.id.btn_cancel);
        alarmText=findViewById(R.id.tv_alarmText);
        enteredDate=findViewById(R.id.et_date_enter);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker=new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
                saveDate();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,min);
        c.set(Calendar.SECOND,0);
        setAlarmText(c);
        startAlarm(c);
    }

    private void setAlarmText(Calendar c){
        String text="Alarm set for: ";
        text+=DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        alarmText.setText(text);
    }

    private void startAlarm (Calendar c){


        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this, AlertReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);

    }

    private void cancelAlarm(){
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(pendingIntent);
        alarmText.setText("Alarm Canceled");
    }


    private void saveDate(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd.MM.yy");
        String inputDate=enteredDate.getText().toString();

        Date date=new Date();
        try {
            date=dateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sPref=getSharedPreferences("datepref", MODE_PRIVATE);
        SharedPreferences.Editor ed=sPref.edit();
        ed.putLong("date",date.getTime());
        ed.commit();


    }
}