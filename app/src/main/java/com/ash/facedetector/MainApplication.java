package com.ash.facedetector;

import android.app.Application;
import android.content.Context;

/**
 * Singleton class to obtain the application context
 */
public class MainApplication extends Application {
    private static MainApplication instance;

    public MainApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
