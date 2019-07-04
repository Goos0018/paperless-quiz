package com.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.R;
import com.paperlessquiz.loginentity.LoginEntity;

import java.util.ArrayList;

public class ShowTeamsAdapter extends RecyclerView.Adapter<ShowTeamsAdapter.ViewHolder> {
    private ArrayList<LoginEntity> teams;
    private int roundNr;
    private ShowTeamsAdapter adapter;


    public ShowTeamsAdapter(Context context, ArrayList<LoginEntity> teams, int roundNr) {
        this.teams = teams;
        this.roundNr = roundNr;
        adapter = this;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamName;
        ImageView ivPresent, ivAnswersSubmitted;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTeamName = itemView.findViewById(R.id.tvTeamName);
            ivPresent = itemView.findViewById(R.id.ivTeamPresent);
            ivAnswersSubmitted = itemView.findViewById(R.id.ivAnswersSubmitted);

        }
    }


    @NonNull
    @Override
    public ShowTeamsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_show_teams, viewGroup, false);
        return new ViewHolder(v);
    }

    //This runs for every item in your list
    @Override
    public void onBindViewHolder(@NonNull ShowTeamsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(teams.get(i));
        viewHolder.tvTeamName.setText(teams.get(i).getId() + ". " + teams.get(i).getName());
        boolean isPresent = teams.get(i).isPresent();
        boolean isLoggedIn = teams.get(i).isLoggedIn();
        boolean answersForThisRoundSubmitted = teams.get(i).isAnswersForThisRoundSubmitted(roundNr);
        if (isPresent) {
            if (isLoggedIn) {
                viewHolder.ivPresent.setImageResource(R.drawable.team_logged_in);
            } else {
                viewHolder.ivPresent.setImageResource(R.drawable.team_not_logged_in);
            }
        } else {
            viewHolder.ivPresent.setImageResource(R.drawable.team_not_present);
        }
        if (answersForThisRoundSubmitted) {
            viewHolder.ivAnswersSubmitted.setImageResource(R.drawable.answers_submitted);
        } else {
            viewHolder.ivAnswersSubmitted.setImageResource(R.drawable.answers_not_submitted);
        }

        /*
        viewHolder.ivPresent.setOnClickListener(new View.OnClickListener()
                //Toggle isPresent from true to false
        {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                teams.get(i).setPresent(!isPresent);
                adapter.notifyDataSetChanged();

            }


        });
        */
    }

        @Override
        public int getItemCount() {
            return teams.size();
        }

        public void setTeams (ArrayList < LoginEntity > teams) {
            this.teams = teams;
        }

    public void setRoundNr(int roundNr) {
        this.roundNr = roundNr;
    }
}
