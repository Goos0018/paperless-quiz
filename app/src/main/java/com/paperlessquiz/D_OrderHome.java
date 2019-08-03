package com.paperlessquiz;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.paperlessquiz.adapters.ShowOrdersAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.orders.CanShowOrderDetail;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

import java.util.ArrayList;

/**
 * This is the homepage for orders. Allows you to reate and submit new orders
 * see your current saldo, an overview of orders placed so far, and details of each order.
 */
public class D_OrderHome extends AppCompatActivity implements CanShowOrderDetail, LoadingActivity {
    RecyclerView rvShowOrders;
    RecyclerView.LayoutManager layoutManager;
    ShowOrdersAdapter showOrdersAdapter;
    TextView tvSaldoIntro, tvSaldo, tvOrderIntro, tvDisplayItemNames, tvDisplayMiddle, tvDisplayAmount;
    Button btnNewOrder;
    Order theNewOrder, theSelectedOrder;
    Quiz thisQuiz = MyApplication.theQuiz;
    QuizLoader quizLoader = new QuizLoader(this);
    ArrayList<Order> myOrders;
    boolean ordersLoaded, orderDetailsLoaded;

    @Override
    public void loadingComplete(int requestID) {
        switch (requestID) {
            case QuizDatabase.REQUEST_ID_GET_ORDERSFORUSER:
                ordersLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_ORDERDETAILS:
                orderDetailsLoaded = true;
                break;
        }
        //If everything is properly loaded, we can start populating the central Quiz object
        if (ordersLoaded) {
            //reset the loading statuses
            ordersLoaded = false;
            myOrders = quizLoader.getOrders.getResultsList();
            //quizLoader.updateAnswersSubmittedIntoQuiz();
            if (showOrdersAdapter != null) {
                //showOrdersAdapter.notifyDataSetChanged();
                showOrdersAdapter = new ShowOrdersAdapter(this, myOrders);
                rvShowOrders.setAdapter(showOrdersAdapter);
            }
        }
        if (orderDetailsLoaded) {
            //reset the loading statuses
            orderDetailsLoaded = false;
            theSelectedOrder.setDetailsLoaded(true);
            theSelectedOrder.loadDetails(quizLoader.getOrderDetails.getResultsList());
            String detailItems = theSelectedOrder.displayOrderItems();
            String detailAmounts = theSelectedOrder.displayOrderAmounts();
            tvDisplayItemNames.setText(detailItems);
            tvDisplayAmount.setText(detailAmounts);
        }
    }

    @Override
    public void showOrderDetails(int position) {
        String detailItems = "";
        String detailAmounts = "";
        //This is an existing order selected from the list
        theSelectedOrder = myOrders.get(position);
        //If details were already loaded for it, display them, otherwise load them first
        if (myOrders.get(position).isDetailsLoaded()) {
            detailItems = theSelectedOrder.displayOrderItems();
            detailAmounts = theSelectedOrder.displayOrderAmounts();
        } else {
            quizLoader.loadOrderDetails(theSelectedOrder.getIdOrder());
        }
        tvDisplayItemNames.setText(detailItems);
        tvDisplayAmount.setText(detailAmounts);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_d_order_home);
        //Get stuff from the interface
        rvShowOrders = findViewById(R.id.rvShowOrders);
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldoIntro = findViewById(R.id.tvSaldoIntro);
        tvSaldoIntro.setText("Saldo: ");
        tvOrderIntro = findViewById(R.id.tvOrdersIntro);
        tvOrderIntro.setText("Details geselecteerde bestelling: ");
        tvDisplayItemNames = findViewById(R.id.tvDisplayItemNames);
        //tvDisplayMiddle = findViewById(R.id.tvMiddle);
        tvDisplayAmount = findViewById(R.id.tvAmountsOrdered);

        btnNewOrder = findViewById(R.id.btnNewOrder);
        //Create empty list here, will be populated when loading is done
        myOrders = new ArrayList<>();
        //Initialize the adapter and recyclerview
        rvShowOrders.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvShowOrders.setLayoutManager(layoutManager);
        showOrdersAdapter = new ShowOrdersAdapter(this, myOrders);
        rvShowOrders.setAdapter(showOrdersAdapter);
        quizLoader.loadOrdersForuser();

        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final int position = view.getId();
                Intent intentOrder = new Intent(D_OrderHome.this, D_NewOrder.class);
                startActivityForResult(intentOrder,0);
            }
        });
    }

    public void refreshDisplay()
    {
        int saldo=0;
        for (int i = 0; i < myOrders.size() ; i++) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Order.RESULT_NO_ORDER_CREATED:
                //No order was created, nothing to do
                break;
            case Order.RESULT_ORDER_CREATED:
                quizLoader.loadOrdersForuser();
                break;
        }
    }

}
