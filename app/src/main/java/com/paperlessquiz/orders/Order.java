package com.paperlessquiz.orders;

import java.util.HashMap;

public class Order {
    HashMap<Integer,Integer> theOrder;
    //<idOrderItem,amountOrdered>
    
    //Remove items where amountOrdered = 0
    public void removeEmpty(){
        for (Integer i: theOrder.keySet()) {
            if (theOrder.get(i) == 0){
                theOrder.remove(i);
            }
        }
    }

    public void oneItemMore(int itemId){
        if (theOrder.containsKey(itemId)){
            int curAmount = theOrder.get(Integer.valueOf(itemId));
            theOrder.put(Integer.valueOf(itemId),curAmount + 1);
        }
        else
        {
            theOrder.put(Integer.valueOf(itemId),1);
        }
    }

    public void oneItemLess(int itemId){
        if (theOrder.containsKey(itemId)){
            int curAmount = theOrder.get(Integer.valueOf(itemId));
            if (curAmount==1){
                theOrder.remove(Integer.valueOf(itemId));
            }
            else {
                theOrder.put(Integer.valueOf(itemId), curAmount -1);
            }
        }
    }
}
