package com.example.paperlessquiz.quizgetter;

import android.location.Location;

import com.example.paperlessquiz.Organizer;
import com.example.paperlessquiz.round.Round;
import com.example.paperlessquiz.Team;

import java.util.Date;

public class QuizGetter
{

    private String id;
    private String title;
    private String description;
    private String spreadsheetDocId;
    private Date date;
    private boolean open;



    public QuizGetter(String id, String title, String description, String spreadsheetDocId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.spreadsheetDocId = spreadsheetDocId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSpreadsheetDocId() {
        return spreadsheetDocId;
    }

    public boolean isOpen() {
        return open;
    }
}
