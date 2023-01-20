package com.test.runHiddenApi;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Developed by Sunil kumar 12-05-2022
 */
public class AzureApplication extends Application {
    static {
        System.loadLibrary("keys");
    }
    public native String getProUrl();
    public native String getX();

    private static final String TAG = "HpApplication";

    private String AZURE_URL;
    public static boolean azureStatus;

    @Override
    public void onCreate() {
        super.onCreate();
        AZURE_URL=EncryptionDecryption.decryptUid(getProUrl(),getX());
        buildAzureConnection(getApplicationContext());
    }


    private void buildAzureConnection(Context context) {
        StringRequest request = new StringRequest(Request.Method.GET, AZURE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String key = "AsureStatus";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("AzureStatus")){
                        key = "AzureStatus";
                    }
                    if(jsonObject.has("AsureStatus")){
                        key = "AsureStatus";
                    }
                    if (jsonObject.getString(key).equals("1")) {
                        azureStatus = true;
                    } else {
                        azureStatus = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    azureStatus = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                azureStatus = false;
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
