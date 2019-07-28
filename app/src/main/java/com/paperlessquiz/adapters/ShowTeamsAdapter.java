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
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.users.Team;

import java.util.ArrayList;

public class ShowTeamsAdapter extends RecyclerView.Adapter<ShowTeamsAdapter.ViewHolder> {
    private ArrayList<Team> teams;
    private int roundNr;
    private ShowTeamsAdapter adapter;


    public ShowTeamsAdapter(Context context, ArrayList<Team> teams, int roundNr) {
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
        viewHolder.tvTeamName.setText(teams.get(i).getIdUser() + ". " + teams.get(i).getName());
        boolean answersForThisRoundSubmitted = teams.get(i).isAnswersForThisRoundSubmitted(roundNr);
        switch (teams.get(i).getUserStatus()) {
            case QuizDatabase.USERSTATUS_NOTPRESENT:
                viewHolder.ivPresent.setImageResource(R.drawable.team_not_present);
                break;
            case QuizDatabase.USERSTATUS_PRESENTNOTLOGGEDIN:
                viewHolder.ivPresent.setImageResource(R.drawable.team_not_logged_in);
                break;
            case QuizDatabase.USERSTATUS_PRESENTLOGGEDIN:
                viewHolder.ivPresent.setImageResource(R.drawable.team_logged_in);
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

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public void setRoundNr(int roundNr) {
        this.roundNr = roundNr;
    }
}
