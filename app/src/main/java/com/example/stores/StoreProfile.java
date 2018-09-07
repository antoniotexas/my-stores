package com.example.stores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class StoreProfile extends AppCompatActivity {

    private ImageView storeLogo;
    private TextView storeAddress;
    private TextView storePhone;
    private TextView storeId;
    private TextView storeLongitude;
    private TextView storeLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_profile);

        storeLogo    = findViewById(R.id.logo);
        storeAddress = findViewById(R.id.store_address);
        storePhone   = findViewById(R.id.store_phone);
        storeId      = findViewById(R.id.store_id);
        storeLongitude = findViewById(R.id.store_longitude);
        storeLatitude  = findViewById(R.id.store_latitude);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Store store = extras.getParcelable("store");

            setTitle(store.getName());// Toolbar title

            String address = store.getAddress() +"\n"+ store.getCity() +", "+ store.getState()
                    +" "+store.getZipCode();

            Picasso.get().load(store.getStoreLogoURL()).into(storeLogo);
            storeAddress.setText(address);
            storePhone.setText(store.getPhone());
            storeId.setText(store.getStoreId());
            storeLongitude.setText(store.getLongitude());
            storeLatitude.setText(store.getLatitude());

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
