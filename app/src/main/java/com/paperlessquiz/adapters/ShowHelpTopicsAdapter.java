package com.paperlessquiz.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.quiz.HelpTopic;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

import java.util.ArrayList;

public class ShowHelpTopicsAdapter extends RecyclerView.Adapter<ShowHelpTopicsAdapter.ViewHolder> {
    private ArrayList<HelpTopic> helpTopics;
    private int previousPosition = -1;
    private int currentPosition = -1;
    int userType, helpType;

    Context context;
    QuizLoader quizLoader;

    public ShowHelpTopicsAdapter(Context context, ArrayList<HelpTopic> helpTopics, int helpType) {
        this.helpTopics = helpTopics;
        this.context = context;
        this.quizLoader = new QuizLoader(context);
        userType = MyApplication.theQuiz.getThisUser().getUserType();
        this.helpType = helpType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRemark, tvResponse;
        CardView cvCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            tvResponse = itemView.findViewById(R.id.tvResponse);
            cvCardView = itemView.findViewById(R.id.cvCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //reset the background of the previously clicked item
                    currentPosition = getAdapterPosition();
                    notifyItemChanged(currentPosition);
                    if (previousPosition >= 0) {
                        notifyItemChanged(previousPosition);
                    }
                    previousPosition = currentPosition;
                    //If this is the Juror or the bar responsible treating incoming messages, allow them to answer them.
                    if ((userType == QuizDatabase.USERTYPE_JUROR && helpType == QuizDatabase.HELPTYPE_QUIZQUESTION) ||
                            (userType == QuizDatabase.USERTYPE_BARRESPONSIBLE && helpType == QuizDatabase.HELPTYPE_ORDERQUESTION)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        String title = "Antwoord";
                        builder.setTitle(title);
                        final TextView tvRemark = new TextView(context);
                        tvRemark.setText(helpTopics.get(currentPosition).getRemark());
                        final EditText input = new EditText(context);
                        input.setText(helpTopics.get(currentPosition).getResponse());
                        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        builder.setView(tvRemark);
                        builder.setView(input);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String response = input.getText().toString();
                                quizLoader.answerRemark(helpTopics.get(currentPosition).getIdHelpTopic(), response);
                                helpTopics.get(currentPosition).setResponse(response);
                                notifyItemChanged(currentPosition);
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
                }
            });
        }
    }

    @NonNull
    @Override
    public ShowHelpTopicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_helptopic, viewGroup, false);
        return new ShowHelpTopicsAdapter.ViewHolder(v);
    }

    //This runs for every item in your list
    @Override
    public void onBindViewHolder(@NonNull ShowHelpTopicsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(helpTopics.get(i));
        viewHolder.tvRemark.setText(helpTopics.get(i).getRemark());
        viewHolder.tvResponse.setText(helpTopics.get(i).getResponse());
        if (currentPosition == i) {
            viewHolder.tvResponse.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvResponse.setVisibility(View.GONE);
        }
        //If this is the Juror or the bar responsible treating incoming messages, always show the answers
        if ((userType == QuizDatabase.USERTYPE_JUROR && helpType == QuizDatabase.HELPTYPE_QUIZQUESTION) ||
                (userType == QuizDatabase.USERTYPE_BARRESPONSIBLE && helpType == QuizDatabase.HELPTYPE_ORDERQUESTION)) {
            if (!helpTopics.get(i).getResponse().equals("")) {
                viewHolder.tvResponse.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvResponse.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return helpTopics.size();
    }

    public void setHelpTopics(ArrayList<HelpTopic> topics) {
        this.helpTopics = topics;
    }
}
