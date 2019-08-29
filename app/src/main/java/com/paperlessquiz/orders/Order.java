package com.paperlessquiz.orders;

import com.paperlessquiz.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents and order, a hashmap of <idOrderItem,amountOrdered> + some metadata
 */
public class Order implements Serializable {

    public static final int RESULT_NO_ORDER_CREATED = 0;
    public static final int RESULT_ORDER_CREATED = 1;
    public static final String PUTEXTRANAME_NEW_ORDER = "newOrder";
    public static final String STR_ORDER = "Bestelling ";
    public static final String STR_SEPARATOR = "-";

    int idOrder, idUser, userNr, userType, orderNr, totalCost, currentStatus;
    String lastStatusUpdate, orderCategory, userName;
    String orderName;
    boolean detailsLoaded;
    HashMap<Integer, Integer> theOrder;

    public Order(int idOrder, int idUser,int userNr, int userType, String userName, int orderNr, int totalCost, int currentStatus, String lastStatusUpdate, String orderCategory) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.userNr = userNr;
        this.userType = userType;
        this.userName=userName;
        this.orderNr = orderNr;
        this.totalCost = totalCost;
        this.currentStatus = currentStatus;
        this.lastStatusUpdate = lastStatusUpdate;
        this.orderCategory = orderCategory;
        this.orderName = STR_ORDER + orderNr + STR_SEPARATOR + orderCategory;
        detailsLoaded = false;
        theOrder = new HashMap<>();
    }

    public Order() {
        this.theOrder = new HashMap<>();
    }


    //Load the details of the order into the theOrder Hashmap based on a list of ItemsOrdered (retrieved from the database)
    public void loadDetails(ArrayList<ItemOrdered> itemsOrdered) {
        for (int i = 0; i < itemsOrdered.size(); i++) {
            ItemOrdered thisItem = itemsOrdered.get(i);
            theOrder.put(Integer.valueOf(thisItem.getIdOrderItem()), Integer.valueOf(thisItem.getAmountOrdered()));
        }
    }

    public String getOrderContentsAsString() {
        String theParam = "";
        for (Integer i : theOrder.keySet()) {
            //String itemName = MyApplication.itemsToOrder.get(i).getItemName();
            theParam = theParam + i.intValue() + "-" + theOrder.get(i) + ",";
        }
        if (theParam !=""){
        theParam = theParam.substring(0, theParam.length() - 1);}
        return theParam;
    }

    public boolean isEmpty() {
        return theOrder.isEmpty();
    }

    public String getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public String displayOrderItems() {
        String orderDetails = "";
        for (Integer i : theOrder.keySet()) {
            String itemName = MyApplication.itemsToOrder.get(i).getItemName();
            orderDetails = orderDetails + itemName + "\n";
        }
        return orderDetails;
    }

    public String displayOrderAmounts() {
        String orderDetails = "";
        for (Integer i : theOrder.keySet()) {
            orderDetails = orderDetails + " : " + theOrder.get(i) + "\n";
        }
        return orderDetails;
    }

    public String displayFullOrderDetails() {
        String orderDetails = "";
        for (Integer i : theOrder.keySet()) {
            String itemName = MyApplication.itemsToOrder.get(i).getItemName();
            String itemAmount = theOrder.get(i) + " x ";
            orderDetails = orderDetails + rpad(itemAmount, 5) + itemName + "\n";
        }
        return orderDetails;
    }

    public String rpad(String inStr, int finalLength) {
        String tmp = "\t\t\t\t\t" + inStr;
        if (finalLength > tmp.length()) {
            return tmp;
        } else {
            return (tmp).substring(tmp.length() - finalLength);
        }
    }

    //Remove items where amountOrdered = 0
    public void removeEmpty() {
        for (Integer i : theOrder.keySet()) {
            if (theOrder.get(i) == 0) {
                theOrder.remove(i);
            }
        }
    }

    public void oneItemMore(int itemId) {
        if (theOrder.containsKey(itemId)) {
            int curAmount = theOrder.get(Integer.valueOf(itemId));
            theOrder.put(Integer.valueOf(itemId), curAmount + 1);
        } else {
            theOrder.put(Integer.valueOf(itemId), 1);
        }
    }

    public void oneItemLess(int itemId) {
        if (theOrder.containsKey(itemId)) {
            int curAmount = theOrder.get(Integer.valueOf(itemId));
            if (curAmount == 1) {
                theOrder.remove(Integer.valueOf(itemId));
            } else {
                theOrder.put(Integer.valueOf(itemId), curAmount - 1);
            }
        }
    }

    public HashMap<Integer, Integer> getTheOrder() {
        return theOrder;
    }

    public int getAmountOrderedForItem(int itemId) {
        int theAmount;
        if (theOrder.containsKey(itemId)) {
            theAmount = theOrder.get(Integer.valueOf(itemId));
        } else {
            theAmount = 0;
        }
        return theAmount;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(int orderNr) {
        this.orderNr = orderNr;
    }

    public int getUserNr() {
        return userNr;
    }

    public void setUserNr(int userNr) {
        this.userNr = userNr;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public void setTheOrder(HashMap<Integer, Integer> theOrder) {
        this.theOrder = theOrder;
    }

    public boolean isDetailsLoaded() {
        return detailsLoaded;
    }

    public void setDetailsLoaded(boolean detailsLoaded) {
        this.detailsLoaded = detailsLoaded;
    }

    public int getUserType() {
        return userType;
    }

    public String getUserName() {
        return userName;
    }

    public int getIdUser() {
        return idUser;
    }
}
