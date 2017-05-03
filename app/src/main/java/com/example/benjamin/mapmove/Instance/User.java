package com.example.benjamin.mapmove.Instance;

/**
 * Created by Benjamin on 10/02/2017.
 */

public class User {

    private String username;
    private String email;
    private String uriUser = null;
    private Boolean boolPro;

    public User (String username, String email){
        this.username = username;
        this.email = email;
        this.boolPro=false;
        this.uriUser="https://firebasestorage.googleapis.com/v0/b/project--7669949180488281577.appspot.com/o/PhotosUser%2Flogommbon.jpg?alt=media&token=9b96b452-f67d-45f9-993a-740617e5915f";
    }


    public User (String username, String email, String uriUser){
        this.username = username;
        this.email = email;
        this.uriUser = uriUser;
        this.boolPro=false;
    }

    public User(){
        // Utile pr Firebase même si non utilisé
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUriUser(){ return uriUser; }

    public void setUriUser(String uriUser) {
        this.uriUser = uriUser;
    }

    public Boolean getBoolPro() {
        return boolPro;
    }

    public void setBoolPro(Boolean boolPro) {
        this.boolPro = boolPro;
    }
}