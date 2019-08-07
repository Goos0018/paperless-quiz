package com.paperlessquiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
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
import java.util.HashMap;

public class C_BarResponsibleHome extends MyActivity implements LoadingActivity, ShowOrdersAdapter.ItemClicked {
    RecyclerView rvShowAllOrders;
    RecyclerView.LayoutManager layoutManager;
    ShowOrdersAdapter showOrdersAdapter;
    TextView tvStatusIntro, tvStatus, tvSelectedOrderIntro, tvItemNames, tvItemAmounts, tvOverviewIntro;
    ImageView ivOrderEdit, ivStatusSubmitted, ivStatusInProgress, ivStatusReady, ivStatusDelivered;
    LinearLayout llShowCats;
    //HashMap<String, Boolean> catsList = new HashMap<>();
    ArrayList<String> catsList = new ArrayList<>();
    String selectedCats="";
    ArrayList<String> selectedCatsArray;
    Order theSelectedOrder;
    Quiz thisQuiz = MyApplication.theQuiz;
    QuizLoader quizLoader = new QuizLoader(this);
    ArrayList<Order> allOrders;
    boolean ordersLoaded, orderDetailsLoaded, orderStatusUpdated;
    String selectedStatuses="", selectedCategories="";

    @Override
    //Things to do when an order in the list is clicked
    public void onItemClicked(int index) {
        showOrder(index);
    }

    @Override
    //Things to do when we are done with a web request
    public void loadingComplete(int requestID) {
        switch (requestID) {
            case QuizDatabase.REQUEST_ID_GET_ALLORDERS:
                ordersLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_ORDERDETAILS:
                orderDetailsLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_UPDATEORDERSTATUS:
                orderStatusUpdated = true;
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
                //showOrdersAdapter = new ShowOrdersAdapter(this, allOrders);
                //rvShowAllOrders.setAdapter(showOrdersAdapter);
                //Select the top row = most recent order
                if (allOrders.size() > 0) {
                    showOrder(0);
                    showOrdersAdapter.setCurrentPosition(0);
                } else {
                    tvOverviewIntro.setText("You have no orders yet: ");
                }
                showOrdersAdapter.setOrders(allOrders);
                showOrdersAdapter.notifyDataSetChanged();
            }
        }
        if (orderDetailsLoaded) {
            //reset the loading statuses
            orderDetailsLoaded = false;
            theSelectedOrder.setDetailsLoaded(true);
            theSelectedOrder.loadDetails(quizLoader.getOrderDetails.getResultsList());
            displayOrder(theSelectedOrder);
        }
        if (orderStatusUpdated) {
            //reset the loading statuses
            orderStatusUpdated = false;
            quizLoader.loadAllOrders(selectedStatuses,selectedCategories);
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
        String header;
        //Show if this is an order for a team or an organizer, in the first case, show the team nr, otherwise, the username
        if (theSelectedOrder.getUserType() == QuizDatabase.USERTYPE_TEAM) {
            header = QuizDatabase.TEAM + Integer.toString(theSelectedOrder.getUserNr());
        }
        else
        {
            header = theSelectedOrder.getUserName();
        }
        header += " // " + theSelectedOrder.getOrderName() + " (" + QuizDatabase.EURO_SIGN + theSelectedOrder.getTotalCost() + ")";
        tvSelectedOrderIntro.setText(header);
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
        /*
        llShowCats = findViewById(R.id.llCheckCats);
        catsList.put("test 1", false);catsList.put("test 2", false);
        int i=0;
        for (String cat : catsList.keySet()) {
            TableRow row =new TableRow(this);
            row.setId(i);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            CheckBox checkBox = new CheckBox(this);
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    boolean checked = ((CheckBox) v).isChecked();
                    String thisCat = (String)((CheckBox) v).getText();
                    catsList.put(thisCat,Boolean.valueOf(checked));
                    selectedCats="";
                    for (String cat : catsList.keySet()) {
                        if (catsList.get(cat)){
                            selectedCats+=cat;
                        }
                    }
                    tvStatusIntro.setText(selectedCats);
                }
            });
            checkBox.setId(i);
            checkBox.setText(cat);
            row.addView(checkBox);
            llShowCats.addView(row);
            i++;
        }

        */
        catsList.add("test1");catsList.add("test2");
        showDialog(catsList);

        //tvStatusIntro.setText(selectedCats);
        //tvStatus = findViewById(R.id.tvStatus);
        //tvStatusIntro.setText("Status");
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
                Intent intentOrder = new Intent(C_BarResponsibleHome.this, D_NewOrder.class);
                intentOrder.putExtra(QuizDatabase.INTENT_EXTRANAME_ORDER_TO_EDIT, theSelectedOrder);
                startActivityForResult(intentOrder, QuizDatabase.REQUEST_CODE_EDITEXISTINGORDER);
            }
        });
        ivStatusSubmitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_SUBMITTED;
                quizLoader.updateOrderStatus(orderId,newStatus,time);
            }
        });
        ivStatusInProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_INPROGRESS;
                quizLoader.updateOrderStatus(orderId,newStatus,time);
            }
        });
        ivStatusReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_READY;
                quizLoader.updateOrderStatus(orderId,newStatus,time);
            }
        });
        ivStatusDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_DELIVERED;
                quizLoader.updateOrderStatus(orderId,newStatus,time);
            }
        });

        //Create empty list here, will be populated when loading is done
        allOrders = new ArrayList<>();
        //Initialize the adapter and recyclerview
        rvShowAllOrders.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvShowAllOrders.setLayoutManager(layoutManager);
        showOrdersAdapter = new ShowOrdersAdapter(this, allOrders);
        rvShowAllOrders.setAdapter(showOrdersAdapter);
        quizLoader.loadAllOrders(selectedStatuses,selectedCategories);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Order.RESULT_NO_ORDER_CREATED:
                //No order was created, nothing to do
                break;
            case Order.RESULT_ORDER_CREATED:
                //This is when we modified an existing order
                quizLoader.loadAllOrders(selectedStatuses,selectedCategories);
                break;
        }
    }

    //Onclick listener for checkboxes
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

    }

    public void showDialog(ArrayList<String> itemsArrayList ){
        Dialog dialog;
        //final String[] items = {" PHP", " JAVA", " JSON", " C#", " Objective-C"};
        String[] items = itemsArrayList.toArray(new String[itemsArrayList.size()]);
        ArrayList itemsSelected = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Languages you know : ");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            itemsSelected.add(selectedItemId);
                        } else if (itemsSelected.contains(selectedItemId)) {
                            itemsSelected.remove(Integer.valueOf(selectedItemId));
                        }
                    }
                })
                .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        selectedCatsArray=new ArrayList<>();
                        selectedCats="";
                        for (int i = 0; i < itemsSelected.size(); i++) {
                           // selectedCats+=catsList.get(int (itemsSelected.get(i)));
                        }
                        tvStatusIntro.setText(selectedCats);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        dialog = builder.create();
        dialog.show();
    }

}
