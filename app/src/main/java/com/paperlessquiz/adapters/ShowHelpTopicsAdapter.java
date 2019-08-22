package com.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paperlessquiz.R;
import com.paperlessquiz.quiz.HelpTopic;

import java.util.ArrayList;

public class ShowHelpTopicsAdapter extends RecyclerView.Adapter<ShowHelpTopicsAdapter.ViewHolder> {
    private ArrayList<HelpTopic> helpTopics;
    private int previousPosition=-1;
    private int currentPosition=-1;
    //int colorWhenClicked, colorWhenNotClicked;

    public ShowHelpTopicsAdapter(Context context, ArrayList<HelpTopic> helpTopics) {
        this.helpTopics = helpTopics;
        //colorWhenClicked= context.getResources().getColor(R.color.colorDivider);
        //colorWhenNotClicked= context.getResources().getColor(R.color.white);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRemark, tvResponse;
        CardView cvCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRemark = itemView.findViewById(R.id.tvRemark);
            tvResponse = itemView.findViewById(R.id.tvResponse);
            cvCardView=itemView.findViewById(R.id.cvCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //reset the background of the previously clicked item
                    currentPosition = getAdapterPosition();
                    notifyItemChanged(currentPosition);
                    if (previousPosition >= 0){
                    notifyItemChanged(previousPosition);}
                    //cvCardView.setCardBackgroundColor(colorWhenClicked);
                    previousPosition = currentPosition;
                }
            });
        }
    }

    @NonNull
    @Override
    public ShowHelpTopicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_helptopic, viewGroup, false);
        return new ShowHelpTopicsAdapter.ViewHolder(v);
    }

    //This runs for every item in your list
    @Override
    public void onBindViewHolder(@NonNull ShowHelpTopicsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(helpTopics.get(i));
        viewHolder.tvRemark.setText(helpTopics.get(i).getRemark());
        viewHolder.tvResponse.setText(helpTopics.get(i).getResponse());
        if (currentPosition==i){
            viewHolder.tvResponse.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.tvResponse.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return helpTopics.size();
    }

    public void setHelpTopics(ArrayList<HelpTopic> topics) {
        this.helpTopics = topics;
    }
}
