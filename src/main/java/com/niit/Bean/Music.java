package com.niit.Bean;

import java.sql.Time;

public class Music {
    private int songId;
    private String songName;
    private String songSource;
    private String artistName;
    private String genreName;
    private Time songDuration;

    public Music(int songId, String songName, String songSource, String artistName, String genreName, Time songDuration) {
        this.songId = songId;
        this.songName = songName;
        this.songSource = songSource;
        this.artistName = artistName;
        this.genreName = genreName;
        this.songDuration = songDuration;
    }

    public Music() {
    }

    public Music(int songId, Object o, Object o1) {
    }

    public Music(int songId, String songName, String songSource, String artistName, String genreName, String duration) {

    }


    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongSource() {
        return songSource;
    }

    public void setSongSource(String songSource) {
        this.songSource = songSource;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Time getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(Time songDuration) {
        this.songDuration = songDuration;
    }

    @Override
    public String toString() {
        return "Music{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", songSource='" + songSource + '\'' +
                ", artistName='" + artistName + '\'' +
                ", genreName='" + genreName + '\'' +
                ", songDuration='" + songDuration + '\'' +
                '}';
    }
}
