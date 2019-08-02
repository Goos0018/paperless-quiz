package com.paperlessquiz;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.paperlessquiz.adapters.ShowOrderItemsAdapter;
import com.paperlessquiz.adapters.ShowTeamsAdapter;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizLoader;

/**
 * This activity is used to create an order
 */
public class D_Order extends AppCompatActivity {
    RecyclerView rvShowOrderItems;
    RecyclerView.LayoutManager layoutManager;
    ShowOrderItemsAdapter showOrderItemsAdapter;
    Order thisOrder = new Order();
    Quiz thisQuiz = MyApplication.theQuiz;

    //QuizLoader quizLoader;
    //boolean  answersSubmittedLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_d_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(D_Order.this.getString(R.string.order_title) + " (" + thisQuiz.getThisUser().getName() + ")");

        rvShowOrderItems = findViewById(R.id.rvShowOrderItems);
        rvShowOrderItems.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvShowOrderItems.setLayoutManager(layoutManager);
        showOrderItemsAdapter = new ShowOrderItemsAdapter(this, MyApplication.itemsToOrderArray, thisOrder);
        rvShowOrderItems.setAdapter(showOrderItemsAdapter);
    }
}
