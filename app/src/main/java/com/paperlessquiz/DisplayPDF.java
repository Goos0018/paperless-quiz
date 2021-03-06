package com.paperlessquiz;

import android.support.v7.app.AppCompatActivity;

/**
 * This activity displays a PDF that must be passed via the StringExtra pdfToDisplay
 */
public class DisplayPDF extends AppCompatActivity {
    /*
    String title;

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
        actionBar.setTitle(title);
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
        setContentView(R.layout.act_display_pdf);
        WebView wvDisplay;
        wvDisplay = findViewById(R.id.wvDisplay);
        setActionBarIcon();
        setActionBarTitle();

        try {
            wvDisplay.getSettings().setJavaScriptEnabled(true);
            String pdfToDisplay = getIntent().getStringExtra(QuizDatabase.PDF_TO_DISPLAY);
            title = getIntent().getStringExtra(QuizDatabase.TITLE_FOR_PDF_DISPLAY);
            String URL = QuizDatabase.GOOGLE_PDFVIEWER_URL + pdfToDisplay;
            wvDisplay.loadUrl(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}
