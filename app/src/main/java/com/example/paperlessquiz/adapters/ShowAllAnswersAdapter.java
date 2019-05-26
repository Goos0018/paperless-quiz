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
    private ShowAllAnswersAdapter adapter;

    public ShowAllAnswersAdapter(Context context, ArrayList<Answer> answers) {
        super(context, R.layout.row_layout_correct_answers,answers);
        this.context = context;
        this.answers = answers;
        adapter = this;
    }

    //Viewholder is an object that holds everything that is displayed in the ListView
    class ViewHolder
    {
        TextView tvAnswer, tvResult;
        Button btnToggle;
        //ImageView score;
    }

/*
    @NonNull
    @Override
    // This runs for every item in the view

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_correct_answers,parent,false);
        TextView tvAnswer = (TextView) rowView.findViewById(R.id.tvAnswer);
        TextView tvResult = (TextView) rowView.findViewById(R.id.tvResult);
        Button btnIsCorrect = (Button) rowView.findViewById(R.id.btnToggle);
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
*/

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout_correct_answers, null);
            holder=new ViewHolder();
            holder.tvAnswer = (TextView) convertView.findViewById(R.id.tvAnswer);
            holder.tvResult = (TextView) convertView.findViewById(R.id.tvResult);
            //holder.score=(ImageView) convertView.findViewById(R.id.ivShowScore);
            //holder.btnFalse = (Button) convertView.findViewById(R.id.btnFalse);
            holder.btnToggle = (Button) convertView.findViewById(R.id.btnTrue);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        //holder.question.setText(getItem(position).getQuestion());
        holder.tvAnswer.setText(getItem(position).getThisAnswer());
        holder.tvResult.setText(getItem(position).isCorrect() + " ");
        holder.btnToggle.setId(position);

        holder.btnToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                //final ImageView Answer = (EditText) v;
                getItem(position).setCorrect(!getItem(position).isCorrect());
                holder.tvResult.setText(getItem(position).isCorrect() + " ");
                //This adds the answer to the array holding all answers, so we have access to them when submitting
                //Answer thisAnswer = new Answer(position+1,Answer.getText().toString());
                //myAnswers.remove(position);
                //answers.set(position,thisAnswer);
                for (int i = 0; i < answers.size(); i++) {
                    if (answers.get(i).getThisAnswer().equals(getItem(position).getThisAnswer())){
                        answers.get(i).setCorrect(getItem(position).isCorrect());
                    }
                }
                adapter.notifyDataSetChanged();
            }

        });

        return convertView;
    }
}
