package com.asymptote.bails;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    Bundle bundle;
    int position;
    boolean flag,matchover;
    int count =0; //for handling multiple notifications after one match innings.

    public NotificationService() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference().child("matches_live");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.i("koi_asi", "service created");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i("koi_asi", "Dhongsho");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("koi_asi", "onStartCommand e dhuksi");
        bundle = intent.getExtras();
        position = (int) bundle.get("position");

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i("koi_asi", "over check hochhe");
                if (check_over()) {
                    if(count==1){                   //if at starting both are 20.0,we get double notification2
                        showNotification();
                        count = count+1;
                    }
                    if(matchover){
                        Log.i("koi_asi", "match shesh");
                        showNotification2();
                        t.cancel();
                        stopSelf();
                    }

                }
            }
        }, 0, 10000);

        Log.i("koi_asi", "got out of timer");
        return Service.START_STICKY;
    }

    public void showNotification() {

        Intent intent = new Intent(this, Live_Matches.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder n = new NotificationCompat.Builder(this)
                .setContentTitle("Innings over.")
                .setContentText("Tap to open the live follow up!")
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setAutoCancel(true)
                .setContentIntent(pIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify((int) (Math.random() * 10), n.build()); //kahini has

    }

    public void showNotification2() {

        Intent intent = new Intent(this, Live_Matches.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder n = new NotificationCompat.Builder(this)
                .setContentTitle("Match has ended.")
                .setContentText("Tap to see reports.")
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setAutoCancel(true)
                .setContentIntent(pIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify((int) (Math.random() * 10), n.build()); //kahini has

    }

    private boolean check_over() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String teamBatting = dataSnapshot.child(String.valueOf(position)).child("matchDetail").child("teamBatting")
                        .child("shortName").getValue().toString();
                String innings_0_team = dataSnapshot.child(String.valueOf(position)).child("matchDetail").child("innings")
                        .child("0").child("shortName").getValue().toString();
                String innings_1_team = dataSnapshot.child(String.valueOf(position)).child("matchDetail").child("innings")
                        .child("1").child("shortName").getValue().toString();
                String team_0_overs = dataSnapshot.child(String.valueOf(position)).child("matchDetail").child("innings")
                        .child("0").child("overs").getValue().toString();
                String team_1_overs = dataSnapshot.child(String.valueOf(position)).child("matchDetail").child("innings")
                        .child("0").child("overs").getValue().toString();

                Log.i("koi_asi", team_0_overs + team_1_overs + teamBatting);

                if (teamBatting.equals(innings_0_team)) {
                    if (team_0_overs.equals("20.0")) {
                        flag = true;
                        count=count+1;
                    }
                } else if (teamBatting.equals(innings_1_team)) {
                    if (team_1_overs.equals("20.0")) {
                        flag = true;
                        count=count+1;
                    }
                }
                if(team_0_overs.equals("20.0") && team_1_overs.equals("20.0")){
                    matchover = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (flag) {
            return true;
        } else {
            return false;
        }
    }


}
