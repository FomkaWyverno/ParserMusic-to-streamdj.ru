package com.wyverno.Main;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyverno.Utils.ConsoleColors;
import com.wyverno.Utils.HTTP.HttpUser;
import com.wyverno.Utils.Unicode.UTF16;
import com.wyverno.Utils.YouTubeAPI.YouTubeAPI;
import com.wyverno.Utils.YouTubeAPI.YouTubePlaylist;
import com.wyverno.Utils.YouTubeAPI.YouTubeVideo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static int count = 0;

    public static void main(String[] args) throws Exception {
        HttpUser httpUser = new HttpUser("https://streamdj.ru/includes/back.php?func=add_track&channel=100377");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Введите URL-Link на плейлист -> ");
        YouTubePlaylist youTubePlaylist = YouTubeAPI.getYouTubePlaylist(reader.readLine()); // https://www.youtube.com/watch?v=uCUnphNkfro&list=PL0MRrOC9iFJidk2tllIenCDVThomnIETO&ab_channel=S1BERIAN
        reader.close();
        List<YouTubeVideo> playlist = youTubePlaylist.getPlaylist();
        int count = 0;
        for (YouTubeVideo video: playlist) {
            Map<String,String> map = new HashMap<>();
            map.put("url", video.getURL());
            map.put("author","Wyverno Family");
            String response = getResultRequest(UTF16.UTF16ToText(httpUser.requestPOST(map)));
            System.out.println(ConsoleColors.YELLOW +"#" + ++count + " Количество тректов: " + Main.count + ConsoleColors.RESET + " "  + response);
        }
        httpUser.close();
    }

    private static String getResultRequest(String json) throws JsonProcessingException {
            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(json);
                if (jsonNode.has("success")) {
                    Main.count++;
                    return ConsoleColors.GREEN_BOLD + "Успешно! Код -> " + jsonNode.get("success").toString() + ConsoleColors.RESET;
                } else if (jsonNode.has("error")) {
                    return ConsoleColors.RED + "Ошибка! Причина -> " + jsonNode.get("error").toString() + ConsoleColors.RESET;
                } else {
                    return ConsoleColors.RED_BOLD + "НЕ ОБРАБАТЫВАЕМОЕ СООБЩЕНИЕ! " + jsonNode + ConsoleColors.RESET;
                }
            } catch (JsonParseException e) {
                System.out.println(ConsoleColors.RED_BOLD + "ПОЯВИЛОСЬ НОВОЕ СООБЩЕНИЕ!" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.RED + json + ConsoleColors.RESET);

                System.out.println();
                System.out.println();

                System.err.println(e.getMessage());
            }
            return null;
    }
}
