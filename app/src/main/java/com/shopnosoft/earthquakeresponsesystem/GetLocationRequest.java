package com.shopnosoft.earthquakeresponsesystem;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aryan on 7/29/16.
 */
public class GetLocationRequest extends StringRequest {

    private static final String GET_LOCATION_REQUEST_URL = "http://androidlogin.shopnosoft.com/locationview.php";

    private Map<String, String> params;

    public GetLocationRequest(String mobileno, Response.Listener<String> listener){
        super(Method.POST, GET_LOCATION_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("mobileno",mobileno);
    }
    @Override
    public java.util.Map<String, String> getParams() {
        return params;
    }

}

