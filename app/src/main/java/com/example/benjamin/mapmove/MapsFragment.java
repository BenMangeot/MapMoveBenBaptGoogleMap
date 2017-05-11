package com.example.benjamin.mapmove;

/**
 * Created by Benjamin on 24/04/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.MapsHelper.MyInfoWindowMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MapsFragment extends Fragment implements OnMapReadyCallback{


    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    private DatabaseReference mDatabase;


    public MapsFragment() {
        // Constructeur vide indispensable
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        // Initiation de la position de base du centrage de la maps
        LatLng posEvent =  new LatLng(50.629728, 3.043672);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posEvent, 10));

        mGoogleMap.setMyLocationEnabled(true);

        final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        /* MISE EN PLACE + CONFIG DES MARQUEURS */
        mDatabase.child("events").addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot){
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    /* On crée un marqueur par event */

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat hours = new SimpleDateFormat("HH:mm");


                    long cdate = System.currentTimeMillis(); // on récupère l'heure actuel
                    String dataHour = hours.format(cdate); //On la transforme en String


                    Date h = null;
                    try {
                        h = hours.parse(dataHour);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                    String dataString = sdf.format(cdate);
                    dataString = dataString.intern();

                    Event event = postSnapshot.getValue(Event.class);
                    String datac = event.getDate().intern();

                    String debut = event.getDebut().intern();

                 ;
                    Date  d = null;
                    try {
                        d = hours.parse(debut);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    String fin = event.getFin().intern();

                    Date f = null;
                    try {
                        f = hours.parse(fin);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    System.out.println(dataHour);
                    System.out.println(d);
                    System.out.println(f);
                    System.out.println(h);
                    System.out.println(h.compareTo(f));
                    System.out.println(h.compareTo(d));



                    if (datac == dataString && h.compareTo(d)>0 && h.compareTo(f)<0){


                    LatLng posEvent =  new LatLng(event.getLat(), event.getLg());
                    float colorMarker = event.getColorMarker();

                    Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                            .position(posEvent).title(event.getNameEvent())
                            .icon(BitmapDescriptorFactory.defaultMarker(colorMarker)));
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
                            Intent intent = new Intent(getActivity(), EventActivity.class);
                            intent.putExtra("my_event", eventToDetail);
                            startActivity(intent);
                            getActivity().finish();

                        }
                    });
               }

            }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });
        /* --FIN-- MISE EN PLACE + CONFIG DES MARQUEURS */



    }
}

