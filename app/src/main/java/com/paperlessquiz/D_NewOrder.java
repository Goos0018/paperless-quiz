package com.paperlessquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.paperlessquiz.adapters.ShowOrderItemsAdapter;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This activity is used to create an order, or modify an existing order
 * Boolean isNewOrder indicates if it is a new order, or an existing order that is being modified
 */
public class D_NewOrder extends AppCompatActivity {
    RecyclerView rvShowOrderItems;
    RecyclerView.LayoutManager layoutManager;
    ShowOrderItemsAdapter showOrderItemsAdapter;
    Order thisOrder;
    Quiz thisQuiz = MyApplication.theQuiz;
    QuizLoader quizLoader;
    boolean isNewOrder;

    //Confirm before submitting an order
    public void confirmOrder(boolean isNewOrder) {
        String title, message;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(D_NewOrder.this);
        if (isNewOrder) {
            title = this.getString(R.string.neworder_sendorder);
            message = this.getString(R.string.neworder_sendthisorder);
        } else {
            title = this.getString(R.string.neworder_modifyorder);
            message = thisOrder.getOrderName() + " (" +
                    thisOrder.getUserName() + ") " + this.getString(R.string.neworder_modifyto);
        }
        // set and show dialog message
        message = message + " \n\n" + thisOrder.displayFullOrderDetails();
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        createOrder(isNewOrder);
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog and show it
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //Create or update the order in the database, the exit this screen
    public void createOrder(boolean isNewOrder) {
        Intent intent = new Intent();
        if (isNewOrder) {
            if (thisOrder.isEmpty()) {
                setResult(Order.RESULT_NO_ORDER_CREATED, intent);
            } else {
                setResult(Order.RESULT_ORDER_CREATED, intent);
                quizLoader.submitOrder(thisOrder.getOrderContentsAsString(), getCurrentTime());
            }
        } else {
            setResult(Order.RESULT_ORDER_CREATED, intent);
            quizLoader.updateExistingOrder(thisOrder, getCurrentTime());
        }
        D_NewOrder.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_d_order);
        ActionBar actionBar = getSupportActionBar();
        String actionBarTitle;
        //Check if an order was passed. If so, we need to edit it, otherwise, we need to create a new order
        if (getIntent().hasExtra(QuizDatabase.INTENT_EXTRANAME_ORDER_TO_EDIT)) {
            thisOrder = (Order) getIntent().getSerializableExtra(QuizDatabase.INTENT_EXTRANAME_ORDER_TO_EDIT);
            isNewOrder = false;
            actionBarTitle = this.getString(R.string.order_modifytitle);
        } else {
            thisOrder = new Order();
            isNewOrder = true;
            actionBarTitle = this.getString(R.string.order_title);
        }
        actionBar.setTitle(actionBarTitle);

        rvShowOrderItems = findViewById(R.id.rvShowOrderItems);
        rvShowOrderItems.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvShowOrderItems.setLayoutManager(layoutManager);
        showOrderItemsAdapter = new ShowOrderItemsAdapter(this, MyApplication.itemsToOrderArray, thisOrder);
        rvShowOrderItems.setAdapter(showOrderItemsAdapter);
        quizLoader = new QuizLoader(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel:
                Intent intent = new Intent();
                setResult(Order.RESULT_NO_ORDER_CREATED, intent);
                D_NewOrder.this.finish();
                break;
            case R.id.submit:
                confirmOrder(isNewOrder);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    //Don't do anything, they should either cancel or submit the order
    public void onBackPressed() {
    }

    //Used to create the time parameter that is passed when creating orders
    public String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "HH:mm";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getDefault());
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
