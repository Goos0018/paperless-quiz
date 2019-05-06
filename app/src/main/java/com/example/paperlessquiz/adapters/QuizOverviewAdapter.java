package com.example.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paperlessquiz.R;
import com.example.paperlessquiz.round.Round;

import java.util.ArrayList;

public class QuizOverviewAdapter extends ArrayAdapter<Round>
{
    private final Context context;
    //private final ArrayList<Round> values;

    public QuizOverviewAdapter(Context context) {
        super(context, R.layout.row_layout_dpa_show_quiz_overview, new ArrayList<Round>());
        this.context = context;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_dpa_show_quiz_overview,parent,false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tv_round_name);
        TextView tvDescription = (TextView) rowView.findViewById(R.id.tv_round_description);
        TextView tvNrOfQuestions = (TextView) rowView.findViewById(R.id.tv_round_nr_of_questions);
        TextView tvAcceptsAnswers = (TextView) rowView.findViewById(R.id.tv_round_accepts_answers);

        ImageView ivleft = (ImageView) rowView.findViewById(R.id.iv_left);

        tvName.setText(getItem(position).getName());
        tvDescription.setText(getItem(position).getDescription());

        tvNrOfQuestions.setText(getItem(position).getNrOfQuestions()+"");
        tvAcceptsAnswers.setText(this.getItem(position).AcceptsAnswers() + "");
        ivleft.setImageResource(R.mipmap.placeholder);
        return rowView;
    }
}
