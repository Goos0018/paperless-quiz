package com.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.R;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.orders.OrderItem;
import com.paperlessquiz.quiz.QuizDatabase;

import java.util.ArrayList;

public class ShowOrderItemsAdapter extends RecyclerView.Adapter<ShowOrderItemsAdapter.ViewHolder> {
    private ArrayList<OrderItem> orderItems;
    private ShowOrderItemsAdapter adapter;
    private Order theOrder;

    public ShowOrderItemsAdapter(Context context, ArrayList<OrderItem> orderItems, Order theOrder) {
        this.orderItems = orderItems;
        adapter = this;
        this.theOrder = theOrder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName,tvItemDescription,tvItemCost;
        ImageView ivOneItemLess, ivOneItemMore;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            tvItemCost = itemView.findViewById(R.id.tvItemCost);
            ivOneItemMore=itemView.findViewById(R.id.ivOneItemMore);
            ivOneItemLess=itemView.findViewById(R.id.ivOneItemLess);
        }
    }


    @NonNull
    @Override
    public ShowOrderItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_order_item, viewGroup, false);
        return new ShowOrderItemsAdapter.ViewHolder(v);
    }

    //This runs for every item in your list
    @Override
    public void onBindViewHolder(@NonNull ShowOrderItemsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(orderItems.get(i));
        viewHolder.tvItemName.setText(orderItems.get(i).getItemName());
        viewHolder.tvItemDescription.setText(orderItems.get(i).getItemDescription());
        viewHolder.tvItemCost.setText(orderItems.get(i).getItemCost());
        viewHolder.ivOneItemLess.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                int itemId = orderItems.get(i).getIdOrderItem();
                theOrder.oneItemLess(itemId);
            }
        });
        viewHolder.ivOneItemLess.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                int itemId = orderItems.get(i).getIdOrderItem();
                theOrder.oneItemMore(itemId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public void setTeams(ArrayList<OrderItem> teams) {
        this.orderItems = orderItems;
    }
}
