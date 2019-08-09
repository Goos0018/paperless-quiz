package com.paperlessquiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

import com.paperlessquiz.adapters.ShowOrdersAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

import java.util.ArrayList;

public class C_BarHelperHome extends MyActivity implements LoadingActivity, ShowOrdersAdapter.ItemClicked {

    RecyclerView rvShowAllOrders;
    RecyclerView.LayoutManager layoutManager;
    ShowOrdersAdapter showOrdersAdapter;
    TextView tvIntroFilter, tvOrderNr, tvOrderUser, tvItemNames, tvItemAmounts, tvOverviewIntro;
    TextView tvCats, tvStatuses;
    ImageView ivStatusToProcess, ivStatusProcessed;
    LinearLayout llFilterRole, llFilterCats, llMain;

    ArrayList<String> catsList = new ArrayList<>();
    ArrayList<String> usersList = new ArrayList<>();
    ArrayList<String> userIdsList = new ArrayList<>();
    ArrayList<String> statusesList = new ArrayList<>();
    ArrayList<String> displayStatusesList = new ArrayList<>();
    Order theSelectedOrder;
    Quiz thisQuiz = MyApplication.theQuiz;
    QuizLoader quizLoader = new QuizLoader(this);
    ArrayList<Order> allOrders;
    boolean ordersLoaded, orderDetailsLoaded, orderStatusUpdated;
    boolean filterVisible;
    String selectedStatuses = "", selectedCategories = "", selectedUsers = "";
    String selectedRole = "";

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
        //If orders are loaded, populate the necessary objects
        if (ordersLoaded) {
            //reset the loading status
            ordersLoaded = false;
            allOrders = quizLoader.getOrders.getResultsList();
            if (showOrdersAdapter != null) {
                //Select the top row = most recent order
                if (allOrders.size() > 0) {
                    showOrder(0);
                    showOrdersAdapter.setCurrentPosition(0);
                } else {
                    tvOverviewIntro.setText("No orders to show");
                }
                showOrdersAdapter.setOrders(allOrders);
                showOrdersAdapter.notifyDataSetChanged();
            }
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
            //reset the loading statuses
            orderStatusUpdated = false;
            //Reload the orders
            quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
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
        String orderUser;
        //Show if this is an order for a team or an organizer, in the first case, show the team nr, otherwise, the username
        if (theSelectedOrder.getUserType() == QuizDatabase.USERTYPE_TEAM) {
            orderUser = QuizDatabase.TEAM + Integer.toString(theSelectedOrder.getUserNr());
        } else {
            orderUser = theSelectedOrder.getUserName();
        }
        tvOrderNr.setText(theSelectedOrder.getOrderNr());
        tvOrderUser.setText(orderUser);
        detailItems = theSelectedOrder.displayOrderItems();
        detailAmounts = theSelectedOrder.displayOrderAmounts();
        tvItemNames.setText(detailItems);
        tvItemAmounts.setText(detailAmounts);
    }

    //Reset roles and responsibilities and prompt the user to set them again
    public void getRolesAndResponsibilities() {
        //Reset everything
        selectedRole = "";
        selectedStatuses = "";
        selectedCategories = "";
        setVisibility();
        //Prompt the user for his role
        showDialog(this.getString(R.string.barhelp_select_role), displayStatusesList, QuizDatabase.DIALOG_STATUSES);
    }


    //Display the appropriate items in the interface, depending on your role selected
    public void setVisibility() {
        switch (selectedRole) {
            case "":
                //Just display the select Role part and hide everything else
                llFilterRole.setVisibility(View.VISIBLE);
                llFilterCats.setVisibility(View.GONE);
                llMain.setVisibility(View.GONE);
                break;
            case QuizDatabase.BARHELPERROLENAME_PREPARE:
            case QuizDatabase.BARHELPERROLENAME_DELIVER:
                //Hide the roles part and show only the main part
                llFilterRole.setVisibility(View.GONE);
                llFilterCats.setVisibility(View.GONE);
                llMain.setVisibility(View.VISIBLE);
                break;
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
        llMain = findViewById(R.id.llMain);
        tvOverviewIntro = findViewById(R.id.tvIntroOverview);
        tvIntroFilter = findViewById(R.id.tvIntroFilter);
        tvCats = findViewById(R.id.tvCats);
        tvStatuses = findViewById(R.id.tvStatuses);
        tvOrderNr = findViewById(R.id.tvOrderNr);
        tvOrderUser = findViewById(R.id.tvUser);
        tvItemNames = findViewById(R.id.tvItemNames);
        tvItemAmounts = findViewById(R.id.tvAmounts);
        ivStatusToProcess = findViewById(R.id.ivStatusToProcess);
        ivStatusProcessed = findViewById(R.id.ivStatusProcessed);
        //Initialize the (display)arrays with the filter conditions, categories, statuses and users
        for (int i = 0; i < MyApplication.itemsToOrderArray.size(); i++) {
            String thisCategory = MyApplication.itemsToOrderArray.get(i).getItemCategory();
            if (!catsList.contains(thisCategory)) {
                catsList.add(thisCategory);
            }
        }
        for (int i = 0; i < thisQuiz.getTeams().size(); i++) {
            String thisTeam = thisQuiz.getTeams().get(i).getUserNr() + ". " + thisQuiz.getTeams().get(i).getName();
            String thisTeamId = Integer.toString(thisQuiz.getTeams().get(i).getIdUser());
            usersList.add(thisTeam);
            userIdsList.add(thisTeamId);
        }
        for (int i = 0; i < thisQuiz.getOrganizers().size(); i++) {
            String thisTeam = thisQuiz.getOrganizers().get(i).getName();
            String thisTeamId = Integer.toString(thisQuiz.getOrganizers().get(i).getIdUser());
            usersList.add(thisTeam);
            userIdsList.add(thisTeamId);
        }
        displayStatusesList = QuizDatabase.displayHelperRolesArray;
        statusesList = QuizDatabase.helperRolesArray;
        //Set onclick listeners for the action icons
        ivStatusToProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_SUBMITTED;
                quizLoader.updateOrderStatus(orderId, newStatus, time);
            }
        });
        ivStatusProcessed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_INPROGRESS;
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
        //Populate the categories buttons
        RadioGroup rgp = (RadioGroup) findViewById(R.id.radio_group);
        int buttons = 5;
        for (int i = 1; i <= buttons ; i++) {
            RadioButton rbn = new RadioButton(this);
            rbn.setId(View.generateViewId());
            rbn.setText("RadioButton" + i);
            rgp.addView(rbn);
        }

        getRolesAndResponsibilities();

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
                //Reload orders, using the given filters
                quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
                break;
            case R.id.filter:
                //Reset role and responsibility

                break;
        }
        return super.onOptionsItemSelected(item);
    }

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

    //Generic function to show a select item dialog
    public void showDialog(String title, ArrayList<String> itemsArrayList, int dialogId) {
        Dialog dialog;
        String[] items = itemsArrayList.toArray(new String[itemsArrayList.size()]);
        ArrayList<Integer> itemsSelected = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            itemsSelected.add(Integer.valueOf(selectedItemId));
                        } else if (itemsSelected.contains(Integer.valueOf(selectedItemId))) {
                            itemsSelected.remove(Integer.valueOf(selectedItemId));
                        }
                    }
                })
                .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        setFilter(dialogId, itemsSelected);
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

    //Called from the setDialog function, sets the filter that was modified
    public void setFilter(int dialogId, ArrayList<Integer> itemsSelected) {
        String filter = "";
        TextView displayField;
        ArrayList<String> sourceList;
        ArrayList<String> targetArray = new ArrayList<>();
        switch (dialogId) {
            case QuizDatabase.DIALOG_CATS:
                displayField = tvCats;
                sourceList = catsList;
                break;
            default:
                displayField = tvStatuses;
                sourceList = displayStatusesList;
                break;
        }
        //Build the filter string
        for (int i = 0; i < itemsSelected.size(); i++) {
            targetArray.add(sourceList.get((int) (itemsSelected.get(i))));
            filter += sourceList.get((int) (itemsSelected.get(i))) + ",";
        }
        //remove the last ","
        if (filter != "") {
            filter = filter.substring(0, filter.length() - 1);
        }
        //Display what was filtered
        if (filter.equals("")) {
            displayField.setText("All");
        } else {
            displayField.setText(filter.replace(",", "\n"));
        }

        //Build and set actual parameters that correspond to the filters
        switch (dialogId) {
            case QuizDatabase.DIALOG_CATS:
                selectedCategories = "";
                for (int i = 0; i < targetArray.size(); i++) {
                    selectedCategories += "\"" + targetArray.get(i) + "\",";
                }
                if (selectedCategories != "") {
                    selectedCategories = selectedCategories.substring(0, selectedCategories.length() - 1);
                }
                break;
            default:
                //The user selected a role
                selectedStatuses = "";
                if (targetArray.size() !=1){
                    //The user MUST select one and only one role
                }
                else
                {
                    String thisRole = targetArray.get(0);
                    if (thisRole.equals(QuizDatabase.BARHELPERROLENAME_PREPARE)){
                        int index = displayStatusesList.indexOf(thisRole);
                        selectedStatuses += statusesList.get(index);
                        //Prompt for the categories
                        showDialog(this.getString(R.string.barhelp_select_cats), catsList, QuizDatabase.DIALOG_CATS);
                    }
                }
                break;
        }
        //Reload the orders
        quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
    }
}