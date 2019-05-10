package com.example.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.paperlessquiz.R;
import com.example.paperlessquiz.answer.Answer;

import java.util.ArrayList;

public class ReviewAnswersAdapter  extends ArrayAdapter<Answer> {
    public final Context context;
    private final ArrayList<Answer> answers;

    public ReviewAnswersAdapter(Context context, ArrayList<Answer> answers) {
        super(context, R.layout.row_layout_display_answer,answers);
        this.context = context;
        this.answers = answers;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_display_answer,parent,false);
        TextView tvDisplayQuestion = (TextView) rowView.findViewById(R.id.tvDisplayQuestionID);
        TextView tvDisplayAnswer = (TextView) rowView.findViewById(R.id.tvDisplayAnswer);
        tvDisplayQuestion.setText("Question " + answers.get(position).getQuestionNr());
        tvDisplayAnswer.setText("Your answer: " + answers.get(position).getAnswer());
        return rowView;
    }
}
