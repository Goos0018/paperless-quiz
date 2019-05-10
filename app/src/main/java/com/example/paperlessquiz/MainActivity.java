package com.example.paperlessquiz;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.QuizBasicsAdapter;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessAddLine;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
import com.example.paperlessquiz.quizbasics.AddQuizBasicsToAdapterLPL;
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizbasics.QuizBasicsParser;

/*
This class/screen is the first screen of the app. It allows users to select a quiz from a list of available quiz'es.
The list of the available quizzes comes from a centra, hardcoded Google Sheet
Basic details of the selected quiz stored and passed around via a quizBasics variable
TODO: extract string resources + constants
TODO: layout
 */

public class MainActivity extends AppCompatActivity {
    ListView lv_QuizList;
    QuizBasicsAdapter adapter;
    String sheetName = QuizBasics.QUIZLIST_TABNAME;
    String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + QuizBasics.QUIZLIST_DOC_ID + GoogleAccess.PARAM_CONCATENATOR +
            GoogleAccess.PARAMNAME_SHEET + QuizBasics.QUIZLIST_TABNAME + GoogleAccess.PARAM_CONCATENATOR +
            GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    lv_QuizList = (ListView) findViewById(R.id.lvQuizList);
    adapter = new QuizBasicsAdapter(this);

    lv_QuizList.setAdapter(adapter);
    lv_QuizList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            // When clicked, go to the A_SelectRole screen to allow the user to select the role for this quiz.
            // Pass the QuizBasics object so the receiving screen can get the rest of the details
            Intent intent = new Intent(MainActivity.this, A_SelectRole.class);
            intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS,adapter.getItem(position));
            startActivity(intent);

        }
    });

    GoogleAccessGet<QuizBasics> googleAccessGet = new GoogleAccessGet<QuizBasics>(this, scriptParams);
    googleAccessGet.getItems(new QuizBasicsParser(), new AddQuizBasicsToAdapterLPL(adapter),
            new LoadingListenerImpl(this, "Please wait", "Loading quizzes", "Something went wrong: "));
  }

  //This part is used to log whenever the user exits the app when he is not supposed to do so
    @Override
    public void onPause()
    {
        //GoogleAccessAddLine logExit = new GoogleAccessAddLine(this,GoogleAccess.PARAMNAME_ACTION+GoogleAccess.PARAMVALUE_ADDLINE + );
        //logExit.addLine();
        super.onPause();
    }
        /*
      try
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(super.getBaseContext());
                dialog.setTitle( "Hello" )
                        .setIcon(R.mipmap.placeholder)
                        .setMessage("Do  you really want to exit the app?")
                         .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                         {
                            public void onClick(DialogInterface dialoginterface, int i)
                            {
                                dialoginterface.cancel();
                            }
                         })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                confirmed=true;
                            }
                        }).show();
                flag = false; //reset you flag
            }
            catch(Exception e){}

        if (confirmed){super.onPause();} else{
            super.onResume();}
            */



}

