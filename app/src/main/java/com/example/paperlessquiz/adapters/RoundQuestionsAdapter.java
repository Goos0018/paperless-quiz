package com.example.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paperlessquiz.R;
import com.example.paperlessquiz.question.Question;

import java.util.ArrayList;

/*
public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
        holder = new ViewHolder();
        convertView = mInflater.inflate(R.layout.item, null);
        holder.caption = (EditText) convertView
        .findViewById(R.id.ItemCaption);
        convertView.setTag(holder);
        } else {
        holder = (ViewHolder) convertView.getTag();
        }
        //Fill EditText with the value you have in data source
        holder.caption.setText(((ListItem)myItems.get(position)).caption);
        holder.caption.setId(position);

        //we need to update adapter once we finish with editing
        holder.caption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus){
final int position = v.getId();
final EditText Caption = (EditText) v;
        ((ListItem) myItems.get(position)).caption = Caption.getText().toString();
        }
        }
        });

        return convertView;
        }

*/



public class RoundQuestionsAdapter extends ArrayAdapter<Question> {
    private final Context context;

    public RoundQuestionsAdapter(Context context ) {
        super(context, R.layout.row_layout_select_login_name, new ArrayList<Question>());
        this.context = context;
    }

    class ViewHolder
    {
        TextView question;
        EditText answer;
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
            holder.answer = (EditText)convertView.findViewById(R.id.etAnswer);
            holder.question=(TextView) convertView.findViewById(R.id.tvQuestion);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.question.setText(getItem(position).getQuestion());
        holder.answer.setText(getItem(position).getThisAnswer());
        holder.answer.setId(position);
        holder.answer.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    final int position = v.getId();
                    final EditText Answer = (EditText) v;
                    getItem(position).setThisAnswer(Answer.getText().toString());
                }
            }
        });

       /* LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_question,parent,false);

        TextView tvQuestion = (TextView) rowView.findViewById(R.id.tvQuestion);

        tvQuestion.setText(getItem(position).getQuestion());
        */

        return convertView;
    }

}
