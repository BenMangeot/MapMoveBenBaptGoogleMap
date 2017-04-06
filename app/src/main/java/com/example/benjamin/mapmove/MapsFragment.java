package com.example.benjamin.mapmove;

/**
 * Created by Benjamin on 06/04/2017.
 */

import android.*;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.MapsHelper.MyInfoWindowMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MapsFragment extends Fragment implements OnMapReadyCallback{


    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    private DatabaseReference mDatabase;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.first_fragment, container, false);
        return mView;


    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng posEvent =  new LatLng(50.629728, 3.043672);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posEvent, 10));


        mGoogleMap.setMyLocationEnabled(true);


        final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("events").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot){
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    /** On crée un marqueur par event */
                    Event event = postSnapshot.getValue(Event.class);
                    LatLng posEvent =  new LatLng(event.getLat(), event.getLg());
                    Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(posEvent).title(event.getNameEvent()));
                    marker.setTag(event);

                    mGoogleMap.setInfoWindowAdapter(new MyInfoWindowMarker(inflater));
                    mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            marker.showInfoWindow();

                            return true;
                        }
                    });

                    mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Event eventToDetail = (Event) marker.getTag();
                            Toast.makeText(getContext(), eventToDetail.getNameEvent(),
                                    Toast.LENGTH_LONG).show();


                        }
                    });
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
}
