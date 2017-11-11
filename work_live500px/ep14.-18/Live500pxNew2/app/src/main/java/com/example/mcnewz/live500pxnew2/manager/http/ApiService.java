package com.example.mcnewz.live500pxnew2.manager.http;

import com.example.mcnewz.live500pxnew2.dao.PhotoItemCollectionDao;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by MCNEWZ on 11-Jan-17.
 */

public interface ApiService {
    @POST("list")
    Call<PhotoItemCollectionDao> loadPhotoList();

}
