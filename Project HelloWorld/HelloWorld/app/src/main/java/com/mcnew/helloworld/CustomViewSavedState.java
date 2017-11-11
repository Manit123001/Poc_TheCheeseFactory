package com.mcnew.helloworld;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by Administrator on 29/8/2016.
 */

public class CustomViewSavedState extends View.BaseSavedState {

    private boolean blue;

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
        // Write var here
        out.writeInt(blue ? 1 : 0);
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel parcel) {
            return new CustomViewSavedState(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new CustomViewSavedState[i];
        }
    };
}
