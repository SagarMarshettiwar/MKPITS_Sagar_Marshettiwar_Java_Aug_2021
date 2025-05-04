package com.trustbank.Module;

import android.graphics.Bitmap;

public class ImageCatalogueModel {
    private String cataname;
    private String catasubname;
    private Bitmap cataicon;
    private String catadata;


    public ImageCatalogueModel() {
    }

    public String getCataname() {
        return cataname;
    }

    public void setCataname(String cataname) {
        this.cataname = cataname;
    }

    public String getCatasubname() {
        return catasubname;
    }

    public void setCatasubname(String catasubname) {
        this.catasubname = catasubname;
    }

    public Bitmap getCataicon() {
        return cataicon;
    }

    public void setCataicon(Bitmap cataicon) {
        this.cataicon = cataicon;
    }

    public String getCatadata() {
        return catadata;
    }

    public void setCatadata(String catadata) {
        this.catadata = catadata;
    }
}
