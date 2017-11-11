package com.mcnew.liveat500px.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 13/9/2016.
 */

public class PhotoItemCollectionDao {
    @SerializedName("success") private boolean succes;
    @SerializedName("data") private List<PhotoItemDao> data;

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public List<PhotoItemDao> getData() {
        return data;
    }

    public void setData(List<PhotoItemDao> data) {
        this.data = data;
    }
}
