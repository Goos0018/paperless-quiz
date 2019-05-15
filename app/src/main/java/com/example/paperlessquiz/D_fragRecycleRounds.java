package com.example.paperlessquiz;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paperlessquiz.adapters.recycler.RoundsAdapter;
import com.example.paperlessquiz.round.Round;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class D_fragRecycleRounds extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    View view;
    D_ParticipantHome participantHome;

    public D_fragRecycleRounds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.d_fragment_list_rounds,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        participantHome = (D_ParticipantHome) getActivity();
        ArrayList<Round> rounds;
        rounds = participantHome.thisQuiz.getRounds();
        recyclerView = view.findViewById(R.id.rvRounds);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL,false);
        SnapHelper snapHelper = new PagerSnapHelper();
        recyclerView.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(recyclerView);
        myAdapter = new RoundsAdapter(this.getActivity(),rounds);
        recyclerView.setAdapter(myAdapter);
    }
}
