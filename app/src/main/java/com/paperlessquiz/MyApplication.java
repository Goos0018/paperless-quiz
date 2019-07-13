package com.paperlessquiz;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.paperlessquiz.googleaccess.EventLogger;
import com.paperlessquiz.loginentity.Team;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * This class contains stuff that is used throughout the application
 */

public class MyApplication extends Application {
    public static String logDocID;                                  //The ID of a google sheet where we log things that do not belong to a particular quiz - in practice the QuizList sheet
    public static EventLogger eventLogger;
    public static String deviceID;                                  //Contains an ID of the device on which the app runs
    public static Quiz theQuiz;                                     //The quiz for which the app is being used
    //public static Team theTeam;                                     //The team that is taking the quiz (null for Organizers)
    public static ArrayList<String> googleLog = new ArrayList<>();  //An arraylist that will contain a log of all transactions to and from a Google sheet (=the back-end of this app)
    public static boolean loggedIn;                                 //Tracks that user is logged in
    public static boolean appPaused;                                //Tracks that app was paused
    public static int debugLevel = 3;                               //Overall debug level for the google interaction, initialized here but later overwritten with what is configured for the Quiz
    public static boolean keepLogs = true;                          //Indicates if you want to overwrite logs of the GScript each time
    public static int appDebugLevel = 3;                            //Overall debug level for the app itself, initialized here but later overwritten with what is configured for the Quiz
    public static HashMap<String,String> helpFiles;

    @Override
    public void onCreate() {
        super.onCreate();
        logDocID = "1A4CGyeZZk2LW-xvh_P1dyeufZhV0qpBgCIQdrNEIDgk";
        eventLogger = new EventLogger(this, logDocID, QuizGenerator.SHEET_EVENTLOG);
        deviceID = getUniquePsuedoID();
        theQuiz = new Quiz();
        helpFiles = new HashMap<>();
        helpFiles.put(QuizGenerator.TYPE_CORRECTOR, QuizGenerator.HELPFILE_CORRECTOR);
        helpFiles.put(QuizGenerator.TYPE_JUROR, QuizGenerator.HELPFILE_JUROR);
        helpFiles.put(QuizGenerator.TYPE_PARTICIPANT, QuizGenerator.HELPFILE_PARTICIPANT);
        helpFiles.put(QuizGenerator.TYPE_QUIZMASTER, QuizGenerator.HELPFILE_QUIZMASTER);
        helpFiles.put(QuizGenerator.TYPE_RECEPTIONIST, QuizGenerator.HELPFILE_RECEPTIONIST);
        //Portrait mode or landscape mode depending on screen size
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                if (isTabletDevice(activity)) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                // for each activity this function is called and so it is set to portrait mode
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    public static boolean isTabletDevice(Context activityContext) {
        // Verifies if the Generalized Size of the device is XLARGE to be
        // considered a Tablet
        boolean xlarge = ((activityContext.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE);

        // If XLarge, checks if the Generalized Density is at least MDPI
        // (160dpi)
        if (xlarge) {
            DisplayMetrics metrics = new DisplayMetrics();
            Activity activity = (Activity) activityContext;
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            // MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
            // DENSITY_TV=213, DENSITY_XHIGH=320
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
                    || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
                    || metrics.densityDpi == DisplayMetrics.DENSITY_TV
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {

                // Yes, this is a tablet!
                return true;
            }
        }

        // No, this is not a tablet!
        return false;
    }

    public static void setLogDocID(String logDocID) {
        MyApplication.logDocID = logDocID;
    }

    //Return pseudo unique ID
    public static String getUniquePsuedoID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // https://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        // Thanks @Joe!
        // https://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        MyApplication.loggedIn = loggedIn;
    }

    public static boolean isAppPaused() {
        return appPaused;
    }

    public static void setAppPaused(boolean appPaused) {
        MyApplication.appPaused = appPaused;
    }

    public static int getDebugLevel() {
        return debugLevel;
    }

    public static void setDebugLevel(int debugLevel) {
        MyApplication.debugLevel = debugLevel;
    }

    public static boolean isKeepLogs() {
        return keepLogs;
    }

    public static void setKeepLogs(boolean keepLogs) {
        MyApplication.keepLogs = keepLogs;
    }

    public static int getAppDebugLevel() {
        return appDebugLevel;
    }

    public static void setAppDebugLevel(int appDebugLevel) {
        MyApplication.appDebugLevel = appDebugLevel;
    }


}
