package com.paperlessquiz;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.paperlessquiz.adapters.ShowOrdersAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

import java.util.ArrayList;

/*
Home screen for barhelpers. Displays the orders based on what the barhelper selects in the radiogroup buttons role and categories.

 */
public class C_BarHelperHome extends MyActivity implements LoadingActivity, ShowOrdersAdapter.ItemClicked {

    RecyclerView rvShowAllOrders;
    RecyclerView.LayoutManager layoutManager;
    ShowOrdersAdapter showOrdersAdapter;
    TextView tvOrderNr, tvOrderUser, tvDetailsIntro;
    TextView tvItemNames, tvItemAmounts, tvOverviewIntro, tvTable;
    TextView tvOrderStatus;
    RadioGroup rgpCategories, rgpRoles;
    ImageView ivStatusToProcess, ivStatusWorkingOnIt, ivStatusProcessed;
    LinearLayout llFilterRole, llFilterCats, llDetails, llOrderList;

    ArrayList<String> catsList = new ArrayList<>();
    ArrayList<String> usersList = new ArrayList<>();
    ArrayList<String> userIdsList = new ArrayList<>();
    ArrayList<String> rolesList = new ArrayList<>();
    ArrayList<String> displayRolesList = new ArrayList<>();
    Order theSelectedOrder;
    Quiz thisQuiz = MyApplication.theQuiz;
    QuizLoader quizLoader = new QuizLoader(this);
    ArrayList<Order> allOrders;
    boolean ordersLoaded, orderDetailsLoaded, orderStatusUpdated, orderLockedForPrep;
    boolean filterHidden, orderSelected;
    String selectedStatuses = "", selectedCategories = "", selectedUsers = "";
    String selectedRole = "";

    @Override
    //Things to do when an order in the list is clicked
    //For Orderprepare role: lock the order, if that is successful, it will be displayed
    //For Deliver: just display  the order, no status change needed
    public void onItemClicked(int index) {
        theSelectedOrder = allOrders.get(index);
        int orderId = theSelectedOrder.getIdOrder();
        String time = MyApplication.getCurrentTime();
        if (selectedRole.equals(QuizDatabase.BARHELPERROLENAME_PREPARE)) {
            quizLoader.lockOrderForPrep(orderId, time);
            //Rest is done when the result of this action is successful
        } else {
            showOrder(theSelectedOrder);
        }
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
            case QuizDatabase.REQUEST_ID_LOCKORDERFORPREP:
                orderLockedForPrep = true;
                break;
        }
        //If orders are loaded, refresh the list view and make sure it is shown
        if (ordersLoaded) {
            //reset the status
            ordersLoaded = false;
            allOrders = quizLoader.getOrdersRequest.getResultsList();
            if (allOrders.size() > 0) {
                tvOverviewIntro.setText(this.getString(R.string.select_order));
            } else {
                tvOverviewIntro.setText(this.getString(R.string.barhelp_no_orders));
            }
            showOrdersAdapter.setOrders(allOrders);
            showOrdersAdapter.notifyDataSetChanged();
            //Don't select any item and make sure the list is shown
            orderSelected = false;
            showOrdersAdapter.setItemSelected(false);
            setVisibility();
            //}
        }
        if (orderDetailsLoaded) {
            //reset the loading status
            orderDetailsLoaded = false;
            //Mark that we loaded the details for this order and then add them to our order and display it
            theSelectedOrder.setDetailsLoaded(true);
            theSelectedOrder.loadDetails(quizLoader.getOrderDetails.getResultsList());
            displayOrder(theSelectedOrder);
        }
        if (orderStatusUpdated) {
            //This means we successfully set the order to the next status.
            orderStatusUpdated = false;
            //Reload the orders to again show the current list
            quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
        }
        if (orderLockedForPrep) {
            orderLockedForPrep = false;
            //Check the result of the prep action
            if (quizLoader.lockOrderForPrepRequest.isRequestOK()) {
                showOrder(theSelectedOrder);
            } else {
                //Reload the list
                quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
            }

        }

    }

    //Load order details if necessary, then continue displaying it
    public void showOrder(Order order) {
        if (order.isDetailsLoaded()) {
            displayOrder(order);
        } else {
            quizLoader.loadOrderDetails(order.getIdOrder());
        }
    }

    //Display the given order, details should be loaded at this point
    public void displayOrder(Order theSelectedOrder) {
        String detailItems = "";
        String detailAmounts = "";
        String orderUser;
        String table;
        //Show if this is an order for a team or an organizer, in the first case, show the team nr, otherwise, the username
        orderUser = theSelectedOrder.getUserName();
        if (theSelectedOrder.getUserType() == QuizDatabase.USERTYPE_TEAM) {
            table = Integer.toString(theSelectedOrder.getUserNr());
        } else {
            table = "NVT (Organisatie)";
        }
        tvOrderNr.setText("" + theSelectedOrder.getOrderNr());
        tvOrderUser.setText("" + orderUser);
        tvTable.setText(table);
        detailItems = theSelectedOrder.displayOrderItems();
        detailAmounts = theSelectedOrder.displayOrderAmounts();
        tvItemNames.setText(detailItems);
        tvItemAmounts.setText(detailAmounts);
        tvOrderStatus.setText(theSelectedOrder.getLastStatusUpdate());
        if (selectedRole.equals(QuizDatabase.BARHELPERROLENAME_DELIVER)) {
            tvDetailsIntro.setText(this.getString(R.string.barhelp_orderdeliver));
        } else {
            tvDetailsIntro.setText(this.getString(R.string.barhelp_orderdetail));
        }
        //Make sure the details part is shown
        orderSelected = true;
        showOrdersAdapter.setItemSelected(true);
        setVisibility();
    }

    //Display the appropriate items in the interface, depending on your role selected
    public void setVisibility() {
        //First show or hide the appropriate filter elements
        switch (selectedRole) {
            case "":
                //No role selected yet, just display the select Role part and hide everything else
                llFilterRole.setVisibility(View.VISIBLE);
                llFilterCats.setVisibility(View.GONE);
                llDetails.setVisibility(View.GONE);
                llOrderList.setVisibility(View.GONE);
                break;
            case QuizDatabase.BARHELPERROLENAME_PREPARE:
                llFilterRole.setVisibility(View.VISIBLE);
                llFilterCats.setVisibility(View.VISIBLE);
                break;
            case QuizDatabase.BARHELPERROLENAME_DELIVER:
                llFilterRole.setVisibility(View.VISIBLE);
                llFilterCats.setVisibility(View.GONE);
                break;
        }
        //Hide filter elements if the user asked so
        if (filterHidden) {
            llFilterRole.setVisibility(View.GONE);
            llFilterCats.setVisibility(View.GONE);
        }
        //In case filters were correctly selected, show or hide either the list or the details screen
        if (selectedRole.equals(QuizDatabase.BARHELPERROLENAME_DELIVER) || (selectedRole.equals(QuizDatabase.BARHELPERROLENAME_PREPARE) && (!selectedCategories.equals("")))) {
            if (orderSelected) {
                llDetails.setVisibility(View.VISIBLE);
                llOrderList.setVisibility(View.GONE);
            } else {
                llDetails.setVisibility(View.GONE);
                llOrderList.setVisibility(View.VISIBLE);
            }
        }
        //Button ivStatusWorkingOnIt should be visible onle for preparers
        if (selectedRole.equals(QuizDatabase.BARHELPERROLENAME_PREPARE)) {
            ivStatusWorkingOnIt.setVisibility(View.VISIBLE);
        } else {
            ivStatusWorkingOnIt.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_bar_helper_home);
        setActionBarIcon();
        setActionBarTitle();
        //Get stuff from the interface
        rvShowAllOrders = findViewById(R.id.rvShowAllOrders);
        llFilterRole = findViewById(R.id.llFilterRole);
        llFilterCats = findViewById(R.id.llFilterCats);
        llDetails = findViewById(R.id.llDetails);
        llOrderList = findViewById(R.id.llOrderList);
        rgpCategories = (RadioGroup) findViewById(R.id.rgCats);
        rgpRoles = (RadioGroup) findViewById(R.id.rgRole);
        tvOverviewIntro = findViewById(R.id.tvIntroOverview);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvOrderNr = findViewById(R.id.tvOrderNr);
        tvOrderUser = findViewById(R.id.tvUser);
        tvItemNames = findViewById(R.id.tvItemNames);
        tvItemAmounts = findViewById(R.id.tvAmounts);
        tvDetailsIntro = findViewById(R.id.tvIntroDetails);
        tvTable = findViewById(R.id.tvTable);
        ivStatusToProcess = findViewById(R.id.ivStatusToProcess);
        ivStatusWorkingOnIt = findViewById(R.id.ivStatusWorkingOnIt);
        ivStatusProcessed = findViewById(R.id.ivStatusProcessed);
        //Initialize the (display)arrays with the filter conditions (categories and roles)
        //Loop over all items tha can be ordered and add their category to the catsList if needed
        for (int i = 0; i < MyApplication.itemsToOrderArray.size(); i++) {
            String thisCategory = MyApplication.itemsToOrderArray.get(i).getItemCategory();
            if (!catsList.contains(thisCategory)) {
                catsList.add(thisCategory);
            }
        }
        //Possible roles and the statuses for which they need to see orders are defined in the QuizDatabase
        displayRolesList = QuizDatabase.displayHelperRolesArray;
        rolesList = QuizDatabase.helperRolesArray;
        //Populate the rgpRoles buttons
        for (int i = 0; i < displayRolesList.size(); i++) {
            RadioButton rbn = new RadioButton(this);
            rbn.setId(View.generateViewId());
            rbn.setText(displayRolesList.get(i));
            rgpRoles.addView(rbn);
        }
        //Populate the rgCategories buttons
        rgpCategories = (RadioGroup) findViewById(R.id.rgCats);
        for (int i = 0; i < catsList.size(); i++) {
            RadioButton rbn = new RadioButton(this);
            rbn.setId(View.generateViewId());
            rbn.setText(catsList.get(i));
            rgpCategories.addView(rbn);
        }
        //Listeners for radiogroups
        //First define the listener for cats change and give it a name - we need to set this to null before clearing the checks
        //Needed to fix a bug in the clearChecked method
        RadioGroup.OnCheckedChangeListener catsCheckedListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedButton = findViewById(checkedId);
                selectedCategories = "\"" + (String) checkedButton.getText() + "\"";
                quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
            }
        };
        rgpCategories.setOnCheckedChangeListener(catsCheckedListener);
        rgpRoles.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedButton = findViewById(checkedId);
                selectedRole = (String) checkedButton.getText();
                int index = displayRolesList.indexOf(selectedRole);
                selectedStatuses = rolesList.get(index);
                if (selectedRole.equals(QuizDatabase.BARHELPERROLENAME_DELIVER)) {
                    //Nothing else needs to be selected - make sure to also uncheck the rgpCategories
                    selectedCategories = "";
                    rgpCategories.setOnCheckedChangeListener(null);
                    rgpCategories.clearCheck();
                    rgpCategories.setOnCheckedChangeListener(catsCheckedListener);
                    quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
                } else {
                    //Refresh display to show the category selector
                    //set the selectedCategories to a dummy value, it will be set correctly when the user selects a category
                    selectedCategories = "dummy";
                    //Reload the orders with this dummy category to make sure nothing is shown until you select a category
                    quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
                }
            }
        });

        //Set onclick listeners for the action icons, action to do depends on the role
        //Action to do for the red "back" arrow: do nothing for deliverers, unlock the order for preparers
        ivStatusToProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedRole.equals(QuizDatabase.BARHELPERROLENAME_DELIVER)) {
                    //Just reload the orders to show the list again
                    quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
                } else {
                    //Reset the order status to unlock the order, that will also reload the order then
                    int orderId = theSelectedOrder.getIdOrder();
                    String time = MyApplication.getCurrentTime();
                    quizLoader.updateOrderStatus(orderId, QuizDatabase.ORDERSTATUS_SUBMITTED, time);
                }
            }
        });
        //Action to do for the yellow "person" button: N/A for deliverers, just save for the preparers
        ivStatusWorkingOnIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Just reload the orders to show the list again
                quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
            }
        });
        //Action to do for the green "OK" check: set the order status to the next status
        ivStatusProcessed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set the order to the next appropriate status
                int newStatus;
                if (selectedRole.equals(QuizDatabase.BARHELPERROLENAME_PREPARE)) {
                    newStatus = QuizDatabase.ORDERSTATUS_READY;
                } else {
                    newStatus = QuizDatabase.ORDERSTATUS_DELIVERED;
                }
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                quizLoader.updateOrderStatus(orderId, newStatus, time);
            }
        });
        //Rest of the stuff
        allOrders = new ArrayList<>();
        rvShowAllOrders.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvShowAllOrders.setLayoutManager(layoutManager);
        showOrdersAdapter = new ShowOrdersAdapter(this, allOrders);
        rvShowAllOrders.setAdapter(showOrdersAdapter);
        setVisibility();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barhelper, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                //Reload orders, using the given filters. If an order is selected here, they first need to close it
                if (orderSelected) {
                    Toast.makeText(this, this.getString(R.string.barhelp_toastclosefirst), Toast.LENGTH_LONG).show();
                } else {
                    quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
                }
                break;
            case R.id.filter:
                //Show or hide the filters
                filterHidden = !filterHidden;
                setVisibility();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    @Override
    //This is called when an order was edited
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Order.RESULT_NO_ORDER_CREATED:
                //Editing was canceled
                break;
            case Order.RESULT_ORDER_CREATED:
                //This is when we modified an existing order
                quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
                break;
        }
    }
    */

    @Override
    //Don't do anything if an order was selected - otherwise, just go back
    public void onBackPressed() {
        if (orderSelected) {
            Toast.makeText(this, this.getString(R.string.barhelp_toastclosefirst), Toast.LENGTH_LONG).show();
        } else
            super.onBackPressed();
    }
}