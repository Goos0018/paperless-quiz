package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
import com.example.paperlessquiz.loginentity.GetLoginEntriesLPL;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.loginentity.LoginEntityParser;
import com.example.paperlessquiz.question.GetQuestionsLPL;
import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.question.QuestionParser;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.quizextradata.GetQuizExtraDataLPL;
import com.example.paperlessquiz.quizextradata.QuizExtraData;
import com.example.paperlessquiz.quizlistdata.QuizListData;
import com.example.paperlessquiz.quizextradata.QuizExtraDataParser;
import com.example.paperlessquiz.round.GetRoundsLPL;
import com.example.paperlessquiz.round.Round;
import com.example.paperlessquiz.round.RoundParser;

/* This screen allows you to select if you are an organizer or a participant.
All data about the quiz is loaded and stored in a Quiz object
TODO: string resources and constants
TODO: layout
 */

public class A_SelectRole extends AppCompatActivity {

    Quiz thisQuiz = new Quiz();
    String quizSheetID, scriptParams;
    boolean quizIsOpen;
    Button btnParticipant;
    Button btnOrganizer;
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_act_select_role);

        btnParticipant = findViewById(R.id.btn_participant);
        btnOrganizer = findViewById(R.id.btn_organizer);
        tvWelcome = findViewById(R.id.tv_welcome);
        QuizListData thisQuizListData = (QuizListData) getIntent().getSerializableExtra(QuizListData.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        tvWelcome.setText("Welcome to " + thisQuizListData.getName() + "\n" + thisQuizListData.getDescription());
        thisQuiz.setListData(thisQuizListData);
        QuizLoader quizLoader = new QuizLoader(this, thisQuiz.getListData().getSheetDocID());
        quizLoader.loadAll();
        /*
        //Get the additional data we don't have yet: nr of rounds, nr of participants, status,  ...
        //Get extra data from the quiz
        String scriptParamsForExtraData = GoogleAccess.PARAMNAME_DOC_ID + thisQuizListData.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + GoogleAccess.SHEET_QUIZDATA + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<QuizExtraData> googleAccessGetQuizExtraData = new GoogleAccessGet<QuizExtraData>(this, scriptParamsForExtraData);

        final GetQuizExtraDataLPL getQuizExtraDataLPL = new GetQuizExtraDataLPL();
        googleAccessGetQuizExtraData.getItems(new QuizExtraDataParser(), getQuizExtraDataLPL,
                new LoadingListenerImpl(this, "Please wait", "Loading quiz data", "Something went wrong: "));

        //Get the list of participating teams
        String scriptParamsForTeams = GoogleAccess.PARAMNAME_DOC_ID + thisQuizListData.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + GoogleAccess.SHEET_TEAMS + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<LoginEntity> googleAccessGetTeams = new GoogleAccessGet<LoginEntity>(this, scriptParamsForTeams);
        final GetLoginEntriesLPL teamsLPL = new GetLoginEntriesLPL();
        googleAccessGetTeams.getItems(new LoginEntityParser(), teamsLPL,
                new LoadingListenerImpl(this, "Please wait", "Loading quiz participants...", "Something went wrong: "));
        //Get the list of organizers
        String scriptParamsForOrganizers = GoogleAccess.PARAMNAME_DOC_ID + thisQuizListData.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + GoogleAccess.SHEET_ORGANIZERS + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<LoginEntity> googleAccessGetOrganizers = new GoogleAccessGet<LoginEntity>(this, scriptParamsForOrganizers);
        final GetLoginEntriesLPL organizersLPL = new GetLoginEntriesLPL();
        googleAccessGetOrganizers.getItems(new LoginEntityParser(), organizersLPL,
                new LoadingListenerImpl(this, "Please wait", "Loading quiz participants...", "Something went wrong: "));
        //Get the rounds
        String scriptParamsForRounds = GoogleAccess.PARAMNAME_DOC_ID + thisQuizListData.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + RoundParser.ROUNDSLIST_TABNAME + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<Round> googleAccessGet = new GoogleAccessGet<Round>(this, scriptParamsForRounds);
        GetRoundsLPL getRoundsLPL = new GetRoundsLPL();
        googleAccessGet.getItems(new RoundParser(), getRoundsLPL,
                new LoadingListenerImpl(this, "Please wait", "Loading rounds", "Something went wrong: "));
        //Get the list of questions
        final GetQuestionsLPL questionsLPL = new GetQuestionsLPL();
        String scriptParamsForQuestions = GoogleAccess.PARAMNAME_DOC_ID + thisQuizListData.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + GoogleAccess.SHEET_QUESTIONS + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<Question> googleAccessGetQuestions = new GoogleAccessGet<Question>(this, scriptParamsForQuestions);
        googleAccessGetQuestions.getItems(new QuestionParser(), questionsLPL,
                new LoadingListenerImpl(this, "Please wait", "Loading questions", "Something went wrong: "));
*/
        btnParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quizLoader.quizExtraDataLPL.getQuizExtraData().isOpen()) {
                    //if we are here, all loading actions should be finished, so we can set the result in the Quiz object
                    //First check that all reqults are OK
                    if (!(quizLoader.allChecksOK())) {
                        Toast.makeText(A_SelectRole.this, "Attention organizers, " +
                                "something is wrong with your quiz", Toast.LENGTH_LONG).show();
                    }
                    thisQuiz.setListData(thisQuizListData);
                    thisQuiz.setAdditionalData(quizLoader.quizExtraDataLPL.getQuizExtraData());
                    thisQuiz.setTeams(quizLoader.quizTeamsLPL.getLoginEntities());
                    thisQuiz.setOrganizers(quizLoader.quizOrganizersLPL.getLoginEntities());
                    thisQuiz.setRounds(quizLoader.quizRoundsLPL.getRounds());
                    thisQuiz.setAllQuestionsPerRound(quizLoader.quizQuestionsLPL.getAllQuestionsPerRound());
                    quizLoader.loadAnswers();
                    thisQuiz.setMyAnswers(quizLoader.myAnswers);
                    //thisQuiz.setAllQuestionsPerRound(questionsLPL.getAllQuestionsPerRound());
                    Intent intent = new Intent(A_SelectRole.this, B_LoginMain.class);
                    intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ, thisQuiz);
                    intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE, LoginEntity.SELECTION_PARTICIPANT);
                    startActivity(intent);
                } else {
                    tvWelcome.setText("Quiz " + thisQuizListData.getName() + " is not open yet");
                }

            }

        });

        btnOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(A_SelectRole.this, old_B_SelectLoginName.class);
                //intent.putExtra(QuizListData.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizListData);
                //intent.putExtra(QuizExtraData.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, quizExtras);
                //intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE, LoginEntity.SELECTION_ORGANIZER);
                //startActivity(intent);
            }

        });


    }
}
