package com.mcnew.liveat500px.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.mcnew.liveat500px.R;
import com.mcnew.liveat500px.activity.MoreInfoActivity;
import com.mcnew.liveat500px.adapter.PhotoListAdapter;
import com.mcnew.liveat500px.dao.PhotoItemCollectionDao;
import com.mcnew.liveat500px.dao.PhotoItemDao;
import com.mcnew.liveat500px.datatype.MutableInteger;
import com.mcnew.liveat500px.manager.HttpManager;
import com.mcnew.liveat500px.manager.PhotolistManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    /************
     * Variables
     *************/

    public interface FragmentListener {
        void onPhotoItemClicked(PhotoItemDao dao);
    }

    ListView listView;
    PhotoListAdapter listAdapter;
    Button btnNewPhotos;

    SwipeRefreshLayout swipeRefreshLayout;

    PhotolistManager photolistManager;
    MutableInteger lastPositionInteger;

    boolean isLoadingMore = false;
    int xx;
    /************
     * Functions
     *************/

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        // Initialize Fragment level's variables
        if(savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);//restore here
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init() {
        // Initialize Fragment level's variable(s) here
        photolistManager = new PhotolistManager();
        lastPositionInteger = new MutableInteger(-1);


    }

    private void initInstances(View rootView, Bundle savedInstanceState) {

        //btn newPhotos gone && show
        //init instance with rootView.findViewbyId here
        btnNewPhotos = (Button) rootView.findViewById(R.id.btnNewPhotos);
        btnNewPhotos.setOnClickListener(buttonClickListener);

        // init instance with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView);
        // Adapter
        listAdapter = new PhotoListAdapter(lastPositionInteger);
        listAdapter.setDao(photolistManager.getDao());
        // setAdapter for ListView
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(listViewItemClickListener);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(pullToRefreshListener);
        listView.setOnScrollListener(listViewScrollListener);

        if(savedInstanceState == null){
            refreshData();
        }

    }

    private void refreshData(){
        if(photolistManager.getCount() == 0){
            reloadData();
        }else{
            reloadDataNewer();
        }
    }

    private void reloadDataNewer() {
        int maxId = photolistManager.getMaximumId();
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance()
                .getService()
                .loadPhotoListAfterId(maxId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD_NEWER));
    }

    private void reloadData() {
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD));
    }

    private void loadMoreData() {
        if (isLoadingMore)
            return;
        isLoadingMore = true;
        int minId = photolistManager.getMinimumId();
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance()
                .getService()
                .loadPhotoListBeforeId(minId);
        call.enqueue(
                new PhotoListLoadCallback(PhotoListLoadCallback.MODE_LOAD_MORE));
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
        outState.putBundle("photoListManager",
                photolistManager.onSaveInstanceState());

        outState.putBundle("lastPositionInteger",
                lastPositionInteger.onSaveInstanceState());
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore instance state here
        photolistManager.onRestoreInstanceState(
                savedInstanceState.getBundle("photoListManager"));

        lastPositionInteger.onRestoreInstanceState(
                savedInstanceState.getBundle("lastPositionInteger"));
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void showButtonNewPhotos(){
        btnNewPhotos.setVisibility(View.VISIBLE);

        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(),
                R.anim.zoom_fade_in
        );
        btnNewPhotos.startAnimation(anim);
    }

    private void hideButtonNewPhotos(){
        btnNewPhotos.setVisibility(View.GONE);
        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(),
                R.anim.zoom_fade_out
        );
        btnNewPhotos.startAnimation(anim);
    }

    private void  showToast(String text){
        Toast.makeText(Contextor.getInstance().getContext(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    /*******************
     * Listenner Zone
     *******************/
    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == btnNewPhotos){
                listView.smoothScrollToPosition(0);
                hideButtonNewPhotos();
            }
        }
    };

    SwipeRefreshLayout.OnRefreshListener pullToRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    AbsListView.OnScrollListener listViewScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView,
                             int firstVisibleItem,
                             int visibleItemCount,
                             int totalItemCount) {

            if(absListView == listView){
                swipeRefreshLayout.setEnabled(firstVisibleItem == 0);
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    if (photolistManager.getCount() > 0) {
                        //Load More
                        loadMoreData();
                    }
                }
            }
        }
    };

    AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position < photolistManager.getCount()){

                PhotoItemDao dao = photolistManager.getDao().getData().get(position);
                FragmentListener listener =  (FragmentListener) getActivity();
                listener.onPhotoItemClicked(dao);
            }
        }
    };

    /****************
     * Inner Class
     *****************/
    class PhotoListLoadCallback implements Callback<PhotoItemCollectionDao> {
        public static final int MODE_RELOAD = 1;
        public static final int MODE_RELOAD_NEWER = 2;
        public static final int MODE_LOAD_MORE = 3;

        int mode;

        public PhotoListLoadCallback(int mode){
            this.mode = mode;
        }
        @Override
        public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
            swipeRefreshLayout.setRefreshing(false);
            if(response.isSuccessful()){
                PhotoItemCollectionDao dao = response.body();

                // ux scall first to see
                int firstVisiblePosition = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 :  c.getTop();

                if(mode == MODE_RELOAD_NEWER) {
                    photolistManager.insertDaoAtTopPosition(dao);
                } else if(mode == MODE_LOAD_MORE){
                    photolistManager.appendDaoAtBottomPosition(dao);
                } else{
                    photolistManager.setDao(dao);
                }

                clearLoadingMoreFlagIfCapable(mode);

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
                    if(additionalSize > 0)
                        showButtonNewPhotos();

                }
                showToast("Load Completed");

            }else{
                // Handle
                clearLoadingMoreFlagIfCapable(mode);
                try {
                    showToast(response.errorBody().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
            // Handle
            clearLoadingMoreFlagIfCapable(mode);
            swipeRefreshLayout.setRefreshing(false);
            showToast(t.toString());

        }

        private void clearLoadingMoreFlagIfCapable(int mode){
            if (mode == MODE_LOAD_MORE)
                isLoadingMore = false;
        }
    }
}
