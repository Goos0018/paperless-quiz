package com.paperlessquiz.orders;

public class OrderItem {
    int idOrderItem, itemCost, itemUnitsAvailable;
    String itemCategory, itemName, itemDescription;
    boolean itemSoldOut;

    public OrderItem(int idOrderItem, int itemCost, int itemUnitsAvailable, String itemCategory, String itemName, String itemDescription, boolean itemSoldOut) {
        this.idOrderItem = idOrderItem;
        this.itemCost = itemCost;
        this.itemUnitsAvailable = itemUnitsAvailable;
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
}
