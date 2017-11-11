package com.example.mcnewz.live500pxnew2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mcnewz.live500pxnew2.manager.PhotoListManager;
import com.example.mcnewz.live500pxnew2.view.PhotoListItem;


/**
 * Created by Administrator on 10/9/2016.
 */

public class PhotoListAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        if(PhotoListManager.getInstance().getDao() == null){
            return 1;
        }
        if (PhotoListManager.getInstance().getDao().getData() == null)
            return 1;

        return PhotoListManager.getInstance().getDao().getData().size() ;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
        return position % 2 == 0 ? 0 : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoListItem item;
        if(convertView != null){
            item = (PhotoListItem) convertView;
        }else{
            item = new PhotoListItem(parent.getContext());
        }
        return  item;
    }
}
