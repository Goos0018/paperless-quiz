package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.paperlessquiz.google.adapter.GoogleAccess;
import com.example.paperlessquiz.google.adapter.LoadingListenerImpl;
import com.example.paperlessquiz.quizgetter.AddQuizGetterToAdapterListParsedListener;
import com.example.paperlessquiz.quizgetter.QuizGetter;
import com.example.paperlessquiz.quizgetter.QuizGetterAdapter;
import com.example.paperlessquiz.quizgetter.QuizGetterParser;

/*
This class/screen is the first screen of the app. It allows users to select a quiz from a list of available quiz'es.
 */

public class MainActivity extends AppCompatActivity {
    ListView lv_QuizList;
    QuizGetterAdapter adapter;
    String quizListSheetID = "1A4CGyeZZk2LW-xvh_P1dyeufZhV0qpBgCIQdrNEIDgk";
    String sheetName = "QuizList";
    String scriptParams = "DocID=" + quizListSheetID + "&Sheet=" + sheetName + "&action=getdata";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    lv_QuizList = (ListView) findViewById(R.id.lvQuizList);
    adapter = new QuizGetterAdapter(this);

    lv_QuizList.setAdapter(adapter);
    lv_QuizList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
            // When clicked, go to the SelectRole screen to allow the user to select the role for this quiz.
            // Pass the SpreadSheetDOcID so this screen can get the rest of the details
            Intent intent = new Intent(MainActivity.this, com.example.paperlessquiz.SelectRole.class);
            //intent.putExtra("QuizOpen",adapter.getItem(position).isOpen());
            //intent.putExtra("QuizSheetDocID",adapter.getItem(position).getSheetDocID());
            intent.putExtra("ThisQuizGetter",adapter.getItem(position));
            startActivity(intent);

        }
    });

    GoogleAccess<QuizGetter> googleAccess = new GoogleAccess<QuizGetter>(this, scriptParams);
    googleAccess.getItems(new QuizGetterParser(), new AddQuizGetterToAdapterListParsedListener(adapter),
            new LoadingListenerImpl(this, "Please wait", "Loading quizzes", "Something went wrong: "));
  }
}
