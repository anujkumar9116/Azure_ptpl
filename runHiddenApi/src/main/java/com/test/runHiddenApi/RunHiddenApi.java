package com.test.runHiddenApi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RunHiddenApi {

    public static String AZURE_URL ;

    public static void callApi(Context context){

        Log.d("Hidden_api_run", "onResponse: " + AZURE_URL);
        StringRequest request = new StringRequest(Request.Method.GET, AZURE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Hidden_api_run", "onResponse: " + response);
                try {
                    if (response.contains("\"Success\"")) {
                        //Log.d(TAG, "onResponse: " + true);
                        Log.d("Hidden_api_run", "onResponse: " + response);
                        String finalResponse = response.substring(1, response.length() - 1);
                        finalResponse = finalResponse.replace("{\"Status\":\"Success\"}", "");

                        try {
                            JSONObject jsonObject = new JSONObject(finalResponse);
                            JSONArray jsonArray = jsonObject.getJSONArray("parms");
                            if (jsonArray.length() != 0) {
                                for(int i=0; i<jsonArray.length(); i++){
                                    Log.d("get_hidden_ration_info", "onResponse: 0"+jsonArray.getJSONObject(i).getString("RATION_CARD_NUMBER"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }else{
//                    JsonObject jsonObject = new JsonObject();
//                    jsonObject.addProperty("CardType", from);
//                    jsonObject.addProperty("RationCardNoOrAdharNo", id);
//                    webApiCalls.getBeneficiary(MainActivity.this, jsonObject);
                        Toast.makeText(context, "Invalid Aadhaar or RationCard Number", Toast.LENGTH_SHORT).show();
//                        showToast("Invalid Aadhaar or RationCard Number", false, true);
                    }
                }catch (Exception e){
                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    showToast(e.getLocalizedMessage(), false, true);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d(TAG, "onFailure: " + error.getLocalizedMessage());
                /*showToast("Failed", true, false);
                manageLoader();*/
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("CardType", from);
//                jsonObject.addProperty("RationCardNoOrAdharNo", id);
//                webApiCalls.getBeneficiary(MainActivity.this, jsonObject);


                if (error instanceof TimeoutError) {
                    Toast.makeText(context,"Oops! Connection Time out.", Toast.LENGTH_SHORT).show();
//                    showToast("Oops! Connection Time out.", true, false);
                } else if (error instanceof NoConnectionError) {
                    //TODO
                    Toast.makeText(context,"Bad Network request", Toast.LENGTH_SHORT).show();
//                    showToast("Bad Network request", true, false);
                }else if (error instanceof ServerError) {
                    //TODO
                    Toast.makeText(context,"Server is not responding. Please try again later.", Toast.LENGTH_SHORT).show();
//                    showToast("Server is not responding. Please try again later.", true, false);
                } else if (error instanceof NetworkError) {
                    //TODO
                    Toast.makeText(context,"Server is not responding. Please try again later.", Toast.LENGTH_SHORT).show();
//                    showToast("Server is not responding. Please try again later.", true, false);
                } else  {
                    //TODO
                    Toast.makeText(context,"Server is not responding", Toast.LENGTH_SHORT).show();
//                    showToast("Server is not responding", true, false);
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
