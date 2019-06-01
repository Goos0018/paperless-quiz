package com.example.paperlessquiz.google.access;

import java.util.List;

public interface ListParsedListener<T> {

    /**
     * This LPL is called at the moment we receive the information from the Google script (rounds, questions, ...)
     * @param list then contains a list of objects T as they were retrieved from the Quiz sheet
     * Every LPL contains a private object to store this information after it is parsed
     * The listParsed method populates this object with the information in the list
     * Later on, we will populate/update the corresponding Quiz object with this data
     */
    void listParsed(List<T> list);
    //List<T> getData();
}
