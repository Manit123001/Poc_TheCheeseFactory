package com.mcnew.liveat500pxv2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mcnew.liveat500pxv2.R;
import com.mcnew.liveat500pxv2.adapter.PhotoListAdapter;
import com.mcnew.liveat500pxv2.dao.PhotoItemCollectionDao;
import com.mcnew.liveat500pxv2.manager.Contextor;
import com.mcnew.liveat500pxv2.manager.HttpManager;
import com.mcnew.liveat500pxv2.manager.PhotolistManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    ListView listView;
    PhotoListAdapter listAdapter;

    SwipeRefreshLayout swipeRefreshLayout;

    PhotolistManager photolistManager;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        photolistManager = new PhotolistManager();

        // init instance with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView);

        listAdapter = new PhotoListAdapter();
        listView.setAdapter(listAdapter);

        //pull to refresh
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem,
                                 int visibleItemCount,
                                 int totalItemCount) {
            swipeRefreshLayout.setEnabled(firstVisibleItem == 0);
            }
        });

        refreshData();
    }

    private void refreshData() {
        if (photolistManager.getCount() == 0)
            reloadData();
        else
            reloadDataNewer();
    }


    private void reloadDataNewer() {
        int maxId = photolistManager.getMaximumId();
        Call<PhotoItemCollectionDao> call = HttpManager
                .getInstance()
                .getService()
                .loadPhotoListAfterId(maxId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD_NEWER));
    }


    private void reloadData() {
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD));
    }


    private void showToast(String text){
        Toast.makeText(Contextor.getInstance().getContext(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }


    class PhotoListLoadCallback implements Callback<PhotoItemCollectionDao> {
        public static final int MODE_RELOAD = 1;
        public static final int MODE_RELOAD_NEWER = 2;

        int mode;

        public PhotoListLoadCallback (int mode){
            this.mode = mode;
        }
        @Override
        public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
            // don't rotate
            swipeRefreshLayout.setRefreshing(false);
            if(response.isSuccessful()){
                PhotoItemCollectionDao dao = response.body();

                int firstVisiblePosition = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 : c.getTop();

                if (mode == MODE_RELOAD_NEWER){
                    photolistManager.insertDaoAtTopPosition(dao);

                }else {
                    photolistManager.setDao(dao);
                }
                listAdapter.setDao(photolistManager.getDao());
                listAdapter.notifyDataSetChanged();

                // restore listView ux scall is pull to refresh
                if(mode == MODE_RELOAD_NEWER){
                    // Maintain Scroll Position
                    int additionalSize = (dao != null && dao.getData() != null) ?  dao.getData().size() : 0;
                    // increase a lastPosition in listAdapter
                    listAdapter.increaseLastPosition(additionalSize);

                    listView.setSelectionFromTop(firstVisiblePosition + additionalSize,
                            top);


                }
                showToast("Load Completed");

            }else {
                //Handale
                try {
                    showToast(response.errorBody().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
            swipeRefreshLayout.setRefreshing(false);
            showToast(t.toString());
        }
    }
}
