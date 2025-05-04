package com.example.sqlitedatainrecycleview;

public class DashDatapojo {
    private String title;
    private int imgid;

    public DashDatapojo(String title, int imgid) {
        this.setTitle(title);
        this.setImgid(imgid);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
