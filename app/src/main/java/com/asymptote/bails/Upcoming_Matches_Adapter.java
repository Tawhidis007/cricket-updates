package com.asymptote.bails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Upcoming_Matches_Adapter extends RecyclerView.Adapter<Upcoming_Matches_Adapter.ViewHolder> {

    Context context;
    ArrayList<Upcoming_Matches_Model> u_model;

    public Upcoming_Matches_Adapter(Context context, ArrayList<Upcoming_Matches_Model> u_model) {
        this.context = context;
        this.u_model = u_model;
    }

    @NonNull
    @Override
    public Upcoming_Matches_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_card, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull Upcoming_Matches_Adapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView up_seriesname = cardView.findViewById(R.id.up_seriesname_tv);
        TextView up_team1_name = cardView.findViewById(R.id.up_team1_name);
        TextView up_team2_name = cardView.findViewById(R.id.up_team2_name);
        ImageView team1_pix = cardView.findViewById(R.id.team1_pix);
        ImageView team2_pix = cardView.findViewById(R.id.team2_pix);
        TextView time = cardView.findViewById(R.id.time);


        up_seriesname.setText(u_model.get(position).getSeries_name());
        up_team1_name.setText(u_model.get(position).getHome_team_name());
        up_team2_name.setText(u_model.get(position).getAway_team_name());
        time.setText(u_model.get(position).getTime());

        Picasso.get().load(u_model.get(position).getHome_team_url()).resize(50, 50)
                .into(team1_pix);
        Picasso.get().load(u_model.get(position).getAway_team_url()).resize(50, 50)
                .into(team2_pix);
    }

    @Override
    public int getItemCount() {
        return u_model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
