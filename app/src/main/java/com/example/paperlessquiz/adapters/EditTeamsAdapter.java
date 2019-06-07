package com.example.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paperlessquiz.R;
import com.example.paperlessquiz.loginentity.LoginEntity;

import java.util.ArrayList;

public class EditTeamsAdapter extends RecyclerView.Adapter<EditTeamsAdapter.ViewHolder> {
    private ArrayList<LoginEntity> teams;
    private EditTeamsAdapter adapter;


    public EditTeamsAdapter(Context context, ArrayList<LoginEntity> teams) {
        this.teams = teams;
        adapter = this;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamNr;
        EditText etTeamName;
        ImageView ivPresent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTeamNr = itemView.findViewById(R.id.tvTeamNr);
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
        viewHolder.tvTeamNr.setText(teams.get(i).getId() + ".");
        viewHolder.etTeamName.setText(teams.get(i).getName());
        boolean isPresent = teams.get(i).isPresent();
        if (isPresent) {
                viewHolder.ivPresent.setImageResource(R.drawable.team_present);
        } else {
            viewHolder.ivPresent.setImageResource(R.drawable.team_not_present);
        }

        viewHolder.ivPresent.setOnClickListener(new View.OnClickListener()
                //Toggle isPresent from true to false
        {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                teams.get(i).setPresent(!isPresent);
                teams.get(i).setName(viewHolder.etTeamName.getText().toString().trim());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void setTeams(ArrayList<LoginEntity> teams) {
        this.teams = teams;
    }
}
