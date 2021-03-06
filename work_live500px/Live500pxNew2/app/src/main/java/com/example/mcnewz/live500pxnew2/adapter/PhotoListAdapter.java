package com.example.mcnewz.live500pxnew2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mcnewz.live500pxnew2.R;
import com.example.mcnewz.live500pxnew2.dao.PhotoItemCollectionDao;
import com.example.mcnewz.live500pxnew2.dao.PhotoItemDao;
import com.example.mcnewz.live500pxnew2.manager.PhotoListManager;
import com.example.mcnewz.live500pxnew2.view.PhotoListItem;


/**
 * Created by Administrator on 10/9/2016.
 */

public class PhotoListAdapter extends BaseAdapter {
    PhotoItemCollectionDao dao;
    int lastPosition = -1;

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if(dao == null){
            return 0;
        }
        if (dao.getData() == null)
            return 0;

        return dao.getData().size() ;
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

        if(position > lastPosition){
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPosition = position;
        }

        return  item;

    }

    public void increaseLastPosition(int amount) {
        lastPosition+= amount;
    }


//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position % 2 == 0 ? 0 : 1;
//    }

//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        if(getItemViewType(i) == 0){
//
//            PhotoListItem item;
//            if(view != null){
//                item = (PhotoListItem) view;
//            }else{
//                item = new PhotoListItem(viewGroup.getContext());
//            }
//            return  item;
//        }else{
//            TextView item;
//            if(view != null){
//                item = (TextView) view;
//            }else{
//                item = new TextView(viewGroup.getContext());
//            }
//            item.setText("Position: " + i);
//            return  item;
//        }
//
//    }
}
