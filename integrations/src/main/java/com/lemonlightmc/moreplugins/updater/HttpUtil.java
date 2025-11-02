package com.lemonlightmc.moreplugins.updater;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpUtil {
    public static final Gson GSON = new Gson();

    public static HttpResponse<String> sendRequest(String uri) throws IOException, InterruptedException {
        return sendRequest(URI.create(uri), null);
    }

    public static HttpResponse<String> sendRequest(String uri, JsonElement payload)
            throws IOException, InterruptedException {
        return sendRequest(URI.create(uri), GSON.toJson(payload));
    }

    public static HttpResponse<String> sendRequest(URI uri, String payload)
            throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(uri)
                .header("User-Agent", "PluginUpdater/1.1.0");

        if (payload != null) {
            requestBuilder
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload));
        } else {
            requestBuilder
                    .GET();
        }

        return HttpClient.newHttpClient().send(
                requestBuilder.build(),
                HttpResponse.BodyHandlers.ofString());
    }
}
