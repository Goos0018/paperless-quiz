package com.example.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.paperlessquiz.R;
import com.example.paperlessquiz.answer.Answer;

import java.util.ArrayList;

public class ShowAllAnswersAdapter extends ArrayAdapter<Answer> {

    public final Context context;
    private final ArrayList<Answer> answers;

    public ShowAllAnswersAdapter(Context context, ArrayList<Answer> answers) {
        super(context, R.layout.row_layout_correct_answers,answers);
        this.context = context;
        this.answers = answers;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_correct_answers,parent,false);
        TextView tvAnswer = (TextView) rowView.findViewById(R.id.tvAnswer);
        TextView tvResult = (TextView) rowView.findViewById(R.id.tvResult);
        Button btnIsCorrect = (Button) rowView.findViewById(R.id.btnTrue);
        Button btnIsFalse = (Button) rowView.findViewById(R.id.btnFalse);


        tvAnswer.setText(getItem(position).getThisAnswer());
        tvResult.setText(getItem(position).isCorrect()+"");
        btnIsCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem(position).setCorrect(true);
            }
        });
        btnIsFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem(position).setCorrect(false);
            }
        });
        return rowView;
    }


    /*
    //Viewholder is an object that holds everything that is displayed in the ListView
    class ViewHolder
    {
        TextView thisAnswer;
        ImageView score;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout_question, null);
            holder=new ViewHolder();
            holder.thisAnswer = (TextView) convertView.findViewById(R.id.tvAnswer);
            //holder.result = (TextView) convertView.findViewById(R.id.tvResult);
            holder.score=(ImageView) convertView.findViewById(R.id.ivShowScore);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        //holder.question.setText(getItem(position).getQuestion());
        holder.thisAnswer.setText(getItem(position).getThisAnswer());
        holder.score.setId(position);

        holder.score.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                final ImageView Answer = (EditText) v;
                getItem(position).setThisAnswer(Answer.getText().toString());
                //This adds the answer to the array holding all answers, so we have access to them when submitting
                Answer thisAnswer = new Answer(position+1,Answer.getText().toString());
                //myAnswers.remove(position);
                myAnswers.set(position,thisAnswer);

            }

        });

        return convertView;
    }
    */
}
