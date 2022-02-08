package com.wyverno.Utils.YouTubeAPI;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class YouTubeAPI_URL {
    private final String url;
    private final Map<String,String> arguments;

    public YouTubeAPI_URL(String url) {
        this.url = url;
        this.arguments = new HashMap<>();
    }
    public void addArg(String key, String value) {
        arguments.put(key,value);
    }
    public boolean removeArg(String key) {
        if (arguments.containsKey(key)) {
            arguments.remove(key);
            return true;
        } else {
            return false;
        }
    }
    public URL getURL() throws MalformedURLException {
        StringBuilder sb = new StringBuilder(url);

        if (!arguments.isEmpty()) {
            sb.append("?");
            boolean needAmpersand = false;
            for (Map.Entry<String,String> pair: arguments.entrySet()) {
                if (needAmpersand) {
                    sb.append("&").append(pair.getKey()).append("=").append(pair.getValue());
                } else {
                    needAmpersand = true;
                    sb.append(pair.getKey()).append("=").append(pair.getValue());
                }
            }
        }
        return new URL(sb.toString());
    }
}
class YouTubeNotPlaylist extends Exception {
    public YouTubeNotPlaylist() {
        super();
    }

    public YouTubeNotPlaylist(String message) {
        super(message);
    }

    public YouTubeNotPlaylist(String message, Throwable cause) {
        super(message, cause);
    }

    public YouTubeNotPlaylist(Throwable cause) {
        super(cause);
    }

    protected YouTubeNotPlaylist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
