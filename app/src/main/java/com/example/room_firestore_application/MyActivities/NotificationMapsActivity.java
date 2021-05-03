package com.example.room_firestore_application.MyActivities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.helpClasses.GeoPointArrayList;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import static com.example.room_firestore_application.helpClasses.GeoPointArrayList.TodaysGeoPointArrayList;

public class NotificationMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarkers(TodaysGeoPointArrayList);
    }

    private void addMarkers(GeoPointArrayList geoPointArrayList) {
        for (GeoPoint geoPoint : geoPointArrayList) {
            LatLng latLng = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng));
        }
    }

}