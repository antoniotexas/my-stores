package com.example.stores;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Store>> {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int LOADER_ID = 1;

    private static final String REQUEST_URL =
            "http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json";

    private Toolbar toolbar;
    private SearchView searchView;
    private List<Store> stores = new ArrayList<>();
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

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID,null,this);
        }
    }


    @Override
    public Loader<List<Store>> onCreateLoader(int id, Bundle args) {
        return new StoreLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Store>> loader, List<Store> data) {

        if (data != null && !data.isEmpty()) {
            stores.addAll(data);
            storeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Store>> loader) {
        stores.clear();
        storeAdapter.notifyDataSetChanged();
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
