package com.madiyar.ikazak.kazak;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * Created by Zhenis Madiyar on 4/21/2015.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;


        // Initialize the Parse SDK.
        Parse.initialize(this, "shDCiJh1QQLj9kFQjSHa2BQrX6tV57F4hoILH4wP", "zKEQuGaP5tpA1FqRb81QI9d4ZtpIe81bCtFza1Q6");

//        Parse.initialize(this, "Dndh2ylPfJzpKqbmKkDbCIbharcvkzsOJfxW5W4U", "jAqekjWTcUGARGOhUAbHZIX3VC3zb084eLLVrvyU");
        // Specify an Activity to handle all pushes by default.
//        PushService.setDefaultPushCallback(this, MainMenu.class);

        PushService.setDefaultPushCallback(this, DisplayPush.class);
        //	ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseInstallation.getCurrentInstallation().saveEventually();
    }

    public static Context getContext(){
        return mContext;
    }
}