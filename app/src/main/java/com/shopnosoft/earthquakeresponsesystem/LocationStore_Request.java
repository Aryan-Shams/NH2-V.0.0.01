package com.shopnosoft.earthquakeresponsesystem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aryan on 7/29/16.
 */
public class LocationStore_Request extends StringRequest {

    private static final String Store_Location_REQUEST_URL = "http://androidlogin.shopnosoft.com/storelocation.php";
    // private static final String REGISTER_REQUEST_URL = "http://192.168.100.2/androidlogin/register.php";

    private Map<String, String> params;

    public LocationStore_Request (String mobileno, String lattitude, String longititude, String address, Response.Listener<String> listener){

        super(Request.Method.POST, Store_Location_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("mobileno",mobileno);
        params.put("lattitude",lattitude);
        params.put("longititude",longititude);
        params.put("address",address);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
