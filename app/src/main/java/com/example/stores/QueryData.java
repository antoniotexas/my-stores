package com.example.stores;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joseramos on 9/5/18.
 */

public class QueryData {

    private static final String LOG_TAG = QueryData.class.getSimpleName();

    //Default constructor
    public QueryData(){
    }

    public static List<Store> fetchData(String requestUrl){

        HttpHandler http = new HttpHandler();
        String response = null;

        try{

           response =  http.makeHttpRequest(requestUrl);

        }catch (IOException e){

            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }

        List<Store> stores = queryJson(response);

        return stores;
    }

    public static List<Store> queryJson(String jsonStr){

        if(TextUtils.isEmpty(jsonStr)){
            return null;
        }

        List<Store> stores = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            //Fetch JSONArray stores
            JSONArray storeArray = jsonObj.getJSONArray("stores");
            //stores data
            for (int i = 0; i < storeArray.length(); i++) {

                JSONObject storeInfo = storeArray.getJSONObject(i);

                String address = storeInfo.getString("address");
                String city = storeInfo.getString("city");
                String name = storeInfo.getString("name");
                String latitude = storeInfo.getString("latitude");
                String zipCode = storeInfo.getString("zipcode");
                String storeLogo = storeInfo.getString("storeLogoURL");
                String phone = storeInfo.getString("phone");
                String longitude = storeInfo.getString("longitude");
                String storeId = storeInfo.getString("storeID");
                String state = storeInfo.getString("state");

                Store store = new Store(address,city,name,latitude,zipCode,storeLogo,phone,longitude,storeId,state);

                stores.add(store);

            }
        } catch (JSONException e) {
                Log.e(LOG_TAG, "Json parsing error: " + e.getMessage());
            }

       // Log.d("Store list size", Integer.toString(stores.size()));
        return stores;
    }
}
