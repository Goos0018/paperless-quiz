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
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.adapters.ShowOrdersAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class C_BarResponsibleHome extends MyActivity implements LoadingActivity, ShowOrdersAdapter.ItemClicked {
    RecyclerView rvShowAllOrders;
    RecyclerView.LayoutManager layoutManager;
    ShowOrdersAdapter showOrdersAdapter;
    TextView tvStatusIntro, tvStatus, tvSelectedOrderIntro, tvItemNames, tvItemAmounts, tvOverviewIntro;
    ImageView ivOrderEdit, ivStatusSubmitted, ivStatusInProgress, ivStatusReady, ivStatusDelivered;
    Order theSelectedOrder;
    Quiz thisQuiz = MyApplication.theQuiz;
    QuizLoader quizLoader = new QuizLoader(this);
    ArrayList<Order> allOrders;
    boolean ordersLoaded, orderDetailsLoaded;
    MyActivity callingActivity;
    String selectedStatuses="", selectedCategories="";

    @Override
    public void onItemClicked(int index) {
        showOrder(index);
    }

    @Override
    public void loadingComplete(int requestID) {
        switch (requestID) {
            case QuizDatabase.REQUEST_ID_GET_ALLORDERS:
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
            allOrders = quizLoader.getOrders.getResultsList();
            //refreshSaldo();
            //quizLoader.updateAnswersSubmittedIntoQuiz();
            if (showOrdersAdapter != null) {
                //showOrdersAdapter.notifyDataSetChanged();
                showOrdersAdapter = new ShowOrdersAdapter(this, allOrders);
                rvShowAllOrders.setAdapter(showOrdersAdapter);
                //Select the top row = most recent order
                if (allOrders.size() > 0) {
                    showOrder(0);
                } else {
                    tvOverviewIntro.setText("You have no orders yet: ");
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
        theSelectedOrder = allOrders.get(index);
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
        tvSelectedOrderIntro.setText("Details " + theSelectedOrder.getOrderName());
        detailItems = theSelectedOrder.displayOrderItems();
        detailAmounts = theSelectedOrder.displayOrderAmounts();
        tvItemNames.setText(detailItems);
        tvItemAmounts.setText(detailAmounts);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_bar_responsible_home);
        setActionBarIcon();
        setActionBarTitle();
        //Get stuff from the interface
        rvShowAllOrders = findViewById(R.id.rvShowAllOrders);
        tvStatusIntro = findViewById(R.id.tvStatusIntro);
        tvStatus = findViewById(R.id.tvStatus);
        tvStatusIntro.setText("Status");
        tvSelectedOrderIntro = findViewById(R.id.tvSelectedOrderIntro);
        tvItemNames = findViewById(R.id.tvItemNames);
        tvItemAmounts = findViewById(R.id.tvAmounts);
        tvOverviewIntro = findViewById(R.id.tvIntroOverview);
        //Get the icons and set onclick listeners for them
        ivOrderEdit=findViewById(R.id.ivEditOrder);
        ivStatusSubmitted=findViewById(R.id.ivStatusSubmitted);
        ivStatusInProgress=findViewById(R.id.ivStatusInProgress);
        ivStatusReady=findViewById(R.id.ivStatusReady);
        ivStatusDelivered=findViewById(R.id.ivStatusDelivered);
        ivOrderEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int orderNr = orders.get(i).getOrderNr();
            }
        });
        ivStatusSubmitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_SUBMITTED;
                quizLoader.updateOrderStatus(orderId,newStatus,time);
                quizLoader.loadAllOrders(selectedStatuses,selectedCategories);
            }
        });
        ivStatusInProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_INPROGRESS;
                quizLoader.updateOrderStatus(orderId,newStatus,time);
                quizLoader.loadAllOrders(selectedStatuses,selectedCategories);
            }
        });
        ivStatusReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_READY;
                quizLoader.updateOrderStatus(orderId,newStatus,time);
                quizLoader.loadAllOrders(selectedStatuses,selectedCategories);
            }
        });
        ivStatusDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_DELIVERED;
                quizLoader.updateOrderStatus(orderId,newStatus,time);
                quizLoader.loadAllOrders(selectedStatuses,selectedCategories);
            }
        });


        //Create empty list here, will be populated when loading is done
        allOrders = new ArrayList<>();
        //Initialize the adapter and recyclerview
        rvShowAllOrders.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvShowAllOrders.setLayoutManager(layoutManager);
        showOrdersAdapter = new ShowOrdersAdapter(this, allOrders);
        //rvShowAllOrders.setAdapter(showOrdersAdapter);
        quizLoader.loadAllOrders(selectedStatuses,selectedCategories);
        //callingActivity =  getIntent().getClass()
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barresponsible, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                quizLoader.loadAllOrders(selectedStatuses,selectedCategories);
        }
        return super.onOptionsItemSelected(item);
    }

}
