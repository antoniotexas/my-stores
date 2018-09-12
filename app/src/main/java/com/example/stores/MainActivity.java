package com.example.stores;

import android.app.Dialog;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Store>> {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int ERROR_DIALOG_REQUEST = 9001;

    private static final int LOADER_ID = 1;

    private static final String REQUEST_URL =
            "http://sandbox.bottlerocketapps.com/BR_Android_CodingExam_2015_Server/stores.json";

    private List<Store> stores = new ArrayList<>();
    private RecyclerView recyclerView;
    private StoreAdapter storeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        stores.clear();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                actionSearch(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void actionSearch(MenuItem item){

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) item.getActionView();

        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String s) {
                storeAdapter.getFilter().filter(s);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);
    }
}
