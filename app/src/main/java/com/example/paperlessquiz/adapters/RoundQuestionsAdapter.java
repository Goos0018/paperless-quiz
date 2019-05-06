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
import com.example.paperlessquiz.question.Question;

import java.util.ArrayList;

public class RoundQuestionsAdapter extends ArrayAdapter<Question> {
    private final Context context;

    public RoundQuestionsAdapter(Context context ) {
        super(context, R.layout.row_layout_select_login_name, new ArrayList<Question>());
        this.context = context;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_question,parent,false);

        TextView tvQuestion = (TextView) rowView.findViewById(R.id.tvQuestion);

        tvQuestion.setText(getItem(position).getQuestion());

        return rowView;
    }

}
