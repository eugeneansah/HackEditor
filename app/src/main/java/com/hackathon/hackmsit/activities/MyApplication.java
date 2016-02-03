package com.hackathon.hackmsit.activities;

import android.app.Application;
import android.content.Context;

/**
 * Created by jarvis on 2/3/16.
 */
public class MyApplication extends Application {
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();

    }


}
