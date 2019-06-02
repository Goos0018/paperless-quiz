package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.RoundsAdapter;
import com.example.paperlessquiz.quiz.Quiz;

public class C_QuizmasterHome extends AppCompatActivity {

    Quiz thisQuiz;
    ListView lvRounds;
    //RecyclerView.LayoutManager layoutManager;
    RoundsAdapter showRoundsAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.c_quizmaster_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_act_quizmaster_home);

        thisQuiz=MyApplication.theQuiz;
        lvRounds=findViewById(R.id.lvRounds);

        //lvRounds.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //lvRounds.setLayoutManager(layoutManager);
        showRoundsAdapter = new RoundsAdapter(this,thisQuiz.getRounds());
        lvRounds.setAdapter(showRoundsAdapter);


    }
}
