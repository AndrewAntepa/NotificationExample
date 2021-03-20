package com.example.notificationexample;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    public static final String SERVICE_ACTION = "Music Service";

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.yikes);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        
        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //TODO создание канала
        NotificationChannel nChanneel = new NotificationChannel("Music channel","Nicki Minaj", NotificationManager.IMPORTANCE_DEFAULT);
        nManager.createNotificationChannel(nChanneel);
        //TODO Intent
        Intent clickNotifIntent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, clickNotifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, "Music channel")
                .setContentTitle("Вечеринка!!!!:)")
                .setContentText("НИКИ МИНАЖЖЖЖ")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId("Music channel")
                .setContentIntent(pIntent)
                .setColor(Color.BLUE);

        Notification notification = nBuilder.build();
        nManager.notify(34, notification);

        
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
