package com.android.splitpersonality.Create_Profile;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.Settings;

public class set_profile_using_intents {
    AudioManager am;
    Context context;
    Activity activity;
    BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
    public set_profile_using_intents(Context context) {
        this.context = context;
        this.activity = activity;
    }




    public void setprofile(String blue,String air, String p){
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        boolean isEnabled = Settings.System.getInt(
                context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1;

// toggle airplane mode
        Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);

// Post an intent to reload
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        if(p.equals("1"))
            am.setRingerMode(2);
        if(p.equals("0"))
            am.setRingerMode(0);

        if(air.equals("true")){
            intent.putExtra("state", !isEnabled);
            context.sendBroadcast(intent);
            }
        if(blue.equals("true")){
            if(!bAdapter.isEnabled()) {
                activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
            }
        }
        if(blue.equals("false")){
            bAdapter.disable();
        }

    }
}
