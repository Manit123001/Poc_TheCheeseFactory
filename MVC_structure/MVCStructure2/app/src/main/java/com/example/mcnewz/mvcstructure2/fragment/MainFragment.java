package com.example.mcnewz.mvcstructure2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mcnewz.mvcstructure2.R;

/**
 * Created by MCNEWZ on 10-Jan-17.
 */

public class MainFragment extends Fragment {

    int someVar;
    private TextView tvHello;

    // Constructore
    // Arguments
    public static MainFragment newInstance(int someVar){
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle(); // Arguments

        args.putInt("someVar", someVar);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read from Arguments
        someVar = getArguments().getInt("someVar");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,
                container, false);

        initInstances(rootView);
        return rootView;
    }

    // find VIew By Id Here
    private void initInstances(View rootView) {
        // findViewById here
        tvHello = (TextView) rootView.findViewById(R.id.tvHello);
    }

    public void setHelloText(String text) {
        tvHello.setText(text);
    }

    // Save State Fragments Here // It's that same an activity State
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save State Here
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // check state Not null
        if (savedInstanceState != null) {
            //Restore State here

        }
    }
}
