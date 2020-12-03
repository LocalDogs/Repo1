package com.example.localdogs;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int EDIT_REQUEST = 1;
    private ArrayList<MarkerOptions> markOptsList = new ArrayList<MarkerOptions>();
    private ArrayList<Marker> markers = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.mMap = map;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                Intent edit = new Intent(MapsActivity.this, EditActivity.class);
                edit.putExtra("location", latLng);
                MapsActivity.this.startActivityForResult(edit, EDIT_REQUEST);
            }
        });
        LatLng tuscaloosa = new LatLng(33.189281, -87.565155);
        markers.add(mMap.addMarker(new MarkerOptions().position(tuscaloosa).title("Tuscaloosa")));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tuscaloosa));

        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(tuscaloosa )      // Sets the center of the map to Mountain View
                .zoom(10)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        if(mMap == null){
            Log.e("Map","ERROR: Map is null after initialization");
        }
        else Log.i("Map","Map is not null, initialized successfully");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (EDIT_REQUEST) : {
                if (resultCode == Activity.RESULT_OK) {
                    MarkerOptions markerOptions = data.getParcelableExtra("marker");

                    // Set the color of the marker to green
                    BitmapDescriptor defaultMarker =
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    if(markerOptions.getTitle().contains("Park")){
                        markerOptions.icon(defaultMarker);
                    }

                    Marker m = mMap.addMarker(markerOptions);
                    Log.i("Map","adding marker: "+m.getTag());
                    markers.add(m);
                    markOptsList.add(markerOptions);
                }
                break;
            }
        }
    }

    public void searchClick(View view) {
        String searchText = ((TextView) findViewById(R.id.editTextTextPersonName)).getText().toString();
        Log.i("Maps","Searching for "+searchText+" through "+markers.size()+" markers");
        for(int i=0; i<markers.size(); i++){
            if(markers.get(i) == null) {} else {
                Log.i("Maps", "[" + i + "] Marker: " + markers.get(i).getTitle());
                if (markers.get(i).getTitle().contains(searchText)) {
                    markers.get(i).setVisible(true);
                } else {
                    markers.get(i).setVisible(false);
                }
            }
        }
        Log.i("Maps","Clicked to search, text: "+searchText);
    }
}