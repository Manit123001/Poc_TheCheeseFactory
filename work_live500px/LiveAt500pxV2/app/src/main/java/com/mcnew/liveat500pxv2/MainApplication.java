package com.mcnew.liveat500pxv2;

import android.app.Application;

import com.mcnew.liveat500pxv2.manager.Contextor;

/**
 * Created by Administrator on 20/9/2016.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // initiallize thines here
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
