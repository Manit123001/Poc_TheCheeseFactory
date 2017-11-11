package com.mcnew.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mcnew.liveat500px.R;
import com.mcnew.liveat500px.dao.PhotoItemCollectionDao;
import com.mcnew.liveat500px.dao.PhotoItemDao;
import com.mcnew.liveat500px.datatype.MutableInteger;
import com.mcnew.liveat500px.manager.PhotolistManager;
import com.mcnew.liveat500px.view.PhotoListItem;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 10/9/2016.
 */

public class PhotoListAdapter extends BaseAdapter {

    PhotoItemCollectionDao dao;
    MutableInteger lastPositionInteger;

    public PhotoListAdapter(MutableInteger lastPositionInteger) {
        this.lastPositionInteger = lastPositionInteger;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if(dao == null){
            return 1;
        }
        if (dao.getData() == null)
            return 1;

        return dao.getData().size() + 1;
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getCount() - 1 ? 1 : 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // Last Position
        if(position == getCount() - 1){
            //Progress Bar
            ProgressBar item;
            if(view !=  null){
                item = (ProgressBar) view;
            } else {
                item = new ProgressBar(viewGroup.getContext());
            }
            return item;
        }

        PhotoListItem item;
        if(view != null){
            item = (PhotoListItem) view;
        }else{
            // if view is null set a new create
            item = new PhotoListItem(viewGroup.getContext());
        }

        PhotoItemDao dao  = (PhotoItemDao) getItem(position);

        item.setNameText(dao.getCaption());
        item.setDescriptionText(dao.getUserName() + "\n" + dao.getCamera());
        item.setImageUrl(dao.getImageUrl());

        // setAnimation listView
        if(position > lastPositionInteger.getValue()){
            Animation anim = AnimationUtils.loadAnimation(viewGroup.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPositionInteger.setValue(position);
        }

        return  item;

    }

    public void increaseLastPosition(int amount){
        lastPositionInteger.setValue(lastPositionInteger.getValue() + amount);
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
