package com.example.paperlessquiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.RoundsAdapter;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * This shows the Rounds to the QM and allows to set status'es for them.
 * Actionbar:Refresh/Upload/Teams/Dorst
 */
public class C_QuizmasterRounds extends MyActivity implements LoadingActivity {

    ListView lvRounds, lvTeams;
    RoundsAdapter showRoundsAdapter;

    @Override
    public void loadingComplete() {
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
            case R.id.upload:
                thisQuiz.updateRounds(C_QuizmasterRounds.this);
                break;

            case R.id.refresh:
                QuizLoader quizLoader = new QuizLoader(C_QuizmasterRounds.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadRounds();

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


    }
}
