package com.paperlessquiz.orders;

public class ItemOrdered {
    int idOrderItem, nrOfItemsOrdered, totalCost;

    public ItemOrdered(int idOrderItem, int nrOfItemsOrdered, int totalCost) {
        this.idOrderItem = idOrderItem;
        this.nrOfItemsOrdered = nrOfItemsOrdered;
        this.totalCost = totalCost;
    }

    public int getIdOrderItem() {
        return idOrderItem;
    }

    public void setIdOrderItem(int idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

    public int getNrOfItemsOrdered() {
        return nrOfItemsOrdered;
    }

    public void setNrOfItemsOrdered(int nrOfItemsOrdered) {
        this.nrOfItemsOrdered = nrOfItemsOrdered;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
