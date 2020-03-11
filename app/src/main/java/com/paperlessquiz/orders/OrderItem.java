package com.paperlessquiz.orders;

public class OrderItem {
    /**
     * An OrderItem is an item that can be ordered.
     * Reflects the similar table in the database, and is simply loaded once and never modified.
     * Except for the itemSoldOut and itemUnitsremaining
     */
    int idOrderItem, itemCost, itemUnitsAvailable, itemUnitsRemaining;
    String itemCategory, itemName, itemDescription;
    boolean itemSoldOut;

    public OrderItem(int idOrderItem, int itemCost, int itemUnitsAvailable, int itemUnitsRemaining, String itemCategory, String itemName, String itemDescription, boolean itemSoldOut) {
        this.idOrderItem = idOrderItem;
        this.itemCost = itemCost;
        this.itemUnitsAvailable = itemUnitsAvailable;
        this.itemUnitsRemaining = itemUnitsRemaining;
        this.itemCategory = itemCategory;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemSoldOut = itemSoldOut;
    }

    public int getIdOrderItem() {
        return idOrderItem;
    }

    public int getItemCost() {
        return itemCost;
    }

    public int getItemUnitsAvailable() {
        return itemUnitsAvailable;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public boolean isItemSoldOut() {
        return itemSoldOut;
    }

    public int getItemUnitsRemaining() {
        return itemUnitsRemaining;
    }
}
