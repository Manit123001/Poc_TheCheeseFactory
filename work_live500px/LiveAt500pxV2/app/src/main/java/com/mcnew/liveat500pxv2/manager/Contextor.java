package com.mcnew.liveat500pxv2.manager;

import android.content.Context;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class Contextor {

    private static Contextor instance;

    public static Contextor getInstance() {
        if (instance == null)
            instance = new Contextor();
        return instance;
    }

    private Context mContext;

    private Contextor() {

    }

    public void init(Context context){
        mContext = context;
    }

    public Context getContext(){
        return  mContext;
    }

}
