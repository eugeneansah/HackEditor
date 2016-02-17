package com.hackathon.hackmsit;

import android.app.Application;
import com.bettervectordrawable.VectorDrawableCompat;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        VectorDrawableCompat.enableResourceInterceptionFor(getResources(),
                R.drawable.file_outline,
                R.drawable.ic_add);
    }
}
