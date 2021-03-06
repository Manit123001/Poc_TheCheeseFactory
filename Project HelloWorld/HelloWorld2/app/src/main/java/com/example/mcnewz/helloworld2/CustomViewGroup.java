package com.example.mcnewz.helloworld2;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by MCNEWZ on 09-Jan-17.
 */

public class  CustomViewGroup extends FrameLayout {

    private Button btnHello;

    public CustomViewGroup(Context context) {

        super(context);
        initInflate();
        initInstances();
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    @RequiresApi(21)
    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
    }

    // Inflate Here
    private void initInflate(){
        // Inflate Layout Here
        inflate(getContext(), R.layout.sample_layout, this);
    }

    // findViewById
    private void initInstances() {
        // findViewById here
        btnHello = (Button) findViewById(R.id.btnCustomViewGroupHello);
    }

    // set Value
    public void setButtonText(String text){
        btnHello.setText(text);
    }



    // Save state ViewGroup //  this call before of savedInstancstate
    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }


    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }


    // Save State EditText
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superstate = super.onSaveInstanceState();

        // Save Children's state as a Bundle
        Bundle childrenStates = new Bundle();
        for (int i = 0; i< getChildCount(); i++){
            int id = getChildAt(i).getId();
            if(id != 0){
                SparseArray childrenState = new SparseArray();
                getChildAt(i).saveHierarchyState(childrenState);
                childrenStates.putSparseParcelableArray(String.valueOf(id),
                        childrenState);
            }
        }

        Bundle bundle = new Bundle();
        bundle.putBundle("childrenStates", childrenStates);

        // Save it to parcelable
        BundleSavedState ss = new BundleSavedState(superstate);
        ss.setBundle(bundle);
        return ss;
    }
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(state);

        //Restore SparseArray
        Bundle childrenStates = ss.getbundle().getBundle("childrenStates");

        // Restore Childres's state
        for (int i = 0; i< getChildCount(); i++){
            int id = getChildAt(i).getId();
            if(id != 0){
                if(childrenStates.containsKey(String.valueOf(id))){
                    SparseArray childrenState =
                            childrenStates.getSparseParcelableArray(String.valueOf(id));
                    getChildAt(i).restoreHierarchyState(childrenState);
                }
            }
        }
    }
}
