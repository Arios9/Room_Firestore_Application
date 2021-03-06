package com.example.room_firestore_application.helpClasses;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.firestore.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GeoPointArrayList extends ArrayList<GeoPoint> {

    // saves today's matches that have geopoint in firebase
    public static GeoPointArrayList TodaysGeoPointArrayList;

    // date must be today and geopoint must exist to add it in the list
    public void checkDateAndGeopoint(String date, GeoPoint geoPoint) {
        String TodayDate = getTodayFormatString();
        if(TodayDate.equals(date) && geoPoint!=null)
            add(geoPoint);
    }

    public void checkForNotification(Activity mainContext) {
        if(!isEmpty())
        new MyNotification(mainContext);
    }

    private String getTodayFormatString() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String string = simpleDateFormat.format(calendar.getTime());
        return string;
    }

}
