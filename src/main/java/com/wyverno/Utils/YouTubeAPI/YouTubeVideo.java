package com.wyverno.Utils.YouTubeAPI;

public class YouTubeVideo {
    private String kind;
    private String videoId;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getURL() {
        return "https://www.youtube.com/watch?v=" + videoId;
    }
}
