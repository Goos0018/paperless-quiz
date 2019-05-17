package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paperlessquiz.adapters.ParticipantsAdapter;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;

import java.util.ArrayList;

/* Select your login name from a list.
    Depending on your role (participant or organizer, this list contains teams or organizers
    Selection is passed in thisTeam or thisOrganizer
    TODO: string resources and constants
    TODO: layout
     */

public class old_B_SelectLoginName extends AppCompatActivity {
    ListView lv_NamesList;
    ParticipantsAdapter adapter;
    String thisQuizDocID;
    String sheetName;
    String loginType;
    String scriptParams;
    TextView tvTest;
    Quiz thisQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_select_login_name);

        lv_NamesList = (ListView) findViewById(R.id.lv_names_list);

        thisQuiz = (Quiz) getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        //Convert HAshmap to Arraylist to pass it to the adapter
        ArrayList<LoginEntity> list = new ArrayList<LoginEntity>();
        int i=0;
        for (LoginEntity entity:thisQuiz.getTeams().values()) {
            list.add(i,entity);
            i++;
        }
        adapter = new ParticipantsAdapter(this, list);
        //tvTest = findViewById(R.id.tv_select_name_prompt);
        //String test = thisQuiz.toString();
        //tvTest.setText(test);

        //QuizListData thisQuizListData = (QuizListData) getIntent().getSerializableExtra(QuizListData.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        //QuizExtraData thisQuizExtraData = (QuizExtraData) getIntent().getSerializableExtra(QuizExtraData.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS);
        loginType=getIntent().getStringExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);
        if (loginType.equals(LoginEntity.SELECTION_PARTICIPANT))
        {
            sheetName="Teams";
        }
        else
        {
            sheetName="Organizers";
        }
        /*scriptParams= GoogleAccess.PARAMNAME_DOC_ID + thisQuizListData.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheetName + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
                */
        lv_NamesList.setAdapter(adapter);
        lv_NamesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, go to the LogIn screen where the user enters his passkey.
                // Pass the thisLoginEntity object so the receiving screen can get the rest of the details
                Intent intent = new Intent(old_B_SelectLoginName.this, old_C_LogInToQuiz.class);
                intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ, thisQuiz);
                //intent.putExtra(QuizExtraData.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, thisQuizExtraData);
                //intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY,adapter.getItem(position));
                startActivity(intent);

            }
        });

        /*
        GoogleAccessGet<LoginEntity> googleAccessGet = new GoogleAccessGet<LoginEntity>(this, scriptParams);
        googleAccessGet.getItems(new LoginEntityParser(), new GetLoginEntriesLPL(),
                //googleAccessGet.getItems(new LoginEntityParser(), new GetLoginEntriesLPL(adapter),
                new LoadingListenerImpl(this, "Please wait", "Loading participants...", "Something went wrong: "));
        */


    }
}
