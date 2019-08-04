package com.paperlessquiz.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.MyActivity;
import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.orders.CanShowOrderDetail;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

import java.util.ArrayList;

/**
 * This adapter is used to display order. It is used by both teams and the bar helpers
 * It depends on the user type what exactly is displayed.
 */
public class ShowOrdersAdapter extends RecyclerView.Adapter<ShowOrdersAdapter.ViewHolder> {
    private ArrayList<Order> orders;
    private ItemClicked parentActivity;
    //private ShowOrderItemsAdapter adapter;
    //private Order theOrder;
    String euro = QuizDatabase.EURO_SIGN;
    String team = QuizDatabase.TEAM;
    Quiz thisQuiz = MyApplication.theQuiz;
    Context context;
    int previousPosition=-1;
    int colorWhenClicked, colorWhenNotClicked;
    //CanShowOrderDetail parentActivity;
    //QuizLoader quizLoader;

    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public ShowOrdersAdapter(Context context, ArrayList<Order> orders) {
        this.orders = orders;
        //parentActivity = (CanShowOrderDetail) context;
        parentActivity = (ItemClicked) context;
        this.context=context;
        colorWhenClicked= context.getResources().getColor(R.color.colorDivider);
        colorWhenNotClicked= context.getResources().getColor(R.color.white);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderName, tvOrderLastStatusUpdate, tvTotalCost;
        ImageView ivShowStatus;
        CardView cvCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvOrderLastStatusUpdate = itemView.findViewById(R.id.tvOrderLastStatusUpdate);
            tvTotalCost = itemView.findViewById(R.id.tvTotalCost); //This item is abused to show the team nr for the bar helpers
            ivShowStatus = itemView.findViewById(R.id.ivShowStatus);
            cvCardView = itemView.findViewById(R.id.cvCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //reset the background of the previously clicked item
                    notifyItemChanged(previousPosition);
                    cvCardView.setCardBackgroundColor(colorWhenClicked);
                    parentActivity.onItemClicked(orders.indexOf((Order) view.getTag()));
                    previousPosition = getAdapterPosition();

                }
            });
        }

        public void setFields(int i) {
            cvCardView.setCardBackgroundColor(colorWhenNotClicked);
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
            switch (MyApplication.theQuiz.getThisUser().getUserType()){
                case QuizDatabase.USERTYPE_BARRESPONSIBLE:
                case QuizDatabase.USERTYPE_BARHELPER:
                    //Show if this is an order for a team or an organizer, in the first case, show the team nr, otherwise, the username
                    if (orders.get(i).getUserType() == QuizDatabase.USERTYPE_TEAM) {
                        tvTotalCost.setText(team + Integer.toString(orders.get(i).getUserNr()));
                    }
                    else
                    {
                        String userName = orders.get(i).getUserName();
                        if (userName.length() > QuizDatabase.MAX_NAME_LENGTH){
                            userName= userName.substring(0,QuizDatabase.MAX_NAME_LENGTH);}
                        tvTotalCost.setText(userName);
                    }
                    break;
                default:
                    tvTotalCost.setText(euro + Integer.toString(orders.get(i).getTotalCost()));
            }

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
