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
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;

/**
 * Created by LestavelBaptiste on 09/02/2017.
 */

public class Event implements Serializable {


    public Double lat;
    public Double lg;
    public String nameEvent;
    public String uriEvent = "https://firebasestorage.googleapis.com/v0/b/project--7669949180488281577.appspot.com/o/Photos%2FnoPhoto.png?alt=media&token=da316777-bf20-451c-85c0-fdb11353adcc";
    public String descriptionEvent;
    public String adress;
    public String type;
    public String date;
    public String userPro;
    public String debut;
    public String fin;

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getUserPro() {
        return userPro;
    }

    public void setUserPro(String userPro) {
        this.userPro = userPro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



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

    public Event(){ // Obligatoir pr firebase même si grisé/non-utilisé
    }

    public Event(Double lat, Double lg, String nameEvent, String descriptionEvent, String adress, String type, String date, String userPro, String debut, String fin){
        this.lat = lat;
        this.lg = lg;
        this.nameEvent=nameEvent;
        this.descriptionEvent=descriptionEvent;
        this.adress = adress;
        this.type = type;
        this.date = date;
        this.userPro = userPro;
        this.debut = debut;
        this.fin = fin;
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

    public float getColorMarker(){
        float color=HUE_RED;
        switch (this.type) {

            case "soirée":
                color = HUE_AZURE;
                break;
            case "musée":
                color = HUE_GREEN;
                break;
            case "concert":
                color = HUE_ORANGE;
                break;
            case "theatre":
                color = HUE_YELLOW;
                break;
        }
            return color;

    }

}
