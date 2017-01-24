package com.example.benjamin.mapmove;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LestavelBaptiste on 23/01/2017.
 */


public class LoginRequest extends StringRequest{
    private static final  String LOGIN_REQUEST_URL = "http://mapmove.esy.es/Login.php";
    private Map<String, String> params;

    public LoginRequest (String pseudo, String password, Response.Listener<String> listener){
        super(Request.Method.POST , LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("pseudo", pseudo);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
