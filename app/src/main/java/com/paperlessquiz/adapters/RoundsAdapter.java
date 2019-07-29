package com.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.paperlessquiz.round.Round;

import java.util.ArrayList;

public class RoundsAdapter extends ArrayAdapter<Round> {

    public final Context context;
    //private final ArrayList<Round> rounds;
    private RoundsAdapter adapter;
    private QuizLoader quizLoader;

    public RoundsAdapter(Context context, ArrayList<Round> rounds) {
        super(context, R.layout.row_layout_show_rounds,rounds);
        this.context = context;
        //this.rounds = rounds;
        adapter = this;
        this.quizLoader=new QuizLoader(context);
    }

    //Viewholder is an object that holds everything that is displayed in the ListView
    class ViewHolder
    {
        TextView tvRoundName,tvRoundDescription;
        ImageView ivRoundStatus;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout_show_rounds, null);
            holder=new ViewHolder();
            holder.tvRoundName = (TextView) convertView.findViewById(R.id.tvRndName);
            holder.tvRoundDescription = (TextView) convertView.findViewById(R.id.tvRndDescription);
            holder.ivRoundStatus = (ImageView) convertView.findViewById(R.id.ivRoundStatus);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tvRoundName.setText(getItem(position).getName());
        holder.tvRoundDescription.setText(getItem(position).getDescription());

        switch (getItem(position).getRoundStatus()){
            case QuizDatabase.ROUNDSTATUS_CLOSED:
                holder.ivRoundStatus.setImageResource(R.drawable.rnd_not_yet_open);
                break;
            case QuizDatabase.ROUNDSTATUS_OPENFORANSWERS:
                holder.ivRoundStatus.setImageResource(R.drawable.rnd_open);
                break;
            case QuizDatabase.ROUNDSTATUS_OPENFORCORRECTIONS:
                holder.ivRoundStatus.setImageResource(R.drawable.rnd_closed);
                break;
            case QuizDatabase.ROUNDSTATUS_CORRECTED:
                holder.ivRoundStatus.setImageResource(R.drawable.rnd_corrected);
                break;
        }

        holder.ivRoundStatus.setId(position);

        holder.ivRoundStatus.setOnClickListener(new View.OnClickListener()
                //Loop through the round Statuses
        {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                if (getItem(position).getRoundStatus() == QuizDatabase.ROUNDSTATUS_CORRECTED){getItem(position).setRoundStatus(QuizDatabase.ROUNDSTATUS_CLOSED);}
                else
                {
                    getItem(position).setRoundStatus(getItem(position).getRoundStatus() + 1);
                }
                quizLoader.updateRoundStatus(getItem(position).getRoundNr(),getItem(position).getRoundStatus());
                adapter.notifyDataSetChanged();
            }

        });

        return convertView;
    }
}
