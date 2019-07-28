package com.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.R;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.users.Team;

import java.util.ArrayList;

public class EditTeamsAdapter extends RecyclerView.Adapter<EditTeamsAdapter.ViewHolder> {
    private ArrayList<Team> teams;
    private EditTeamsAdapter adapter;


    public EditTeamsAdapter(Context context, ArrayList<Team> teams) {
        this.teams = teams;
        adapter = this;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamNr, tvTeamName;
        EditText etTeamName;
        ImageView ivPresent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTeamNr = itemView.findViewById(R.id.tvTeamNr);
            tvTeamName = itemView.findViewById(R.id.tvTeamName);
            etTeamName = itemView.findViewById(R.id.etTeamName);
            ivPresent = itemView.findViewById(R.id.ivTeamPresent);
        }
    }


    @NonNull
    @Override
    public EditTeamsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_edit_teams, viewGroup, false);
        return new EditTeamsAdapter.ViewHolder(v);
    }

    //This runs for every item in your list
    @Override
    public void onBindViewHolder(@NonNull EditTeamsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(teams.get(i));
        viewHolder.tvTeamNr.setText(teams.get(i).getIdUser() + ".");
        viewHolder.etTeamName.setText(teams.get(i).getName());
        viewHolder.tvTeamName.setText(teams.get(i).getName());
        if (teams.get(i).getUserStatus() == QuizDatabase.USERSTATUS_NOTPRESENT) {
            viewHolder.ivPresent.setImageResource(R.drawable.team_not_present);

        } else {
            viewHolder.ivPresent.setImageResource(R.drawable.team_present);
        }

        viewHolder.tvTeamName.setOnClickListener(new View.OnClickListener()
                //Make field editable when clicked
        {
            @Override
            public void onClick(View view) {
                viewHolder.tvTeamName.setVisibility(View.GONE);
                viewHolder.etTeamName.setVisibility(View.VISIBLE);
            }
        });

        viewHolder.ivPresent.setOnClickListener(new View.OnClickListener()
                //Toggle isPresent from true to false
        {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                if (teams.get(i).getUserStatus() == QuizDatabase.USERSTATUS_NOTPRESENT) {
                    teams.get(i).setUserStatus(QuizDatabase.USERSTATUS_PRESENTNOTLOGGEDIN);
                } else {
                    teams.get(i).setUserStatus(QuizDatabase.USERSTATUS_NOTPRESENT);
                }

                teams.get(i).setName(viewHolder.etTeamName.getText().toString().trim());
                viewHolder.tvTeamName.setVisibility(View.VISIBLE);
                viewHolder.etTeamName.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
}
