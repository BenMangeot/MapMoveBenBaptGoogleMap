package com.example.benjamin.mapmove.Instance;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by LestavelBaptiste on 12/02/2017.
 */

public class MarkerFunction {
    private static MarkerFunction instance;

    public static MarkerFunction getInstance() {
        if (instance == null) {
            instance = new MarkerFunction();
        }
        return instance;
    }



    private MarkerFunction() {
    }

    public void afficherMarker(GoogleMap mMap, Event event){
        LatLng posEvent =  new LatLng(event.getLat(), event.getLg());
        mMap.addMarker(new MarkerOptions().position(posEvent).title(event.getNameEvent()));

    }
}
