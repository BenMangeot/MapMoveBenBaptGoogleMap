package com.example.benjamin.mapmove.Instance;

/**
 * Created by Benjamin on 04/04/2017.
 */

public class Post {

    public Post(){

    }

    public Post(String nameEvent, String descriptionEvent, String adress, String uriEvent) {
        this.nameEvent = nameEvent;
        this.descriptionEvent = descriptionEvent;
        this.adress = adress;
        this.uriEvent = uriEvent;
    }

    private String nameEvent;
    private String descriptionEvent;
    private String adress;
    private String uriEvent;

    public String getUriEvent() {
        return uriEvent;
    }

    public void setUriEvent(String uriEvent) {
        this.uriEvent = uriEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
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





}
