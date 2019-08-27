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

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.quiz.Question;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

import java.util.ArrayList;

/**
 * 20190728: Used by the participant to display the answers the user has given. After they have been corrected, the user can submit remarks on a certain question
 */
public class DisplayAnswersAdapter extends RecyclerView.Adapter<DisplayAnswersAdapter.ViewHolder> {
    private ArrayList<Question> questions;
    private int teamNr;
    QuizLoader quizLoader;
    Context context;

    public DisplayAnswersAdapter(Context context, ArrayList<Question> questions, int teamNr) {
        this.questions = questions;
        this.teamNr = teamNr;
        this.context=context;
        this.quizLoader=new QuizLoader(context);
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
        if (isCorrected) {
            if (isCorrect) {
                viewHolder.ivIsCorrect.setImageResource(R.drawable.answer_ok);
            } else {
                viewHolder.ivIsCorrect.setImageResource(R.drawable.answer_nok);
            }
        } else {
            viewHolder.ivIsCorrect.setVisibility(View.INVISIBLE);
        }
        viewHolder.ivIsCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String title = "Opmerking bij vraag " + questions.get(i).getQuestionNr() + " Ronde " + questions.get(i).getRoundNr();
                builder.setTitle(title);
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String remark = input.getText().toString();
                        quizLoader.submitRemark(QuizDatabase.HELPTYPE_QUIZQUESTION,title + ": " + remark);
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
        return questions.size();
    }

    public void setAnswers(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
