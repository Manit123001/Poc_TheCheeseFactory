package com.example.mcnewz.live500pxnew2.manager;

import android.content.Context;

import com.example.mcnewz.live500pxnew2.dao.PhotoItemCollectionDao;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListManager {


    private Context mContext;
    private PhotoItemCollectionDao dao;

    public PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
        // Load data from Persistent Storage
    }

    // Open role to set Dao
    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    public void insertDaoAtTopPosition (PhotoItemCollectionDao newDao){
        if(dao == null)
            dao = new PhotoItemCollectionDao();
        if(dao.getData() == null)
            dao = new PhotoItemCollectionDao();
        dao.getData().addAll(0, newDao.getData());

    }

    // Get Max ID
    public int getMaximumId(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        if(dao.getData().size() == 0)
            return 0;

        int maxId = dao.getData().get(0).getId();

        for(int i = 1; i < dao.getData().size(); i++){
            maxId = Math.max(maxId, dao.getData().get(i).getId());
        }
        return maxId;
    }

    // refresh when view on top 0
    public int getCount(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        return dao.getData().size() ;

    }
}
