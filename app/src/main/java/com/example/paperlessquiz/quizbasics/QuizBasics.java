package com.example.paperlessquiz.quizbasics;

import java.io.Serializable;
import java.util.Date;

public class QuizBasics implements Serializable
{
    private String id;
    private String name;
    private String description;
    private String sheetDocID;
    private Date date;
    private boolean open;

//TODO : rename to QuizBasics object and define Quiz as an extention (QuizBasics + Teamlist + RoundList) + define
//TODO: define different constructors for use with GetterParser, QuizPArser, ...
//Logic: Generate a list of QuizBasics objects from the QuizList + ask user to select one
//After selection, load remaining basic data (nrOfRounds and IsOpen) from the QuizData in QuizSheet
// Then ask user to log in as Participant or Organizer
//Participant: if isOpen, load Participants list and ask user to select + enter code. If valid, load and display Rounds.
//Organizer: ask user to give role and passcode. If Valid, load screen depending on Role:
    /*
    - Presenter: Participants screen or Rounds screen
    - Juror: See remarks from teams or correctors per question and calculate intermediary and final scores
    - Corrector: select a question from a list, load and correct a single question.

     */

    public QuizBasics(String id, String name, String description, String sheetDocID, boolean open) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sheetDocID = sheetDocID;
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSheetDocID() {
        return sheetDocID;
    }

    public boolean isOpen() {
        return open;
    }
}
