package com.example.paperlessquiz;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessAddLine;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quizextradata.QuizExtraData;
import com.example.paperlessquiz.quizlistdata.QuizListData;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class B_frag_LoginEntity extends Fragment {

    Button btnSubmit;


    public B_frag_LoginEntity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.b_frag_login_entity, container, false);

    }

}
