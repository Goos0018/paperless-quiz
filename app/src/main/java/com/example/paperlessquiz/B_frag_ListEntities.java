package com.example.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.ParticipantsAdapter;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;


/**
 * A simple {@link Fragment} subclass.
 */
public class B_frag_ListEntities extends ListFragment {
    Quiz thisQuiz;
    ParticipantsAdapter adapter;
    String loginType;

    ItemSelected actLoginMain;
    public interface  ItemSelected{
        void onItemSelected(int index);
    }

    public B_frag_ListEntities() {
        // Required empty public constructor
    }


    @Override
    //called when the fragment is associated with the activity
    public void onAttach(Context context) {
        super.onAttach(context);
        actLoginMain = (ItemSelected) context;
        thisQuiz = (Quiz) getActivity().getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        loginType = getActivity().getIntent().getStringExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (loginType.equals(LoginEntity.SELECTION_PARTICIPANT)){
            adapter = new ParticipantsAdapter(getActivity(), thisQuiz.getTeams());
        }
        else
        {
            adapter = new ParticipantsAdapter(getActivity(), thisQuiz.getOrganizers());
        }
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        actLoginMain.onItemSelected(position);
    }

}
