package com.java.linzexi;

import android.app.Application;

import com.java.linzexi.database.AppDatabase;

/**
 * Android Application class. Used for accessing singletons.
 */
public class COVID19NewsApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
        getDatabase();
    }

    public AppExecutors getExecutors() {
        return mAppExecutors;
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }
}
