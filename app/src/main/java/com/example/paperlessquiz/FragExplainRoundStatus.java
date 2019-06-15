package com.example.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragExplainRoundStatus extends Fragment {

    TextView tvExplainRoundStatus;
    HasExplainRoundStatus callingActivity;

    interface HasExplainRoundStatus{
        String getRoundStatusExplanation();
    }

    public FragExplainRoundStatus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.frag_explain_round_status, container, false);
        tvExplainRoundStatus = v.findViewById(R.id.tvExplainRndStatus);
        tvExplainRoundStatus.setText(callingActivity.getRoundStatusExplanation());
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callingActivity=(HasExplainRoundStatus) context;
    }
    public void setStatus(String status){
        if (tvExplainRoundStatus!=null){
        tvExplainRoundStatus.setText(callingActivity.getRoundStatusExplanation());}
    }
}
