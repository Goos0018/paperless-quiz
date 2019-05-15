package com.example.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paperlessquiz.adapters.QuestionsAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class D_fragListQuestions extends Fragment {
    //The below variable is used to access anything in the calling activity
    //D_ParticipantHome participantHome;
    //QuestionsAdapter adapter;
    //ItemSelected actParticipantHome;
    public interface ItemSelected {
        void onItemSelected(int index);
    }

    public D_fragListQuestions() {
        // Required empty public constructor
    }
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //actParticipantHome = (ItemSelected) context;
        //participantHome = (D_ParticipantHome) getActivity();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.d_fragment_list_questions, container, false);
    }

}
