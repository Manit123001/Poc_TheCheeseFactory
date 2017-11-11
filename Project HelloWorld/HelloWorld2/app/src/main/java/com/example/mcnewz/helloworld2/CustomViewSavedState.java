package com.example.mcnewz.helloworld2;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by Administrator on 29/8/2016.
 */

public class CustomViewSavedState extends View.BaseSavedState {

    private boolean blue;

    // generate Geter setter
    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public CustomViewSavedState(Parcel source) {
        super(source);
        // Read back
        blue = source.readInt() == 1;
    }

    public CustomViewSavedState(Parcelable superState) {
        super(superState);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        // Write var Here
        out.writeInt(blue? 1 : 0);
    }

    public static final Creator CREATE = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new CustomViewSavedState(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new CustomViewSavedState[size];
        }
    };
}

