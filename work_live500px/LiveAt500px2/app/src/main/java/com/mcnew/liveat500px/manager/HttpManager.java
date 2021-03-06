package com.mcnew.liveat500px.manager;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.mcnew.liveat500px.manager.http.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HttpManager {

    private static HttpManager instance;

    public static HttpManager getInstance() {
        if (instance == null)
            instance = new HttpManager();
        return instance;
    }

    private Context mContext;

    // ขึ้น2
    private ApiService service;

    private HttpManager() {
        mContext = Contextor.getInstance().getContext();
        // ขึ้น 4 จัด ฟอแมต วันที่
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        // 1 ขั้น 1
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nuuneoi.com/courses/500px/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        // ขึ้น 3
        service = retrofit.create(ApiService.class);
    }

    public ApiService getService(){
        return service;
    }

}
