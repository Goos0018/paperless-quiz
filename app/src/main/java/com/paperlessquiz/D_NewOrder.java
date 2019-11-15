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
import android.widget.TextView;
import android.widget.Toast;

import com.paperlessquiz.adapters.ShowOrderItemsAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.paperlessquiz.users.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This activity is used to create an order, or modify an existing order
 * Boolean isNewOrder indicates if it is a new order, or an existing order that is being modified
 */
public class D_NewOrder extends AppCompatActivity implements LoadingActivity {
    RecyclerView rvShowOrderItems;
    RecyclerView.LayoutManager layoutManager;
    TextView tvSaldo, tvSaldoAfterThis;
    ShowOrderItemsAdapter showOrderItemsAdapter;
    Order thisOrder;
    Quiz thisQuiz = MyApplication.theQuiz;
    User thisUser;
    QuizLoader quizLoader;
    boolean isNewOrder;
    boolean usersLoaded, orderItemsLoaded, orderCreated, orderUpdated;
    //int thisUserId;

    @Override
    public void loadingComplete(int requestId) {
        switch (requestId) {
            case QuizDatabase.REQUEST_ID_GET_USERS:
                usersLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_ORDERITEMS:
                orderItemsLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_SUBMITORDER:
                orderCreated=true;
                break;
            case QuizDatabase.REQUEST_ID_UPDATEEXISTINGORDER:
                orderUpdated=true;
                        break;
        }
        if (usersLoaded) {
            usersLoaded = false;
            quizLoader.updateUsersIntoQuiz();
            updateSaldo();
        }
        if (orderItemsLoaded) {
            orderItemsLoaded = false;
            quizLoader.loadOrderItemsIntoQuiz();
            showOrderItemsAdapter.setOrderItems(MyApplication.itemsToOrderArray);
            showOrderItemsAdapter.notifyDataSetChanged();
        }
        if (orderCreated) {
            orderCreated = false;
            D_NewOrder.this.finish();
        }
        if (orderUpdated) {
            orderUpdated = false;
            D_NewOrder.this.finish();
        }
    }

    //Update saldo
    public void updateSaldo() {
        int bonnekesBought = thisUser.getUserCredits();
        int totalSpent = thisUser.getTotalSpent();
        int saldo = bonnekesBought - totalSpent;
        tvSaldo.setText(saldo + "");
        tvSaldoAfterThis.setText(saldo + "");
    }

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
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
                Toast.makeText(this, this.getString(R.string.order_nothing_to_order), Toast.LENGTH_LONG).show();
                D_NewOrder.this.finish();
            } else {
                setResult(Order.RESULT_ORDER_CREATED, intent);
                quizLoader.submitOrder(thisOrder.getOrderContentsAsString(), getCurrentTime());
            }
        } else {
            setResult(Order.RESULT_ORDER_CREATED, intent);
            quizLoader.updateExistingOrder(thisOrder, getCurrentTime());
        }
        //D_NewOrder.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_d_order);
        ActionBar actionBar = getSupportActionBar();
        String actionBarTitle;
        //Check if an order was passed. If so, we need to edit it, otherwise, we need to create a new order
        //Also, set thisUser correctly
        if (getIntent().hasExtra(QuizDatabase.INTENT_EXTRANAME_ORDER_TO_EDIT)) {
            thisOrder = (Order) getIntent().getSerializableExtra(QuizDatabase.INTENT_EXTRANAME_ORDER_TO_EDIT);
            isNewOrder = false;
            actionBarTitle = this.getString(R.string.order_modifytitle);
            if (thisOrder.getUserType() == 0) {
                thisUser = thisQuiz.getTeam(thisOrder.getUserNr());
            } else {
                thisUser = thisQuiz.getOrganizer(thisOrder.getUserNr());
            }
        } else {
            thisOrder = new Order();
            isNewOrder = true;
            actionBarTitle = this.getString(R.string.order_title);
            thisUser = thisQuiz.getThisUser();
        }
        actionBar.setTitle(actionBarTitle);
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldoAfterThis = findViewById(R.id.tvSaldoAfterThis);
        rvShowOrderItems = findViewById(R.id.rvShowOrderItems);
        rvShowOrderItems.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvShowOrderItems.setLayoutManager(layoutManager);
        quizLoader = new QuizLoader(this);
        showOrderItemsAdapter = new ShowOrderItemsAdapter(this, MyApplication.itemsToOrderArray, thisOrder, tvSaldoAfterThis, quizLoader);
        rvShowOrderItems.setAdapter(showOrderItemsAdapter);

        //Make sure we always have the latest information
        quizLoader.loadUsers(thisUser.getIdUser() + "");
        quizLoader.loadOrderItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                quizLoader.loadOrderItems();
                break;
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
        Toast.makeText(this, getString(R.string.order_confirmorcancel), Toast.LENGTH_LONG).show();
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
