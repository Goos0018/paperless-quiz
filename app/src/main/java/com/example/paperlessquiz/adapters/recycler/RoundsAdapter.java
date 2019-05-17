package com.example.paperlessquiz.adapters.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paperlessquiz.R;
import com.example.paperlessquiz.round.Round;

import java.util.ArrayList;

public class RoundsAdapter extends RecyclerView.Adapter<RoundsAdapter.ViewHolder> {
    private ArrayList<Round> rounds;
    ItemClicked activity;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }
    public RoundsAdapter(Context context,ArrayList<Round> list){
        rounds=list;
        activity=(ItemClicked) context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivLeft;
        TextView tvRoundName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoundName = itemView.findViewById(R.id.tv_round_name);
            ivLeft = itemView.findViewById(R.id.ivQuizLogo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    activity.onItemClicked(rounds.indexOf((Round)v.getTag()));
                }
            });
        }
    }
    @NonNull
    @Override
    public RoundsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //v refers to the row layout view given
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_round,viewGroup,false);
        return new ViewHolder(v);
    }

    //Runs for every item in the list
    @Override
    public void onBindViewHolder(@NonNull RoundsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(rounds.get(i));
        viewHolder.tvRoundName.setText(rounds.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return rounds.size();
    }
}
