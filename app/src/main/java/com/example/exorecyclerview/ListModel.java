package com.example.exorecyclerview;

public class ListModel {

    private String title;
    private String media_url;
    private String description;

    public ListModel(String title, String media_url, String description) {
        this.title = title;
        this.media_url = media_url;
        this.description = description;
    }

    public ListModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
