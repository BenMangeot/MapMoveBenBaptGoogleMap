package com.example.benjamin.mapmove.Instance;

import android.net.Uri;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;

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
    public String type;

    public String getDescriptionEvent() {
        return descriptionEvent;
    }

    public void setDescriptionEvent(String descriptionEvent) {
        this.descriptionEvent = descriptionEvent;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLg(Double lg) {
        this.lg = lg;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
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

    public Event(){ // Obligatoire pr firebase même si grisé/non-utilisé
    }

    public Event(Double lat, Double lg, String nameEvent, String descriptionEvent, String adress, String type){
        this.lat = lat;
        this.lg = lg;
        this.nameEvent=nameEvent;
        this.descriptionEvent=descriptionEvent;
        this.adress = adress;
        this.type= type;
    }

    public Event(String nameEvent, String descriptionEvent, String adress, String uriEvent) {
        this.nameEvent = nameEvent;
        this.descriptionEvent = descriptionEvent;
        this.adress = adress;
        this.uriEvent = uriEvent;
    }

    public String getUriEvent() {
        return uriEvent;
    }

    public void setUriEvent(String uriEvent) {
        this.uriEvent = uriEvent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getCouleurMarker(){
        float couleur=HUE_RED;
        switch (this.type){
            case "soirée":
                couleur=HUE_AZURE;
                break;
            case "theatre":
                couleur=HUE_GREEN;
                break;
            case "musée":
                couleur=HUE_RED;
                break;
            case "concert":
                couleur=HUE_YELLOW;
                break;
        }

        return couleur;
    }
}
