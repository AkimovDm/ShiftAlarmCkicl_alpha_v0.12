package ru.noinknight.clock;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class theAlarm extends AppCompatActivity {

    TextView alarmText;
    Uri notification ;
    Ringtone r ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);

        alarmText=findViewById(R.id.alarm_text);

        Intent starterIntent=getIntent();
        if ((starterIntent.hasExtra("FireAlarm"))&&
                (starterIntent.hasExtra("Days"))){
            long days=starterIntent.getLongExtra("Days",-1);
            if(starterIntent.getExtras().getBoolean("FireAlarm")){

                alarmText.setText((days+1) +"\n"+"Alarm MUST fire");
            } else alarmText.setText((days+1) +"\n"+"Alarm MUST NOT fire");
        } else alarmText.setText("Дата не передалась");

        // alarm sound
        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        r.stop();

    }
}
