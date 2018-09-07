package com.example.stores;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by joseramos on 9/4/18.
 */

public class StoreLoader extends AsyncTaskLoader<List<Store>> {

    private static final String LOG_TAG = StoreLoader.class.getName();

    private String mUrl;

    public StoreLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Store> loadInBackground() {

        if(mUrl == null){
            return null;
        }

        List<Store> stores = QueryData.fetchData(mUrl);

        return stores;
    }
}



//rosa fuentes



