package pers.hu.oneradio.net.model;

import java.util.Arrays;

public class Program {
    public Song getMainSong() {
        return mainSong;
    }

    public void setMainSong(Song mainSong) {
        this.mainSong = mainSong;
    }

    public int getMainTrackId() {
        return mainTrackId;
    }

    public void setMainTrackId(int mainTrackId) {
        this.mainTrackId = mainTrackId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Song mainSong;
    private int mainTrackId;
    private String coverUrl;
    private String name;

}
