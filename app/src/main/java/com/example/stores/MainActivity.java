package com.example.stores;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private SearchView searchView;
    private ArrayList<Store> stores = new ArrayList<>();
    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setTitle("Stores");
        setSupportActionBar(toolbar);

        // get the reference of RecyclerView
        recyclerView = findViewById(R.id.recycler_view);

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        //call the constructor of StoreAdapter to send the reference and data to Adapter
        storeAdapter = new StoreAdapter(MainActivity.this,stores);
        recyclerView.setAdapter(storeAdapter); // set the Adapter to RecyclerView

        new GetStoresData().execute();
    }

    private class GetStoresData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();
            // Request URL
            String url = "http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json";

            String jsonStr = null;

            try {
                jsonStr = httpHandler.makeServiceCall(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }


            if (jsonStr != null) {
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
                } catch (final JSONException e) {
                    Log.e(LOG_TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(LOG_TAG, "Error, fail to retrieve stores data from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Error, fail to retrieve stores data from server",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            storeAdapter.notifyDataSetChanged();
            searchField();
        }
    }

    public void searchField(){

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                    return false;
                }
            @Override
            public boolean onQueryTextChange(String s) {
                storeAdapter.getFilter().filter(s);
                    return true;
            }
        });
    }
}
