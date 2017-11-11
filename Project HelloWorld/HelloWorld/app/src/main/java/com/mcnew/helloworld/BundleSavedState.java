package com.mcnew.helloworld;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by Administrator on 29/8/2016.
 */

public class BundleSavedState extends View.BaseSavedState {


    private Bundle bundle;

    public Bundle getbundle() {
        return bundle;
    }

    public void setBundle (Bundle blue) {
        this.bundle = blue;
    }

    public BundleSavedState(Parcel source) {
        super(source);
        // Read back
        bundle = source.readBundle();

    }


    public BundleSavedState(Parcelable superState) {
        super(superState);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        // Write var here
        out.writeBundle(bundle);
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new BundleSavedState(source);
        }

        @Override
        public Object[] newArray(int i) {
            return new BundleSavedState[i];
        }
    };
}
