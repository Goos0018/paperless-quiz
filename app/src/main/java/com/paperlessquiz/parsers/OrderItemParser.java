package com.paperlessquiz.parsers;

import com.paperlessquiz.orders.OrderItem;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderItemParser implements JsonParser<OrderItem> {

    @Override
    public OrderItem parse(JSONObject jo) throws JSONException {
        int idOrderItem, itemCost, itemUnitsAvailable;
        boolean itemSoldOut;
        String itemCategory,itemName,itemDescription;
        OrderItem orderItem;
        try {
            //idQuiz = jo.getInt(QuizDatabase.COLNAME_ID_QUIZ);
            idOrderItem = jo.getInt(QuizDatabase.COLNAME_IDORDERITEM);
            itemCost = jo.getInt(QuizDatabase.COLNAME_ITEMCOST);
            itemUnitsAvailable = jo.getInt(QuizDatabase.COLNAME_ITEMUNITSAVAIALABLE);
            itemSoldOut = (jo.getInt(QuizDatabase.COLNAME_ITEMSOLDOUT) == 1);
            itemCategory = jo.getString(QuizDatabase.COLNAME_ITEMCATEGORY);
            itemName = jo.getString(QuizDatabase.COLNAME_ITEMNAME);
            itemDescription = jo.getString(QuizDatabase.COLNAME_ITEMDESCRIPTION);
        } catch (Exception e) {
            //idQuiz = 0;
            idOrderItem = 0;
            itemCost = 0;
            itemUnitsAvailable = 0;
            itemSoldOut = true;
            itemCategory = "";
            itemName = "Error parsing " + jo.toString();
            itemDescription = "";
        }
        orderItem = new OrderItem(idOrderItem,itemCost,itemUnitsAvailable, itemCategory,itemName,itemDescription, itemSoldOut);
        return orderItem;
    }
}
