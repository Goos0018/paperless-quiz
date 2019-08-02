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
import com.paperlessquiz.orders.CanShowOrderDetail;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.Quiz;

import java.util.ArrayList;

/**
 * This is the homepage for orders. Allows you to reate and submit new orders
 * see your current saldo, an overview of orders placed so far, and details of each order.
 */
public class D_OrderHome extends AppCompatActivity implements CanShowOrderDetail {
    RecyclerView rvShowOrders;
    RecyclerView.LayoutManager layoutManager;
    ShowOrdersAdapter showOrdersAdapter;
    TextView tvSaldoIntro, tvSaldo, tvNoOrders, tvDisplayOrder;
    Button btnNewOrder, btnSubmitOrder;
    Order theNewOrder;
    int theOrderNr;
    Quiz thisQuiz = MyApplication.theQuiz;
    ArrayList<Order> myOrders;

    @Override
    public void showOrderDetails(int orderNr) {
        theOrderNr = orderNr;
        String details;
        if (orderNr == 0){
            //This is a new order
            details = theNewOrder.displayOrderDetails();
        }
        else
        {
            //This is an existing order selected from the list
            details=myOrders.get(orderNr - 1).displayOrderDetails();
        }
        tvDisplayOrder.setText(details);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_d_order_home);
        //Get stuff from the interface
        rvShowOrders = findViewById(R.id.rvShowOrders);
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldoIntro = findViewById(R.id.tvSaldoIntro);
        tvNoOrders = findViewById(R.id.tvNoOrders);
        tvDisplayOrder = findViewById(R.id.tvDisplayOrder);
        btnNewOrder = findViewById(R.id.btnNewOrder);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);
        //Create empty list here, will be populated when loading is done
        myOrders = new ArrayList<>();
        //Initialize the adapter and recyclerview
        rvShowOrders.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvShowOrders.setLayoutManager(layoutManager);
        showOrdersAdapter = new ShowOrdersAdapter(this, myOrders);
        rvShowOrders.setAdapter(showOrdersAdapter);

        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final int position = view.getId();
                Intent intentOrder = new Intent(D_OrderHome.this, D_NewOrder.class);
                startActivityForResult(intentOrder, 0);
            }
        });

        btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theNewOrder == null) {
                    //nothing to do
                } else {
                    //submit order to database
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Order.RESULT_NO_ORDER_CREATED:
                //No order was created, nothing to do
                break;
            case Order.RESULT_ORDER_CREATED:
                theNewOrder = (Order) data.getSerializableExtra(Order.PUTEXTRANAME_NEW_ORDER);
                showOrderDetails(0);
                break;
        }
    }
}
