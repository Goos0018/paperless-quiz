package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paperlessquiz.adapters.QuizOverviewAdapter;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizextras.QuizExtras;
import com.example.paperlessquiz.quizextras.QuizExtrasParser;
import com.example.paperlessquiz.round.AddRoundsToQuizOverviewAdapterLPL;
import com.example.paperlessquiz.round.Round;
import com.example.paperlessquiz.round.RoundParser;

public class DPA_ShowQuizOverview extends AppCompatActivity {

    QuizExtras thisQuizExtras;
    QuizBasics thisQuizBasics;
    LoginEntity thisLoginEntity;
    ListView lv_DisplayRoundsList;
    QuizOverviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpa_show_quiz_overview);

        thisQuizBasics = (QuizBasics)getIntent().getSerializableExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        thisQuizExtras = (QuizExtras)getIntent().getSerializableExtra(QuizExtrasParser.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS);
        thisLoginEntity = (LoginEntity)getIntent().getSerializableExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY);
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuizBasics.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + RoundParser.ROUNDSLIST_TABNAME + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        lv_DisplayRoundsList = (ListView) findViewById(R.id.lv_display_rounds);
        adapter = new QuizOverviewAdapter(this);

        lv_DisplayRoundsList.setAdapter(adapter);
        lv_DisplayRoundsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, go to the A_SelectRole screen to allow the user to select the role for this quiz.
                // Pass the QuizBasics object so the receiving screen can get the rest of the details
                /*
                Intent intent = new Intent(DPA_ShowQuizOverview.this, A_SelectRole.class);
                intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS,adapter.getItem(position));
                startActivity(intent);
                */
                Toast.makeText(DPA_ShowQuizOverview.this, "Loading round", Toast.LENGTH_SHORT);

            }
        });

        GoogleAccessGet<Round> googleAccessGet = new GoogleAccessGet<Round>(this, scriptParams);
        googleAccessGet.getItems(new RoundParser(), new AddRoundsToQuizOverviewAdapterLPL(adapter),
                new LoadingListenerImpl(this, "Please wait", "Loading rounds", "Something went wrong: "));
    }


}
