package com.asymptote.bails;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Live_Matches extends AppCompatActivity {

    RecyclerView live_matches_rv;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    ArrayList<Live_model> l_model;
    RecyclerView.LayoutManager layoutManager;
    Live_Matches_Adapter live_matches_adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    LocalBroadcastManager lbr;
    BroadcastReceiver br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live__matches);
        live_matches_rv = findViewById(R.id.live_matches_rv);
        swipeRefreshLayout = findViewById(R.id.swiper);

        lbr = LocalBroadcastManager.getInstance(getApplicationContext());
        br = new MyReceiver();

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference().child("matches_live");


        l_model = new ArrayList<>();
        load_data();

        live_matches_adapter = new Live_Matches_Adapter(getApplicationContext(), l_model);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        live_matches_rv.setLayoutManager(layoutManager);
        live_matches_rv.setAdapter(live_matches_adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                l_model.clear();
                load_data();
                live_matches_adapter.notifyDataSetChanged();
            }
        });

        live_matches_adapter.setlistener(new Live_Matches_Adapter.bridge() {
            @Override
            public void listener(int position) {
                Toast.makeText(Live_Matches.this, position+"", Toast.LENGTH_SHORT).show();

                Intent i = new Intent();
                i.putExtra("custom","jaa");
                i.putExtra("position",position);
                i.setAction("custom_hr");
                i.addCategory("android.intent.category.DEFAULT");
                lbr.sendBroadcast(i);
            }
        });
    }


    private void load_data() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String match_name = ds.child("matchDetail").child("matchSummary").child("name").getValue().toString();
                    String team_batting = ds.child("matchDetail").child("teamBatting").child("shortName").getValue().toString();
                    String home_score = ds.child("matchDetail").child("matchSummary").child("scores").child("homeScore")
                            .getValue().toString();
                    String away_score = ds.child("matchDetail").child("matchSummary").child("scores").child("awayScore")
                            .getValue().toString();
                    String home_team = ds.child("matchDetail").child("matchSummary")
                            .child("homeTeam").child("shortName").getValue().toString();
                    String away_team = ds.child("matchDetail").child("matchSummary")
                            .child("awayTeam").child("shortName").getValue().toString();

                    if (ase(l_model, match_name, home_team, away_team)) {
                        live_matches_adapter.notifyDataSetChanged();
                    } else {
                        l_model.add(new Live_model(match_name, team_batting, home_score, away_score, home_team, away_team));
                        live_matches_adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private boolean ase(ArrayList<Live_model> check, String match_name, String home, String away) {
        for (Live_model c : check) {
            if (match_name.equals(c.match_name) && home.equals(c.home_team_name) && away.equals(c.away_team_name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() { //onstop e error hoy
        super.onDestroy();

        //unregisterReceiver(br);

    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter("custom_hr");
        intentFilter.addCategory("android.intent.category.DEFAULT"); // because custom so specifying a default
        lbr.registerReceiver(br, intentFilter);

    }
}
