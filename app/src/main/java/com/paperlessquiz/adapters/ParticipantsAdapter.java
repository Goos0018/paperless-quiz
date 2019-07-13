package com.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paperlessquiz.R;
import com.paperlessquiz.loginentity.Team;
import com.paperlessquiz.quiz.QuizGenerator;

import java.util.ArrayList;

public class ParticipantsAdapter extends ArrayAdapter<Team> {

    private final Context context;

    public ParticipantsAdapter(Context context, ArrayList<Team> list) {
        //Construct a list from the Hashmap
        super(context, R.layout.row_layout_select_login_name, list);
        this.context = context;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_select_login_name, parent, false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvType = (TextView) rowView.findViewById(R.id.tvType);
        //ImageView ivleft = (ImageView) rowView.findViewById(R.id.ivQuizLogo);
        tvName.setText(getItem(position).getName());
        //tvType.setText(this.getItem(position).getType());
        if (getItem(position).getType().equals(QuizGenerator.TYPE_PARTICIPANT)) {
            tvType.setText("Team " + getItem(position).getIdUser());
        } else {
            tvType.setText(this.getItem(position).getType());
        }
        return rowView;
    }

}
