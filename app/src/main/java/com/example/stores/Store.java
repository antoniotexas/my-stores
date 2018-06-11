package com.example.stores;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joseramos on 6/9/18.
 */

public class Store implements Parcelable {

    private String address;
    private String city;
    private String name;
    private String latitude;
    private String zipCode;
    private String logoURL;
    private String phone;
    private String longitude;
    private String storeId;
    private String state;

    public Store(String address, String city, String name, String latitude, String zipCode,
                 String logoURL, String phone, String longitude, String storeId, String state){

        this.address   = address;
        this.city      = city;
        this.name      = name;
        this.latitude  = latitude;
        this.zipCode   = zipCode;
        this.logoURL   = logoURL;
        this.phone     = phone;
        this.longitude = longitude;
        this.storeId   = storeId;
        this.state     = state;

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    public Store(Parcel in){
        this.address   = in.readString();
        this.city      = in.readString();
        this.name      = in.readString();
        this.latitude  = in.readString();
        this.zipCode   = in.readString();
        this.logoURL   = in.readString();
        this.phone     = in.readString();
        this.longitude = in.readString();
        this.storeId   = in.readString();
        this.state     = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.city);
        dest.writeString(this.name);
        dest.writeString(this.latitude);
        dest.writeString(this.zipCode);
        dest.writeString(this.logoURL);
        dest.writeString(this.phone);
        dest.writeString(this.longitude);
        dest.writeString(this.storeId);
        dest.writeString(this.state);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStoreLogoURL() {
        return logoURL;
    }

    public void setStoreLogoURL(String storeLogoURL) {
        this.logoURL = storeLogoURL;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.logoURL = phone;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
