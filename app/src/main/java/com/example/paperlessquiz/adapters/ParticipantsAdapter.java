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
import com.example.paperlessquiz.loginentity.LoginEntity;

import java.util.ArrayList;

public class ParticipantsAdapter extends ArrayAdapter<LoginEntity> {

    private final Context context;

    public ParticipantsAdapter(Context context, ArrayList<LoginEntity> list) {
        //Construct a list from the Hashmap
        super(context, R.layout.row_layout_select_login_name, list);
        this.context = context;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_select_login_name,parent,false);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvType = (TextView) rowView.findViewById(R.id.tvType);
        //ImageView ivleft = (ImageView) rowView.findViewById(R.id.ivQuizLogo);

        tvName.setText(getItem(position).getName());
        tvType.setText(this.getItem(position).getType());
        //ivleft.setImageResource(R.mipmap.placeholder);
        return rowView;
    }

}
