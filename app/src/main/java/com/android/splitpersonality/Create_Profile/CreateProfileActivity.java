package com.android.splitpersonality.Create_Profile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TimePicker;

import com.android.splitpersonality.Create_Profile.Alarm.AlarmReceiver;
import com.android.splitpersonality.Profile_Data.Alarmdata;
import com.android.splitpersonality.Profile_Data.profileDB;
import com.android.splitpersonality.R;

import java.util.Calendar;

import butterknife.BindView;

public class CreateProfileActivity extends AppCompatActivity {
    Button save;
    Switch bluetooth,airplane;
    TimePicker tp;
    String blue,air,p,h,m;
    String bluedata,airplaneString,profilestring;
    int profile,hour,minute;
    profileDB myDB;
    Alarmdata myDBAlarm;
    RadioButton general,mute;
    AlarmManager alarmManager;
    set_profile_using_intents s;
    private static CreateProfileActivity inst;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        bluetooth =findViewById(R.id.bluetooth);
        airplane = findViewById(R.id.airplane);

        general = findViewById(R.id.general);
        mute = findViewById(R.id.mute);

        tp = findViewById(R.id.time_picker);
        save = findViewById(R.id.save);

        blue = "false";
        air = "false";
        profile = 1;
        hour = 9;
        minute = 0;

        myDB = new profileDB(getApplicationContext());
        myDBAlarm = new Alarmdata(getApplicationContext());
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        s = new set_profile_using_intents(getApplicationContext());


        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                hour = tp.getHour();
                minute = tp.getMinute();
                h = Integer.toString(hour);
                m = Integer.toString(minute);


                if(bluetooth.isActivated())
                    blue = "true";
                if(airplane.isActivated())
                    air = "true";
                if(general.isSelected())
                    profile = 1;
                if(mute.isSelected())
                    profile = 0;

                p = Integer.toString(profile);
                myDB.addData(blue, air, p);
                myDBAlarm.addAlarmData(h,m);

                Cursor data = myDB.getData();
                data.moveToPosition(0);
                bluedata = data.getString(1);
                airplaneString = data.getString(2);
                profilestring = data.getString(3);

                s.setprofile(bluedata,airplaneString,profilestring);




                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                Intent myIntent = new Intent(getApplicationContext(),AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent, 0);
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);


            }
        });
    }
}
