package com.example.alfreq;

import org.w3c.dom.ls.LSOutput;

public class Channel {
    private String id;
    private String url;
    private String title;
    private String artist;
    private String artwork;


    public Channel(String id, String url, String title, String artist, String artwork) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.artist = artist;
        this.artwork = artwork;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public String toString() {
     return title.toString();
    }

}





