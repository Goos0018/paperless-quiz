package com.paperlessquiz.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    public static final int  SMALLTEXTSIZE = 14;
    public static final int MEDIUMTEXTSIZE = 18;
    public static final int LARGETEXTSIZE = 22;
    public static final int HUGETEXTSTYLE = 26;
    int currentSize = SMALLTEXTSIZE;

    public DisplayAnswersAdapter(Context context, ArrayList<Question> questions, int teamNr) {
        this.questions = questions;
        this.teamNr = teamNr;
        this.context = context;
        this.quizLoader = new QuizLoader(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestionID, tvDisplayAnswer, tvSchiftingsAnswer;
        ImageView ivIsCorrect;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuestionID = itemView.findViewById(R.id.tvQuestionID);
            tvDisplayAnswer = itemView.findViewById(R.id.tvDisplayAnswer);
            tvSchiftingsAnswer = itemView.findViewById(R.id.tvSchiftingsAnswer);
            ivIsCorrect = itemView.findViewById(R.id.ivIsCorrect);
        }
        public void setSize(){
            float size;
            switch (currentSize){
                case SMALLTEXTSIZE:
                    size = context.getResources().getDimension(R.dimen.text_size_small);
                    break;
                case MEDIUMTEXTSIZE:
                    size = context.getResources().getDimension(R.dimen.text_size_medium);
                    break;
                case LARGETEXTSIZE:
                    size = context.getResources().getDimension(R.dimen.text_size_large);
                    break;
                case HUGETEXTSTYLE:
                    size = context.getResources().getDimension(R.dimen.text_size_huge);
                    break;
                default:
                    size = context.getResources().getDimension(R.dimen.text_size_small);
            }

            tvQuestionID.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
            tvDisplayAnswer.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
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
        viewHolder.setSize();
        viewHolder.itemView.setTag(questions.get(i));
        viewHolder.tvQuestionID.setText(Integer.toString(questions.get(i).getQuestionNr()) + ".");
        viewHolder.tvDisplayAnswer.setText(questions.get(i).getAnswerForTeam(teamNr).getTheAnswer());
        boolean isSubmitted = questions.get(i).getAnswerForTeam(teamNr).isSubmitted();
        boolean isCorrect = questions.get(i).getAnswerForTeam(teamNr).isCorrect();
        boolean isCorrected = questions.get(i).getAnswerForTeam(teamNr).isCorrected();
        int questionType = questions.get(i).getQuestionType();
        if (isCorrected) {
            //The question is corrected
            if (questionType == 1) {
                //This is a Schiftingsvraag - hide the icon and display N/A
                viewHolder.ivIsCorrect.setImageResource(R.drawable.blanc);
                viewHolder.tvSchiftingsAnswer.setVisibility(View.VISIBLE);
                viewHolder.tvSchiftingsAnswer.setText("N/A");
            } else {
                //This is a normal question - show an icon indicating if it was right or wrong
                if (isCorrect) {
                    viewHolder.ivIsCorrect.setImageResource(R.drawable.answer_ok);
                } else {
                    viewHolder.ivIsCorrect.setImageResource(R.drawable.answer_nok);
                }
            }
        } else {
            //The question is not corrected - check if the answer was submitted
            if (!isSubmitted) {
                //Answer is not submitted - display alert icon
                viewHolder.ivIsCorrect.setImageResource(R.drawable.answer_not_submitted);
            } else {
                //Answer submitted but not corrected yet - display nothing
                viewHolder.ivIsCorrect.setVisibility(View.INVISIBLE);
            }
        }

        viewHolder.ivIsCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String title = "Ronde " + questions.get(i).getRoundNr()  + " - Vraag " + questions.get(i).getQuestionNr();
                String intro = "Ploeg " + teamNr + " - ";
                builder.setTitle(title);
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String remark = input.getText().toString();
                        quizLoader.submitRemark(QuizDatabase.HELPTYPE_QUIZQUESTION, intro + title + ": " + remark);
                        //Dismiss the keyboard explicitly
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
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

    //Toggle text size from small to large and back
    public void toggleTextSize(){
        switch (currentSize){
            case SMALLTEXTSIZE:
                currentSize = MEDIUMTEXTSIZE;
                break;
            case MEDIUMTEXTSIZE:
                currentSize = LARGETEXTSIZE;
                break;
            case LARGETEXTSIZE:
                currentSize=HUGETEXTSTYLE;
                break;
            case HUGETEXTSTYLE:
                currentSize=SMALLTEXTSIZE;
                break;
            default:
                currentSize=SMALLTEXTSIZE;
        }
        this.notifyDataSetChanged();
    }

}
