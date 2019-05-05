package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.ParticipantsAdapter;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
import com.example.paperlessquiz.loginentity.AddLoginEntitiesToParticipantsAdapterLPL;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.loginentity.LoginEntityParser;
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizextras.QuizExtras;

/* Select your login name from a list.
    Depending on your role (participant or organizer, this list contains teams or organizers
    Selection is passed in thisTeam or thisOrganizer
    TODO: string resources and constants
    TODO: layout
     */

public class SelectLoginName extends AppCompatActivity {
    ListView lv_NamesList;
    ParticipantsAdapter adapter;
    String thisQuizDocID;
    String sheetName;
    String loginType;
    String scriptParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_name);

        lv_NamesList = (ListView) findViewById(R.id.lv_names_list);
        adapter = new ParticipantsAdapter(this);
        QuizBasics thisQuizBasics = (QuizBasics) getIntent().getSerializableExtra("thisQuizBasics");
        QuizExtras thisQuizExtras = (QuizExtras) getIntent().getSerializableExtra("thisQuizExtras");
        loginType=getIntent().getStringExtra("thisLoginType");
        if (loginType.equals("Participant"))
        {
            sheetName="Teams";
        }
        else
        {
            sheetName="Organizers";
        }
        thisQuizDocID = thisQuizBasics.getSheetDocID();
        scriptParams= "DocID=" + thisQuizDocID + "&Sheet=" + sheetName + "&action=getdata";
        lv_NamesList.setAdapter(adapter);
        lv_NamesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, go to the LogIn screen where the user enters his passkey.
                // Pass the thisLoginEntity object so the receiving screen can get the rest of the details
                Intent intent = new Intent(SelectLoginName.this, LogInToQuiz.class);
                intent.putExtra("thisQuizBasics", thisQuizBasics);
                intent.putExtra("thisQuizExtras", thisQuizExtras);
                intent.putExtra("thisLoginEntity",adapter.getItem(position));
                startActivity(intent);

            }
        });

        GoogleAccessGet<LoginEntity> googleAccessGet = new GoogleAccessGet<LoginEntity>(this, scriptParams);
        googleAccessGet.getItems(new LoginEntityParser(), new AddLoginEntitiesToParticipantsAdapterLPL(adapter),
                new LoadingListenerImpl(this, "Please wait", "Loading participants...", "Something went wrong: "));



    }
}
