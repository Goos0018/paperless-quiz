package com.paperlessquiz;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.paperlessquiz.R;
import com.paperlessquiz.quiz.QuizGenerator;

/**
 * This activity displays a PDF that must be passed via the StringExtra pdfToDisplay
 */
public class DisplayPDF extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setActionBarIcon() {
        //Set the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false); //Display the "back" icon, we will replace this with the icon of this Quiz
    }

    public void setActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HELP!!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pdf);
        WebView wvDisplay;
        wvDisplay = findViewById(R.id.wvDisplay);
        setActionBarIcon();
        setActionBarTitle();

        try {
            wvDisplay.getSettings().setJavaScriptEnabled(true);
            String pdfToDisplay = QuizGenerator.HELPFILE_URL + getIntent().getStringExtra(QuizGenerator.PDF_TO_DISPLAY);
            String URL = "https://docs.google.com/gview?embedded=true&url=" + pdfToDisplay;
            wvDisplay.loadUrl(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
