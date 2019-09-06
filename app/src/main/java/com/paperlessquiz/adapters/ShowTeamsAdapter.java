package com.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.users.Team;

import java.util.ArrayList;

/**
 * This adapter is used by the QuizMaster to show the teams with their status + the number of non-blank answers resp. the nr of corrected answers they submitted for the round
 * that is passed as a parameter or set by the calling activity
 */
public class ShowTeamsAdapter extends RecyclerView.Adapter<ShowTeamsAdapter.ViewHolder> {
    private ArrayList<Team> teams;
    private ArrayList<Integer> nrOfAnswers;
    private int roundNr, nrOfQuestionsInThisRound;
    private ShowTeamsAdapter adapter;
    int colorAlert, colorNormal;

    public ShowTeamsAdapter(Context context, ArrayList<Team> teams, int roundNr, ArrayList<Integer> nrOfAnswers) {
        this.teams = teams;
        this.roundNr = roundNr;
        adapter = this;
        colorAlert = context.getResources().getColor(R.color.wrongRed);
        colorNormal = context.getResources().getColor(R.color.colorSecondaryText);
        this.nrOfAnswers = nrOfAnswers;
        this.nrOfQuestionsInThisRound = MyApplication.theQuiz.getRound(roundNr).getQuestions().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamName, tvTotalTimePaused, tvNrOfAnswers, tvTeamNr;
        ImageView ivPresent, ivAnswersSubmitted;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTeamName = itemView.findViewById(R.id.tvTeamName);
            tvTotalTimePaused = itemView.findViewById(R.id.tvTotalTimePaused);
            tvNrOfAnswers = itemView.findViewById(R.id.tvNrOfAnswers);
            tvTeamNr = itemView.findViewById(R.id.tvTeamNr);
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
        viewHolder.tvTeamName.setText(teams.get(i).getName());
        viewHolder.tvTeamNr.setText(teams.get(i).getUserNr() + "");
        if (teams.size() == nrOfAnswers.size()) {
            viewHolder.tvNrOfAnswers.setText(nrOfAnswers.get(i).toString());
            if (nrOfQuestionsInThisRound > (int) nrOfAnswers.get(i)) {
                viewHolder.ivAnswersSubmitted.setImageResource(R.drawable.circle_red);
            } else {
                viewHolder.ivAnswersSubmitted.setImageResource(R.drawable.circle_green);
            }
        } else {
            viewHolder.tvNrOfAnswers.setText("#");
        }
        int totalPause = teams.get(i).getTotalTimePaused();
        viewHolder.tvTotalTimePaused.setText(totalPause + "");
        if (totalPause > QuizDatabase.MAX_ALLOWED_PAUSE) {
            viewHolder.tvTotalTimePaused.setTextColor(colorAlert);
            viewHolder.tvTeamName.setTextColor(colorAlert);
        } else {
            viewHolder.tvTotalTimePaused.setTextColor(colorNormal);
            viewHolder.tvTeamName.setTextColor(colorNormal);
        }
        //boolean answersForThisRoundSubmitted = teams.get(i).isAnswersForThisRoundSubmitted(roundNr);
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
        nrOfQuestionsInThisRound = MyApplication.theQuiz.getRound(roundNr).getQuestions().size();
    }

    public void setNrOfAnswers(ArrayList<Integer> nrOfAnswers) {
        this.nrOfAnswers = nrOfAnswers;
    }
}
