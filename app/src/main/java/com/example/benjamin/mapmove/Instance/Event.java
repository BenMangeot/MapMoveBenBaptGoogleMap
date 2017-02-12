package com.example.benjamin.mapmove.Instance;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by LestavelBaptiste on 09/02/2017.
 */

public class Event {

    public Double lat;
    public Double lg;

    public Double getLat() {
        return lat;
    }

    public Double getLg() {
        return lg;
    }

    public Event(){

    }

    public Event(Double lat, Double lg){
        this.lat = lat;
        this.lg = lg;
    }
}
