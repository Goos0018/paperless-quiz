package com.paperlessquiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Extention of AppCompatActivity - used to set the actionBar icon in a standard way + sets the thisQuiz variable
 */
public class MyActivity extends AppCompatActivity {

    public Target mTarget;
    public Quiz thisQuiz = MyApplication.theQuiz;

    /*
    public void displayPDF(String pdfToDisplay, String title){
        Intent intentDisplayPDF = new Intent(MyActivity.this, DisplayPDF.class);
        intentDisplayPDF.putExtra(QuizDatabase.PDF_TO_DISPLAY, pdfToDisplay);
        intentDisplayPDF.putExtra(QuizDatabase.TITLE_FOR_PDF_DISPLAY, title);
        startActivity(intentDisplayPDF);
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.common, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                MyApplication.authorizedBreak = true;
                Intent intentHelp = new Intent(MyActivity.this, DisplayHelpTopics.class);
                intentHelp.putExtra(QuizDatabase.INTENT_EXTRANAME_HELPTYPE, thisQuiz.getThisUser().getUserType());
                startActivity(intentHelp);
                break;
            case R.id.order:
                MyApplication.authorizedBreak = true;
                Intent intent = new Intent(MyActivity.this, D_OrderHome.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

        public void setActionBarIcon () {
            //Set the action bar
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true); //Display the "back" icon, we will replace this with the icon of this Quiz
            //final Target mTarget = new Target() {
            mTarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                    actionBar.setHomeAsUpIndicator(mBitmapDrawable);
                }

                @Override
                public void onBitmapFailed(Drawable drawable) {
                }

                @Override
                public void onPrepareLoad(Drawable drawable) {
                }
            };
            String URL = thisQuiz.getListData().getLogoURL();
            if (URL.equals("")) {
                actionBar.setDisplayHomeAsUpEnabled(false);//If the Quiz has no logo, then don't display anything
            } else {
                //Picasso.with(this).load("http://www.meerdaal.be//assets/logo-05c267018885eb67356ce0b49bf72129.png").into(mTarget);
                Picasso.with(this).load(thisQuiz.getListData().getLogoURL()).resize(Quiz.ACTIONBAR_ICON_WIDTH, Quiz.ACTIONBAR_ICON_HEIGHT).into(mTarget);
            }
        }

        public void setActionBarTitle () {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(thisQuiz.getThisUser().getName());
        }


        //Show one frag and hide two other - used for toggling a holder that can host max three different frags
        protected void toggleFragments ( int placeHolderID, Fragment fragToShow, Fragment
        fragToHide1, Fragment fragToHide2){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (fragToShow.isAdded()) { // if the fragment is already in container
                ft.show(fragToShow);
            } else { // fragment needs to be added to frame container
                ft.add(placeHolderID, fragToShow);
                ft.show(fragToShow);
            }
            // Hide other fragments
            if (fragToHide1.isAdded()) {
                ft.hide(fragToHide1);
            }
            if (fragToHide2.isAdded()) {
                ft.hide(fragToHide2);
            }
            // Commit changes
            ft.commit();
        }
    }
