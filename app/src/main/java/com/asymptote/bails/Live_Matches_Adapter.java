package com.asymptote.bails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class Live_Matches_Adapter extends RecyclerView.Adapter<Live_Matches_Adapter.ViewHolder> {

    Context context;
    ArrayList<Live_model> l_model;
    bridge my_listener;

    public Live_Matches_Adapter(Context context, ArrayList<Live_model> l_model) {
        this.context = context;
        this.l_model = l_model;
    }

    interface bridge {
        void listener(int position);

    }

    void setlistener(bridge o) {
        my_listener = o;
    }

    @NonNull
    @Override
    public Live_Matches_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.live_matches_card, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull Live_Matches_Adapter.ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        TextView match_name = cardView.findViewById(R.id.match_name_tv);
        TextView team_1_name_tv = cardView.findViewById(R.id.team_1_name_tv);
        TextView team_1_score_tv = cardView.findViewById(R.id.team_1_score_tv);
        TextView team_2_name_tv = cardView.findViewById(R.id.team_2_name_tv);
        TextView team_2_score_tv = cardView.findViewById(R.id.team_2_score_tv);
        TextView live_matches_over_tv = cardView.findViewById(R.id.live_matches_over_tv);
        TextView who_batting_tv = cardView.findViewById(R.id.who_batting_tv);
        CheckBox live_matches_notify_cb = cardView.findViewById(R.id.live_matches_notify_cb);
        Button match_details_btn = cardView.findViewById(R.id.match_details_btn);

        match_name.setText(l_model.get(position).getMatch_name());
        team_1_name_tv.setText(l_model.get(position).getHome_team_name()+" : ");
        team_2_name_tv.setText(l_model.get(position).getAway_team_name()+" : ");
        team_1_score_tv.setText(l_model.get(position).getHome_score());
        team_2_score_tv.setText(l_model.get(position).getAway_score());
        who_batting_tv.setText(l_model.get(position).getTeam_batting());

        match_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (my_listener != null) {
                    my_listener.listener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return l_model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public ViewHolder(CardView cardView) { //for onclick on card and shiz but thats where i was wrong -_- woahh
            super(cardView);
            this.cardView = cardView;

        }
    }
}
