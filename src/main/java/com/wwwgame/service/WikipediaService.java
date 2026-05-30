package com.wwwgame.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WikipediaService {

    private static final Logger log = LoggerFactory.getLogger(WikipediaService.class);
    private final OkHttpClient client = new OkHttpClient();

    public String getSummary(String topic) {
        if (topic == null || topic.isBlank()) {
            return null;
        }

        String encoded = URLEncoder.encode(topic.trim(), StandardCharsets.UTF_8)
                .replace("+", "_");
        String url = "https://ru.wikipedia.org/api/rest_v1/page/summary/" + encoded;

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "me")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return null;
            }
            String body = response.body().string();
            JSONObject json = new JSONObject(body);
            String extract = json.optString("extract", null);
            if (extract == null || extract.isBlank()) {
                return null;
            }
            return extract.length() > 300 ? extract.substring(0, 300) + "…" : extract;
        } catch (Exception e) {
            log.error("Wikipedia error for topic '{}': {}", topic, e.getMessage());
            return null;
        }
    }
}
