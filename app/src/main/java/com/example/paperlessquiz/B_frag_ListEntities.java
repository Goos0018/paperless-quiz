package com.example.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.ParticipantsAdapter;
import com.example.paperlessquiz.loginentity.LoginEntity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class B_frag_ListEntities extends ListFragment {
    Quiz thisQuiz;
    ParticipantsAdapter adapter;

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
        thisQuiz = (Quiz) getActivity().getIntent().getSerializableExtra(Quiz.INTENT_PUTEXTRANAME_THIS_QUIZ);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ParticipantsAdapter(getActivity(), thisQuiz.getTeamsList());
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        actLoginMain.onItemSelected(position);
    }
}
