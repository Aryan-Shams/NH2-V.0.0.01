package com.shopnosoft.earthquakeresponsesystem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aryan on 7/24/16.
 */
public class LoginRequest_003 extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://androidlogin.shopnosoft.com/login.php";
    //private static final String LOGIN_REQUEST_URL = "http://192.168.100.2/androidlogin/login.php";
    private Map<String, String> params;

    public LoginRequest_003(String username, String password, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);


    }
    @Override
    public java.util.Map<String, String> getParams() {
        return params;
    }

}


