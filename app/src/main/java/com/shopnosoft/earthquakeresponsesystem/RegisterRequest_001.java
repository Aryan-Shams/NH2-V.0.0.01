package com.shopnosoft.earthquakeresponsesystem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aryan on 7/15/16.
 */
public class RegisterRequest_001 extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://androidlogin.shopnosoft.com/register.php";
    // private static final String REGISTER_REQUEST_URL = "http://192.168.100.2/androidlogin/register.php";

    private Map<String, String> params;

    public RegisterRequest_001 (String name, String email, String username, String password, String mobileno, Response.Listener<String> listener){

        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("name",name);
        params.put("email",email);
        params.put("username",username);
        params.put("password",password);
        params.put("mobileno",mobileno);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
