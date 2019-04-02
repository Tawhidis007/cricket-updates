package com.asymptote.bails;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Upcoming_Matches extends AppCompatActivity {

    RecyclerView upcoming_matches_rv;
    RecyclerView.LayoutManager layoutManager2;
    SwipeRefreshLayout swipeRefreshLayout2;
    FirebaseDatabase firebaseDatabase2;
    DatabaseReference ref2;
    ArrayList<Upcoming_Matches_Model> u_model;
    Upcoming_Matches_Adapter upcoming_matches_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming__matches);

        upcoming_matches_rv = findViewById(R.id.upcoming_matches_rv);
        swipeRefreshLayout2 = findViewById(R.id.swiper2);

        firebaseDatabase2 = FirebaseDatabase.getInstance();
        ref2 = firebaseDatabase2.getReference().child("matches_upcoming");

        u_model = new ArrayList<>();
        load_data2();

        upcoming_matches_adapter = new Upcoming_Matches_Adapter(getApplicationContext(), u_model);
        layoutManager2 = new LinearLayoutManager(getApplicationContext());
        upcoming_matches_rv.setLayoutManager(layoutManager2);
        upcoming_matches_rv.setAdapter(upcoming_matches_adapter);

        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout2.setRefreshing(false);
                u_model.clear();
                load_data2();
                upcoming_matches_adapter.notifyDataSetChanged();
            }
        });

    }

    private void load_data2() {
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String team1name = ds.child("match").child("awayTeam").child("name").getValue().toString();
                    String team2name = ds.child("match").child("homeTeam").child("name").getValue().toString();
                    String team1url = ds.child("match").child("awayTeam").child("logoUrl").getValue().toString();
                    String team2url = ds.child("match").child("homeTeam").child("logoUrl").getValue().toString();
                    String time = ds.child("match").child("cmsMatchStartDate").getValue().toString();
                    String series_name = ds.child("match").child("series").child("name").getValue().toString();

                    if (ase(u_model, team1name, team2name, time)) {
                        upcoming_matches_adapter.notifyDataSetChanged();
                    } else {
                        u_model.add(new Upcoming_Matches_Model(team1name, team2name, team1url, team2url, series_name, time));
                        upcoming_matches_adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean ase(ArrayList<Upcoming_Matches_Model> check, String t1n, String t2n, String tim) {
        for (Upcoming_Matches_Model c : check) {
            if (t1n.equals(c.getHome_team_name()) && t2n.equals(c.getAway_team_name()) && tim.equals(c.getTime())) {
                return true;
            }
        }
        return false;
    }


}
