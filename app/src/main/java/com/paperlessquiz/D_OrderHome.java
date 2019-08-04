package com.paperlessquiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * This is the homepage for orders. Allows you to reate and submit new orders
 * see your current saldo, an overview of orders placed so far, and details of each order.
 */
public class D_OrderHome extends AppCompatActivity implements LoadingActivity, ShowOrdersAdapter.ItemClicked {
    RecyclerView rvShowOrders;
    RecyclerView.LayoutManager layoutManager;
    ShowOrdersAdapter showOrdersAdapter;
    TextView tvSaldoIntro, tvSaldo, tvOrderIntro, tvDisplayItemNames, tvOverviewIntro, tvDisplayAmount;
    Button btnNewOrder;
    Order theNewOrder, theSelectedOrder;
    Quiz thisQuiz = MyApplication.theQuiz;
    QuizLoader quizLoader = new QuizLoader(this);
    ArrayList<Order> myOrders;
    boolean ordersLoaded, orderDetailsLoaded;
    MyActivity callingActivity;

    @Override
    public void onItemClicked(int index) {
        showOrder(index);
    }

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
            refreshSaldo();
            //quizLoader.updateAnswersSubmittedIntoQuiz();
            if (showOrdersAdapter != null) {
                //showOrdersAdapter.notifyDataSetChanged();
                showOrdersAdapter = new ShowOrdersAdapter(this, myOrders);
                rvShowOrders.setAdapter(showOrdersAdapter);
                //Select the top row = most recent order
                if (myOrders.size() > 0) {
                    showOrder(0);
                } else {
                    tvOrderIntro.setText("You have no orders yet: ");
                }
            }
        }
        if (orderDetailsLoaded) {
            //reset the loading statuses
            orderDetailsLoaded = false;
            theSelectedOrder.setDetailsLoaded(true);
            theSelectedOrder.loadDetails(quizLoader.getOrderDetails.getResultsList());
            displayOrder(theSelectedOrder);
        }
    }

    //Show the order with the given index, loading it if needed
    public void showOrder(int index) {
        theSelectedOrder = myOrders.get(index);
        if (theSelectedOrder.isDetailsLoaded()) {
            displayOrder(theSelectedOrder);
        } else {
            quizLoader.loadOrderDetails(theSelectedOrder.getIdOrder());
        }
    }

    //Display the given order, details should be loaded at this point
    public void displayOrder(Order theSelectedOrder) {
        String detailItems = "";
        String detailAmounts = "";
        //Details should be loaded at this point
        tvOrderIntro.setText("Details " + theSelectedOrder.getOrderName());
        detailItems = theSelectedOrder.displayOrderItems();
        detailAmounts = theSelectedOrder.displayOrderAmounts();
        tvDisplayItemNames.setText(detailItems);
        tvDisplayAmount.setText(detailAmounts);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_d_order_home);
        setActionBarIcon();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("the Boooozz...");
        //Get stuff from the interface
        rvShowOrders = findViewById(R.id.rvShowOrders);
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldoIntro = findViewById(R.id.tvSaldoIntro);
        tvSaldoIntro.setText("Saldo: ");
        tvOrderIntro = findViewById(R.id.tvOrdersIntro);
        tvDisplayItemNames = findViewById(R.id.tvDisplayItemNames);
        tvOverviewIntro = findViewById(R.id.tvIntroOverview);
        tvOverviewIntro.setText("Overzicht bestellingen");
        tvDisplayAmount = findViewById(R.id.tvAmountsOrdered);

        //Create empty list here, will be populated when loading is done
        myOrders = new ArrayList<>();
        //Initialize the adapter and recyclerview
        rvShowOrders.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvShowOrders.setLayoutManager(layoutManager);
        showOrdersAdapter = new ShowOrdersAdapter(this, myOrders);
        rvShowOrders.setAdapter(showOrdersAdapter);
        quizLoader.loadOrdersForuser();
        //callingActivity =  getIntent().getClass()
    }

    public void refreshSaldo() {
        int saldo = 0;
        for (int i = 0; i < myOrders.size(); i++) {
            saldo += myOrders.get(i).getTotalCost();
        }
        tvSaldo.setText(QuizDatabase.EURO_SIGN + saldo);
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


    public void setActionBarIcon() {
        //Set the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //Display the "back" icon, we will replace this with the icon of this Quiz
        final Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                actionBar.setHomeAsUpIndicator(mBitmapDrawable);
            }

            @Override
            public void onBitmapFailed(Drawable drawable) {
            }

            @Override
            public void onPrepareLoad(Drawable drawable) {
            }
        };
        String URL = thisQuiz.getListData().getLogoURL();
        if (URL.equals("")) {
            actionBar.setDisplayHomeAsUpEnabled(false);//If the Quiz has no logo, then don't display anything
        } else {
            //Picasso.with(this).load("http://www.meerdaal.be//assets/logo-05c267018885eb67356ce0b49bf72129.png").into(mTarget);
            Picasso.with(this).load(thisQuiz.getListData().getLogoURL()).resize(Quiz.ACTIONBAR_ICON_WIDTH, Quiz.ACTIONBAR_ICON_HEIGHT).into(mTarget);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.orderhome, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.neworder:
                Intent intentOrder = new Intent(D_OrderHome.this, D_NewOrder.class);
                startActivityForResult(intentOrder, 0);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
