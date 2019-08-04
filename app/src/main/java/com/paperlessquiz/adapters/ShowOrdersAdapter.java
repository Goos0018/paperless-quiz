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
import com.paperlessquiz.orders.CanShowOrderDetail;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

import java.util.ArrayList;

public class ShowOrdersAdapter extends RecyclerView.Adapter<ShowOrdersAdapter.ViewHolder> {
    private ArrayList<Order> orders;
    private ItemClicked parentActivity;
    //private ShowOrderItemsAdapter adapter;
    //private Order theOrder;
    String euro = QuizDatabase.EURO_SIGN;
    //CanShowOrderDetail parentActivity;
    //QuizLoader quizLoader;

    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public ShowOrdersAdapter(Context context, ArrayList<Order> orders) {
        this.orders = orders;
        //parentActivity = (CanShowOrderDetail) context;
        parentActivity = (ItemClicked) context;
        //quizLoader = new QuizLoader(context);
        //adapter = this;
        //this.theOrder = theOrder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderName, tvOrderLastStatusUpdate, tvTotalCost;
        ImageView ivShowStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvOrderLastStatusUpdate = itemView.findViewById(R.id.tvOrderLastStatusUpdate);
            tvTotalCost = itemView.findViewById(R.id.tvTotalCost);
            ivShowStatus = itemView.findViewById(R.id.ivShowStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    parentActivity.onItemClicked(orders.indexOf((Order) view.getTag()));
                }
            });
        }

        public void setFields(int i) {
            itemView.setTag(orders.get(i));
            tvOrderName.setText(orders.get(i).getOrderName());
            tvOrderLastStatusUpdate.setText(orders.get(i).getLastStatusUpdate());
            String lastUpdate = orders.get(i).getLastStatusUpdate();
            if (lastUpdate.isEmpty()) {
                tvOrderLastStatusUpdate.setVisibility(View.GONE);
            } else {
                tvOrderLastStatusUpdate.setVisibility(View.VISIBLE);
                tvOrderLastStatusUpdate.setText(lastUpdate);
            }
            tvTotalCost.setText(euro + Integer.toString(orders.get(i).getTotalCost()));
            switch (orders.get(i).getCurrentStatus()) {
                case QuizDatabase.ORDERSTATUS_NEW:
                    ivShowStatus.setImageResource(R.drawable.order_new);
                    break;
                case QuizDatabase.ORDERSTATUS_SUBMITTED:
                    ivShowStatus.setImageResource(R.drawable.order_submitted);
                    break;
                case QuizDatabase.ORDERSTATUS_INPROGRESS:
                    ivShowStatus.setImageResource(R.drawable.order_inprogress);
                    break;
                case QuizDatabase.ORDERSTATUS_READY:
                    ivShowStatus.setImageResource(R.drawable.order_ready);
                    break;
                case QuizDatabase.ORDERSTATUS_DELIVERED:
                    ivShowStatus.setImageResource(R.drawable.order_delivered);
                    break;
            }
        }
    }

    @NonNull
    @Override
    public ShowOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_order, viewGroup, false);
        return new ShowOrdersAdapter.ViewHolder(v);
    }

    //This runs for every item in your list
    @Override
    public void onBindViewHolder(@NonNull ShowOrdersAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setFields(i);
        /*viewHolder.ivShowStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                //int orderNr = orders.get(i).getOrderNr();
                parentActivity.showOrderDetails(i);
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
