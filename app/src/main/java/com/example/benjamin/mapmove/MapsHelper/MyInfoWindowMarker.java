package com.example.benjamin.mapmove.MapsHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.benjamin.mapmove.Instance.Event;
import com.example.benjamin.mapmove.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by LestavelBaptiste on 24/02/2017.
 */

public class MyInfoWindowMarker implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;
    LayoutInflater mInflater;

    public MyInfoWindowMarker(LayoutInflater mInflater){
        myContentsView = mInflater.inflate(R.layout.infoviewevent, null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        /** On récupere l'event associé au marker et on associe ces valeurs aux textvieux */

        Event event = (Event) marker.getTag();
        TextView tvTitle=(TextView)myContentsView.findViewById(R.id.titleEvent);
        TextView tvDescription=(TextView)myContentsView.findViewById(R.id.coucou);
        tvTitle.setText(event.getNameEvent());
        tvDescription.setText(event.getDescriptionEvent());

        return(myContentsView);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

}
