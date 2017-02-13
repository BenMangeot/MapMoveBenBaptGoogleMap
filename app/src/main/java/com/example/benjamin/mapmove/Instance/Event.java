package com.example.benjamin.mapmove.Instance;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;

/**
 * Created by LestavelBaptiste on 09/02/2017.
 */

public class Event {

    public Double lat;
    public Double lg;
    public String nameEvent;

    public Double getLat() {
        return lat;
    }

    public Double getLg() {
        return lg;
    }

    public String getNameEvent() {
        return nameEvent;
    }


    public Event(){

    }

    public Event(Double lat, Double lg, String nameEvent){
        this.lat = lat;
        this.lg = lg;
        this.nameEvent=nameEvent;
    }

}
