package com.shopnosoft.earthquakeresponsesystem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aryan on 7/31/16.
 */
public class UserStatusReportRequest extends StringRequest {

    private static final String Show_user_statusREQUEST_URL = "http://androidlogin.shopnosoft.com/UpdateUserStatus.php";
    // private static final String REGISTER_REQUEST_URL = "http://192.168.100.2/androidlogin/register.php";

    private Map<String, String> params;

    public UserStatusReportRequest(String shsttsmobileno, String sendstatus, Response.Listener<String> listener){

        super(Request.Method.POST, Show_user_statusREQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("mobileno",shsttsmobileno);
        params.put("status",sendstatus);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
