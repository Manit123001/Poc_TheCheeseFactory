package com.mcnew.liveat500pxv2.manager;

import android.content.Context;

import com.mcnew.liveat500pxv2.dao.PhotoItemCollectionDao;
import com.mcnew.liveat500pxv2.dao.PhotoItemDao;

import java.util.ArrayList;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotolistManager {


    private Context mContext;
    private PhotoItemCollectionDao dao;
    public PhotolistManager() {
        mContext = Contextor.getInstance().getContext();
    }

    // open to send dao to function , add getset
    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    // add new dao item to top position

    public void insertDaoAtTopPosition(PhotoItemCollectionDao newDao){
        if(dao == null)
            dao = new PhotoItemCollectionDao();
        if(dao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDao>());

        dao.getData().addAll(0, newDao.getData());
    }

    //logic to calculate data id in dao is a maximum
    public int getMaximumId(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        if(dao.getData().size() == 0)
            return 0;

        // find value to more than
        int maxId = dao.getData().get(0).getId();
        for(int i = 1; i < dao.getData().size(); i++){
            maxId = Math.max(maxId, dao.getData().get(i).getId());
        }
        return maxId;
    }

    // cound of item in show
    public int getCount(){
        if (dao == null)
            return 0;
        if (dao.getData() == null)
            return 0;

        return dao.getData().size();
    }
}
