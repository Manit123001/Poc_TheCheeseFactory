package com.mcnew.liveat500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.mcnew.liveat500px.R;
import com.mcnew.liveat500px.adapter.PhotoListAdapter;
import com.mcnew.liveat500px.dao.PhotoItemCollectionDao;
import com.mcnew.liveat500px.manager.HttpManager;
import com.mcnew.liveat500px.manager.PhotolistManager;

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
        // init instance with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView);
        // Adapter
        listAdapter = new PhotoListAdapter();
        // setAdapter for ListView
        listView.setAdapter(listAdapter);

        // call 1
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();
        // call 2
        call.enqueue(new Callback<PhotoItemCollectionDao>() {
            @Override
            public void onResponse(Call<PhotoItemCollectionDao> call,
                                   Response<PhotoItemCollectionDao> response) {
                if(response.isSuccessful()){
                    PhotoItemCollectionDao dao = response.body();
                    // เมื่อมีการโหลด ข้อมูลมาจะ เอาไปเก็บไว้ ที่ PhotoListManager
                    PhotolistManager.getInstance().setDao(dao);

                    listAdapter.notifyDataSetChanged();

                    Toast.makeText(Contextor.getInstance().getContext(),
                            dao.getData().get(0).getCaption(),
                            Toast.LENGTH_SHORT)
                            .show();
                }else{
                    // Handle
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(),
                                response.errorBody().string() + "Connected but fail.",
                                Toast.LENGTH_SHORT)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PhotoItemCollectionDao> call,
                                  Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(),
                        t.toString()+ "Server have a problem.",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
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
}
