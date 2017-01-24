package com.example.benjamin.mapmove;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LestavelBaptiste on 23/01/2017.
 */

public class RegisterRequest extends StringRequest {

    private static final  String REGISTER_REQUEST_URL = "http://mapmove.esy.es/Register.php";
    private Map<String, String> params;

    public RegisterRequest (String pseudo, String mail, String password, Response.Listener<String> listener){
        super(Method.POST , REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("pseudo", pseudo);
        params.put("password", password);
        params.put("mail", mail);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}