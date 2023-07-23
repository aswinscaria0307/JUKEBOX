package com.niit.Bean;

public class Playlist {
    private int playlistid;
    private String playlistname;

    public Playlist(int playlistid, String playlistname) {
        this.playlistid = playlistid;
        this.playlistname = playlistname;
    }

    public Playlist() {
    }

    public int getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(int playlistid) {
        this.playlistid = playlistid;
    }

    public String getPlaylistname() {
        return playlistname;
    }

    public void setPlaylistname(String playlistname) {
        this.playlistname = playlistname;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistid=" + playlistid +
                ", playlistname='" + playlistname + '\'' +
                '}';
    }
}
