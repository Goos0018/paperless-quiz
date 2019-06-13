package com.example.paperlessquiz;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;


/**
 * A simple {@link Fragment} subclass.
 */
public class B_frag_LoginEntity extends Fragment {

    //Button btnSubmit;
    /*
    //NEW
    //Extra objects from intent
    Quiz thisQuiz;
    LoginEntity thisLoginEntity;
    //Local items in interface
    TextView tvDisplayName, tvDisplayID;
    EditText etPasskey;
    Button btnSubmit;
    String loginType;
    //other local variables needed
    int teamNr;
    FragmentListener listener;

public interface FragmentListener{
    public void setFields(String name,String id);
}

*/
    //END NEW
    public B_frag_LoginEntity() {
        // Required empty public constructor
    }

    /*
    public static B_frag_LoginEntity getInstance(){
        return new B_frag_LoginEntity();
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.b_frag_login_entity, container, false);
/*
        tvDisplayName = view.findViewById(R.id.tvDisplayName);
        tvDisplayID = view.findViewById(R.id.tvDisplayID);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit_login);
        etPasskey = (EditText) view.findViewById(R.id.et_passkey);
        //thisQuiz = (Quiz) getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        thisQuiz=MyApplication.theQuiz;
        Activity activity=getActivity();
        loginType = (String) activity.getIntent().getStringExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamNr = Integer.valueOf(tvDisplayID.getText().toString());
                String input = etPasskey.getText().toString().trim();
                if (loginType.equals(LoginEntity.SELECTION_PARTICIPANT)) {
                    thisLoginEntity = thisQuiz.getTeam(teamNr);
                } else {
                    thisLoginEntity = thisQuiz.getOrganizer(teamNr);
                }
                if (input.isEmpty()) {
                    //If no password was entered
                    Toast.makeText(activity, "Please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                } else {
                    //If the correct password was entered
                    if (input.equals(thisLoginEntity.getPasskey())) {
                        //If this is a participant or a corrector or the setFields team
                        if (thisLoginEntity.getType().equals(LoginEntity.SELECTION_PARTICIPANT) ||
                                (thisLoginEntity.getType().equals(LoginEntity.SELECTION_CORRECTOR)) || (thisLoginEntity.getType().equals(LoginEntity.SELECTION_TESTTEAM))) {
                            Intent intent = new Intent(activity, C_ParticipantHome.class);
                            thisQuiz.setMyLoginentity(thisLoginEntity);
                            startActivity(intent);
                        }
                        //If this is a quizmaster
                        if (thisLoginEntity.getType().equals(LoginEntity.SELECTION_QUIZMASTER)) {
                            Intent intent = new Intent(activity, C_QuizmasterRounds.class);
                            thisQuiz.setMyLoginentity(thisLoginEntity);
                            startActivity(intent);
                        }
                        //If this is a receptionist
                        if (thisLoginEntity.getType().equals("Receptionist")) {
                            Intent intent = new Intent(activity, C_QuizmasterTeams.class);
                            thisQuiz.setMyLoginentity(thisLoginEntity);
                            startActivity(intent);
                        }
                    } else {
                        //If the wrong password was entered
                        Toast.makeText(activity, "Passkey " + input + " is incorrect - please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

*/
        return view;

    }
/*
    public void setFields(String displayName, String id){

        tvDisplayName.setText(displayName);
        tvDisplayID.setText(id);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        //listener = (FragmentListener) context;
    }
*/
}
