package com.asymptote.bails;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    Button live_matches_btn;
    Button upcoming_matches_btn;
    Button history_matches_btn;
    ImageView logo_iv;
    ImageView tag_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        live_matches_btn = findViewById(R.id.live_matches_id);
        upcoming_matches_btn = findViewById(R.id.upcoming_id);
        history_matches_btn = findViewById(R.id.history_id);
        logo_iv = findViewById(R.id.logo_iv);
        tag_iv = findViewById(R.id.tag_iv);
        Picasso.get().load(R.drawable.b2).resize(220,140).into(logo_iv);
        Picasso.get().load(R.drawable.b1).resize(220,100).into(tag_iv);

        live_matches_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Live_Matches.class);
                startActivity(i);
            }
        });

        upcoming_matches_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Upcoming_Matches.class);
                startActivity(i);
            }
        });
    }
}
