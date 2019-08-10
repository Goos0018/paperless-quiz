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
import android.support.v7.widget.CardView;
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

/**
 * Home screen for the bar responsible. Allows to view all or a selection of orders, change statuses of orders and modify them
 */
public class C_BarResponsibleHome extends MyActivity implements LoadingActivity, ShowOrdersAdapter.ItemClicked {
    RecyclerView rvShowAllOrders;
    RecyclerView.LayoutManager layoutManager;
    ShowOrdersAdapter showOrdersAdapter;
    TextView tvIntroFilter, tvSelectedOrderIntro, tvItemNames, tvItemAmounts, tvOverviewIntro, tvOrderStatus;
    TextView tvCats, tvStatuses, tvUser;
    ImageView ivOrderEdit, ivStatusSubmitted, ivStatusInProgress, ivStatusReady, ivStatusDelivered;
    ImageView ivFilterCats, ivFilterStatus, ivFilterUser;
    CardView cvFilter;

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

    @Override
    //Things to do when an order in the list is clicked
    public void onItemClicked(int index) {
        theSelectedOrder=allOrders.get(index);
        showOrder(theSelectedOrder);
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
                    theSelectedOrder=allOrders.get(0);
                    showOrder(theSelectedOrder);
                    showOrdersAdapter.setCurrentPosition(0);
                    showOrdersAdapter.setItemSelected(true);
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
    public void showOrder(Order theSelectedOrder) {
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
        String header;
        //Show if this is an order for a team or an organizer, in the first case, show the team nr, otherwise, the username
        if (theSelectedOrder.getUserType() == QuizDatabase.USERTYPE_TEAM) {
            header = QuizDatabase.TEAM + Integer.toString(theSelectedOrder.getUserNr());
        } else {
            header = theSelectedOrder.getUserName();
        }
        header += " - " + theSelectedOrder.getOrderName() + " (" + QuizDatabase.EURO_SIGN + theSelectedOrder.getTotalCost() + ")";
        tvSelectedOrderIntro.setText(header);
        detailItems = theSelectedOrder.displayOrderItems();
        detailAmounts = theSelectedOrder.displayOrderAmounts();
        tvItemNames.setText(detailItems);
        tvItemAmounts.setText(detailAmounts);
        tvOrderStatus.setText(theSelectedOrder.getLastStatusUpdate());
        showOrdersAdapter.setItemSelected(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_bar_responsible_home);
        setActionBarIcon();
        setActionBarTitle();
        //Get stuff from the interface
        rvShowAllOrders = findViewById(R.id.rvShowAllOrders);
        cvFilter = findViewById(R.id.cvFilter);
        tvIntroFilter = findViewById(R.id.tvIntroFilter);
        tvCats = findViewById(R.id.tvCats);
        tvStatuses = findViewById(R.id.tvStatuses);
        tvUser = findViewById(R.id.tvUser);
        tvSelectedOrderIntro = findViewById(R.id.tvSelectedOrderIntro);
        tvItemNames = findViewById(R.id.tvItemNames);
        tvItemAmounts = findViewById(R.id.tvAmounts);
        tvOverviewIntro = findViewById(R.id.tvIntroOverview);
        tvOrderStatus=findViewById(R.id.tvOrderStatus);
        ivOrderEdit = findViewById(R.id.ivEditOrder);
        ivStatusSubmitted = findViewById(R.id.ivStatusSubmitted);
        ivStatusInProgress = findViewById(R.id.ivStatusInProgress);
        ivStatusReady = findViewById(R.id.ivStatusReady);
        ivStatusDelivered = findViewById(R.id.ivStatusDelivered);
        ivFilterCats = findViewById(R.id.ivFilterCats);
        ivFilterStatus = findViewById(R.id.ivFilterStatus);
        ivFilterUser = findViewById(R.id.ivFilterUser);
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
        displayStatusesList = QuizDatabase.displayStatusesArray;
        statusesList = QuizDatabase.statusesArray;
        //Set onclick listeners for the filter icons
        ivFilterCats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Categories", catsList, 1);
            }
        });
        ivFilterStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Statuses", displayStatusesList, 2);
            }
        });
        ivFilterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Users", usersList, 3);
            }
        });
        //Set onclick listeners for the action icons
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
                quizLoader.updateOrderStatus(orderId, newStatus, time);
            }
        });
        ivStatusInProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_INPROGRESS;
                quizLoader.updateOrderStatus(orderId, newStatus, time);
            }
        });
        ivStatusReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_READY;
                quizLoader.updateOrderStatus(orderId, newStatus, time);
            }
        });
        ivStatusDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = theSelectedOrder.getIdOrder();
                String time = MyApplication.getCurrentTime();
                int newStatus = QuizDatabase.ORDERSTATUS_DELIVERED;
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
        //Finally, load all orders
        quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
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
                //Show or hide the filters
                if (filterVisible) {
                    tvIntroFilter.setVisibility(View.VISIBLE);
                    cvFilter.setVisibility(View.VISIBLE);
                } else {
                    tvIntroFilter.setVisibility(View.GONE);
                    cvFilter.setVisibility(View.GONE);
                }
                filterVisible = !filterVisible;
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
            case 1:
                displayField = tvCats;
                sourceList = catsList;
                break;
            case 2:
                displayField = tvStatuses;
                sourceList = displayStatusesList;
                break;
            default:
                displayField = tvUser;
                sourceList = usersList;
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
        if (filter.equals("")){
            displayField.setText("All");
        }
        else
        {
            displayField.setText(filter.replace(",", "\n"));
        }

        //Build and set actual parameters that correspond to the filters
        switch (dialogId) {
            case 1:
                selectedCategories="";
                for (int i = 0; i < targetArray.size(); i++) {
                    selectedCategories+="\"" + targetArray.get(i) + "\",";
                }
                if (selectedCategories != "") {
                    selectedCategories = selectedCategories.substring(0, selectedCategories.length() - 1);
                }
                break;
            case 2:
                selectedStatuses = "";
                for (int i = 0; i < targetArray.size(); i++) {
                    String thisStatus=targetArray.get(i);
                    int index = displayStatusesList.indexOf(thisStatus);
                    //selectedStatuses+="\"" + rolesList.get(index) + "\",";
                    selectedStatuses+=statusesList.get(index) + ",";
                }
                if (selectedStatuses != "") {
                    selectedStatuses = selectedStatuses.substring(0, selectedStatuses.length() - 1);
                }
                break;
            default:
                selectedUsers = "";
                for (int i = 0; i < targetArray.size(); i++) {
                    String thisUser=targetArray.get(i);
                    int index = usersList.indexOf(thisUser);
                    selectedUsers+="\"" + userIdsList.get(index) + "\",";
                }
                if (selectedUsers != "") {
                    selectedUsers = selectedUsers.substring(0, selectedUsers.length() - 1);
                }
                break;
        }
        //Reload the orders
        quizLoader.loadAllOrders(selectedStatuses, selectedCategories, selectedUsers);
    }
}
