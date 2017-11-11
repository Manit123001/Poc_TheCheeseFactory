package com.mcnew.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mcnew.liveat500px.dao.PhotoItemDao;
import com.mcnew.liveat500px.manager.PhotolistManager;
import com.mcnew.liveat500px.view.PhotoListItem;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 10/9/2016.
 */

public class PhotoListAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        if(PhotolistManager.getInstance().getDao() == null){
            return 0;
        }
        if (PhotolistManager.getInstance().getDao().getData() == null)
            return 0;

        return PhotolistManager.getInstance().getDao().getData().size();
    }

    @Override
    public Object getItem(int position) {
        return PhotolistManager.getInstance().getDao().getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

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

        return  item;

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
