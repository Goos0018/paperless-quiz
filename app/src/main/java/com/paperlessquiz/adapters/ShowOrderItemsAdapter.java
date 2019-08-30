package com.paperlessquiz.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.orders.OrderItem;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

import java.util.ArrayList;

/**
 * Adapter used to create or edit an order
 * It displays a list of all items that can be ordered and allows the user to set/ modify the quantities of each item
 * If theOrder contains something, we will display initially the quantities of that order
 */

public class ShowOrderItemsAdapter extends RecyclerView.Adapter<ShowOrderItemsAdapter.ViewHolder> {
    private ArrayList<OrderItem> orderItems;
    private QuizLoader quizLoader;
    private ShowOrderItemsAdapter adapter;
    private Order theOrder;
    TextView tvSaldoAfterThis;
    String euro = QuizDatabase.EURO_SIGN;
    Context context;
    int soldOutColor, availableColor;

    public ShowOrderItemsAdapter(Context context, ArrayList<OrderItem> orderItems, Order theOrder, TextView tvSaldoAfterThis, QuizLoader quizLoader) {
        this.orderItems = orderItems;
        adapter = this;
        this.theOrder = theOrder;
        this.tvSaldoAfterThis = tvSaldoAfterThis;
        this.context = context;
        this.quizLoader = quizLoader;
        this.soldOutColor = context.getResources().getColor(R.color.lightYellow);
        this.availableColor = context.getResources().getColor(R.color.white);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemDescription, tvItemCost, tvAmountOrdered, tvItemsRemaining;
        ImageView ivOneItemLess, ivOneItemMore;
        CardView cvCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            tvItemCost = itemView.findViewById(R.id.tvItemCost);
            tvItemsRemaining = itemView.findViewById(R.id.tvItemsRemaining);
            tvAmountOrdered = itemView.findViewById(R.id.tvAmountOrdered);
            ivOneItemMore = itemView.findViewById(R.id.ivOneItemMore);
            ivOneItemLess = itemView.findViewById(R.id.ivOneItemLess);
            cvCardView = itemView.findViewById(R.id.cvCardView);
        }

        public void setFields(int i) {
            itemView.setTag(orderItems.get(i));
            tvItemName.setText(orderItems.get(i).getItemName());
            String desc = orderItems.get(i).getItemDescription();
            int itemsRemaining = orderItems.get(i).getItemUnitsRemaining();
            if (desc.isEmpty()) {
                tvItemDescription.setVisibility(View.GONE);
            } else {
                tvItemDescription.setVisibility(View.VISIBLE);
                tvItemDescription.setText(desc);
            }
            tvItemCost.setText(euro + Integer.toString(orderItems.get(i).getItemCost()));
            tvAmountOrdered.setText(Integer.toString(theOrder.getAmountOrderedForItem(orderItems.get(i).getIdOrderItem())));
            String extraInfo = "";
            if (MyApplication.theQuiz.getThisUser().getUserType() == QuizDatabase.USERTYPE_BARRESPONSIBLE) {
                extraInfo = "Nog " + itemsRemaining + " over";
            } else {
                extraInfo = "Nog maar " + itemsRemaining + " over!";
            }

            if (orderItems.get(i).isItemSoldOut()) {
                cvCardView.setCardBackgroundColor(soldOutColor);
                tvItemsRemaining.setVisibility(View.VISIBLE);
                tvItemsRemaining.setText("Uitverkocht!");
            } else {
                cvCardView.setCardBackgroundColor(availableColor);
                if ((itemsRemaining < QuizDatabase.UNITS_REMAINING_FLAG) || (MyApplication.theQuiz.getThisUser().getUserType() == QuizDatabase.USERTYPE_BARRESPONSIBLE)) {
                    tvItemsRemaining.setVisibility(View.VISIBLE);
                    tvItemsRemaining.setText(extraInfo);
                } else {
                    tvItemsRemaining.setVisibility(View.GONE);
                }
            }
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
                boolean itemSoldOut = orderItems.get(i).isItemSoldOut();
                if (!itemSoldOut) {
                    int itemId = orderItems.get(i).getIdOrderItem();
                    int itemsOrdered = theOrder.getAmountOrderedForItem(itemId);
                    if (itemsOrdered > 0) {
                        theOrder.oneItemLess(itemId);
                        String cur = tvSaldoAfterThis.getText().toString();
                        int curSaldo = (int) Integer.valueOf(cur);
                        int newValue = curSaldo + orderItems.get(i).getItemCost();
                        tvSaldoAfterThis.setText(newValue + "");
                        viewHolder.setFields(i);
                    }
                }
            }
        });
        viewHolder.ivOneItemMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                boolean itemSoldOut = orderItems.get(i).isItemSoldOut();
                if (!itemSoldOut) {
                    int itemId = orderItems.get(i).getIdOrderItem();
                    String cur = tvSaldoAfterThis.getText().toString();
                    int curSaldo = (int) Integer.valueOf(cur);
                    int newValue = curSaldo - orderItems.get(i).getItemCost();
                    if ((newValue < 0) && MyApplication.theQuiz.getThisUser().getUserType() == 0) {
                        Toast.makeText(context, "Saldo ontoereikend", Toast.LENGTH_LONG).show();
                    } else {
                        theOrder.oneItemMore(itemId);
                        tvSaldoAfterThis.setText(newValue + "");
                        viewHolder.setFields(i);
                    }
                }
            }
        });

        viewHolder.tvItemCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.theQuiz.getThisUser().getUserType() == QuizDatabase.USERTYPE_BARRESPONSIBLE) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    String title = orderItems.get(i).getItemName() + " uitverkocht?";
                    int itemId = orderItems.get(i).getIdOrderItem();
                    int newStatus;
                    if (orderItems.get(i).isItemSoldOut()) {
                        newStatus = 0;
                        title = "RESET " + title;

                    } else {
                        newStatus = 1;
                    }
                    builder.setTitle(title);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            quizLoader.setItemSoldOut(itemId, newStatus);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });

        viewHolder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.theQuiz.getThisUser().getUserType() == QuizDatabase.USERTYPE_BARRESPONSIBLE) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    String title = "Extra eenheden voor " + orderItems.get(i).getItemName() + " toevoegen?";
                    int itemId = orderItems.get(i).getIdOrderItem();
                    final EditText input = new EditText(context);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    //input.setKeyListener(DigitsKeyListener.getInstance("-0123456789"));
                    builder.setView(input);
                    builder.setTitle(title);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int extraUnits = Integer.valueOf(input.getText().toString());
                            quizLoader.addUnits(itemId, extraUnits);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
