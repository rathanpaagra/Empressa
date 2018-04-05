package com.example.user.empressa.values;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by User on 22-02-2018.
 */

public class FunctionCalls {


    public final boolean isInternetOn(Activity activity) {
        ConnectivityManager connect = (ConnectivityManager) activity.getSystemService(activity.getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connect.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()==true)
            return true;
        else return false;
    }

    public void LogStatus(String message) {
        Log.d("debug", message);
    }
}
