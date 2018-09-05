package com.example.stores;

/**
 * Created by joseramos on 6/8/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> implements Filterable {

    private List<Store> stores;
    private List<Store> filterStores;
    private Context context;

    // Provide a reference to the views for each data item
    // you provide access to all the views for a data item in a view holder
    public class StoreViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView  phone;
        TextView  address;

        public StoreViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            logo    = itemView.findViewById(R.id.store_logo);
            phone   = itemView.findViewById(R.id.store_phone);
            address = itemView.findViewById(R.id.store_address);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public StoreAdapter(Context context, List<Store> stores) {
        this.context      = context;
        this.stores       = stores;
        this.filterStores = stores;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        StoreViewHolder storeViewHolder = new StoreViewHolder(view); // pass the view to View Holder
        return storeViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(StoreViewHolder storeViewHolder, final int position) {
        // get element from dataset at this position
        // replace the contents of the view with that element

        Picasso.get().load(filterStores.get(position).getStoreLogoURL()).into(storeViewHolder.logo);
        storeViewHolder.phone.setText(filterStores.get(position).getPhone());
        storeViewHolder.address.setText(filterStores.get(position).getAddress());
        // implement setOnClickListener event on item view.
        storeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send store object to StoreProfile Activity
                Intent intent = new Intent(view.getContext(), StoreProfile.class);
                intent.putExtra("store", filterStores.get(position));
                (view.getContext()).startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (filterStores == null) {
            return 0;
        }
        return filterStores.size();
    }

    //Filter stores from data set
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    results.count = stores.size();
                    results.values = stores;
                } else {//do the search
                    String searchStr = constraint.toString().toUpperCase();
                    List<Store> resultsData = new ArrayList<>();
                    for (Store store : stores)
                        if (store.getName().toUpperCase().contains(searchStr)) {
                            resultsData.add(store);
                        }
                    results.count = resultsData.size();
                    results.values = resultsData;
                }
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count != 0) {
                    filterStores = (List<Store>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}