package com.example.lphver3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String chuoi_string = intent.getExtras().getString("extra");
        String noidung = intent.getExtras().getString("noidung");
        int id = intent.getExtras().getInt("id");

        Intent intentMusic = new Intent(context, Music.class);
        intentMusic.putExtra("extra", chuoi_string);
        intentMusic.putExtra("noidung",noidung);
        intentMusic.putExtra("id",id);
        context.startService(intentMusic);
    }
}
