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
import com.paperlessquiz.question.Question;

import java.util.ArrayList;

/**
 *  20190728: Used by the participant to display the answers the user has given
 */
public class DisplayAnswersAdapter extends RecyclerView.Adapter<DisplayAnswersAdapter.ViewHolder> {
    private ArrayList<Question> questions;
private int teamNr;

    public DisplayAnswersAdapter(Context context, ArrayList<Question> questions,int teamNr) {
        this.questions = questions;
        this.teamNr=teamNr;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionID, tvDisplayAnswer;
        ImageView ivIsCorrect;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuestionID = itemView.findViewById(R.id.tvQuestionID);
            tvDisplayAnswer = itemView.findViewById(R.id.tvDisplayAnswer);
            ivIsCorrect = itemView.findViewById(R.id.ivIsCorrect);
        }
    }

    @NonNull
    @Override
    public DisplayAnswersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_display_answer, viewGroup, false);
        return new ViewHolder(v);
    }

    //This runs for every item in your list
    @Override
    public void onBindViewHolder(@NonNull DisplayAnswersAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(questions.get(i));
        viewHolder.tvQuestionID.setText(Integer.toString(questions.get(i).getQuestionNr()));
        viewHolder.tvDisplayAnswer.setText(questions.get(i).getAnswerForTeam(teamNr).getTheAnswer());
        boolean isCorrect = questions.get(i).getAnswerForTeam(teamNr).isCorrect();
        boolean isCorrected = questions.get(i).getAnswerForTeam(teamNr).isCorrected();
        if (isCorrected){
            if (isCorrect){
                viewHolder.ivIsCorrect.setImageResource(R.drawable.answer_ok);
            }
            else
            {
                viewHolder.ivIsCorrect.setImageResource(R.drawable.answer_nok);
            }}
        else{
            viewHolder.ivIsCorrect.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void setAnswers(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
