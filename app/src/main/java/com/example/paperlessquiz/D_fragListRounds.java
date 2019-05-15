package com.example.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.QuizRoundsAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class D_fragListRounds extends ListFragment {
    //The below variable is used to access anything in the calling activity
    D_ParticipantHome participantHome;
    QuizRoundsAdapter adapter;

    ItemSelected actParticipantHome;
    public interface ItemSelected {
        void onItemSelected(int index);
    }


    public D_fragListRounds() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        actParticipantHome = (ItemSelected) context;
        participantHome = (D_ParticipantHome) getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new QuizRoundsAdapter(getActivity(), participantHome.thisQuiz.getRounds());
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        actParticipantHome.onItemSelected(position);
    }
}
