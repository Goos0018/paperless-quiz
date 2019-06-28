package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class DisplayPDF extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pdf);
        WebView wvDisplay;
        wvDisplay=findViewById(R.id.wvDisplay);


        wvDisplay.getSettings().setJavaScriptEnabled(true);
        //String pdf = "http://democratie2punt0.be/onewebmedia/Tegen verkiezingen.pdf";
        String pdf = "https://drive.google.com/uc?export=download&id=1uOphquZ69SG8ZCd9yAOwdl0rG9TKZCVQ";

        wvDisplay.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdf);
    }
}
