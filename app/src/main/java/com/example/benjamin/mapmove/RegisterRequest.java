package com.example.benjamin.mapmove;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Benjamin on 20/01/2017.
 */

public class RegisterRequest extends StringRequest{

    private static final  String REGISTER_REQUEST_URL = "";
    private Map<String, String> params;

    public RegisterRequest (String name, String mail, String password, Response.Listener<String> listener){
        super(Method.POST , REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("password", password);
        params.put("mail", mail);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

