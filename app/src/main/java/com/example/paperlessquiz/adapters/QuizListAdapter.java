package com.example.paperlessquiz.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paperlessquiz.R;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quizlistdata.QuizListData;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class QuizListAdapter extends ArrayAdapter<QuizListData>
{
    private final Context context;
    public Image image;

    public QuizListAdapter(Context context) {
        super(context, R.layout.row_layout_select_quiz, new ArrayList<QuizListData>());
        this.context = context;
    }

    @NonNull
    @Override
    // This runs for every item in the view
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_select_quiz,parent,false);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
        TextView tvDescription = (TextView) rowView.findViewById(R.id.tv_description);
        ImageView ivQuizLogo = (ImageView) rowView.findViewById(R.id.ivQuizLogo);
        tvTitle.setText(getItem(position).getName());
        tvDescription.setText(this.getItem(position).getDescription());
        String URL = this.getItem(position).getLogoURL();
        //URL ="http://i.imgur.com/DvpvklR.png";
        if (URL.equals(""))
        {
            ivQuizLogo.setImageResource(R.mipmap.placeholder);
        }
        else {
            Picasso.with(context)
                    .load(URL)
                    .resize(Quiz.TARGET_WIDTH, Quiz.TARGET_HEIGHT)
                    .centerCrop()
                    .into(ivQuizLogo);
        }
        return rowView;
    }
}