package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.QuestionsAdapter;
import com.example.paperlessquiz.adapters.recycler.RoundsAdapter;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;

public class D_ParticipantHome extends AppCompatActivity implements RoundsAdapter.ItemClicked {
//    public class D_ParticipantHome extends AppCompatActivity implements D_fragListRounds.ItemSelected {

    //Extra objects from intent
    Quiz thisQuiz;
    LoginEntity thisLoginEntity;
    //Local items in interface
    ListView lvQuestions;
    Button btnSubmit;
    //other local variables needed
    QuestionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_act_participant_home);

        lvQuestions=(ListView) findViewById(R.id.lv_show_round_questions);

        thisQuiz = (Quiz) getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        thisLoginEntity = (LoginEntity) getIntent().getSerializableExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY);
    }
    /*
    @Override
    public void onItemSelected(int index) {
        //tvDisplayName.setText(thisQuiz.getTeams().get(index).getName());
        //tvDisplayID.setText(thisQuiz.getTeams().get(index).getId());
        adapter = new QuestionsAdapter(this, thisQuiz.getRounds().get(index));
        lvQuestions.setAdapter(adapter);
    }
    */
    @Override
    public void onItemClicked(int index)
    {
        adapter = new QuestionsAdapter(this, thisQuiz.getRounds().get(index));
        lvQuestions.setAdapter(adapter);
    }
}
