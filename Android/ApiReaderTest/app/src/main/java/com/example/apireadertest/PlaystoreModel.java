package com.example.apireadertest;

import android.net.Uri;

public class PlaystoreModel {
    private String name;
    private String thumb_image;
    private Uri app_link;
    private String package_name;
    public  PlaystoreModel(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public Uri getApp_link() {
        return app_link;
    }

    public void setApp_link(Uri app_link) {
        this.app_link = app_link;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }
}
