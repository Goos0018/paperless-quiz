package com.example.paperlessquiz.xxxObsolete;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.paperlessquiz.R;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.round.RoundParser;

public class old_D_PA_ShowRounds extends AppCompatActivity {
    /*

    Quiz thisQuiz;
    LoginEntity thisLoginEntity;
    //ListView lv_DisplayRoundsList;
    RecyclerView rvRounds;
    RecyclerView.Adapter adapter;
    //QuizRoundsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_pa_show_quiz_overview);

        thisQuiz = (Quiz)getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        thisLoginEntity = (LoginEntity)getIntent().getSerializableExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY);
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuiz.getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + RoundParser.ROUNDSLIST_TABNAME + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        rvRounds = (RecyclerView) findViewById(R.id.rvRounds);
        adapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };
/*
        lv_DisplayRoundsList.setAdapter(adapter);
        lv_DisplayRoundsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, go to the A_SelectRole screen to allow the user to select the role for this quiz.
                // Pass the QuizListData object so the receiving screen can get the rest of the details

                Intent intent = new Intent(old_D_PA_ShowRounds.this, old_D_PB_ShowRoundQuestions.class);
                intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ, thisQuiz);
                intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY,thisLoginEntity);
                intent.putExtra(Round.INTENT_EXTRA_NAME_THIS_ROUND,adapter.getItem(position));
                startActivity(intent);

                //Toast.makeText(old_D_PA_ShowRounds.this, "Loading round", Toast.LENGTH_SHORT).show();

            }
        });
        */
/*
        //GoogleAccessGet<Round> googleAccessGet = new GoogleAccessGet<Round>(this, scriptParams);
        //googleAccessGet.getItems(new RoundParser(), new GetRoundsLPL(),
        //        new LoadingListenerImpl(this, "Please wait", "Loading rounds", "Something went wrong: "));
    }
*/

}
