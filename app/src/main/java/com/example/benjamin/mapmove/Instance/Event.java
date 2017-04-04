package com.example.benjamin.mapmove.Instance;

import android.net.Uri;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

/**
 * Created by LestavelBaptiste on 09/02/2017.
 */

public class Event implements Serializable {

    public Double lat;
    public Double lg;
    public String nameEvent;
    public String uriEvent = null;
    public String descriptionEvent;
    public String adress;

    public String getDescriptionEvent() {
        return descriptionEvent;
    }

    public void setDescriptionEvent(String descriptionEvent) {
        this.descriptionEvent = descriptionEvent;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLg() {
        return lg;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public Event(){ // Obligatoir pr firebase même si grisé/non-utilisé
    }

    public Event(Double lat, Double lg, String nameEvent, String descriptionEvent, String adress){
        this.lat = lat;
        this.lg = lg;
        this.nameEvent=nameEvent;
        this.descriptionEvent=descriptionEvent;
        this.adress = adress;
    }

    public String getUriEvent() {
        return uriEvent;
    }

    public void setUriEvent(String uriEvent) {
        this.uriEvent = uriEvent;
    }
}
