package com.example.room_firestore_application.helpClasses;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.firestore.GeoPoint;

public class ParcelableGeopoint implements Parcelable {

    public static final String PARCELABLE_GEOPOINT_EXTRA_TEXT = "ParcelableGeopoint";

    private double latitude;
    private double longitude;

    public ParcelableGeopoint(GeoPoint geoPoint){
        this.latitude = geoPoint.getLatitude();
        this.longitude = geoPoint.getLongitude();
    }

    public ParcelableGeopoint(Marker marker) {
        this.latitude = marker.getPosition().latitude;
        this.longitude = marker.getPosition().longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    protected ParcelableGeopoint(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<ParcelableGeopoint> CREATOR = new Creator<ParcelableGeopoint>() {
        @Override
        public ParcelableGeopoint createFromParcel(Parcel in) {
            return new ParcelableGeopoint(in);
        }

        @Override
        public ParcelableGeopoint[] newArray(int size) {
            return new ParcelableGeopoint[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public LatLng toLatLng() {
        return new LatLng(latitude, longitude);
    }

    public GeoPoint toGeoPoint() {
        return new GeoPoint(latitude, longitude);
    }
}
