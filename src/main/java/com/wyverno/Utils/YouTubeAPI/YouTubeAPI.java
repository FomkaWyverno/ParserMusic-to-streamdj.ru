package com.wyverno.Utils.YouTubeAPI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class YouTubeAPI {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API;
    private static final String YOUTUBE_PLAYLIST_ITEM = "https://www.googleapis.com/youtube/v3/playlistItems";


    static { // Загрузка API ключа
        File fileAPI = null;
        try {
            fileAPI = new File(Objects.requireNonNull(YouTubeAPI.class.getClassLoader().getResource("YouTube-Token.txt")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String keyAPI = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileAPI)))) {
            keyAPI = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert keyAPI != null;
        API = keyAPI;
    }

    public static YouTubePlaylist getYouTubePlaylist(String urlPlaylist) throws YouTubeNotPlaylist, IOException {
        return getYouTubePlaylist(new YouTubePlaylistURL(urlPlaylist));
    }
    public static YouTubePlaylist getYouTubePlaylist(YouTubePlaylistURL ytPlaylistURL) throws YouTubeNotPlaylist, IOException {
        YouTubePlaylist youTubePlaylist = new YouTubePlaylist();
        if (ytPlaylistURL.hasKey("list")) {
            YouTubeAPI_URL urlYoutubeAPI = new YouTubeAPI_URL(YOUTUBE_PLAYLIST_ITEM); // URL API Ютуба
            urlYoutubeAPI.addArg("key",API);
            urlYoutubeAPI.addArg("playlistId",ytPlaylistURL.getArgument("list"));
            urlYoutubeAPI.addArg("part","snippet");
            urlYoutubeAPI.addArg("maxResults","50");

            try {
                JsonNode json = objectMapper.readTree(urlYoutubeAPI.getURL());

                do {
                    for (JsonNode node : json.findParents("videoId")) {
                        YouTubeVideo youTubeVideo = objectMapper.readValue(node.toString(), YouTubeVideo.class);
                        youTubePlaylist.addVideo(youTubeVideo);
                    }
                    if (json.has("nextPageToken")) {
                        urlYoutubeAPI.addArg("pageToken", json.get("nextPageToken").textValue());
                        json = objectMapper.readTree(urlYoutubeAPI.getURL());

                    } else {
                        break;
                    }
                } while (true);
            } catch (FileNotFoundException e) {
                System.err.println("Ресурс не может быть найден! Возможно плейлист в ограниченом доступе или ошибка в ссылке.");
                return youTubePlaylist;
            }

        } else {
            throw new YouTubeNotPlaylist("This is link maybe is not Youtube Playlist -> " + ytPlaylistURL.getUrl());
        }
        return youTubePlaylist;
    }

}


