package com.shopnosoft.earthquakeresponsesystem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aryan on 7/29/16.
 */
public class NoticeRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://androidlogin.shopnosoft.com/login.php";
    //private static final String LOGIN_REQUEST_URL = "http://192.168.100.2/androidlogin/login.php";
    private Map<String, String> params;

    public NoticeRequest(String date, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("date",date);


    }
    @Override
    public java.util.Map<String, String> getParams() {
        return params;
    }

}


