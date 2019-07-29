package com.paperlessquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.paperlessquiz.R;
import com.paperlessquiz.adapters.RoundsAdapter;
import com.paperlessquiz.googleaccess.LoadingActivity;
import com.paperlessquiz.quiz.QuizLoader;

/**
 * This shows the Rounds to the QM and allows to set status'es for them.
 * Actionbar:Refresh/Upload/Teams/Dorst
 */
public class C_QuizmasterRounds extends MyActivity implements LoadingActivity {

    ListView lvRounds, lvTeams;
    RoundsAdapter showRoundsAdapter;
    QuizLoader quizLoader;

    @Override
    public void loadingComplete(int requestID) {
        quizLoader.updateRounds();
        showRoundsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quizmaster, menu);
        //Hide item for the Rounds button
        MenuItem item = menu.findItem(R.id.rounds);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                QuizLoader quizLoader = new QuizLoader(C_QuizmasterRounds.this);
                //quizLoader.loadRounds();

                break;

            case R.id.teams:
                Intent intent = new Intent(C_QuizmasterRounds.this, C_QuizmasterTeams.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_quizmaster_rounds);
        //Set the action bar
        setActionBarIcon();
        setActionBarTitle();
        lvRounds = findViewById(R.id.lvRounds);
        showRoundsAdapter = new RoundsAdapter(this, thisQuiz.getRounds());
        lvRounds.setAdapter(showRoundsAdapter);
        quizLoader = new QuizLoader(this);
        quizLoader.loadRounds();
    }
}
