package com.paperlessquiz.parsers;

import com.paperlessquiz.orders.ItemOrdered;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemOrderedParser implements JsonParser<ItemOrdered> {

    @Override
    public ItemOrdered parse(JSONObject jo) throws JSONException {
        int idOrder, idOrderItem, amountOrdered;
        ItemOrdered itemOrdered;
        try {
            idOrder = jo.getInt(QuizDatabase.COLNAME_IDORDER);
            idOrderItem = jo.getInt(QuizDatabase.COLNAME_IDORDERITEM);
            amountOrdered = jo.getInt(QuizDatabase.COLNAME_ORDEREDAMOUNT);
        } catch (Exception e) {
            idOrder = 0;
            idOrderItem = 0;
            amountOrdered = 0;
        }
        itemOrdered = new ItemOrdered(idOrder, idOrderItem, amountOrdered);
        return itemOrdered;
    }
}