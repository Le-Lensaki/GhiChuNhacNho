package com.example.lphver3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Date;

public class Music extends Service {
    MediaPlayer mediaPlayer;
    int id=2;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String nhankey = intent.getExtras().getString("extra");
        String noidung = intent.getExtras().getString("noidung");
        int idTask = intent.getIntExtra("id",-1);


        if(nhankey.equals("on"))
        {
            id=1;
        } else if(nhankey.equals("off"))
        {
        }

        if(id==1)
        {
            mediaPlayer = MediaPlayer.create(this,R.raw.thieniendikhapthegian);
            mediaPlayer.start();
            Notification(noidung,idTask);


            //Toast.makeText(this, "123 "+mediaPlayer.isPlaying(), Toast.LENGTH_SHORT).show();
            id=0;
        } else if(id==0)
        {
            //Toast.makeText(this, "123 "+mediaPlayer.isPlaying(), Toast.LENGTH_SHORT).show();
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        return START_NOT_STICKY;
    }

    public void Notification(String noidung,int idTask){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_calender);

        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, InfoTask.class);
        resultIntent.putExtra("_id",idTask);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(getNotificationId(), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, MyNotification.CHANNEL_ID)
                .setContentTitle("Nhắc nhở")
                .setContentText(noidung)
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.ic_calender_foreground)
                .setLargeIcon(bitmap)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null) {
            notificationManager.notify(getNotificationId(),notification);
        }
    }
    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}
