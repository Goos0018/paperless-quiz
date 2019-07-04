package com.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.R;
import com.paperlessquiz.round.Round;

import java.util.ArrayList;

public class RoundsAdapter extends ArrayAdapter<Round> {

    public final Context context;
    //private final ArrayList<Round> rounds;
    private RoundsAdapter adapter;

    public RoundsAdapter(Context context, ArrayList<Round> rounds) {
        super(context, R.layout.row_layout_show_rounds,rounds);
        this.context = context;
        //this.rounds = rounds;
        adapter = this;
    }

    //Viewholder is an object that holds everything that is displayed in the ListView
    class ViewHolder
    {
        TextView tvRoundName,tvRoundDescription;
        ImageView ivRoundStatus;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout_show_rounds, null);
            holder=new ViewHolder();
            holder.tvRoundName = (TextView) convertView.findViewById(R.id.tvRndName);
            holder.tvRoundDescription = (TextView) convertView.findViewById(R.id.tvRndDescription);
            holder.ivRoundStatus = (ImageView) convertView.findViewById(R.id.ivRoundStatus);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tvRoundName.setText(getItem(position).getName());
        holder.tvRoundDescription.setText(getItem(position).getDescription());
        boolean acceptsAnswers = getItem(position).getAcceptsAnswers();
        boolean acceptsCorrections = getItem(position).getAcceptsCorrections();
        boolean isCorrected = getItem(position).isCorrected();
        if (!acceptsAnswers && !acceptsCorrections && !isCorrected){holder.ivRoundStatus.setImageResource(R.drawable.rnd_not_yet_open);}
        if (acceptsAnswers){holder.ivRoundStatus.setImageResource(R.drawable.rnd_open);}
        if (acceptsCorrections){holder.ivRoundStatus.setImageResource(R.drawable.rnd_closed);}
        if (isCorrected){holder.ivRoundStatus.setImageResource(R.drawable.rnd_corrected);}

        holder.ivRoundStatus.setId(position);

        holder.ivRoundStatus.setOnClickListener(new View.OnClickListener()
                //Toggle isCorrect from true to false
        {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                if (!acceptsAnswers && !acceptsCorrections && !isCorrected){getItem(position).setAcceptsAnswers(true);}
                if (acceptsAnswers && !acceptsCorrections && !isCorrected){getItem(position).setAcceptsAnswers(false);getItem(position).setAcceptsCorrections(true);}
                if (!acceptsAnswers && acceptsCorrections && !isCorrected){getItem(position).setAcceptsCorrections(false);getItem(position).setCorrected(true);}
                if (!acceptsAnswers && !acceptsCorrections && isCorrected){getItem(position).setCorrected(false);}
                adapter.notifyDataSetChanged();
            }

        });

        return convertView;
    }
}
