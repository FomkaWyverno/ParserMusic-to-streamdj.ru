package com.wyverno.Utils.YouTubeAPI;

import java.util.HashMap;
import java.util.Map;

public class YouTubePlaylistURL {
    private final String url;
    private final Map<String,String> arguments;

    public YouTubePlaylistURL(String url) {
        this.url = url;
        this.arguments = new HashMap<>();
        createArguments();
    }

    private void createArguments() {
        StringBuilder sb = new StringBuilder(this.url);
        sb.delete(0,sb.indexOf("?")+1);
        String[] args = sb.toString().split("&");
        for (String s: args) {
            String[] pair = s.split("=");
            arguments.put(pair[0],pair[1]);
        }
    }

    public boolean hasKey(String key) {
        return arguments.containsKey(key);
    }
    public String getArgument(String key) {
        return arguments.get(key);
    }
    public String getUrl() {
        return url;
    }
}
