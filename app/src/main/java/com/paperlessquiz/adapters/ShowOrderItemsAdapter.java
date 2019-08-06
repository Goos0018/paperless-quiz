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
import java.util.Currency;
import java.util.Locale;

/**
 * Adapter used to create or edit an order
 * It displays a list of all items that can be ordered and allows the user to set/ modify the quantities of each item
 * If theOrder contains something, we will display initially the quantities of that order
 */

public class ShowOrderItemsAdapter extends RecyclerView.Adapter<ShowOrderItemsAdapter.ViewHolder> {
    private ArrayList<OrderItem> orderItems;
    private ShowOrderItemsAdapter adapter;
    private Order theOrder;
    String euro = "€ ";

    public ShowOrderItemsAdapter(Context context, ArrayList<OrderItem> orderItems, Order theOrder) {
        this.orderItems = orderItems;
        adapter = this;
        this.theOrder = theOrder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemDescription, tvItemCost, tvAmountOrdered;
        ImageView ivOneItemLess, ivOneItemMore;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            tvItemCost = itemView.findViewById(R.id.tvItemCost);
            tvAmountOrdered = itemView.findViewById(R.id.tvAmountOrdered);
            ivOneItemMore = itemView.findViewById(R.id.ivOneItemMore);
            ivOneItemLess = itemView.findViewById(R.id.ivOneItemLess);
        }

        public void setFields(int i) {
            itemView.setTag(orderItems.get(i));
            tvItemName.setText(orderItems.get(i).getItemName());
            String desc = orderItems.get(i).getItemDescription();
            if (desc.isEmpty()) {
                tvItemDescription.setVisibility(View.GONE);
            } else {
                tvItemDescription.setVisibility(View.VISIBLE);
                tvItemDescription.setText(desc);
            }
            tvItemCost.setText(euro + Integer.toString(orderItems.get(i).getItemCost()));
            tvAmountOrdered.setText(Integer.toString(theOrder.getAmountOrderedForItem(orderItems.get(i).getIdOrderItem())));
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
        viewHolder.setFields(i);
        viewHolder.ivOneItemLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                int itemId = orderItems.get(i).getIdOrderItem();
                theOrder.oneItemLess(itemId);
                viewHolder.setFields(i);
            }
        });
        viewHolder.ivOneItemMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                int itemId = orderItems.get(i).getIdOrderItem();
                theOrder.oneItemMore(itemId);
                viewHolder.setFields(i);
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
