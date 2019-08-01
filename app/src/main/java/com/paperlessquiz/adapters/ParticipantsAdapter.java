package com.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paperlessquiz.R;
import com.paperlessquiz.users.User;

import java.util.ArrayList;

/**
 * Show a list of all teams so users can select their team
 * Used also for the organizers
 */
public class ParticipantsAdapter extends ArrayAdapter<User> {

    private final Context context;

    public ParticipantsAdapter(Context context, ArrayList<User> list) {
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
        tvName.setText(getItem(position).getName());
        tvType.setText(getItem(position).getDescription());
        return rowView;
    }
}
