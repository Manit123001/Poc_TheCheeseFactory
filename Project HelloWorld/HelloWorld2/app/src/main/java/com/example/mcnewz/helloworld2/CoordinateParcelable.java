package com.example.mcnewz.helloworld2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 29/8/2016.
 */
// step 1 implements Parcelable
public class CoordinateParcelable implements Parcelable {
    // step 2create variable
    public int x, y, z;

    //Empty Constructor
    public CoordinateParcelable(){

    }
    // convert to object that to use
    protected CoordinateParcelable(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    public static final Creator<CoordinateParcelable> CREATOR = new Creator<CoordinateParcelable>() {
        @Override
        public CoordinateParcelable createFromParcel(Parcel in) {
            return new CoordinateParcelable(in);
        }

        @Override
        public CoordinateParcelable[] newArray(int size) {
            return new CoordinateParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(x);
        parcel.writeInt(y);
        parcel.writeInt(z);
    }
}
