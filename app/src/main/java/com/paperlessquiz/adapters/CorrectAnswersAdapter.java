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
import com.paperlessquiz.quiz.Answer;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizLoader;

import java.util.ArrayList;

//This class is used to correct all answers given by the participating teams for a specific question
//Option to correct questions per team is also available
public class CorrectAnswersAdapter extends ArrayAdapter<Answer> {

    public final Context context;
    private final ArrayList<Answer> answers;
    private CorrectAnswersAdapter adapter;
    public boolean allAnswersCorrected;
    private boolean correctPerQuestion;
    private int thisRoundNr, thisTeamNr;
    private Quiz thisQuiz = MyApplication.theQuiz;
    private QuizLoader quizLoader;

    public CorrectAnswersAdapter(Context context, ArrayList<Answer> answers) {
        super(context, R.layout.row_layout_correct_answers, answers);
        this.context = context;
        this.answers = answers;
        adapter = this;
        quizLoader = new QuizLoader(context);
    }

    //Viewholder is an object that holds everything that is displayed in the ListView
    class ViewHolder {
        TextView tvAnswer;
        ImageView ivToggle;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout_correct_answers, null);
            holder = new ViewHolder();
            holder.tvAnswer = (TextView) convertView.findViewById(R.id.tvAnswer);
            holder.ivToggle = (ImageView) convertView.findViewById(R.id.ivToggle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String prefix = (position + 1) + ". ";
        holder.tvAnswer.setText(prefix + getItem(position).getTheAnswer());
        boolean isCorrect = getItem(position).isCorrect();
        boolean isCorrected = getItem(position).isCorrected();
        if (isCorrected) {
            if (isCorrect) {
                holder.ivToggle.setImageResource(R.drawable.answer_ok);
            } else {
                holder.ivToggle.setImageResource(R.drawable.answer_nok);
            }
        } else {
            holder.ivToggle.setImageResource(R.drawable.answer_notcorrected);
        }
        holder.ivToggle.setId(position);

        //Toggle isCorrect property of the answer from true to false + submit to database
        holder.ivToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                getItem(position).setCorrect(!isCorrect);
                getItem(position).setCorrected(true);
                String teamIds = "";
                int isAnswerCorrect;
                int idQuestion = thisQuiz.getQuestionID(thisRoundNr, getItem(position).getQuestionNr());
                if (getItem(position).isCorrect()) {
                    isAnswerCorrect = 1;
                } else {
                    isAnswerCorrect = 0;
                }
                //Loop over all answers and set the score for identical answers to the same score as this answer
                //only if we are correcting per question
                if (correctPerQuestion) {
                    for (int i = 0; i < answers.size(); i++) {
                        if (answers.get(i).getTheAnswer().equals(getItem(position).getTheAnswer())) {
                            answers.get(i).setCorrect(getItem(position).isCorrect());
                            answers.get(i).setCorrected(true);
                            teamIds = teamIds + thisQuiz.getTeam(i + 1).getIdUser() + ",";
                        }
                    }
                    teamIds = teamIds.substring(0, teamIds.length() - 1);
                } else {
                    teamIds += thisQuiz.getTeam(thisTeamNr).getIdUser();
                }
                quizLoader.correctQuestion(thisRoundNr, idQuestion, isAnswerCorrect, teamIds);
                allAnswersCorrected = true;
                for (int i = 0; i < answers.size(); i++) {
                    allAnswersCorrected = allAnswersCorrected && answers.get(i).isCorrected();
                }
                adapter.notifyDataSetChanged();
            }

        });

        return convertView;
    }

    public void setCorrectPerQuestion(boolean correctPerQuestion) {
        this.correctPerQuestion = correctPerQuestion;
    }

    public void setThisRoundNr(int thisRoundNr) {
        this.thisRoundNr = thisRoundNr;
    }

    public void setThisTeamNr(int thisTeamNr) {
        this.thisTeamNr = thisTeamNr;
    }
}
