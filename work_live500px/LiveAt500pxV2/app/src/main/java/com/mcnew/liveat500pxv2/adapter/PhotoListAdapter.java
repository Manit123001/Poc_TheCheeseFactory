package com.mcnew.liveat500pxv2.adapter;

import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.mcnew.liveat500pxv2.R;
import com.mcnew.liveat500pxv2.dao.PhotoItemCollectionDao;
import com.mcnew.liveat500pxv2.dao.PhotoItemDao;
import com.mcnew.liveat500pxv2.view.PhotoListItem;

/**
 * Created by Administrator on 20/9/2016.
 */

public class PhotoListAdapter extends BaseAdapter {
    PhotoItemCollectionDao dao;

    int lastPosition = -1;

    public  void setDao(PhotoItemCollectionDao dao){
        this.dao = dao;
    }
    @Override
    public int getCount() {
        if(dao == null){
            return 0;
        }
        if(dao.getData() == null) {
            return 0;
        }

        return dao.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoListItem item;
        if(convertView != null){
            item = (PhotoListItem) convertView;
        }else{
            item = new PhotoListItem(parent.getContext());
        }

        PhotoItemDao dao = (PhotoItemDao) getItem(position);

        item.setNameText(dao.getCaption());
        item.setDescriptionText(dao.getUserName() + "\n" + dao.getCamera());
        item.setImageUrl(dao.getImageUrl());

        // setAnimation listView
        if(position > lastPosition){
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPosition = position;
        }
        return item;
    }

    public void increaseLastPosition (int amount) {
        lastPosition += amount;
    }


}
