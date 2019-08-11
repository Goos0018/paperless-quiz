package com.paperlessquiz.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paperlessquiz.R;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.paperlessquiz.users.Team;

import java.util.ArrayList;

/**
 * Used by the receptionist to view and edit team names/statuses + buy and refund bonnekes
 */
public class EditTeamsAdapter extends RecyclerView.Adapter<EditTeamsAdapter.ViewHolder> {
    private ArrayList<Team> teams;
    private EditTeamsAdapter adapter;
    private QuizLoader quizLoader;
    private Context context;
    private int nrOfBonnekes;
    private int thisTeamID;

    public EditTeamsAdapter(Context context, ArrayList<Team> teams, QuizLoader quizLoader) {
        this.teams = teams;
        this.context = context;
        adapter = this;
        this.quizLoader = quizLoader;
    }

    //This represents all fields for one item in the list
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTeamNr, tvTeamName, tvTotalAmount, tvRemaining;
        EditText etTeamName;
        ImageView ivPresent, ivBonnekes;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTeamNr = itemView.findViewById(R.id.tvTeamNr);
            tvTeamName = itemView.findViewById(R.id.tvTeamName);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
            tvRemaining = itemView.findViewById(R.id.tvRemaining);
            etTeamName = itemView.findViewById(R.id.etTeamName);
            ivPresent = itemView.findViewById(R.id.ivTeamPresent);
            ivBonnekes = itemView.findViewById(R.id.ivBonnekes);
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
        viewHolder.tvTeamNr.setText(teams.get(i).getUserNr() + ".");
        viewHolder.etTeamName.setText(teams.get(i).getName());
        viewHolder.tvTeamName.setText(teams.get(i).getName());
        viewHolder.tvTotalAmount.setText("" + QuizDatabase.EURO_SIGN + teams.get(i).getUserCredits());
        viewHolder.tvRemaining.setText((teams.get(i).getUserCredits() - teams.get(i).getTotalSpent()) + "");
        if (teams.get(i).getUserStatus() == QuizDatabase.USERSTATUS_NOTPRESENT) {
            viewHolder.ivPresent.setImageResource(R.drawable.team_not_present);

        } else {
            viewHolder.ivPresent.setImageResource(R.drawable.team_present);
        }
        //Make team name field editable when clicked
        viewHolder.tvTeamName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                viewHolder.tvTeamName.setVisibility(View.GONE);
                viewHolder.etTeamName.setVisibility(View.VISIBLE);
            }
        });
        //Toggle isPresent from true to false when clicking the team status indicator + send the update (including possible the new name) to the db
        viewHolder.ivPresent.setOnClickListener(new View.OnClickListener()
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
                //Send the update to the database
                quizLoader.updateTeam(teams.get(i).getUserNr(), teams.get(i).getUserStatus(), teams.get(i).getName());
                viewHolder.tvTeamName.setVisibility(View.VISIBLE);
                viewHolder.etTeamName.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });
        //Buy or rezfund bonnekes when clicking the Euro sign
        viewHolder.ivBonnekes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bonnekes voor " + teams.get(i).getName());
                thisTeamID = teams.get(i).getIdUser();
                int currentAmount = teams.get(i).getUserCredits() - teams.get(i).getTotalSpent();
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nrOfBonnekes = Integer.valueOf(input.getText().toString());
                        if ((currentAmount + nrOfBonnekes) < 0) {
                            Toast.makeText(context, "Error: saldo kan niet negatief zijn. Huidig saldo = " + currentAmount, Toast.LENGTH_LONG).show();
                        } else {
                            quizLoader.buyBonnekes(thisTeamID, nrOfBonnekes);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
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
