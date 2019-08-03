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
import android.widget.Toast;

import com.paperlessquiz.adapters.ShowOrderItemsAdapter;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.Quiz;

/**
 * This activity is used to create an order
 */
public class D_NewOrder extends AppCompatActivity {
    RecyclerView rvShowOrderItems;
    RecyclerView.LayoutManager layoutManager;
    ShowOrderItemsAdapter showOrderItemsAdapter;
    Order thisOrder = new Order();
    Quiz thisQuiz = MyApplication.theQuiz;

    //QuizLoader quizLoader;
    //boolean  answersSubmittedLoaded;

    public void confirmOrder() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(D_NewOrder.this);

        // set title
        alertDialogBuilder.setTitle("Bestelling doorsturen?");

        String message = "Onderstaande bestelling doorgeven? \n\n" +
                thisOrder.displayOrderDetails();
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        createOrder();
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    public void createOrder() {
        Intent intent = new Intent();
        if (thisOrder.isEmpty()) {
            setResult(Order.RESULT_NO_ORDER_CREATED, intent);
        } else {
            intent.putExtra(Order.PUTEXTRANAME_NEW_ORDER, thisOrder);
            setResult(Order.RESULT_ORDER_CREATED, intent);
        }
        D_NewOrder.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_d_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(D_NewOrder.this.getString(R.string.order_title));

        rvShowOrderItems = findViewById(R.id.rvShowOrderItems);
        rvShowOrderItems.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvShowOrderItems.setLayoutManager(layoutManager);
        showOrderItemsAdapter = new ShowOrderItemsAdapter(this, MyApplication.itemsToOrderArray, thisOrder);
        rvShowOrderItems.setAdapter(showOrderItemsAdapter);
    }

    public Order getThisOrder() {
        return thisOrder;
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
                confirmOrder();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //Don't do anything, they should either cancel or submit the order
        //Toast.makeText(this, D_NewOrder.this.getString(R.string.participant_nobackallowed), Toast.LENGTH_SHORT).show();
    }
}
