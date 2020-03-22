package com.paperlessquiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.squareup.picasso.Picasso;

/**
 * After having selected a Quiz, this screen allows you to select if you are an organizer or a participant.
 * We also load the list of Quiz users (teams and organizers) into the central Quiz object, so they can be selected in the next screen.
 * This userlist can also be retrieved anonymously.
 * After this screen is done, thisQuiz contains the Users+Organizers of the quiz
 */

public class A_SelectRole extends AppCompatActivity implements LoadingActivity {

    Quiz thisQuiz;
    QuizLoader quizLoader;
    Intent intent;
    Button btnParticipant;
    Button btnOrganizer;
    TextView tvQuizName, tvWelcome;
    ImageView ivQuizLogo;
    int counter = 0;

    @Override
    public void loadingComplete(int requestID) {
        //Write all the data we retrieved to the Quiz object
        switch (requestID) {
            case QuizDatabase.REQUEST_ID_GET_USERS:
                //Load the users that were retrieved into the Organizers and the Teams
                quizLoader.loadUsersIntoQuiz();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_a_select_role);
        //Get the stuff from the interface
        btnParticipant = findViewById(R.id.btn_participant);
        btnOrganizer = findViewById(R.id.btn_organizer);
        tvQuizName = findViewById(R.id.tvQuizName);
        tvWelcome = findViewById(R.id.tvWelcome);
        ivQuizLogo = findViewById(R.id.ivQuizLogo);
        thisQuiz = MyApplication.theQuiz;
        tvQuizName.setText(thisQuiz.getListData().getName());
        tvWelcome.setText(thisQuiz.getListData().getDescription());
        //Get the quiz logo
        String URL = thisQuiz.getListData().getLogoURL();
        if (URL.equals("")) {
            ivQuizLogo.setImageResource(R.mipmap.placeholder);
        } else {
            Picasso.with(this)
                    .load(URL)
                    .resize(Quiz.TARGET_WIDTH, Quiz.TARGET_HEIGHT)
                    .centerCrop()
                    .into(ivQuizLogo);
        }
        //Load the users from the Quiz
        quizLoader = new QuizLoader(this);
        quizLoader.loadUsers();
        //Login as participant
        btnParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(A_SelectRole.this, B_Login.class);
                intent.putExtra(QuizDatabase.INTENT_EXTRANAME_IS_ORGANIZER, false);
                startActivity(intent);
            }
        });
        //Login as organizer
        btnOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(A_SelectRole.this, B_Login.class);
                intent.putExtra(QuizDatabase.INTENT_EXTRANAME_IS_ORGANIZER, true);
                startActivity(intent);
            }
        });

    }
}
