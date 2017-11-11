package com.example.mcnewz.live500pxnew2;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by MCNEWZ on 11-Jan-17.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initial thing(s) here
        Contextor.getInstance().init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }
}
