package com.paperlessquiz;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.paperlessquiz.loadinglisteners.LLSilent;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.webrequest.EventLogger;
import com.paperlessquiz.orders.OrderItem;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.webrequest.HTTPSubmit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;

/**
 * This class contains stuff that is used throughout the application
 * theQuiz is initialized as an empty quiz
 */

public class MyApplication extends Application {
    public static EventLogger eventLogger;
    public static String deviceID;                                  //Contains an ID of the device on which the app runs
    public static Quiz theQuiz;                                     //The quiz for which the app is being used
    public static ArrayList<OrderItem> itemsToOrderArray;           //The items you can order here in an array for usage in an adapter
    public static HashMap<Integer, OrderItem> itemsToOrder;         //The items you can order here in a hashmap indexed by the itemId
    public static boolean loggedIn;                                 //Tracks that user is logged in
    public static boolean appPaused;                                //Tracks that app was paused
    public static boolean authorizedBreak;

    @Override
    public void onCreate() {
        super.onCreate();
        deviceID = getUniquePsuedoID();
        //Create an empty Quiz object that will be populated later on
        theQuiz = new Quiz();
        itemsToOrderArray = new ArrayList<>();
        itemsToOrder = new HashMap<>();
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

    //11/3/2020: updated to use HH:mm
    public static String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "HH:mm";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getDefault());
        String formattedDate = dateFormat.format(date);
        return formattedDate;
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

    public static void logMessage(Context context, int level, String message) {
        //Log a message to the database
        //Used to keep track of errors that happen in the app
        int idUser;
        String password;
        if (theQuiz.getThisUser() == null){
            idUser=QuizDatabase.DUMMY_USER_ID;
            password=QuizDatabase.DUMMY_USER_PSWD;
        }
        else {
            idUser = theQuiz.getThisUser().getIdUser();
            password=theQuiz.getThisUser().getUserPassword();
        }
        String encodedMessage;
        try {
            encodedMessage = URLEncoder.encode(message.replaceAll("'", "\""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encodedMessage = "Message contains characters that are not allowed";
        }
        String scriptParams = QuizDatabase.SCRIPT_LOGEVENT + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + password +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_LOGLEVEL + level +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_LOGMESSAGE + encodedMessage;
        HTTPSubmit logMessageRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_LOGMESSAGE);
        logMessageRequest.sendRequest(new LLSilent());
    }
}
