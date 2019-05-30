package com.example.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paperlessquiz.R;
import com.example.paperlessquiz.answer.Answer;

import java.util.ArrayList;

public class DisplayAnswersAdapter extends RecyclerView.Adapter<DisplayAnswersAdapter.ViewHolder> {
    private ArrayList<Answer> answers;

    public DisplayAnswersAdapter(Context context, ArrayList<Answer> answers) {
        this.answers = answers;
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
        viewHolder.itemView.setTag(answers.get(i));
        viewHolder.tvQuestionID.setText(answers.get(i).getQuestionNr()+"");
        viewHolder.tvDisplayAnswer.setText(answers.get(i).getThisAnswer());
        boolean isCorrect = answers.get(i).isCorrect();
        boolean isCorrected = answers.get(i).isCorrected();
        if (isCorrected){
            if (isCorrect){
                viewHolder.ivIsCorrect.setImageResource(R.drawable.answer_ok);
            }
            else
            {
                viewHolder.ivIsCorrect.setImageResource(R.drawable.answer_nok);
            }}
        else{
            viewHolder.ivIsCorrect.setImageResource(R.drawable.answer_notcorrected);
        }
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
