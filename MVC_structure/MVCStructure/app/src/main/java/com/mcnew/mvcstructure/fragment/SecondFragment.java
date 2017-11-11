package com.mcnew.mvcstructure.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mcnew.mvcstructure.R;
import com.mcnew.mvcstructure.activity.MainActivity;
import com.mcnew.mvcstructure.util.ScreenUtils;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SecondFragment extends Fragment {
    Button btnOk;

    public SecondFragment() {
        super();
    }

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        btnOk = (Button) rootView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        int screenWidth = ScreenUtils.getInstance().getScreenWidth();
        int screenHeight= ScreenUtils.getInstance().getScreenHeight();

        // Toast screenWidth & screenHeight
       Toast.makeText(getContext(),
                "Width = " + screenWidth + "Height = " + screenHeight,
                Toast.LENGTH_SHORT).show();

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
