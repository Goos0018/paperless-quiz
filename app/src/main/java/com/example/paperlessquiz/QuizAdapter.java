package com.example.paperlessquiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizAdapter extends ArrayAdapter<Quiz>
{
    private final Context context;
    private final ArrayList<Quiz> values;

    public QuizAdapter(Context context, ArrayList<Quiz> list) {
        super(context, R.layout.row_layout, list);
        this.context = context;
        this.values = list;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout,parent,false);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
        TextView tvDescription = (TextView) rowView.findViewById(R.id.tv_description);
        ImageView ivleft = (ImageView) rowView.findViewById(R.id.iv_left);

        tvTitle.setText(values.get(position).getTitle());
        tvDescription.setText(values.get(position).getDescription());

        if (values.get(position).isavailable())
        {
            ivleft.setImageResource(R.mipmap.placeholder);
        }
        else
        {
            ivleft.setImageResource(R.mipmap.placeholder);
        }


        return rowView;
    }
}
