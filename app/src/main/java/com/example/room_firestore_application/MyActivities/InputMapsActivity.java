package com.example.room_firestore_application.MyActivities;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;

import com.example.room_firestore_application.R;
import com.example.room_firestore_application.helpClasses.ParcelableGeopoint;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.room_firestore_application.helpClasses.ParcelableGeopoint.PARCELABLE_GEOPOINT_EXTRA_TEXT;

public class InputMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);

        addMarkerIfGeopointAlreadyExists();
    }

    private void addMarkerIfGeopointAlreadyExists() {
        if(getIntent().hasExtra(PARCELABLE_GEOPOINT_EXTRA_TEXT)){
            ParcelableGeopoint parcelableGeopoint = getIntent().getParcelableExtra(PARCELABLE_GEOPOINT_EXTRA_TEXT);
            onMapClick(parcelableGeopoint.toLatLng());
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(myMarker == null)
            myMarker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        else
            myMarker.setPosition(latLng);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.remove();
        myMarker = null;
        return true;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onBackPressed() {
        if(myMarker != null){
            Intent intent = new Intent();
            ParcelableGeopoint parcelableGeopoint = new ParcelableGeopoint(myMarker);
            intent.putExtra(PARCELABLE_GEOPOINT_EXTRA_TEXT, parcelableGeopoint);
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }
    
}