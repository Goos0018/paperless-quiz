package com.paperlessquiz.orders;

/**
 * This represents one ordered item as it is read from the database
 * Will be used to create the order details for the user
 */
public class ItemOrdered {

    int idOrder, idOrderItem,amountOrdered;

    public ItemOrdered(int idOrder, int idOrderItem, int amountOrdered) {
        this.idOrder = idOrder;
        this.idOrderItem = idOrderItem;
        this.amountOrdered = amountOrdered;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public int getIdOrderItem() {
        return idOrderItem;
    }

    public int getAmountOrdered() {
        return amountOrdered;
    }
}
