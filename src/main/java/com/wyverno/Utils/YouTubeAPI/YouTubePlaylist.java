package com.wyverno.Utils.YouTubeAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YouTubePlaylist {
    private final List<YouTubeVideo> playlist;

    public YouTubePlaylist() {
        this.playlist = new ArrayList<>();
    }

    public void addVideo(YouTubeVideo youTubeVideo) {
        this.playlist.add(youTubeVideo);
    }
    public void removeVideo(YouTubeVideo youTubeVideo) {
        this.playlist.remove(youTubeVideo);
    }

    public List<YouTubeVideo> getPlaylist() {
        return this.playlist;
    }
}
