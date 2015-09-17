package com.owl.downloadcomponent;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by qinghui on 2015/9/17.
 */
public class CustomAppication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
