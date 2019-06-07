package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.RoundsAdapter;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.google.access.LoadingListenerNotify;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.round.Round;
import com.example.paperlessquiz.round.RoundParser;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

public class C_QuizmasterHome extends AppCompatActivity {

    Quiz thisQuiz;
    ListView lvRounds,lvTeams;
    //RecyclerView.LayoutManager layoutManager;
    RoundsAdapter showRoundsAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.c_quizmaster_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upload:
                thisQuiz.updateRounds(C_QuizmasterHome.this);
                /*
                ArrayList<Round> roundsList = thisQuiz.getRounds();
                String tmp = "[";
                for (int i = 0; i < roundsList.size(); i++) {
                    tmp = tmp + roundsList.get(i).toString();
                    if (i<roundsList.size()-1){
                        tmp = tmp + ",";
                    }
                    else{
                        tmp = tmp + "]";
                    }
                }

                String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuiz.getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_SHEET + GoogleAccess.SHEET_ROUNDS + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_RECORD_ID + "1" + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_FIELDNAME + RoundParser.ROUND_NAME + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_NEWVALUES + tmp + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SETDATA;
                GoogleAccessSet submitRounds = new GoogleAccessSet(C_QuizmasterHome.this, scriptParams);
                submitRounds.setData(new LoadingListenerNotify(C_QuizmasterHome.this, thisQuiz.getMyLoginentity().getName(),
                        "Submitting round updates"));
                */
                break;

            case R.id.download:
                    QuizLoader quizLoader = new QuizLoader(C_QuizmasterHome.this, thisQuiz.getListData().getSheetDocID());
                    quizLoader.loadRounds();
                    break;

            case R.id.refresh:
                showRoundsAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_act_quizmaster_home);

        thisQuiz = MyApplication.theQuiz;
        lvRounds = findViewById(R.id.lvRounds);

        //lvRounds.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //lvRounds.setLayoutManager(layoutManager);
        showRoundsAdapter = new RoundsAdapter(this, thisQuiz.getRounds());
        lvRounds.setAdapter(showRoundsAdapter);


    }
}
