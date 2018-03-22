package com.matallahnh.nhadmin.day13app03;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private NotificationCompat.Builder notification = null;
    private final int NOTIFICATION_ID = 55441;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        notification = new NotificationCompat.Builder(this);
    }
//test2
    public void btn_click(View view) {
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setContentTitle("My App Notification");
        notification.setContentText("Open the app to get updates from ....");
        notification.setWhen(System.currentTimeMillis());

        Intent i = new Intent(Main2Activity.this,MainActivity.class);

        PendingIntent pending =
                PendingIntent.getActivity(Main2Activity.this,0,i,0);

        notification.setContentIntent(pending);

        NotificationManager nm =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        nm.notify(NOTIFICATION_ID,notification.build());
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

    }
}
