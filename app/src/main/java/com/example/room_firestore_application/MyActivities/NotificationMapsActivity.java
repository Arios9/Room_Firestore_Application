package com.example.room_firestore_application.MyActivities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.MyFragments.ListFragments.MatchFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarkers(MatchFragment.GeoPointArrayList);
    }


    private void addMarkers(ArrayList<GeoPoint> geoPointArrayList) {
        for (GeoPoint geoPoint : geoPointArrayList) {
            LatLng latLng = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng));
        }
    }
}