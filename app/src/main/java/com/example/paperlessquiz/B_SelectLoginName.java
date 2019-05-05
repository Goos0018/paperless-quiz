package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.ParticipantsAdapter;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
import com.example.paperlessquiz.loginentity.AddLoginEntitiesToParticipantsAdapterLPL;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.loginentity.LoginEntityParser;
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizextras.QuizExtras;
import com.example.paperlessquiz.quizextras.QuizExtrasParser;

/* Select your login name from a list.
    Depending on your role (participant or organizer, this list contains teams or organizers
    Selection is passed in thisTeam or thisOrganizer
    TODO: string resources and constants
    TODO: layout
     */

public class B_SelectLoginName extends AppCompatActivity {
    ListView lv_NamesList;
    ParticipantsAdapter adapter;
    String thisQuizDocID;
    String sheetName;
    String loginType;
    String scriptParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_select_login_name);

        lv_NamesList = (ListView) findViewById(R.id.lv_names_list);
        adapter = new ParticipantsAdapter(this);
        QuizBasics thisQuizBasics = (QuizBasics) getIntent().getSerializableExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        QuizExtras thisQuizExtras = (QuizExtras) getIntent().getSerializableExtra(QuizExtrasParser.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS);
        loginType=getIntent().getStringExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);
        if (loginType.equals(LoginEntity.SELECTION_PARTICIPANT))
        {
            sheetName="Teams";
        }
        else
        {
            sheetName="Organizers";
        }
        scriptParams= GoogleAccess.PARAMNAME_DOC_ID + thisQuizBasics.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheetName + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        lv_NamesList.setAdapter(adapter);
        lv_NamesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, go to the LogIn screen where the user enters his passkey.
                // Pass the thisLoginEntity object so the receiving screen can get the rest of the details
                Intent intent = new Intent(B_SelectLoginName.this, C_LogInToQuiz.class);
                intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizBasics);
                intent.putExtra(QuizExtrasParser.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, thisQuizExtras);
                intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY,adapter.getItem(position));
                startActivity(intent);

            }
        });

        GoogleAccessGet<LoginEntity> googleAccessGet = new GoogleAccessGet<LoginEntity>(this, scriptParams);
        googleAccessGet.getItems(new LoginEntityParser(), new AddLoginEntitiesToParticipantsAdapterLPL(adapter),
                new LoadingListenerImpl(this, "Please wait", "Loading participants...", "Something went wrong: "));



    }
}
