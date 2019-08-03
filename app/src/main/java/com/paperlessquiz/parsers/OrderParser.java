package com.paperlessquiz.parsers;

import com.paperlessquiz.orders.Order;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderParser implements JsonParser<Order> {

    @Override
    public Order parse(JSONObject jo) throws JSONException {
        int idOrder, userNr, orderNr, totalCost, currentStatus;
        String lastStatusUpdate, orderCategory;
        Order order;
        try {
            idOrder = jo.getInt(QuizDatabase.COLNAME_IDORDER);
            userNr = jo.getInt(QuizDatabase.COLNAME_USER_NR);
            orderNr = jo.getInt(QuizDatabase.COLNAME_ORDERNUMBER);
            totalCost = jo.getInt(QuizDatabase.COLNAME_ORDERCOST);
            currentStatus = jo.getInt(QuizDatabase.COLNAME_ORDERSTATUS);
            lastStatusUpdate = jo.getString(QuizDatabase.COLNAME_ORDERLASTUPDATE);
            orderCategory = jo.getString(QuizDatabase.COLNAME_ORDERCATEGORY);
        } catch (Exception e) {
            idOrder = 0;
            userNr = 0;
            orderNr = 0;
            totalCost = 0;
            currentStatus = 0;
            lastStatusUpdate = "Error parsing " + jo.toString();
            orderCategory = "Error parsing " + jo.toString();
        }
        order = new Order(idOrder, userNr, orderNr, totalCost, currentStatus, lastStatusUpdate, orderCategory);
        return order;
    }
}

