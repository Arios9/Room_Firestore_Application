package com.example.room_firestore_application.helpClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class GeoPointArrayList extends ArrayList<GeoPoint> implements Parcelable {

    public GeoPointArrayList() {

    }

    public GeoPointArrayList(Parcel in) {
    }

    public static final Creator<GeoPointArrayList> CREATOR = new Creator<GeoPointArrayList>() {
        @Override
        public GeoPointArrayList createFromParcel(Parcel in) {
            return new GeoPointArrayList(in);
        }

        @Override
        public GeoPointArrayList[] newArray(int size) {
            return new GeoPointArrayList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
